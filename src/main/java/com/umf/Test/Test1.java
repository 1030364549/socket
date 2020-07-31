package com.umf.Test;

import com.umf.utils.NewUtils;

import java.util.Map;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/7/23 14:11
 * @Description:
 */
public class Test1 {
    public static void main(String[] args) {
        Map<String, Object> add = NewUtils.add("1", "哈哈哈", "2", "嘎嘎嘎", "3", "嘤嘤嘤");
        add.forEach((k,v) -> System.out.println(k+"="+v));
    }
}
