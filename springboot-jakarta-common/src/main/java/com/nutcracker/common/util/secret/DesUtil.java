package com.nutcracker.common.util.secret;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

/**
 * DES加解密工具类，1972美国IBM研制，对称加密算法
 *
 * @author 胡桃夹子
 * @date 2021/11/17 15:56
 */
@Slf4j
public class DesUtil {

    /**
     * 加密KEY,8个长度即可
     */
    public static final String ENCRYPT_KEY = "protocol";

    /**
     * IV偏移量,8个长度即可
     */
    public static final String ENCRYPT_IV = "protocol";

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";

    /**
     * NoPadding
     * API或算法本身不对数据进行处理，加密数据由加密双方约定填补算法。例如若对字符串数据进行加解密，可以补充\0或者空格，然后trim
     * PKCS5Padding
     * 加密前：数据字节长度对8取余，余数为m，若m>0,则补足8-m个字节，字节数值为8-m，即差几个字节就补几个字节，字节数值即为补充的字节数，若为0则补充8个字节的8
     */
    private static final String CIPHER_ALGORITHM = "DES/CBC/NoPadding";

    private static Key getKey() {
        return new SecretKeySpec(DesUtil.ENCRYPT_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }

    private static AlgorithmParameterSpec getIv() {
        return new IvParameterSpec(DesUtil.ENCRYPT_IV.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 此函数是pkcs7padding填充函数
     *
     * @param data 字符参数
     * @return 填充后的字符
     */
    public static String pkcs7padding(String data) {
        int bs = 16;
        int padding = bs - (data.length() % bs);
        return data + String.valueOf((char) padding).repeat(padding);
    }

    /**
     * 加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String encrypt(String data) throws RuntimeException {
        try {
            String input = pkcs7padding(data);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = input.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIv());

            byte[] encrypted = cipher.doFinal(plaintext);
            String hexStr = byte2hex(encrypted);
            return string2base64(hexStr);
        } catch (Exception e) {
            log.error("# 加密异常,data={}", data, e);
            throw new RuntimeException("加密失败");
        }
    }

    /**
     * 解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decrypt(String data) throws RuntimeException {
        try {
            String base64 = base642string(data);
            byte[] hexStr = hex2byte(base64);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey(), getIv());
            assert hexStr != null;
            byte[] original = cipher.doFinal(hexStr);
            return new String(original, StandardCharsets.UTF_8).trim();
        } catch (Exception e) {
            log.error("# 解密异常,data={}", data, e);
            throw new RuntimeException("加密失败");
        }
    }


    public static String byte2hex(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();
        for (byte aByte : bytes) {
            hexStr.append(Integer.toHexString(0x0100 + (aByte & 0x00FF)).substring(1).toUpperCase());
        }
        return hexStr.toString();
    }

    public static byte[] hex2byte(String hex) {
        if (HexUtil.isBlank(hex)) {
            return null;
        }
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }

    public static String base642string(String str) {
        return new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
    }

    public static String string2base64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

}
