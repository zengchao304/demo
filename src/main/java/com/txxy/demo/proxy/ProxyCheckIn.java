package com.txxy.demo.proxy;

import com.txxy.demo.domain.CheckInResponseDO;
import com.txxy.demo.util.JacksonConverter;
import com.txxy.demo.util.StringUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author zengch
 * @Date 2023-06-16
 **/
@Component
public class ProxyCheckIn {


    @Resource
    private RestTemplate restTemplate;

    public Map checkIn() {
        String loginUrl = "https://glados.rocks/api/user/checkin";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        headers.set("Cookie", "b-user-id=ae013456-662b-209d-8757-2fa058728b56; koa:sess=eyJ1c2VySWQiOjE4NTYwOCwiX2V4cGlyZSI6MTcxNjYwNDEzMjUwMSwiX21heEFnZSI6MjU5MjAwMDAwMDB9; koa:sess.sig=wDokGNmvkEoiCU7055QpVb1rpuI");
        headers.setContentType(MediaType.APPLICATION_JSON);


        HashMap<String, String> bodyMap = new HashMap<>(16);
        bodyMap.put("token", "glados.one");

        try {
            HttpEntity httpEntity = new HttpEntity(bodyMap, headers);
            ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, httpEntity,
                    String.class);
            if (isSuccess(response)) {

                String body = response.getBody();

                if (StringUtil.isEmpty(body)) {
                    //todo 发邮件提醒
                }

                CheckInResponseDO checkInResponse = JacksonConverter.read(body, CheckInResponseDO.class);

                if (Objects.nonNull(checkInResponse)) {
                    Integer code = checkInResponse.getCode();
                    String message = checkInResponse.getMessage();
                    if ("0".equals(code) && "Checkin! Get 1 Day".equals(message)) {

                    }
                }

                System.out.println("result============ " + body);

                return JacksonConverter.read(body, Map.class);
            }

        } catch (Exception e) {
            //timeout exception
            return checkIn();
        }
        HashMap<String, String> resultMap = new HashMap<>(16);
        resultMap.put("result", "签到异常！");
        return resultMap;
    }

    public Map checkInFor88FoxMail() {
        String loginUrl = "https://glados.rocks/api/user/checkin";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        headers.set("Cookie", "koa:sess=eyJ1c2VySWQiOjIzMjczNywiX2V4cGlyZSI6MTcxNjczNTE3NTA4MCwiX21heEFnZSI6MjU5MjAwMDAwMDB9; koa:sess.sig=l6gmUqAUYRG_AJLQ__h8_5peLGk");
        headers.setContentType(MediaType.APPLICATION_JSON);


        HashMap<String, String> bodyMap = new HashMap<>(16);
        bodyMap.put("token", "glados.one");

        try {
            HttpEntity httpEntity = new HttpEntity(bodyMap, headers);
            ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, httpEntity,
                    String.class);
            if (isSuccess(response)) {

                String body = response.getBody();

                if (StringUtil.isEmpty(body)) {
                    //todo 发邮件提醒
                }

                CheckInResponseDO checkInResponse = JacksonConverter.read(body, CheckInResponseDO.class);

                if (Objects.nonNull(checkInResponse)) {
                    Integer code = checkInResponse.getCode();
                    String message = checkInResponse.getMessage();
                    if ("0".equals(code) && "Checkin! Get 1 Day".equals(message)) {

                    }
                }

                System.out.println("result============ " + body);

                return JacksonConverter.read(body, Map.class);
            }

        } catch (Exception e) {
            //timeout exception
            return checkInFor88FoxMail();
        }
        HashMap<String, String> resultMap = new HashMap<>(16);
        resultMap.put("result", "签到异常！");
        return resultMap;
    }

    /**
     * 判断response是否正常
     *
     * @param responseEntity
     * @return
     */
    public boolean isSuccess(ResponseEntity<?> responseEntity) {
        return Objects.nonNull(responseEntity) && responseEntity.getStatusCode().is2xxSuccessful();
    }

}
