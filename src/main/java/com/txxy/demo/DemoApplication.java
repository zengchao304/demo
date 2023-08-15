package com.txxy.demo;

import com.txxy.demo.attendance.Attendance;
import com.txxy.demo.configure.ChromeDriverConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    private static final Integer DEFAULT_TIMEOUT = 5000;


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean(name = "instance1")
    public Attendance attendance(){
        return new Attendance();
    }

    @Bean(name = "instance1")
    public ChromeDriverConfig chromeDriverConfig(){
        return new ChromeDriverConfig();
    }

    /**
     * restTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("utf-8")));
//        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("gb2312")));
        restTemplate.setErrorHandler(noOpResponseErrorHandler());
        return restTemplate;
    }

    /**
     * 不对response错误处理 ErrorHandler
     */
    private ResponseErrorHandler noOpResponseErrorHandler() {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus statusCode = response.getStatusCode();
                return statusCode.series() == HttpStatus.Series.CLIENT_ERROR
                        || statusCode.series() == HttpStatus.Series.SERVER_ERROR;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                log.error("noOpResponseErrorHandler execute error, status code:{}",
                        response.getStatusCode().toString());
            }
        };
    }

    private RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();

            clientBuilder.setSSLContext(sslContext);
            // 开启重试3次
            clientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
                    .build();

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
            // 连接池总数
            connectionManager.setMaxTotal(200);
            // 共用池子发送的请求对不同的域名的并发数
            connectionManager.setDefaultMaxPerRoute(20);

            clientBuilder.setConnectionManager(connectionManager);

            clientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
            clientBuilder.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    return 30 * 1000;
                }
            });

            CloseableHttpClient httpClient = clientBuilder.build();
            requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

            requestFactory.setReadTimeout(DEFAULT_TIMEOUT);
            requestFactory.setConnectTimeout(DEFAULT_TIMEOUT);
            requestFactory.setConnectionRequestTimeout(DEFAULT_TIMEOUT);
        } catch (Exception e) {
            log.error("{}|build requestFactory error!", "BUSSINESS", e);
        }

        return new RestTemplate(requestFactory);
    }



}
