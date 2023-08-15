//package com.txxy.demo.schedule;
//
//import com.txxy.demo.configure.ChromeDriverConfig;
//import com.txxy.demo.service.CaptchaService;
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author zengch
// * @date 2021-09-09 11:21
// */
//@Component
//public class DouYinCaptcha implements CommandLineRunner {
//
//    @Resource
//    private ChromeDriverConfig chromeDriverConfig;
//    @Resource
//    private CaptchaService captchaService;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        String tcaptchaUrl =
//            "https://www.douyin.com/search/%E9%BE%99%E5%8D%8E%E5%85%AC%E5%AE%89?source=normal_search&type=user";
//        captchaService.doVerify(tcaptchaUrl);
//    }
//
//    @PostConstruct
//    private void init() {
//        System.setProperty("webdriver.chrome.driver", "F:\\chromeDriver\\chromedriver.exe");
//
//    }
//
//    private void begin() {
//        String url =
//            "https://www.douyin.com/search/%E9%BE%99%E5%8D%8E?aid=5ffb6747-2755-4532-8e27-d76165c885f1&source=normal_search&type=user";
//        ChromeDriver driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
//        driver.manage().window().setSize(new Dimension(714, 700));
//        driver.get(url);
//    }
//}
