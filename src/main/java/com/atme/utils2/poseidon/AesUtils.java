package com.atme.utils2.poseidon;

/**
 * @author amao
 * @create 2023-07-26-21:06
 */
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具类
 */
public class AesUtils {
    /**
     * 日志相关
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AesUtils.class);
    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 算法定义
     */
    private static final String AES_ALGORITHM = "AES";
    /**
     * 指定填充方式
     */
    private static final String CIPHER_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * 偏移量(CBC中使用，增强加密算法强度)
     */
    private static final String IV_SEED = "ABCDEF1234123412";

    public static final String PWD = "1234123412ABCDEF";

    /**
     * AES加密
     * @param content 待加密内容
     * @param aesKey  密码
     * @return
     */
    public static String encrypt(String content, String aesKey){
        if(StringUtils.isBlank(content)){
            LOGGER.info("AES encrypt: the content is null!");
            return null;
        }
        //判断秘钥是否为16位
        if(StringUtils.isNotBlank(aesKey) && aesKey.length() == 16){
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置加密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_PADDING);

                //2、不偏移情况下
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

                //根据待加密内容生成字节数组
                byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
                //返回base64字符串
                return Base64Utils.encodeToString(encrypted);
            } catch (Exception e) {
                LOGGER.info("AES encrypt exception:" + e.getMessage());
                throw new RuntimeException(e);
            }

        }else {
            LOGGER.info("AES encrypt: the aesKey is null or error!");
            return null;
        }
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param aesKey  密码
     * @return
     */
    public static String decrypt(String content, String aesKey){
        if(StringUtils.isBlank(content)){
            LOGGER.info("AES decrypt: the content is null!");
            return null;
        }
        //判断秘钥是否为16位
        if(StringUtils.isNotBlank(aesKey) && aesKey.length() == 16){
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置解密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
                //选择解密
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);

                //先进行Base64解码
                byte[] decodeBase64 = Base64Utils.decodeFromString(content);

                //根据待解密内容进行解密
                byte[] decrypted = cipher.doFinal(decodeBase64);
                //将字节数组转成字符串
                return new String(decrypted, ENCODING);
            } catch (Exception e) {
                LOGGER.info("AES decrypt exception:" + e.getMessage());
                throw new RuntimeException(e);
            }

        }else {
            LOGGER.info("AES decrypt: the aesKey is null or error!");
            return null;
        }
    }

    public static void main(String[] args) {
        String encrypt = encrypt("rW8Xbyr5dKT2k4PX4U94", PWD);
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt, PWD);
        System.out.println(decrypt);

    }

}
