package com.txxy.demo.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "webdriver.chrome")
@Data
public class ChromeDriverConfig {

    /**
     * chrom驱动
     */
    private String driver;

    /**
     * 是否启动浏览器无头模式(不拉起浏览器)
     */
    private boolean headless;

    /**
     * 池子容量
     */
    private int capacity;

    /**
     * 浏览器路径
     */
    private String path;
}
