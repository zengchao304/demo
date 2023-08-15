package com.txxy.demo.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.JsonComparedOption;
import me.codeleep.jsondiff.core.DefaultJsonDifference;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zengch
 * @Date 2023-07-03
 **/
public class JsonCompare {

    public static void main(String[] args) {
        String expect = "{\"orgId\": \"1999082\",\"orgName\": \"爱建证券有限责任公司\",\"orgTypeCode\": \"10\"," +
                "\"pracPersonCnt\": 902,\"prac0Cnt\": 480,\"prac1Cnt\": 0,\"prac2Cnt\": 3,\"prac3Cnt\": 352," +
                "\"prac4Cnt\": 4,\"prac5Cnt\": 52,\"prac6Cnt\": 10,\"prac7Cnt\": 10}";
        String actual = "{\"orgId\": \"1999082\",\"orgName\": \"爱建证券有限责任公司\",\"orgTypeCode\": \"10\"," +
                "\"pracPersonCnt\": 902,\"prac0Cnt\": 480,\"prac1Cnt\": 0,\"prac2Cnt\": 3,\"prac3Cnt\": 352," +
                "\"prac4Cnt\": 4,\"prac5Cnt\": 52,\"prac6Cnt\": 10,\"prac7Cnt\": 103}";

        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);

        JsonCompareResult jsonCompareResult =
                new DefaultJsonDifference().option(jsonComparedOption).detectDiff(JSON.parseObject(expect),
                        JSON.parseObject(actual));

        System.out.println(JSON.toJSONString(jsonCompareResult));

    }

}
