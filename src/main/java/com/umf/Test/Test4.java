package com.umf.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/8/13 10:35
 * @Description:
 */
public class Test4 {
    public static void main(String[] args) {
        String s = "123456789abcde , hhhh";
        String abc = s.replaceFirst("abc", "5");

        String abc1 = s.replaceFirst("(.*)abc(.*)", "5");
        System.out.println(s);
        System.out.println(abc);
        System.out.println(abc1);

        System.out.println("ing".equals("ing")?1:2);


    }
}
