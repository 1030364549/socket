package com.umf.Test;

import com.umf.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/8/4 17:57
 * @Description:
 */
public class Test3 {
    public static void main(String[] args) throws ParseException {
        System.out.println(DateUtil.diffDays("2021-08-03 17:58:00",null));
        System.out.println(DateUtil.diffDaysByMill("2021-08-03 17:58:00",null));

        System.out.println(CollectionUtils.isEmpty(new ArrayList()));
        System.out.println(CollectionUtils.isEmpty(null));
        System.out.println(CollectionUtils.isEmpty(new ArrayList(){{add("1");}}));
    }
}
