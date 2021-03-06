package com.cynovan.neptune.oauth.base.utils;

import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;

public class StringLib extends org.apache.commons.lang3.StringUtils {

    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";

    /**
     * split char @
     */
    public static final String SPLIT_1 = "_";
    public static final String SPLIT_2 = ":";

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    private static final String CHAR1 = "+";
    private static final String ChAR1_ENCODE = "%20";

    public static String decodeURI(String str) {
        if (StringLib.isBlank(str)) {
            return EMPTY;
        }
        try {
            str = URLDecoder.decode(str, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean equalsAny(String key, String... values) {
        if (ArrayUtils.isNotEmpty(values)) {
            for (String str : values) {
                if (StringLib.equalsIgnoreCase(key, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String builderKey(String... values) {
        if (ArrayUtils.isNotEmpty(values)) {
            return StringLib.join(values, StringLib.SPLIT_1);
        }
        return "";
    }

    public static String encodeURI(String part) {
        try {
            return URLEncoder.encode(part, UTF_8).replace(CHAR1, ChAR1_ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return EMPTY;
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : str.toCharArray()) {
                currentLength += StringLib.toString(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }


    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = (String) request.getAttribute("X-Real-IP");
        if (StringLib.isEmpty(remoteAddr)) {
            remoteAddr = request.getHeader("X-Real-IP");
        }
        if (StringLib.isEmpty(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        }
        if (StringLib.isEmpty(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        if (StringLib.isEmpty(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }


    /**
     * convert long to binary string, empty byte will be filled with 0, like, toBinaryString(2, 4) => 0010
     *
     * @param value
     * @param length
     */
    public static String toBinaryString(long value, int length) {
        String binString = Long.toBinaryString(value);
        StringBuffer b = new StringBuffer(binString);

        while (b.length() < length) {
            b.insert(0, '0');
        }
        return b.toString();
    }

    /**
     * convert file size in byte to readable string, like xxKB, xxMB, xxGB
     *
     * @param size file size (byte)
     * @return
     */
    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
