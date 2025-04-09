package com.nutcracker.common.util.secret;


/**
 * 16进制工具类
 *
 * @author 胡桃夹子
 * @date Dec 13, 2017 1:35:26 PM
 */
public class HexUtil {

    /**
     * byte二进制 转 hex 十六进制
     *
     * @param bytes byte二进制
     * @return hex 十六进制
     * @author 胡桃夹子
     */
    public static String byte2hex(byte[] bytes) {
        return DesUtil.byte2hex(bytes);
    }

    /**
     * hex十六进制 转 byte二进制
     *
     * @param hex hex十六进制
     * @return byte二进制
     * @author 胡桃夹子
     */
    public static byte[] hex2byte(String hex) {
        if (isBlank(hex)) {
            return null;
        }
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}
