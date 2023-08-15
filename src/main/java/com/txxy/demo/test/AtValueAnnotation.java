package com.txxy.demo.test;

import com.google.common.collect.LinkedHashMultimap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sun.applet.AppletEventMulticaster;

import java.io.File;
import java.util.IdentityHashMap;

/**
 * @Author zengch
 * @Date 2023-06-20
 **/
@Component
public class AtValueAnnotation implements CommandLineRunner {

    @Value("#{T(java.io.File).separator}")
    private String path;


    @Override
    public void run(String... args) throws Exception {

        System.out.println("path is "+path);
    }

    public static void main(String[] args) {
        LinkedHashMultimap<String, String> linkedHashMultimap =LinkedHashMultimap.create();
        linkedHashMultimap.put("a","666");
        linkedHashMultimap.put("a","777");
        linkedHashMultimap.put("a","7888");



        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();
        identityHashMap.put(new String("aaa"),"111");
        identityHashMap.put(new String("aaa"),"222");
        identityHashMap.put(new String("aaa"),"333");


        System.out.println(new String("abg").equals(new String("abg")));
    }
}
