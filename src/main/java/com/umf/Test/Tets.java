package com.umf.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/9/28 17:28
 * @Description:
 */
public class Tets {
    public static void main(String[] args) throws ParseException {

//        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = simpleDateFormat.parse("2020-09-28 17:31:00");
//        long ts = date.getTime();
//        res = String.valueOf(ts);
//        System.out.println(res);


        long timeStamp = System.currentTimeMillis();
        System.out.println(timeStamp);
        System.out.println(String.valueOf(timeStamp).substring(0,10));
        System.out.println(String.valueOf(System.currentTimeMillis()).substring(0,10));
    }
}
