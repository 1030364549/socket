package com.umf.Test;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.umf.utils.NewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/7/30 11:06
 * @Description:
 */
public class Test2 {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("12");
        list.add("");
        list.add(1);
        list.add(2);
        list.add(3);
//        list.add(2);
        try {
            System.out.println(NewUtils.judgeType(list,String.class));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
