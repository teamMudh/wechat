package com.gnnt.demo.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 创建日期:2017年12月17日
 * 创建时间:下午6:11:21
 * 创建者    :yellowcong
 * 机能概要:
 */
public class PassTest {

    public static void main(String[] args) {
        //加密方式
        String algorithmName="MD5";

        //加密的字符串
        String source="123456";

        //盐值，用于和密码混合起来用
        ByteSource salt = ByteSource.Util.bytes("admin");

        //加密的次数,可以进行多次的加密操作
        int hashIterations = 1;

        //通过SimpleHash 来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName, source, salt, hashIterations);

        System.out.println(hash.toString());
    }
}