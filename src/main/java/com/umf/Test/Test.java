package com.umf.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/7/9 15:47
 * @Description:
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        log.info("这是log4j2的日志测试，info级别");
        log.warn("这是log4j2的日志测试，warn级别");
        log.error("这是log4j2的日志测试，error级别");

        //如果在日志输出中想携带参数的化，可以这样设置
        String bug = "约翰·冯·诺依曼 保佑，永无BUG";
        log.info("这是带参数的输出格式:{}",bug);
    }
}
