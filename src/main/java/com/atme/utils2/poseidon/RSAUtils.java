package com.atme.utils2.poseidon;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * RSA非对称加密工具.
 * @author amao
 * @version 1.0 2023/12/21
 * @since 1.0
 **/
public class RSAUtils {

    public static final String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuer5IjsE22MgWX/UYNvHXEaY4EG+u7EtJS38rGvSsodZksVJ6iWklLNSEYxAt19PwOMOzK8vEHQs/N8K3plbyf7gCKNYcRHiHPU9kGpB23Ti1/xzj4giv5nImUA5X3OUBFecbd+8S+bUe0GzG2R1q9gh204TYkJSvoBYIMWdjFQIDAQAB";
    public static final String priKey  = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK56vkiOwTbYyBZf9Rg28dcRpjgQb67sS0lLfysa9Kyh1mSxUnqJaSUs1IRjEC3X0/A4w7Mry8QdCz83wremVvJ/uAIo1hxEeIc9T2QakHbdOLX/HOPiCK/mciZQDlfc5QEV5xt37xL5tR7QbMbZHWr2CHbThNiQlK+gFggxZ2MVAgMBAAECgYAw4UIdoaCPdPvZCNjpB7uIvPEME861+oy84+GkedDB7DTb0LGCkqxaNczzqa0Gqsg9LCnnMAXN0zUraLusJ3WD0o4bYVPwC5id8isvCf5LnF1eMgXSh2eni5u+eYzTOJhixkW0g6+4y6Id6D98Id4ZiUbPnBHGS/3HO25qFCDVoQJBAPQXuw4dCUMh4ZKbSQ4WyxUU5/0mRR0z/paysr4xjoh6p787MguTedrcohvk45C13Gu6ReRPOVCbhIfbzjR3ce8CQQC2/avWzOkR8yA3y2bwoDAmmzxWA38ERUINxx5/TLaDqTbjqPkFOr2pPBO05t1aG/3794HEBYMr1os7LKcLnu87AkEA7iu1lljYr8uAeffROdEdyW1Dy+wqpgsU0GB/LjvWsu7TfTG6bDczQE3mU8dsEMiS0f7IsbRn3XJRx+q+8szVuwJBALOSC2cmfjEXHK6j88h96cJ446SkmahfLorGs67IYPKiwzJ1RqlTE3tXcR88zkr23S69m6H4ptgGGWtgTiyhMrECQDw6QCQ6vUsYkbkp40nfilf5BcwoackISeGxnfgzGclzVinGKltHV001mIWUJTdczNF4DWBOtzXVhN0EzUtoqBU=";

    /**
     * 私钥解密
     * @param code 待解码对象
     * @param privateKey 私钥
     * @return 解密后的字符串明文
     */
    public static String decrypt(String code, String privateKey) throws Exception{
        byte[] decode = Base64Utils.decode(code.getBytes(StandardCharsets.UTF_8));
        RSAPrivateCrtKey priKey = (RSAPrivateCrtKey) KeyFactory.getInstance("RSA").generatePrivate(
                new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey.getBytes())));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,priKey);

        //解密每次最多128
        return getDivide(decode, 128,v-> {
            try {
                return new String(cipher.doFinal(v));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        });
    }

    /**
     * 公钥加密
     * @param value 待加密字符串
     * @param publicKey 公钥
     * @return 加密后的字符串-base64编码
     */
    public static String encrypt(String value, String publicKey) {
        try {
            byte[] decoded = Base64Utils.decode(publicKey.getBytes());
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,pubKey);
            byte[] inputArray = value.getBytes(StandardCharsets.UTF_8);
            int length = inputArray.length;
            int maxLength=117;
            int offset=0;
            byte[] resultBytes={};
            byte[] cache={};
            while (length-offset>0){
                //比117大
                if (length - offset > maxLength) {
                    cache = cipher.doFinal(inputArray, offset, maxLength);
                    offset += maxLength;
                } else {
                    //比117小就是最后一次
                    cache = cipher.doFinal(inputArray, offset, length - offset);
                    offset = length;
                }
                //拷贝到扩容的结果数组里
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                //拷贝缓存中的数据到结果数组
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
            return  Base64Utils.encodeToString(resultBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getDivide(byte[] src, int maxLength, Function<byte[],String> doing){
        //计算需要几个数组 10/3=3
        int time = src.length / maxLength;
        List<byte[]> result=new ArrayList<>();
        StringBuffer sbf=new StringBuffer();
        for (int i = 0; i < time; i++) {
            //拷贝值
            byte[] cache=new byte[maxLength];
            //拷贝旧值到新数组 源 源位置 目标 目标位置 总长度
            System.arraycopy(src, i * maxLength, cache, 0, maxLength);
            result.add(cache);
            sbf.append(doing.apply(cache));
        }
        //是否拷贝完成
        int total = time * maxLength;
        if (src.length>total){
            //拷贝值
            int surplus = src.length - total;
            byte[] cache=new byte[surplus];
            //拷贝旧值到新数组 源 源位置 目标 目标位置 总长度
            System.arraycopy(src, total, cache, 0, surplus);
            result.add(cache);
            sbf.append(doing.apply(cache));
        }
        return sbf.toString();
    }

    public static void main(String[] args) {
        try {
            String encrypt = RSAUtils.encrypt("DEUs9AH9dtcqUf4EcMSF", pubKey);
            System.out.println(encrypt);
            String decrypt = decrypt(encrypt, priKey);
            System.out.println(decrypt);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
