package com.umf.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Package com.umf.utils
 * @Author:LiuYuKun
 * @Date:2020/7/9 16:17
 * @Description:
 */
@Slf4j
public class LogUtil {
    private StringBuffer msg;
    private DateUtil du = new DateUtil();
    private LoFunction lo = new LoFunction();

//    public void info(String reqpack,Map map) throws Exception {
//        msg=new StringBuffer();
//        msg.append("************************************************************").append(InitConfig.newLine);
//        msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
//        msg.append("【请求地址】:").append(RunningData.getIp()).append(InitConfig.newLine);
//        msg.append("【交易流水】:").append(RunningData.getSerial()).append(InitConfig.newLine);
//        msg.append("【请求报文】:").append(reqpack).append(InitConfig.newLine);
//        saveLog(msg.toString(), 3);
//    }
}
