package com.txxy.demo.test;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：
 *
 * @author zengch
 * @date 2022-01-10 15:19
 * <p>
 * //MTU2@#/rank/marketRank@#-1515125653845@#2
 * //MTU2@#/rank/marketRank@#-1515125652845@#2
 * //MTU2@#/rank/marketRank@#-1515125653845@#2
 * //MTU2@#/rank/marketRank@#-1515125653845@#2
 * <p>
 * //dTBhBCETH0JRXlsfVQVFUwFAZABeW3ATHQEFCVEGClECA1IDBAhwEwI=
 * //dTBhBCETH0JRXlsfVQVFUwFAZABeW3ATHQEFCVEGClECA1MIBAVwEwI=
 * //dTBhBCENAUJRBQUBVQVFUwEoZAAFBXANAQEFCVEGAFECA1MIBAVwDQI=
 * <p>
 * //u0a%04!%13%1FBQ%5E%5B%1FU%05ES%01%40d%00%5E%5Bp%13%1D%01%05%09Q%06%0AQ%02%03R%08%04%05p%13%02
 * //u0a%04!%13%1FBQ%5E%5B%1FU%05ES%01%40d%00%5E%5Bp%13%1D%01%05%09Q%06%0AQ%02%03S%08%04%05p%13%02
 */
public class QiMai {

    private static final String cookie = "";
    //网页终端秘钥
    private static final String PcSecretKey = "0000000c735d856";
    //移动设备终端秘钥
    private static final String mobileSecretKey = "00000008d78d46a";

    public static final String ALLOWED_CHARS =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

    public static void main(String[] args) throws Exception {

        HashMap<String, String> paramsMap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        paramsMap.put("market", "6");
        paramsMap.put("category", "5");
        paramsMap.put("page", "1");
        map.put("url", "https://api.qimai.cn/rank/marketRank");
        map.put("baseURL", "https://api.qimai.cn");
        map.put("params", paramsMap);
        String qiMaiAnalysis = getQiMaiAnalysis(map, paramsMap);
        System.out.println(qiMaiAnalysis);

    }

    public static String getQiMaiAnalysis(HashMap<String, Object> objMap, HashMap<String, String> paramsMap) {
        long l = System.currentTimeMillis() - 1000;
        long diff = System.currentTimeMillis() - l - 1515125653845L;
        Set<String> keySet = paramsMap.keySet();
        if (keySet.size() > 0) {
            int[] paramsValueArr = new int[keySet.size()];
            String[] strValueArr = new String[keySet.size()];
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < keySet.size(); i++) {
                paramsMap.get(keySet);
            }
            for (String key : keySet) {
                String value = paramsMap.get(key);
                list.add(Integer.parseInt(value));
            }
            for (int i = 0; i < list.size(); i++) {
                paramsValueArr[i] = list.get(i);
            }
            bubbleSort(paramsValueArr);
            for (int i = 0; i < paramsValueArr.length; i++) {
                strValueArr[i] = paramsValueArr[i] + "";
            }
            String join = StringUtils.join(strValueArr);
            String sorted = base64(join);
            sorted += "@#" + ((String)objMap.get("url")).replace((String)objMap.get("baseURL"), "");
            sorted += "@#" + diff;
            sorted += "@#" + 2;
            String analysisResult = base64(encrypt(sorted, PcSecretKey));
            return analysisResult;

        }
        return null;
    }

    /**
     * 排序（冒泡）
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * base64编码
     *
     * @param str
     * @return
     */
    public static String base64(String str) {

        try {
            String encodeStr = UriComponentUtil.encodeUriComponent(str);
            String newStr = encodeStr;
            Pattern pattern = Pattern.compile("%([0-9A-F]{2})");
            Matcher matcher = pattern.matcher(encodeStr);
            List<String> list = new ArrayList<>();
            while (matcher.find()) {
                list.add(matcher.group(1));
            }
            if (list.size() > 0) {
                for (String regexStr : list) {
                    String m = ascii2String("0x" + regexStr);
                    newStr = newStr.replace("%" + regexStr, m);
                }
                return new String(Base64.encodeBase64(newStr.getBytes("UTF-8")));
            } else {
                return new String(Base64.encodeBase64(str.getBytes("UTF-8")));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ascii码转字符串
     *
     * @param hexStr
     * @return
     */
    public static String ascii2String(String hexStr) {
        return AsciiToString.asciiToString(AsciiToString.hexToTen(hexStr) + "");
    }

    /**
     * 明文与key进行计算
     *
     * @param str
     * @param key
     * @return
     */
    public static String encrypt(String str, String key) {

        String[] split = str.split("");
        String[] keySplit = key.split("");
        int splitLength = split.length;
        int keyLength = key.length();
        String[] newArr = new String[splitLength];
        for (int i = 0; i < splitLength; i++) {
            int left = Integer.parseInt(AsciiToString.stringToAscii(split[i]));
            int right = Integer.parseInt(AsciiToString.stringToAscii(keySplit[(i + 10) % keyLength]));
            int xorRes = left ^ right;
            String m = ascii2String(AsciiToString.tenToHex(xorRes) + "");
            newArr[i] = m;
        }
        return StringUtils.join(newArr);
    }

    /**
     * uri编码，模拟实现JavaScript中的encodeURIComponent
     *
     * @param input
     * @return
     */
    public static String encodeUriComponent(String input) {
        if (null == input || "".equals(input.trim())) {
            return input;
        }

        int l = input.length();
        StringBuilder o = new StringBuilder(l * 3);
        try {
            for (int i = 0; i < l; i++) {
                String e = input.substring(i, i + 1);
                if (ALLOWED_CHARS.indexOf(e) == -1) {
                    byte[] b = e.getBytes("utf-8");
                    o.append(getHex(b));
                    continue;
                }
                o.append(e);
            }
            return o.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 十六进制编码
     *
     * @param buf
     * @return
     */
    private static String getHex(byte[] buf) {
        StringBuilder o = new StringBuilder(buf.length * 3);
        for (int i = 0; i < buf.length; i++) {
            int n = (int)buf[i] & 0xff;
            o.append("%");
            if (n < 0x10) {
                o.append("0");
            }
            o.append(Long.toString(n, 16).toUpperCase());
        }
        return o.toString();
    }

}
