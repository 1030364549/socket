package com.umf.Test;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/9/28 17:40
 * @Description:
 */
public class Test7 {
    public static void main(String[] args) {
        String s = DigestUtils.sha1Hex("appId=0b40ac42fed711eaa61434415db1f6ba&appOrderNo=1601021328");
        System.out.println(s);
    }
}
