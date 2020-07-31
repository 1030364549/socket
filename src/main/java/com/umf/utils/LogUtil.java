package com.umf.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Package com.umf.utils
 * @Author:LiuYuKun
 * @Date:2020/7/9 16:17
 * @Description:
 */
@Slf4j
@Component
public class LogUtil {

    private StringBuffer msg;
    private DateUtil du = new DateUtil();
    private String newLine = System.getProperty("line.separator");

    public void info(String reqpack) throws Exception {
        msg=new StringBuffer();
        msg.append("************************************************************").append(newLine);
        msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(newLine);
        msg.append("【请求地址】:").append(RunningData.getIp()).append(newLine);
        msg.append("【交易流水】:").append(RunningData.getSerial()).append(newLine);
        msg.append("【请求报文】:").append(reqpack).append(newLine);
        log.info(msg.toString());
    }

    public void errorE(Exception e) {
        try {
            msg = new StringBuffer();
            msg.append("************************************************************").append(newLine);
            msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(newLine);
            msg.append("【交易流水】:").append(RunningData.getSerial()).append(newLine);
            msg.append("【错误信息】:").append(changeExtInfo(e)).append(newLine).append(newLine);
            log.error(msg.toString());
        } catch (Exception e1) {
            errorE(e1);
        }
    }

    public void errorS(Exception e) {
        try {
            msg = new StringBuffer();
            msg.append("************************************************************").append(newLine);
            msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(newLine);
            msg.append("【错误信息】:").append(changeExtInfo(e)).append(newLine).append(newLine);
            log.error(msg.toString());
        } catch (Exception e1) {
            errorE(e1);
        }
    }

    /** 将异常信息打印到字符串中 */
    private String changeExtInfo(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }


}
