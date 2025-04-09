package com.nutcracker.common.util.secret;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA加解密工具类
 *
 * @author 胡桃夹子
 * @date 2021/11/17 15:51
 */
@Slf4j
public class RsaUtil {

    private final static String PUBLIC_KEY_STRING = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIrN6qD1lqJ5QIljmpH7AxCocyy9RW8uWwM3dNATVzFw7UE2Kt+NzZFasRFIg/RxVfvMHhpQ8v12K9q8Vd9+m2cCAwEAAQ==";
    private final static String PRIVATE_KEY_STRING = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAis3qoPWWonlAiWOakfsDEKhzLL1Fby5bAzd00BNXMXDtQTYq343NkVqxEUiD9HFV+8weGlDy/XYr2rxV336bZwIDAQABAkA4hQyrKhWCZxkuWI6SLsHawJzVdOSXFyscLjK0n4t7p8KF3Mxme3tJpH4tMw7r08newkH5EI7Aaha5gBD9nz2hAiEA3dZfMgJARoYQdy+0VxUOXpozdTpyHNdtgch0ctIXA6kCIQCgLhV6Vs86SfkXAPl+8TQi0DPx4lI5MnBuVHIwAsEQjwIgBeZgq8TRjs6b+3+CTVqbAjsZqUF/rXKxT+VT64XY5NkCIFhelELWOaVywhVK2FqMP7MlIkNEFRCxHw3/UK/kFRdJAiBC6NRLJe2N+YmK34Jqj0UqRe3gRldUsBLpFeDCX9dasA==";

    /**
     * 随机生成密钥对
     */
    public static void generateKey() throws RuntimeException {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(512, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
            // 将公钥和私钥保存到Map
            //0表示公钥
            log.info("publicKeyString:{}", publicKeyString);
            //1表示私钥
            log.info("privateKeyString:{}", privateKeyString);
        } catch (Exception e) {
            log.error("# 生成密钥对异常,error msg={}", e.getLocalizedMessage());
            throw new RuntimeException("生成密钥对失败");
        }
    }

    /**
     * RSA公钥加密
     *
     * @param str 加密字符串
     * @return 密文
     * @throws RuntimeException 加密过程中的异常信息
     */
    public static String encrypt(String str) throws RuntimeException {
        String outStr = "";
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(PUBLIC_KEY_STRING);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("# 加密异常,data={}", str, e);
            throw new RuntimeException("加密失败");
        }
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str 加密字符串
     * @return 铭文
     * @throws RuntimeException 解密过程中的异常信息
     */
    public static String decrypt(String str) throws RuntimeException {
        String outStr = "";
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(PRIVATE_KEY_STRING);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            log.error("# 解密异常,data={}", str, e);
            throw new RuntimeException("解密失败");
        }
        return outStr;
    }

    //public static void main(String[] args) {
    //    generateKey();
    //}
}
