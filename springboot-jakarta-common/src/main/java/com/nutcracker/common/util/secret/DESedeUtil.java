package com.nutcracker.common.util.secret;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 3DES算法工具类
 *
 * @author 胡桃夹子
 * @date 2021/11/18 18:06
 */
@Slf4j
public class DESedeUtil {

    public static final String KEY = "DAC10EE5E5C8CDE980C46D946857EADF7FD59B9EFEA48F64";

    private static final String KEY_ALGORITEM = "DESede";
    private static final String CIPHER_ALGORITEM = "DESede/ECB/PKCS5Padding";

    /**
     * 随机生成密钥
     */
    public static String generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            String key = HexUtil.byte2hex(secretKey.getEncoded());
            log.info("# key={}", key);
            return key;
        } catch (NoSuchAlgorithmException e) {
            log.error("# 随机生成密钥失败", e);
            return null;
        }
    }


    private static Key getKey(String key) throws Exception {
        byte[] keyByte = HexUtil.hex2byte(key);
        assert keyByte != null;
        DESedeKeySpec dks = new DESedeKeySpec(keyByte);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITEM);
        return skf.generateSecret(dks);
    }

    /**
     * 加密
     *
     * @param key  密钥
     * @param data 业务参数
     * @return 加密后的业务参数
     */
    public static String encrypt(String key, String data) throws RuntimeException {
        try {
            byte[] encryptByte = data.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITEM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
            return HexUtil.byte2hex(cipher.doFinal(encryptByte));
        } catch (Exception e) {
            log.error("# 加密异常,data={}", data, e);
            throw new RuntimeException("加密失败");
        }
    }

    /**
     * 解密
     *
     * @param key  密钥
     * @param data 业务参数
     * @return 解密后业务参数
     */
    public static String decrypt(String key, String data) throws RuntimeException {
        try {
            byte[] hexByte = HexUtil.hex2byte(data);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITEM);
            cipher.init(Cipher.DECRYPT_MODE, getKey(key));
            assert hexByte != null;
            return new String(cipher.doFinal(hexByte), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("# 解密异常,data={}", data, e);
            throw new RuntimeException("解密失败");
        }
    }


    //public static void main(String[] args) throws NoSuchAlgorithmException {
    //    String key = generateKey();
    //    System.out.println("key=" + key);
    //    String param = "18202146378";
    //    String encr = encrypt(key, param);
    //    String decr = decrypt(key, encr);
    //    System.out.println("key:" + key);
    //    System.out.println(param + " ==>> " + encr);
    //    System.out.println(encr + " ==>> " + decr);
    //}
}
