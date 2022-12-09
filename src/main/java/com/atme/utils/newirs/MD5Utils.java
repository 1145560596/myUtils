package com.atme.utils.newirs;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    /**
     * 将指定的字符串用MD5加密
     * @param originStr 需要加密的字符串
     * @return 加密后的字符串
     */

    public static String encodeByMD5(String originStr) {

        String result = null;

        char hexDigits[] = {//用来将字节转换成 16 进制表示的字符

                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        if(originStr != null){

            try {

                //返回实现指定摘要算法的 MessageDigest 对象

                MessageDigest md = MessageDigest.getInstance("MD5");

                //使用utf-8编码将originStr字符串编码并保存到source字节数组

                byte[] source = originStr.getBytes("utf-8");

                //使用指定的 byte 数组更新摘要

                md.update(source);

                //通过执行诸如填充之类的最终操作完成哈希计算，结果是一个128位的长整数

                byte[] tmp = md.digest();

                //用16进制数表示需要32位

                char[] str = new char[32];

                for(int i=0,j=0; i < 16; i++){

                    //j表示转换结果中对应的字符位置

                    //从第一个字节开始，对 MD5 的每一个字节

                    //转换成 16 进制字符

                    byte b = tmp[i];

                    //取字节中高 4 位的数字转换

                    //无符号右移运算符>>> ，它总是在左边补0

                    //0x代表它后面的是十六进制的数字. f转换成十进制就是15

                    str[j++] = hexDigits[b>>>4 & 0xf];

                    // 取字节中低 4 位的数字转换

                    str[j++] = hexDigits[b&0xf];

                }

                result = new String(str);//结果转换成字符串用于返回

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static final String str2MD5(String _str) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            byte[] strTemp = _str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for(int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            return null;
        }
    }


    public static void main(String[] args){
        System.out.println(MD5Utils.encodeByMD5("123456"));
    }

    public static String ecodeByMD5(String originstr) {
        String result = null;
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        if (originstr != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] source = originstr.getBytes("utf-8");
                md.update(source);
                byte[] tmp = md.digest();
                char[] str = new char[32];
                int i = 0;

                for(int var8 = 0; i < 16; ++i) {
                    byte b = tmp[i];
                    str[var8++] = hexDigits[b >>> 4 & 15];
                    str[var8++] = hexDigits[b & 15];
                }

                result = new String(str);
            } catch (NoSuchAlgorithmException var10) {
                var10.printStackTrace();
            } catch (UnsupportedEncodingException var11) {
                var11.printStackTrace();
            }
        }

        return result;
    }

}
