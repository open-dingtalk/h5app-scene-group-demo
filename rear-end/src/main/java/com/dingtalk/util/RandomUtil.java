package com.dingtalk.util;

import java.util.Random;

/**
 * 随机数工具类
 */
public class RandomUtil {

    /**
     * 基础字符
     */
    private static final String BASE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 获取指定长度随机串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE.length());
            buffer.append(BASE.charAt(number));
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String randomString = getRandomString(12432);
        System.out.println(randomString);

    }
}
