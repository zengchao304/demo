package com.txxy.demo.test;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * 功能：
 *
 * @author zengch
 * @date 2021-10-15 16:49
 */
@Component
public class JvmOomTest {

    @Resource
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        String param = "param ";
        while (true) {
            JSONObject information = new JSONObject();
            for (int j = 0; j < 10; j++) {
                                information.put(param + j, new Byte[1024 * 1024]);
//                information.put(param + j, j + "00");
            }
            sendMessage(information);

            System.out.println(information.size());
        }
    }

    public static void sendMessage(JSONObject jsonObject) {
        JSONObject cloneObject = (JSONObject)jsonObject.clone();
        cloneObject.put("sendTime", System.currentTimeMillis());

    }

}
