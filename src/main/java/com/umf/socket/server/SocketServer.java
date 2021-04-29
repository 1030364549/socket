package com.umf.socket.server;


import com.umf.config.Config;
import com.umf.service.mianservice.TradingService;
import com.umf.socket.base.BaseIO;
import com.umf.utils.LoFunction;
import com.umf.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

@Component
public class SocketServer extends BaseIO {

    @Autowired
    private LogUtil log;

    @Autowired
    private Config config;

    @Autowired
    TradingService ts;

    private LoFunction loFunction = new LoFunction();

    public Runnable createTask(final Socket socket) {
        return new Runnable(){
            InputStream input = null;
            OutputStream output = null;
            public void run(){
                    try {
                        input = socket.getInputStream();
                        output = socket.getOutputStream();
                        String ip = socket.getInetAddress().toString();
                        socket.setSoTimeout(config.getTimeOut());
                        byte[] lenByte = new byte[2];
                        input.read(lenByte, 0, 2);// 读取长度
                        int len = (int) loFunction.byteArrayToShort(lenByte);
                        if(len <= 0){
                            return ;
                        }
                        byte[] bb = new byte[len];
                        int rdLen = input.read(bb, 0, len);
                        while(rdLen < len) {
                            rdLen += input.read(bb, rdLen, input.available());
                        }
                        String reqpack = loFunction.hexStr2Str(loFunction.byte2HexStr(bb));
                        System.out.println(ip);
                        System.out.println(reqpack);

                        /** 应答 */
                        ts.insRunningData(reqpack,ip);

                        /** 应答 */
//                        send_message();

                        /** 判断是否为小微商户 */
//                        if((RunningData.getLocaldate() !=null && !"".equals(RunningData.getLocaldate())) || loFunction.isXw()){
//                            ts.mainServiceXw();
//                        }else{
//                            ts.test1();
                            ts.excel();
//                            ts.mainService();
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.errorS(e);
                    }finally {
                        closeIO(socket, input, output);
                    }
                }

                /**
                 *
                 *********************************************************.<br>
                 * [方法] send_message <br>
                 * [描述] TODO(这里用一句话描述这个方法的作用) <br>
                 * [作者] UMF
                 * [时间] 2019年8月9日 下午3:48:12 <br>
                 *********************************************************.<br>
                 */
            private void send_message() throws Exception {
                String respStr = "200";
                byte[] resByte = respStr.getBytes();
                byte[] reqPack = new byte[2 + resByte.length];
                int bodyLen = reqPack.length - 2; // 获得报文的长度(不包括2个byte的长度)
                String resp = loFunction.initSize(Integer.toString(bodyLen, 16));
                byte[] lenByteHex = loFunction.hexStr2Bytes(resp);
                byte[] endRsB = loFunction.assemble(lenByteHex, resByte);
                output.write(endRsB);
                output.flush();
            }
        };


    }
}