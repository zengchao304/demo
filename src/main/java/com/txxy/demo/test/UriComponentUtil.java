package com.txxy.demo.test;

import java.io.UnsupportedEncodingException;

/**
 * 功能：javaScript中encodeURIComponent和decodeURIComponent方法的JAVA实现
 *
 * @author zengch
 * @date 2022-01-11 10:16
 * @desc encodeURIComponent() 函数可把字符串作为 URI 组件进行编码。
 * <p>
 * 该方法不会对 ASCII 字母和数字进行编码，也不会对这些 ASCII 标点符号进行编码： - _ . ! ~ * ' ( ) 。
 * <p>
 * 其他字符（比如 ：;/?:@&=+$,# 这些用于分隔 URI 组件的标点符号），都是由一个或多个十六进制的转义序列替换的。
 */
public class UriComponentUtil {

    public static final String ALLOWED_CHARS =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

    public static void main(String[] args) {
        String s = encodeUriComponent("https://www");
        String s1 = encodeUriComponent("https://www!-_*");
    }

    /**
     * Description:
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    public static String encodeUri(String str) throws UnsupportedEncodingException {
        String isoStr = new String(str.getBytes("UTF8"), "ISO-8859-1");
        char[] chars = isoStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] <= 'z' && chars[i] >= 'a') || (chars[i] <= 'Z' && chars[i] >= 'A') || chars[i] == '-'
                || chars[i] == '_' || chars[i] == '.' || chars[i] == '!' || chars[i] == '~' || chars[i] == '*'
                || chars[i] == '\'' || chars[i] == '(' || chars[i] == ')' || chars[i] == ';' || chars[i] == '/'
                || chars[i] == '?' || chars[i] == ':' || chars[i] == '@' || chars[i] == '&' || chars[i] == '='
                || chars[i] == '+' || chars[i] == '$' || chars[i] == ',' || chars[i] == '#' || (chars[i] <= '9'
                && chars[i] >= '0')) {
                sb.append(chars[i]);
            } else {
                sb.append("%");
                sb.append(Integer.toHexString(chars[i]));
            }
        }
        return sb.toString();
    }

    /**
     * Description:
     *
     * @param input
     * @return
     * @see
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

    /**
     * Description:
     *
     * @param encodedUri
     * @return
     * @see
     */
    public static String decodeUriComponent(String encodedUri) {
        char actualChar;

        StringBuffer buffer = new StringBuffer();

        int bytePattern, sumb = 0;

        for (int i = 0, more = -1; i < encodedUri.length(); i++) {
            actualChar = encodedUri.charAt(i);

            switch (actualChar) {
                case '%': {
                    actualChar = encodedUri.charAt(++i);
                    int hb = (Character.isDigit(actualChar) ? actualChar - '0' :
                        10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
                    actualChar = encodedUri.charAt(++i);
                    int lb = (Character.isDigit(actualChar) ? actualChar - '0' :
                        10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
                    bytePattern = (hb << 4) | lb;
                    break;
                }
                case '+': {
                    bytePattern = ' ';
                    break;
                }
                default: {
                    bytePattern = actualChar;
                }
            }

            if ((bytePattern & 0xc0) == 0x80) {
                // 10xxxxxx
                sumb = (sumb << 6) | (bytePattern & 0x3f);
                if (--more == 0) {
                    buffer.append((char)sumb);
                }
            } else if ((bytePattern & 0x80) == 0x00) {
                // 0xxxxxxx
                buffer.append((char)bytePattern);
            } else if ((bytePattern & 0xe0) == 0xc0) {
                // 110xxxxx
                sumb = bytePattern & 0x1f;
                more = 1;
            } else if ((bytePattern & 0xf0) == 0xe0) {
                // 1110xxxx
                sumb = bytePattern & 0x0f;
                more = 2;
            } else if ((bytePattern & 0xf8) == 0xf0) {
                // 11110xxx
                sumb = bytePattern & 0x07;
                more = 3;
            } else if ((bytePattern & 0xfc) == 0xf8) {
                // 111110xx
                sumb = bytePattern & 0x03;
                more = 4;
            } else {
                // 1111110x
                sumb = bytePattern & 0x01;
                more = 5;
            }
        }
        return buffer.toString();
    }

}
