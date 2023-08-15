package com.txxy.demo.test;

import java.math.BigInteger;

/**
 * 功能：
 *
 * @author zengch
 * @date 2022-01-10 15:58
 */
public class AsciiToString {

    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            try {
                String replaced = chars[i].replaceAll("[^0-9]", "");
                sbu.append((char)Integer.parseInt(replaced));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sbu.toString();
    }

    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int)chars[i]).append(",");
            } else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();
    }

    public static int hexToTen(String strHex) {
        if (strHex.contains("0x")) {
            strHex = strHex.replace("0x", "");
        }
        BigInteger bigInteger = new BigInteger(strHex, 16);
        return bigInteger.intValue();

    }

    public static String tenToHex(int valueTen) {
        return String.format("%08X", valueTen);

    }

    public static void main(String[] args) {

        System.out.println(tenToHex(48));
        String str = "{name:1234,password:4444}";
        String asciiResult = stringToAscii(str);
        System.out.println(asciiResult);
        String stringResult = asciiToString(asciiResult);
        String stringResult2 = asciiToString("48");
        System.out.println(stringResult2);
    }

}
