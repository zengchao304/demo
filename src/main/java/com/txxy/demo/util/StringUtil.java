package com.txxy.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串util
 *
 * @author chenzb
 */
@Slf4j
public class StringUtil {

    /**
     * UTF-8字符集
     **/
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String EMPTY = "";

    public static final String BLANK = " ";

    public static final String POINT = ".";

    /**
     * 分隔符:逗号
     */
    public static final String SEPARATOR_COMMA = ",";

    /**
     * 分隔符:逗号
     */
    public static final String SEPARATOR_CHINESE_COMMA = "，";

    /**
     * 分隔符:#
     */
    public static final String SEPARATOR_POUND = "#";

    /**
     * 分隔符:/
     */
    public static final String SEPARATOR_SLASH = "/";

    /**
     * 百分符:%
     */
    public static final String PERCENT = "%";
    /**
     * 美元符：￥
     */
    public static final String DOLLAR = "$";

    /**
     * 分隔符:引号
     */
    public static final String QUOTATION = "'";

    /**
     * 分隔符:分号
     */
    public static final String SEPARATOR_SEMICOLON = ";";

    /**
     * 分隔符:中文分号
     */
    public static final String SEPARATOR_CHINESE_SEMICOLON = "；";

    /**
     * 分隔符=分号
     */
    public static final String EQUAL = "=";

    /**
     * 分隔符+加号
     */
    public static final String PLUS = "+";

    /**
     * 分隔符&分号
     */
    public static final String AND = "&";
    /**
     * 分隔符?分号
     */
    public static final String QUESTION = "?";

    /**
     * 分隔符:冒号
     */
    public static final String SEPARATOR_COLON = ":";

    /**
     * 分隔符:中文冒号
     */
    public static final String SEPARATOR_CHINESE__COLON = "：";

    /**
     * 冒号分隔字符串
     */
    public static final String SEPARATOR_COLON_STR = ":：";

    /**
     * 分隔符:顿号
     */
    public static final String SEPARATOR_CASEURE = "、";

    /**
     * 分隔符:-
     */
    public static final String SEPARATOR_LINE = "-";

    /**
     * 分隔符:_
     */
    public static final String SEPARATOR_UNDERLINE = "_";

    /**
     * 分隔符:|
     */
    public static final String SEPARATOR_VERTICAL = "|";

    /**
     * 分隔字符串
     */
    public static final String SPLIT_STR = ";|；";

    /**
     * 分隔字符串
     */
    public static final String SPLIT_VERTICAL = "\\|";

    public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.00");

    private static int RESERVESNUM;

    private static char reserve[];

    private static final char QUOTE_ENCODE[] = "&quot;".toCharArray();

    private static final char AMP_ENCODE[] = "&amp;".toCharArray();

    private static final char LT_ENCODE[] = "&lt;".toCharArray();

    private static final char GT_ENCODE[] = "&gt;".toCharArray();

    public static final long SECOND = 1000L;

    public static final long MINUTE = 60000L;

    public static final long HOUR = 0x36ee80L;

    public static final long DAY = 0x5265c00L;

    public static final long WEEK = 0x240c8400L;

    public static final long KBYTES = 1024L;

    private static MessageDigest digest = null;

    public static NumberFormat FORMAT = NumberFormat.getInstance();

    private static Pattern TRIM_LINE_ENTER_CHARACTER_PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    private static Pattern NUMBERS_PATTERN = Pattern.compile("[0-9]+(\\.[0-9]+)?");

    /**
     * HTML标签替换
     */
    private final static String HTML_ESCAPE_REGEX = "<[^>]*>";

    /**
     * 构造函数
     */
    public StringUtil() {
    }

    /**
     * 把布尔型的字符串转换成整数,true->1,false->0,不区分大小写 不是布尔型的字符串全部转成0
     *
     * @param booleanStr
     * @return int
     */
    public static int booleanStr2int(String booleanStr) {
        return new Boolean(booleanStr).booleanValue() ? 1 : 0;
    }

    /**
     * 去除字符串头尾的空白,如果是null,则返回空白
     *
     * @param str
     * @return String
     */
    public static String trim(String str) {
        return (str == null) ? "" : str.trim();
    }

    /**
     * 只适用网页上进行字符串null或空白转换成空白 返回&nbsp;,如果有值则返回当前值
     *
     * @param str
     * @return str
     */
    public static String webTrim(String str) {
        str = trim(str);
        return "".equals(str) ? "&nbsp;" : str;
    }

    /**
     * 只适用网页上进行对象null或空白转换成空白 返回&nbsp;,如果有值则返回当前值
     *
     * @param obj
     * @return str
     */
    public static String webTrim(Object obj) {
        String str = trim(obj);
        return "".equals(str) ? "&nbsp;" : str;
    }

    /**
     * 替换字符串，如果当str没值，则用defult来替换，否则返回当前的str
     *
     * @param str
     * @param defult
     * @return str
     */
    public static String trim(String str, String defult) {
        return (str == null) ? trim(defult) : str.trim();
    }

    /**
     * 替换对象，如果当obj==null，则用defult来替换，否则返回当前的obj字符串
     *
     * @param obj
     * @param defult
     * @return
     */
    public static String trim(Object obj, String defult) {
        return (obj == null) ? trim(defult) : obj.toString().trim();
    }

    /**
     * 格式化Object对象为字符串，如果为null则返回空白.
     *
     * @param str
     * @return str
     */
    public static String trim(Object str) {
        return (str == null) ? "" : str.toString().trim();
    }

    /**
     * 删除字符串结尾中给定字符串
     *
     * @param str
     * @param delStr
     * @return
     */
    public static String trimSuffix(String str, String delStr) {
        int delStrLength = delStr.length();
        if (str.endsWith(delStr)) {
            str = str.substring(0, str.length() - delStrLength);
        }
        return str;
    }

    /**
     * @param str
     * @return
     * @deprecated
     */
    public static String trim_web(String str) {
        String tmp = trim(str);
        return ("".equals(tmp)) ? "&nbsp;" : tmp;
    }

    /**
     * 把为null的字符串转换成空白。
     *
     * @param str
     * @return str
     */
    public static String nonNull(String str) {
        return (str == null) ? "" : str;
    }

    /**
     * 从输入流中转换成字符串流
     *
     * @param in
     * @return stringbuffer
     * @throws IOException
     */
    public static StringBuffer getStringBuffer(InputStream in) throws IOException {
        if (in == null) {
            return new StringBuffer("");
        }
        StringBuffer result = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        char buf[] = new char[1024];
        for (boolean quit = false; !quit; ) {
            int len = reader.read(buf);
            if (len < 0) {
                quit = true;
            } else {
                result.append(buf, 0, len);
            }
        }
        reader.close();
        return result;
    }

    /**
     * 从输入流中转换成字符串
     *
     * @param in
     * @return str
     * @throws IOException
     */
    public static String getText(InputStream in) throws IOException {
        return getStringBuffer(in).toString();
    }

    /**
     * 从Reader中获取字符串
     *
     * @param reader
     * @return str
     * @throws IOException
     */
    public static String getText(Reader reader) throws IOException {
        if (reader == null) {
            return "";
        }
        StringBuffer dat = new StringBuffer();
        char buf[] = new char[1024];
        do {
            int l = reader.read(buf);
            if (l >= 0) {
                dat.append(buf, 0, l);
            } else {
                return dat.toString();
            }
        } while (true);
    }

    /**
     * 字符串转换成数字，转换出错或者为空则返回-1
     *
     * @param str
     * @return int
     */
    public static int getAsInt(String str) {
        return getAsInt(str, -1);
    }

    /**
     * 字符串转换成数字，转换出错或者为空则返回defaultv
     *
     * @param str
     * @param defaultv
     * @return
     */
    public static int getAsInt(String str, int defaultv) {
        if (str == null || "".equals(str)) {
            return defaultv;
        }
        try {
            return Integer.parseInt(str, 10);
        } catch (Exception e) {
            return defaultv;
        }
    }

    /**
     * 字符串转换成long，转换出错或者为空则返回-1L
     *
     * @param str
     * @return long
     */
    public static long getAsLong(String str) {
        return getAsLong(str, -1L);
    }

    /**
     * 字符串转换成long，转换出错或者为空则返回defaultv
     *
     * @param str
     * @param defaultv
     * @return long
     */
    public static long getAsLong(String str, long defaultv) {
        if (str == null || "".equals(str)) {
            return defaultv;
        }
        try {
            return Long.parseLong(str, 10);
        } catch (Exception e) {
            return defaultv;
        }
    }

    /**
     * 字符串转换成big decimal，转换出错或者为空则返回defaultv
     */
    public static BigDecimal getAsDecimal(String str) {
        return getAsDecimal(str, BigDecimal.ZERO);
    }

    /**
     * 字符串转换成big decimal，转换出错或者为空则返回defaultv
     */
    public static BigDecimal getAsDecimal(String str, BigDecimal defaultv) {

        if (str == null || "".equals(str)) {
            return defaultv;
        }

        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return defaultv;
        }
    }

    /**
     * 分离字符串中特定的字符串返回数组 (可以分离特殊的字符串如"|$")
     *
     * @param str
     * @param seperator
     * @return
     */
    public static String[] split(String str, String seperator) {
        return split(str, seperator, false);
    }

    /**
     * 分离字符串中特定的字符串返回数组 (可以分离特殊的字符串如"|$"),并且限定是否继承。默认为不限定
     *
     * @param str
     * @param seperator
     * @param tail
     * @return
     */
    public static String[] split(String str, String seperator, boolean tail) {
        if (str == null || "".equals(str) || "".equals(trim(seperator))) {
            return (tail) ? (new String[]{""}) : new String[0];
        }
        if (!tail && str.endsWith(seperator)) {
            str = str.substring(0, str.length() - seperator.length());
        }
        List<String> temp = new ArrayList<String>();
        int oldPos = 0;
        int newPos = str.indexOf(seperator);
        int parentLength = str.length();
        int subStrLength = seperator.length();
        if (newPos != -1) {
            newPos += subStrLength;
        }
        while (newPos <= parentLength && newPos != -1) {
            temp.add(str.substring(oldPos, newPos - subStrLength));
            oldPos = newPos;
            newPos = str.indexOf(seperator, oldPos);
            if (newPos != -1) {
                newPos += seperator.length();
            }
        }
        if (oldPos <= parentLength) {
            temp.add(str.substring(oldPos));
        }
        return (String[]) temp.toArray(new String[temp.size()]);
    }

    /**
     * 检查特定的字符串在指定的字符串中出现的次数，如果没有则返回0
     *
     * @param src
     * @param key
     * @return
     */
    public static int count(String src, String key) {
        int count = 0;
        for (String temp = src; temp.indexOf(key) >= 0; temp = temp.substring(temp.indexOf(key) + key.length())) {
            count++;
        }
        return count;
    }

    /**
     * 把指定的字符串src中特定的某些字符oldStr替换成其他的字符newStr
     *
     * @param src
     * @param oldStr
     * @param newStr
     * @return
     */
    public static String replace(String src, String oldStr, String newStr) {
        StringBuffer result = new StringBuffer();
        boolean found = false;
        if (src == null || oldStr == null || newStr == null) {
            throw new NullPointerException("parameter is null");
        }
        if (oldStr.length() == 0) {
            throw new IllegalArgumentException("illegal parameter");
        }
        int pos = 0;
        int pos1 = 0;
        while (pos < src.length()) {
            pos1 = src.indexOf(oldStr, pos);
            if (pos1 < 0) {
                pos1 = src.length();
                found = false;
            } else {
                found = true;
            }
            result.append(src.substring(pos, pos1));
            if (found) {
                result.append(newStr);
                pos = pos1 + oldStr.length();
            } else {
                pos = pos1;
            }
        }
        return result.toString();
    }

    /**
     * 指定的字符串中替换一些成特定的编码格式的字符串 如 ? 变成UTF-8的%3F
     *
     * @param url
     * @param encode
     * @return
     */
    public static String urlEncode(String url, String encode) {
        if (url == null) {
            return "";
        }
        byte dat[];
        if (encode != null) {
            try {
                dat = url.getBytes(encode);
            } catch (UnsupportedEncodingException ex) {
                return "";
            }
        } else {
            dat = url.getBytes();
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < dat.length; i++) {
            int ch = dat[i] & 0xff;
            if (isReserve(ch)) {
                result.append("%");
                result.append(Integer.toHexString(ch).toUpperCase());
            } else {
                result.append((char) ch);
            }
        }
        return result.toString();
    }

    /**
     * 判断是否存储有指定的字符
     *
     * @param ch
     * @return
     */
    private static boolean isReserve(int ch) {
        if (ch > 126 || ch < 32) {
            return true;
        }
        for (int i = 0; i < RESERVESNUM; i++) {
            if (ch == reserve[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 特定的字符串中把<>符号转换成HTML的&lt;&gt;
     *
     * @param in
     * @return str
     */
    public static final String escapeHTMLTags(String in) {
        if (in == null) {
            return null;
        }
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>') {
                if (ch == '<') {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else if (ch == '>') {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(GT_ENCODE);
                }
            }
        }
        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    /**
     * 指定的字符串转换成Html 如 < " & 符号的转换
     *
     * @param in
     * @return str
     */
    public static final String escapeForXML(String in) {
        if (in == null) {
            return null;
        }
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>') {
                if (ch == '<') {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else if (ch == '&') {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(AMP_ENCODE);
                } else if (ch == '"') {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(QUOTE_ENCODE);
                }
            }
        }

        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    /**
     * 还原已被转化成的Html字符成String字符串。
     *
     * @param string
     * @return str
     */
    public static final String unescapeFromXML(String string) {
        string = replace(string, "&lt;", "<");
        string = replace(string, "&gt;", ">");
        string = replace(string, "&quot;", "\"");
        return replace(string, "&amp;", "&");
    }

    /**
     * 字符串转换成日历Calendar，但必须str为14位的字符串数字，如: 20120217140014
     *
     * @param str
     * @return Calendar
     */
    public static Calendar toTime(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() != 14) {
            return null;
        }
        Calendar time = Calendar.getInstance();
        time.clear();
        try {
            String tmp = str.substring(0, 4);
            int y = Integer.parseInt(tmp);
            tmp = str.substring(4, 6);
            int m = Integer.parseInt(tmp) - 1;
            tmp = str.substring(6, 8);
            int d = Integer.parseInt(tmp);
            tmp = str.substring(8, 10);
            int h = Integer.parseInt(tmp);
            tmp = str.substring(10, 12);
            int min = Integer.parseInt(tmp);
            tmp = str.substring(12, 14);
            int s = Integer.parseInt(tmp);
            time.set(y, m, d, h, min, s);
        } catch (NumberFormatException ex) {
            return null;
        }
        return time;
    }

    /**
     * 时间转换成字符串 采用的是默认的yyyyMMddHHmmss格式
     *
     * @param date
     * @return str
     */
    public static String dateToTimeStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return (date == null) ? "" : sdf.format(date);
    }

    /**
     * 时间转换成字符串 采用的是默认的yyyy-MM-dd HH:mm格式
     *
     * @param date
     * @return
     */
    public static String getDateText(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return (date == null) ? "" : sdf.format(date);
    }

    /**
     * 字符串转换成日历日期，但必须str为14位或8位的字符串数字，如: 20120217140014、 20120214
     *
     * @param str
     * @return Date
     */
    public static Date timeStrToDate(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        if (str.length() != 14 && str.length() != 8) {
            return null;
        }
        Calendar time = Calendar.getInstance();
        time.clear();
        try {
            String tmp = str.substring(0, 4);
            int y = Integer.parseInt(tmp);
            tmp = str.substring(4, 6);
            int m = Integer.parseInt(tmp) - 1;
            tmp = str.substring(6, 8);
            int d = Integer.parseInt(tmp);
            if (str.length() > 8) {
                tmp = str.substring(8, 10);
                int h = Integer.parseInt(tmp);
                tmp = str.substring(10, 12);
                int min = Integer.parseInt(tmp);
                tmp = str.substring(12, 14);
                int s = Integer.parseInt(tmp);
                time.set(y, m, d, h, min, s);
            } else {
                time.set(y, m, d);
            }
        } catch (NumberFormatException ex) {
            return null;
        }
        return time.getTime();
    }

    /**
     * 把指定的系统属性替换成属性值，如果${java.version} ${java.home} ${os.name} ${user.home} ...
     *
     * @param s
     * @return
     */
    public static String replaceToken(String s) {
        int startToken = s.indexOf("${");
        int endToken = s.indexOf("}", startToken);
        String token = s.substring(startToken + 2, endToken);
        StringBuffer value = new StringBuffer();
        value.append(s.substring(0, startToken));
        value.append(System.getProperty(token));
        value.append(s.substring(endToken + 1));
        return value.toString();
    }

    /**
     * 把文件大小B转换成KB，1024B = 1KB 注意四舍五入
     *
     * @param size long
     * @return str
     */
    public static String getFileSizeText(long size) {
        long num = size / 1024L + (long) (size % 1024L <= 0L ? 0 : 1);
        return num + "KB";
    }

    public static final synchronized String hash(String data) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                log.error("Failed to load the MD5 MessageDigest. System will be unable to function normally.", e);
            }
        }
        digest.update(data.getBytes());
        return encodeHex(digest.digest());
    }

    public static final String encodeHex(byte bytes[]) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 16) {
                buf.append("0");
            }
            buf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static final byte[] decodeHex(String hex) {
        char chars[] = hex.toCharArray();
        byte bytes[] = new byte[chars.length / 2];
        int byteCount = 0;
        for (int i = 0; i < chars.length; i += 2) {
            byte newByte = 0;
            newByte |= hexCharToByte(chars[i]);
            newByte <<= 4;
            newByte |= hexCharToByte(chars[i + 1]);
            bytes[byteCount] = newByte;
            byteCount++;
        }
        return bytes;
    }

    private static final byte hexCharToByte(char ch) {
        switch (ch) {
            case 48: // '0'
                return 0;

            case 49: // '1'
                return 1;

            case 50: // '2'
                return 2;

            case 51: // '3'
                return 3;

            case 52: // '4'
                return 4;

            case 53: // '5'
                return 5;

            case 54: // '6'
                return 6;

            case 55: // '7'
                return 7;

            case 56: // '8'
                return 8;

            case 57: // '9'
                return 9;

            case 97: // 'a'
                return 10;

            case 98: // 'b'
                return 11;

            case 99: // 'c'
                return 12;

            case 100: // 'd'
                return 13;

            case 101: // 'e'
                return 14;

            case 102: // 'f'
                return 15;

            case 65: // 'A'
                return 10;

            case 66: // 'B'
                return 11;

            case 67: // 'C'
                return 12;

            case 68: // 'D'
                return 13;

            case 69: // 'E'
                return 14;

            case 70: // 'F'
                return 15;

            case 58: // ':'
            case 59: // ';'
            case 60: // '<'
            case 61: // '='
            case 62: // '>'
            case 63: // '?'
            case 64: // '@'
            case 71: // 'G'
            case 72: // 'H'
            case 73: // 'I'
            case 74: // 'J'
            case 75: // 'K'
            case 76: // 'L'
            case 77: // 'M'
            case 78: // 'N'
            case 79: // 'O'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            case 83: // 'S'
            case 84: // 'T'
            case 85: // 'U'
            case 86: // 'V'
            case 87: // 'W'
            case 88: // 'X'
            case 89: // 'Y'
            case 90: // 'Z'
            case 91: // '['
            case 92: // '\\'
            case 93: // ']'
            case 94: // '^'
            case 95: // '_'
            case 96: // '`'
            default:
                return 0;
        }
    }

    /**
     * 字符串用64位转换
     *
     * @param data
     * @return
     */
    public static String encodeBase64(String data) {
        return encodeBase64(trim(data).getBytes());
    }

    /**
     * 字符数组用64位转换
     *
     * @param data
     * @return
     */
    public static String encodeBase64(byte data[]) {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
        for (int i = 0; i < len; i++) {
            int c = data[i] >> 2 & 0x3f;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            c = data[i] << 4 & 0x3f;
            if (++i < len) {
                c |= data[i] >> 4 & 0xf;
            }
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            if (i < len) {
                c = data[i] << 2 & 0x3f;
                if (++i < len) {
                    c |= data[i] >> 6 & 0x3;
                }
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else {
                i++;
                ret.append('=');
            }
            if (i < len) {
                c = data[i] & 0x3f;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else {
                ret.append('=');
            }
        }
        return ret.toString();
    }

    /**
     * 还原64位的字符串
     *
     * @param data
     * @return
     */
    public static String decodeBase64(String data) {
        return decodeBase64(data.getBytes());
    }

    /**
     * 还原64位的字符数组
     *
     * @param data
     * @return
     */
    public static String decodeBase64(byte data[]) {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len * 3) / 4);
        for (int i = 0; i < len; i++) {
            int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            i++;
            int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            c = c << 2 | c1 >> 4 & 0x3;
            ret.append((char) c);
            if (++i < len) {
                c = data[i];
                if (61 == c) {
                    break;
                }
                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char) c);
                c1 = c1 << 4 & 0xf0 | c >> 2 & 0xf;
                ret.append((char) c1);
            }
            if (++i >= len) {
                continue;
            }
            c1 = data[i];
            if (61 == c1) {
                break;
            }
            c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char) c1);
            c = c << 6 & 0xc0 | c1;
            ret.append((char) c);
        }
        return ret.toString();
    }

    /**
     * 字符串数组用特定的字符分割链接成字符串返回
     *
     * @param list
     * @param seperator
     * @return
     */
    public static String join(String list[], String seperator) {
        StringBuffer result = new StringBuffer();
        if (list == null || list.length <= 0) {
            return "";
        }
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                result.append(list[i].toString());
            }
            if (i != list.length - 1) {
                result.append(seperator);
            }
        }
        return result.toString();
    }

    /**
     * long数组用特定的字符分割链接成字符串返回
     *
     * @param values
     * @param seperator
     * @return
     */
    public static String join(long[] values, String seperator) {
        StringBuffer result = new StringBuffer();
        if (values == null || values.length <= 0) {
            return "";
        }
        for (int i = 0; i < values.length; i++) {
            result.append(trim(values[i]));
            if (i != values.length - 1) {
                result.append(seperator);
            }
        }
        return result.toString();
    }

    /**
     * 用gb格式 格式化 特定的字符串
     *
     * @param gbString
     * @return
     */
    public static String gbEncoding(String gbString) {
        char utfBytes[] = gbString.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * 判断是否全是ascii的字符串
     *
     * @param str
     * @return
     */
    public static boolean isASCIIString(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        byte bytes[] = str.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用source补全多少位count返回
     *
     * @param source
     * @param count
     * @return
     */
    public static String duplicate(String source, int count) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(source);
        }
        return sb.toString();
    }

    public static String fillCharAtHeadForNumber(int source, String count, char fillchr) {
        int i = getAsInt(count);
        String str = (new StringBuffer(String.valueOf(source))).toString();
        if (str.length() < i) {
            for (int j = str.length(); j < i; j++) {
                str = fillchr + str;
            }
        }
        return str;
    }

    public static String fillCharAtHead(String source, String count, char fillchr) {
        int i = getAsInt(count);
        if (source.length() < i) {
            for (int j = source.length(); j < i; j++) {
                source = fillchr + source;
            }
        }
        return source;
    }

    public static String fillCharAtTail(String source, String count, char fillchr) {
        int i = getAsInt(count);
        if (source.length() < i) {
            for (int j = source.length(); j < i; j++) {
                source = source + fillchr;
            }
        }
        return source;
    }

    public static String pad(String words, String filling, int length) {
        for (int i = words.length(); i < length; i++) {
            words = filling + words;
        }
        return words;
    }

    public static String rpad(String words, String filling, int length) {
        for (int i = words.length(); i < length; i++) {
            words = words + filling;
        }
        return words;
    }

    public static boolean in(String str, String array[]) {
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                if (str == null && array[i] == null) {
                    return true;
                }
                if (str != null && str.equals(array[i])) {
                    return true;
                }
            }
        } else {
            return str == null;
        }

        return false;
    }

    public static String base64ToString(String base64str, boolean isTrim) {
        if (isTrim) {
            return trim(new String(Base64.decodeBase64(trim(base64str))));
        } else {
            return new String(Base64.decodeBase64(base64str));
        }
    }

    public static String stringToBase64(String str, boolean isTrim) {
        if (str == null) {
            str = "";
        }
        if (isTrim) {
            str = trim(str);
        }
        return Base64.encodeBase64String(str.getBytes());
    }

    public static String base64ToString(String base64str) {
        return new String(Base64.decodeBase64(base64str));
    }

    public static String stringToBase64(String str) {
        if (str == null) {
            str = "";
        }
        return Base64.encodeBase64String(str.getBytes());
    }

    public static byte[] base64ToByte(String base64str) {
        return Base64.decodeBase64(base64str);
    }

    public static String byteToBase64(byte bt[]) {
        return Base64.encodeBase64String(bt);
    }

    public static int indexof(String strs[], String str) {
        if (str == null || strs == null) {
            return -1;
        }
        for (int i = 0; i < strs.length; i++) {
            if (str.equals(strs[i])) {
                return i;
            }
        }

        return -1;
    }

    public static boolean checkIntercross(String strs1[], String strs2[]) {
        if (strs1 == null || strs2 == null) {
            return false;
        }
        for (int i = 0; i < strs1.length; i++) {
            for (int k = 0; k < strs2.length; k++) {
                if (strs1[i].equals(strs2[k])) {
                    return true;
                }
            }

        }

        return false;
    }

    public static String acfSubstring(String s, int j) throws IOException {
        if (s == null) {
            return "";
        }
        int k = 0;
        int l = 0;
        for (int i1 = 0; i1 < s.length() && j > l * 2 + k; i1++) {
            if (s.charAt(i1) > '\200') {
                l++;
            } else {
                k++;
            }
        }

        if (l + k >= s.length()) {
            return s;
        }
        if (j >= l * 2 + k && l + k > 0) {
            return s.substring(0, l + k) + "...";
        }
        if (l + k > 0) {
            return s.substring(0, (l + k) - 1) + "...";
        } else {
            return s;
        }
    }

    static {
        RESERVESNUM = 12;
        reserve = new char[RESERVESNUM];
        reserve[0] = '!';
        reserve[1] = '#';
        reserve[2] = '%';
        reserve[3] = '*';
        reserve[4] = '/';
        reserve[5] = ':';
        reserve[6] = '?';
        reserve[7] = '&';
        reserve[8] = ';';
        reserve[9] = '=';
        reserve[10] = '@';
        reserve[11] = ' ';
    }

    public static boolean hasText(String str) {
        return !"".equals(nonNull(str));
    }

    /**
     * 过滤数组中重复的数值只保留其中的一个
     *
     * @param arrays
     * @return String[]
     */
    public static String[] filterDuplicate(String[] arrays) {
        if (arrays == null || arrays.length == 0) {
            return null;
        }
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < arrays.length; i++) {
            if (!list.contains(arrays[i])) {
                list.add(arrays[i]);
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * 填充空白的str为newstr
     *
     * @param str
     * @param newstr
     * @return
     */
    public static String fillblankstr(String str, String newstr) {
        return StringUtil.hasText(str) ? str : newstr;
    }

    /**
     * 判断数组是否为空
     *
     * @param arr
     * @return
     */
    public static boolean isEmptyArray(String[] arr) {
        if (arr == null || arr.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取两个数组之间的交集
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] getArraysIntersection(String[] arr1, String[] arr2) {
        if (isEmptyArray(arr1)) {
            if (isEmptyArray(arr2)) {
                return null;
            } else {
                return arr2;
            }
        } else {
            if (isEmptyArray(arr2)) {
                return arr1;
            } else {
                List<String> vallist = new ArrayList<String>();
                for (int i = 0; i < arr1.length; i++) {
                    String val = arr1[i];
                    if (in(val, arr2) && !vallist.contains(val)) {
                        vallist.add(val);
                    }
                }
                return (String[]) vallist.toArray(new String[0]);
            }
        }
    }


    public static String firstCharToLowCase(String str) {
        String result = "";
        if (str != null && str.length() > 0) {
            String firstChar = str.substring(0, 1);
            firstChar = firstChar.toLowerCase();
            result = (new StringBuffer(String.valueOf(firstChar))).append(str.substring(1, str.length())).toString();
        }
        return result;
    }

    public static String firstCharToUpperCase(String str) {
        String result = "";
        if (str != null && str.length() > 0) {
            String firstChar = str.substring(0, 1);
            firstChar = firstChar.toUpperCase();
            result = (new StringBuffer(String.valueOf(firstChar))).append(str.substring(1, str.length())).toString();
        }
        return result;
    }

    /**
     * 将一个null转化为空串
     *
     * @param obj
     * @return
     */
    public static String null2String(Object obj) {
        if (obj == null) {
            return "";
        } else if ("null".equals(obj)) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     * 主要用来去掉来自字符串的空白
     *
     * @param str 要检查的字符串
     * @return 去掉空格的字符串
     * @see Character#isWhitespace
     */
    public static String trimLeadingWhitespace(String str) {
        if (str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    /**
     * 将一个对象输出格式化已delim为分割的字符串
     *
     * @param arr
     * @param delim
     * @return
     */
    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (arr == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    /**
     * 解析给定的字符串添加到StringTokenizer数组中 省略空的令牌.
     *
     * @param str        字符串令牌
     * @param delimiters 分割符
     * @return 返回一个数组令牌
     */
    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * 解析给定的字符串添加到StringTokenizer数组中
     *
     * @param str
     * @param delimiters
     * @param trimTokens
     * @param ignoreEmptyTokens
     * @return
     */
    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
                                                 boolean ignoreEmptyTokens) {

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return (String[]) tokens.toArray(new String[tokens.size()]);
    }

    @SuppressWarnings("rawtypes")
    public static String join(List as, String s) {
        StringBuffer stringbuffer = new StringBuffer();
        if (null == as) {
            return "";
        }
        for (int j = 0, len = as.size(); j < len; j++) {
            if (null != as.get(j)) {
                stringbuffer.append(as.get(j).toString());
            }
            if (j != len - 1) {
                stringbuffer.append(s);
            }
        }

        return stringbuffer.toString();
    }

    public static String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static final double getDouble(String s) throws Exception, NumberFormatException {
        if (s == null) {
            throw new Exception("getDouble(String strName):Input value is NULL!");
        }
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException numberformatexception) {
            return 0.0D;
        }
    }

    public static final int getInt(HttpServletRequest httpservletrequest, String s, int i) {
        if (httpservletrequest.getParameter(s) != null) {
            try {
                int j = Integer.parseInt(httpservletrequest.getParameter(s));
                return j;
            } catch (NumberFormatException numberformatexception) {
                return 0;
            }
        }
        return i;
    }

    /**
     * 保留小数点后两位
     *
     * @param amount
     * @return
     */
    public static String getNum2Point(Object amount) {
        String result = "0.00";
        String zjc = (String.valueOf(amount == null ? "0.00" : amount));
        Double dzjc = Double.parseDouble(zjc);
        DecimalFormat bzjc = new DecimalFormat("#.00");
        if (zjc.indexOf(".") > 0) {
            result = bzjc.format(dzjc);
        } else {
            result = zjc;
        }
        if (".00".equals(result) || "0".equals(result)) {
            result = "0.00";
        }
        return result;
    }

    public static String nullToEmpty(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    public static String nullToZero(Object obj) {
        return obj == null ? "0" : String.valueOf(obj);
    }

    public static String getAsAsString(Object obj, String defaultStr) {
        return (obj == null || "".equals(obj)) ? defaultStr : String.valueOf(obj);
    }

    /**
     * 获取值：notEmpty返回str，否则返回defaultStr
     *
     * @param str
     * @param defaultStr
     * @return
     */
    public static String getOrDefault(String str, String defaultStr) {
        return isNotEmpty(str) ? str : defaultStr;
    }

    public static String getOrDefault(String str, Function<String, String> fun, String defaultStr) {
        if (isNotEmpty(str)) {
            return fun.apply(str);
        }
        return defaultStr;
    }

    /**
     * 获取值：notEmpty返回str，否则返回null
     *
     * @param str
     * @return
     */
    public static String getOrNull(String str) {
        return getOrDefault(str, null);
    }

    public static String emptyToZero(Object obj) {
        return (null == obj || "".equals(obj)) ? "0" : String.valueOf(obj);
    }

    /**
     * 保留两位小数
     *
     * @param obj
     * @return
     */
    public static String null2Zero(Object obj) {
        return obj == null ? "0.00" : String.valueOf(obj);
    }

    public static boolean isNotEmpty(String... arr) {
        for (String s : arr) {
            if (isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s.trim());
    }

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim()) || "null".equalsIgnoreCase(s);
    }

    public static boolean isEmpty(String... arr) {
        return !isNotEmpty(arr);
    }

    /**
     * 判断字符串是否为空
     *
     * @param args
     * @return
     */
    public static boolean isAllEmpty(String... args) {
        for (String str : args) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String nullToEmptyArray(Object o) {
        if (o == null) {
            return "[]";
        }
        return String.valueOf(o);
    }

    // 将Unicode码转换为中文
    public static String tozhCN(String unicode) {
        StringBuffer gbk = new StringBuffer();
        String hex[] = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) { // 注意要从 1 开始，而不是从0开始。第一个是空。
            int data = Integer.parseInt(hex[i], 16); // 将16进制数转换为 10进制的数据。
            gbk.append((char) data); // 强制转换为char类型就是我们的中文字符了。
        }
        return gbk.toString();
    }

    // 将字符串转换为Unicode码
    public static String toUnicode(String zhStr) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < zhStr.length(); i++) {
            char c = zhStr.charAt(i);
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    public static Float getAsFloat(String str) {
        return getAsFloat(str, null);
    }

    public static Float getAsFloat(String str, Float defaultV) {

        if (StringUtils.isBlank(str)) {
            return defaultV;
        }

        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
        }

        return defaultV;
    }

    public static double getAsDouble(String str) {
        return getAsDouble(str, 0d);
    }

    public static double getAsDouble(String str, double defaultv) {
        if (str == null || "".equals(str)) {
            return defaultv;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defaultv;
        }
    }

    /**
     * 生成多位随机数(validateCode()方法有时未生成指定位的随机数)
     *
     * @param digCount 位数
     * @return
     */
    public static String getRandomNumber(int digCount) {
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++) {
            sb.append((char) ('0' + new Random().nextInt(10)));
        }
        return sb.toString();
    }

    /**
     * <b>功能：</b> 根据一个数组形式的tree对象，返回json形式数据 <br>
     * <b>日期：</b> 2013-9-29 上午10:03:35 <br>
     *
     * @param list
     * @param parentid
     * @param text
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getJsonTree(List<Map> list, int parentid, String text) {
        StringBuffer resultJson = new StringBuffer();
        resultJson.append("[");
        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            resultJson.append("{");
            resultJson.append("id:").append(map.get("id")).append(",");
            resultJson.append("pId:").append(map.get("parentid")).append(",");
            if ("-1".equals(map.get("parentid"))) {
                resultJson.append("open:").append("false,");
            }
            resultJson.append("name:\"").append(map.get(text)).append("\"");
            resultJson.append("}");
            if (i < list.size() - 1) {
                resultJson.append(",");
            }
        }
        resultJson.append("]");
        return resultJson.toString();
    }

    /**
     * <b>功能：</b> 过滤List集合相同元素 <br>
     * <b>日期：</b> 2013-6-21 下午06:59:41 <br>
     *
     * @param list
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List removeDuplicate(List list) {
        HashSet set = new HashSet(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    /**
     * 根据带标签格式的字符串生成内容
     *
     * @param formattedContent 包含标签格式的字符串
     * @param params           动态数据(替换formattedContent中的${key}标签)
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getTextByFormattedContent(String formattedContent, Map<Object, Object> params) {
        if (params != null) {
            Iterator<Map.Entry<Object, Object>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                formattedContent = formattedContent.replace("${" + key + "}", value);
            }
        }
        return formattedContent;
    }

    @SuppressWarnings("rawtypes")
    public static String getContentByFormatted(String formattedContent, Map<String, String> params) {
        if (params != null) {
            Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                formattedContent = formattedContent.replace("${" + key + "}", value);
            }
        }
        return formattedContent;
    }

    /**
     * 根据身份证号查询年龄
     *
     * @param idCard
     * @return
     */
    public static int getAgeFromIdCard(String idCard) {
        Integer year = Integer.valueOf(idCard.substring(6, 10));
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }

    /**
     * 替换like通配符
     *
     * @param str
     * @return
     */
    public static String filterExpr(String str) {
        return str.replace("_", "\\_").replace("%", "\\%");
    }

    /**
     * XSS过滤
     *
     * @param value
     * @return
     */
    public static String cleanXss(String value) {
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    private static final Pattern PATTERN = Pattern.compile("<[^>]+>");


    /**
     * XSS还原
     *
     * @param value
     * @return
     */
    public static String returnXss(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }

        value = value.replaceAll("& lt;", "<").replaceAll("& gt;", ">");
        value = value.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        value = value.replaceAll("& #40;", " \\(").replaceAll("& #41;", "\\)");
        value = value.replaceAll("& #39;", "'");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll(";", "");
        value = value.replaceAll("&nbsp", "").replaceAll("&amp", "");
        value = value.replaceAll("<[^>]+>", "");
        value = value.replaceAll("\\s*|\t|\r|\n", "");
        return value;
    }

    /**
     * 全角转半角
     */
    public static String toDBC(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char[] strChar = str.toCharArray();
        for (int i = 0; i < strChar.length; i++) {
            if (strChar[i] == '\u3000') {
                strChar[i] = ' ';
            } else if (strChar[i] > '\uFF00' && strChar[i] < '\uFF5F') {
                strChar[i] = (char) (strChar[i] - 65248);
            }
        }

        return new String(strChar);
    }

    /**
     * 移除换行、回车、制表符,替换成""
     *
     * @param value
     * @return
     */
    public static String trimLineEnterCharacter(String value) {

        if (value == null || "".equals(value)) {
            return "";
        }

        Matcher matcher = TRIM_LINE_ENTER_CHARACTER_PATTERN.matcher(value);

        return matcher.replaceAll("");
    }

    /**
     * 格式化金额数据为 千分位---###.##
     *
     * @param bigDecimal
     * @param digitals   包含几位小数点
     * @return
     */
    public static String formatBigDecimal(BigDecimal bigDecimal, int digitals) {
        if (bigDecimal == null) {
            bigDecimal = BigDecimal.ZERO;
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(digitals);
        nf.setMinimumFractionDigits(digitals);
        return nf.format(bigDecimal);
    }

    /**
     * 对字符串求余
     */
    public static BigInteger remainder(String str, Integer radix, BigInteger remainder) {

        if (isEmpty(str)) {
            return null;
        }

        BigInteger result = null;

        try {
            result = new BigInteger(str, radix).remainder(remainder);
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString 原始字符串
     * @param length      指定长度
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString 原始字符串
     * @param length      指定长度
     * @param size        指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length, int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length, (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     *
     * @param str 原始字符串
     * @param f   开始位置
     * @param t   结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length()) {
            return null;
        } else if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

    /**
     *
     */

    public static boolean numStrGreaterThanZero(String str) {
        if (StringUtils.isNotEmpty(str) && NumberUtils.isCreatable(str)
                && new BigDecimal(str).compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }
        return false;
    }

    public static boolean isStrCreatable(String str) {
        if (StringUtils.isNotEmpty(str) && NumberUtils.isCreatable(str)) {
            return true;
        }
        return false;
    }

    public static String firstNonNull(String... elements) {
        for (String e : elements) {
            if (StringUtils.isNotEmpty(e)) {
                return e;
            }
        }
        return null;
    }

    public static String getNumbers(String content) {
        Matcher matcher = NUMBERS_PATTERN.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * 驼峰转小写下划线
     *
     * @param name
     * @return
     */
    public static String camelToUnderline(String name) {
        StringBuilder result = new StringBuilder(name);
        //定位
        int temp = 0;
        if (!name.contains("_")) {
            if (Character.isLowerCase(name.charAt(0))) {
                //第一个字符必须小写驼峰
                for (int i = 0; i < name.length(); i++) {
                    if (Character.isUpperCase(name.charAt(i))) {
                        result.insert(i + temp, SEPARATOR_UNDERLINE);
                        temp += 1;
                    }
                }
            }
        }
        return result.toString().toLowerCase();
    }

    /**
     * 下划线转驼峰
     *
     * @param underlineStr
     * @return
     */
    public static String underlineToCamel(String underlineStr) {

        StringBuilder camelBuilder = new StringBuilder();
        String[] camels = underlineStr.split(SEPARATOR_UNDERLINE);
        for (String name : camels) {
            if (camelBuilder.length() == 0) {
                camelBuilder.append(name.toLowerCase());
            } else {
                camelBuilder.append(name.substring(0, 1).toUpperCase());
                camelBuilder.append(name.substring(1).toLowerCase());
            }

        }

        return camelBuilder.toString();
    }

    /**
     * 内容是否包含s
     *
     * @param content 查找内容
     * @param s       被包含文本
     * @return
     */
    public static boolean contains(String content, String s) {
        if (StringUtil.isNotEmpty(content, s)) {
            return content.contains(s);
        }

        return false;
    }

    /**
     * 拼接字符串，处理null值
     *
     * @param pre
     * @param next
     * @return
     */
    public static String concat(String pre, String next) {
        return getOrDefault(pre, EMPTY).concat(getOrDefault(next, EMPTY));
    }

    public static String concatLeft(String pre, String next) {
        if (StringUtil.isNotEmpty(pre)) {
            return pre.concat(getOrDefault(next, EMPTY));
        }
        return pre;
    }

    /**
     * 删除所有的标点符号
     *
     * @param str 处理的字符串
     */
    public static String trimPunct(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("[\\pP\\p{Punct}]", "");
    }

    /**
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @param str 需要处理的字符串
     */
    public static String replaceBlank(String str) {
        if (str != null) {
            Matcher m = Pattern.compile("\\s*|\t|\r|\n").matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    /**
     * 清除html
     *
     * @param text
     * @return
     */
    public static String clearHtml(String text) {
        if (isNotEmpty(text)) {
            return text.replaceAll(HTML_ESCAPE_REGEX, StringUtil.EMPTY);
        }
        return text;
    }

    public static String deleteWhitespace(String str) {
        if (StringUtils.containsWhitespace(str)) {
            return StringUtils.deleteWhitespace(str);
        }
        return str;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        //		-439184165
        //		1347639114
        //		-810625833
        //		System.out.println("R80451c6c7a097883a97594ed9ed06af5".hashCode());
        //		System.out.println("1737e81d6af7a0-0fbb62f4e6eaf-500a1d4e-266000-1737e81d6b17b2".hashCode());
        //		System.out.println("6681d3fcd63609cf5c9bb558eead3642".hashCode());

        //System.out.println(underlineToCamel("p_channel"));
        // System.out.println(underlineToCamel("report_exppp_date"));

        System.out.println(camelToUnderline("MARRIAGE"));
        System.out.println(concatLeft("2", null));
        System.out.println(concatLeft("2", "年"));
        System.out.println(concatLeft(null, "年"));
    }

}
