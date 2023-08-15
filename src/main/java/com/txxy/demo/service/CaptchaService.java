//package com.txxy.demo.service;
//
//import org.apache.commons.io.FileUtils;
//import org.opencv.core.Point;
//import org.opencv.core.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.remote.CapabilityType;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class CaptchaService {
//
//    private static ChromeDriver driver;
//    private static Proxy seleniumProxy;
//
//    public void doVerify(String tcaptchaUrl) {
//        long begin = System.currentTimeMillis();
//        try {
//            createNewTabAndSwitchTo();
//            driver.get(tcaptchaUrl);
//            verifyCaptcha(driver);
//            System.out.println("总耗时： " + (System.currentTimeMillis() - begin));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            driver.get(tcaptchaUrl);
//            sleep(180000);
//            closeWindow();
//        }
//    }
//
//    /**
//     * 初始化
//     */
//    @PostConstruct
//    private static void init() throws UnknownHostException {
//        System.setProperty("webdriver.chrome.driver", "F:\\chromeDriver\\chromedriver.exe");
//
//        ChromeOptions options = new ChromeOptions();
//        options.setCapability(CapabilityType.PROXY, seleniumProxy);
//        driver = new ChromeDriver(options);
//        //设置隐性等待
//        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
//        //设置浏览器窗口大小,,此参数为了让验证码图片尽量在浏览器上是1:1的形式展现
//        driver.manage().window().setSize(new Dimension(714, 700));
//    }
//
//    private void createNewTabAndSwitchTo() {
//        String currentWindowHandle = driver.getWindowHandle();
//        driver.executeScript("window.open('')");
//        Set<String> windowHandles = driver.getWindowHandles();
//        for (String handle : windowHandles) {
//            if (currentWindowHandle.equals(handle)) {
//                continue;
//            }
//            driver.switchTo().window(handle);
//        }
//    }
//
//    public void closeWindow() {
//
//        try {
//            String winHandleBefore = driver.getWindowHandle();//关闭当前窗口前，获取当前窗口句柄
//            Set<String> winHandles = driver.getWindowHandles();//使用set集合获取所有窗口句柄
//
//            driver.close();//关闭窗口
//
//            Iterator<String> it = winHandles.iterator();//创建迭代器，迭代winHandles里的句柄
//            while (it.hasNext()) {//用it.hasNext()判断时候有下一个窗口,如果有就切换到下一个窗口
//                String win = it.next();//获取集合中的元素
//                if (!win.equals(winHandleBefore)) { //如果此窗口不是关闭前的窗口
//                    driver.switchTo().window(win);//切换到新窗口
//                    break;
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//    }
//
//    private void sleep(long millis) {
//        try {
//            Thread.sleep(millis);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void verifyCaptcha(ChromeDriver driver) throws Exception {
//        sleep(200);
//
//        //获取滑动按钮
//        WebElement moveElement = waitWebElement(driver, By.className("fiQtnm"), 500);
//        sleep(100);
//        //获取带阴影的背景图
//        String bgUrl = waitWebElement(driver, By.id("captcha-verify-image"), 500).getAttribute("src");
//        sleep(100);
//        //获取拼图小块
//        String sUrl = waitWebElement(driver, By.className("ggNWOG"), 500).getAttribute("src");
//        sleep(100);
//
//        String styleStr = waitWebElement(driver, By.className("ggNWOG"), 500).getAttribute("style");
//
//        String widthStr;
//        String topStr;
//        int top;
//        Double radio;//背景图与原图比例
//        if (styleStr.contains("em")) {
//            topStr = styleStr.substring(styleStr.indexOf("top: ") + 5, styleStr.indexOf("em; transform"));
//            //实图与原图的比例，实图尺寸（w:68px h:68px），原图尺寸（w:110px h:110px） radio=68/110
//            radio = 0.61818181818181818181818181818182;
//            //任意获取一张拼图小块验证图，当top高度为145px时，style的top值为0.85em，计算出em与px的关系：em=145/0.85
//            Double em = 170.58823529411764705882352941176;
//            top = (int)(em * Double.parseDouble(topStr));
//        } else {
//            widthStr = styleStr.substring(styleStr.indexOf("width: ") + 7, styleStr.indexOf("px; top"));
//            topStr = styleStr.substring(styleStr.indexOf("top: ") + 5, styleStr.indexOf("px; left"));
//            //转换比例w2/w1=0.1.0303030303030303 (浏览器滑块小图w1:132px,h1:132px,原图w2:110px,h2:110px)
//            radio = 110 / Double.parseDouble(widthStr);
//            top = (int)(Integer.parseInt(topStr) * radio);
//        }
//        sleep(50);
//        //计算滑动距离
//        int distance = (int)Double.parseDouble(getTencentDistance(bgUrl, sUrl, top, radio));
//        move(driver, moveElement, distance);
//        sleep(500);
//
//    }
//
//    /**
//     * 获取验证滑动距离
//     *
//     * @return
//     */
//    public String getTencentDistance(String bUrl, String sUrl, int top, Double radio) {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        File bFile = new File("D:\\tcaptcha\\byte_dance_b.jpg");
//        File sFile = new File("D:\\tcaptcha\\byte_dance_s.jpg");
//        BufferedImage bgBI;
//        BufferedImage sBI;
//        try {
//            FileUtils.copyURLToFile(new URL(bUrl), bFile);
//            FileUtils.copyURLToFile(new URL(sUrl), sFile);
//            bgBI = ImageIO.read(bFile);
//            sBI = ImageIO.read(sFile);
//            // 裁剪  拼图小块尺寸为w:110px h:110px，一般阴影位置出现拼图小块的右边，x坐标从110开始，120是裁剪的一个偏移量，一般不会出现阴影位置刚好在最右侧
//            bgBI = bgBI.getSubimage(110, top, bgBI.getWidth() - 120, sBI.getHeight());
//            ImageIO.write(bgBI, "png", bFile);
//            Mat s_mat = Imgcodecs.imread(sFile.getPath());
//            Mat b_mat = Imgcodecs.imread(bFile.getPath());
//            // 转灰度图像
//            Mat s_newMat = new Mat();
//            Imgproc.cvtColor(s_mat, s_newMat, Imgproc.COLOR_BGR2GRAY);
//            // 二值化图像
//            binaryzation(s_newMat);
//            Imgcodecs.imwrite(sFile.getPath(), s_newMat);
//
//            int result_rows = b_mat.rows() - s_mat.rows() + 1;
//            int result_cols = b_mat.cols() - s_mat.cols() + 1;
//            Mat g_result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
//            Imgproc.matchTemplate(b_mat, s_mat, g_result, Imgproc.TM_SQDIFF); // 归一化平方差匹配法
//            // 归一化相关匹配法
//            Core.normalize(g_result, g_result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
//            Point matchLocation = new Point();
//            Core.MinMaxLocResult mmlr = Core.minMaxLoc(g_result);
//            matchLocation = mmlr.maxLoc; // 此处使用maxLoc还是minLoc取决于使用的匹配算法
//            Imgproc.rectangle(b_mat, matchLocation,
//                new Point(matchLocation.x + s_mat.cols(), matchLocation.y + s_mat.rows()), new Scalar(0, 0, 0, 0));
//            Imgcodecs.imwrite("D:\\tcaptcha\\byte_dance_rectangle.jpg", b_mat);
//            return "" + (int)((matchLocation.x + s_mat.cols() + 110 - sBI.getWidth()) * radio);
//        } catch (Throwable e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            bFile.delete();
//            sFile.delete();
//        }
//    }
//
//    /**
//     * @param mat 二值化图像
//     */
//    public void binaryzation(Mat mat) {
//        int BLACK = 0;
//        int WHITE = 255;
//        int ucThre = 0, ucThre_new = 127;
//        int nBack_count, nData_count;
//        int nBack_sum, nData_sum;
//        int nValue;
//        int i, j;
//        int width = mat.width(), height = mat.height();
//        // 寻找最佳的阙值
//        while (ucThre != ucThre_new) {
//            nBack_sum = nData_sum = 0;
//            nBack_count = nData_count = 0;
//
//            for (j = 0; j < height; ++j) {
//                for (i = 0; i < width; i++) {
//                    nValue = (int)mat.get(j, i)[0];
//
//                    if (nValue > ucThre_new) {
//                        nBack_sum += nValue;
//                        nBack_count++;
//                    } else {
//                        nData_sum += nValue;
//                        nData_count++;
//                    }
//                }
//            }
//            nBack_sum = nBack_sum / nBack_count;
//            nData_sum = nData_sum / nData_count;
//            ucThre = ucThre_new;
//            ucThre_new = (nBack_sum + nData_sum) / 2;
//        }
//        // 二值化处理
//        int nBlack = 0;
//        int nWhite = 0;
//        for (j = 0; j < height; ++j) {
//            for (i = 0; i < width; ++i) {
//                nValue = (int)mat.get(j, i)[0];
//                if (nValue > ucThre_new) {
//                    mat.put(j, i, WHITE);
//                    nWhite++;
//                } else {
//                    mat.put(j, i, BLACK);
//                    nBlack++;
//                }
//            }
//        }
//        // 确保白底黑字
//        if (nBlack > nWhite) {
//            for (j = 0; j < height; ++j) {
//                for (i = 0; i < width; ++i) {
//                    nValue = (int)(mat.get(j, i)[0]);
//                    if (nValue == 0) {
//                        mat.put(j, i, WHITE);
//                    } else {
//                        mat.put(j, i, BLACK);
//                    }
//                }
//            }
//        }
//    }
//
//    // 延时加载
//    public WebElement waitWebElement(WebDriver driver, By by, int count) throws Exception {
//        WebElement webElement = null;
//        boolean isWait = false;
//        for (int k = 0; k < count; k++) {
//            try {
//                webElement = driver.findElement(by);
//                if (isWait) {
//                    System.out.println(" ok!");
//
//                }
//                return webElement;
//            } catch (NoSuchElementException ex) {
//                isWait = true;
//                if (k == 0) {
//                    System.out.print("waitWebElement(" + by.toString() + ")");
//
//                } else {
//                    System.out.print(".");
//
//                }
//                Thread.sleep(50);
//            }
//        }
//        if (isWait) {
//            System.out.println(" outTime!");
//
//        }
//        return null;
//    }
//
//    /**
//     * @param driver
//     * @param element  页面滑块
//     * @param distance 需要移动距离
//     * @throws InterruptedException
//     */
//    public void move(WebDriver driver, WebElement element, int distance) throws InterruptedException {
//        int randomTime = 0;
//        if (distance > 90) {
//            randomTime = 80;
//        } else if (distance > 80) {
//            randomTime = 50;
//        }
//        List<Integer> track = getMoveTrack(distance - 2);
//        int moveY = 0;
//        try {
//            Actions actions = new Actions(driver);
//            actions.clickAndHold(element).perform();
//            Thread.sleep(200);
//            for (int i = 0; i < track.size(); i++) {
//                actions.moveByOffset(track.get(i), moveY).perform();
//                Thread.sleep(new Random().nextInt(200) + randomTime);
//            }
//            Thread.sleep(200);
//            shakeMouse(driver, element, actions);
//            actions.release(element).perform();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 模拟人手释放鼠标抖动
//     *
//     * @param driver
//     * @param element
//     * @param actions
//     */
//    public void shakeMouse(WebDriver driver, WebElement element, Actions actions) {
//        actions.moveByOffset(-2, 0).perform();
//        actions.moveByOffset(2, 0).perform();
//
//    }
//
//    /**
//     * @param distance 需要移动的距离
//     * @return
//     */
//    public List<Integer> getMoveTrack(int distance) {
//        List<Integer> track = new ArrayList<>();// 移动轨迹
//        Random random = new Random();
//        int current = 0;// 已经移动的距离
//        int mid = (int)distance * 4 / 5;// 减速阈值
//        int a = 0;
//        int move = 0;// 每次循环移动的距离
//        while (true) {
//            a = random.nextInt(150);
//            if (current <= mid) {
//                move += a;// 不断加速
//            } else {
//                move -= a;
//            }
//            if ((current + move) < distance) {
//                track.add(move);
//            } else {
//                track.add(distance - current);
//                break;
//            }
//            current += move;
//        }
//        return track;
//    }
//
//}
