//package com.txxy.demo.test;
//
//import com.microsoft.playwright.*;
//import com.microsoft.playwright.options.RequestOptions;
//import com.txxy.demo.util.JacksonConverter;
//import org.seimicrawler.xpath.JXDocument;
//import org.seimicrawler.xpath.JXNode;
//import org.springframework.http.HttpHeaders;
//
//import java.nio.file.Paths;
//import java.util.*;
//
///**
// * @Author zengch
// * @Date 2023-07-06
// **/
//public class TestTimeOut {
//
//    public static void main(String[] args) {
//
//        Browser browser = getBrowser();
//        BrowserContext browserContext = browser.newContext();
//        APIRequestContext apiRequestContext = browserContext.request();
//
//        APIResponse apiResponse1 = apiRequestContext.get("https://cnse.e-cqs.cn/info-pub/pub/pubQueryVCodeData.json",
//                RequestOptions.create().setHeader(HttpHeaders.REFERER, "https://cnse.e-cqs.cn/info-pub/pub")
//                        .setTimeout(5000));
//
//        String text1 = apiResponse1.text();
//        Map map = JacksonConverter.read(text1, Map.class);
//
//        Integer xWidth = (Integer) map.get("xWidth");
//
//
//        APIResponse apiResponse2 = apiRequestContext.get("https://cnse.e-cqs.cn/info-pub/pub/perResult",
//                RequestOptions.create().setHeader(HttpHeaders.REFERER, "https://cnse.e-cqs.cn/info-pub/pub")
//                        .setQueryParam("sfzh", "610526198804202216")
//                        .setQueryParam("moveLength", xWidth)
//                        .setTimeout(5000));
//
//        String text2 = apiResponse2.text();
//
//
//        JXDocument jxDocument = JXDocument.create(text2);
//        JXNode validCodeNode = jxDocument.selNOne("//input[@id='validCode']");
//        String validCodeParam = validCodeNode.asElement().attr("value");
//
//
//        APIResponse apiResponse3 = apiRequestContext.get("https://cnse.e-cqs.cn/info-pub/pub/getPerInfoBySfzh.json",
//                RequestOptions.create().setHeader(HttpHeaders.REFERER,"https://cnse.e-cqs.cn/info-pub/pub")
//                        .setQueryParam("sfzh", "610526198804202216")
//                        .setQueryParam("validCode", validCodeParam)
//                        .setQueryParam("r", System.currentTimeMillis() + "")
//                        .setTimeout(5000));
//
////        APIResponse apiResponse = apiRequestContext.get("http://www.baidu.com", RequestOptions.create().setTimeout(100));
//        String text = apiResponse3.text();
//        System.out.println(text);
//
//    }
//
//    public static Browser getBrowser() {
//
//        try {
//            Playwright.CreateOptions createOptions = new Playwright.CreateOptions();
//            Map<String, String> envMap = new HashMap<>(16);
//            //跳过下载浏览器，只要设置了值就会跳过
//            envMap.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
//            createOptions.setEnv(envMap);
//            Playwright playwright = Playwright.create(createOptions);
//
//
//            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
//            launchOptions.setHeadless(false);
//            launchOptions.setChromiumSandbox(false);
//            List<String> argList = new ArrayList<>();
//            argList.add("--no-sandbox");
//            argList.add("--disable-setuid-sandbox");
//            argList.add("--start-maximized");
//            argList.add("--disable-blink-features=AutomationControlled");
////            argList.add("--user-agent=".concat(userAgentSupport.generateAgentForBrowser()));
//            argList.add("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, likeGecko)" +
//                    " Chrome/104.0.5112.20 Safari/537.36");
//
//            List<String> ignoreArgList = Arrays.asList("--enable-automation");
//
//            launchOptions.setArgs(argList);
//            launchOptions.setDevtools(true);
//            launchOptions.setIgnoreDefaultArgs(ignoreArgList);
//            launchOptions.setExecutablePath(Paths.get("E:\\chromeDownload\\chromium-1045\\chrome-win\\chrome.exe"));
//
//            BrowserType browserType = playwright.chromium();
//            return browserType.launch(launchOptions);
//        } catch (Exception e) {
////            log.error("{}|获取browser失败", TxxyLogCategoryEnum.TXXYBUSSINESS, e);
//            System.out.println("errrrr");
//        }
//
//        return null;
//
//    }
//}
