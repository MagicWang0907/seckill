package com.wang.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    private static final String salt = "1a2b3c4d";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * 将前端密码进行MD5加密
     * @param inputPass
     * @return 加密密码
     */
    public static String inputPassToFromPass(String inputPass){
        String src = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(src);
    }

    public static String fromPassToDBPass(String formPass,String salt){
        String src = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(src);
    }

    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass = inputPassToFromPass(inputPass);
        return fromPassToDBPass(fromPass,salt);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }

}
