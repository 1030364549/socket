package com.umf.Test;

import com.umf.utils.EncryUtil;
import com.umf.utils.LoFunction;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Package com.umf.socket
 * @Author:LiuYuKun
 * @Date:2020/7/8 14:47
 * @Description:
 */
public class TestCli {

    private static LoFunction lo = new LoFunction();
    private static EncryUtil eu = new EncryUtil();

    public static void main(String[] args) throws Exception {
        Map<String, String> data = new TreeMap<>();
//        data.put("serial", "X10000048296");     // 交易流水号
//        data.put("merno", "100000000167425");      // 商户编号
//        data.put("posno", "910000800036");      // 终端号
//        data.put("msgtype", "H201");// 交易类型 0 线下 1 线上
//        data.put("localdate", "2020-04-03");     // 时间
        data.put("serial", "X100034286328");     // 交易流水号
        data.put("merno", "100000000167702");      // 商户编号
        data.put("posno", "100001394407");      // 终端号
        data.put("msgtype", "H007");// 交易类型 0 线下 1 线上
        data.put("signature", getSign(data));
        String reqdata= lo.changeMapToJson(data);
        byte[] resByte = reqdata.getBytes();
        byte[] reqPack = new byte[2 + resByte.length];
        int lenByte = reqPack.length - 2; // 获得报文的长度(不包括2个byte的长度)
        String str = lo.initSize(Integer.toString(lenByte, 16));
        byte[] lenByteHex = lo.hexStr2Bytes(str);
        byte[] reqBody = lo.assemble(lenByteHex, resByte);
        byte[] resp=connServer(reqBody);
        String pack = lo.byte2HexStr(resp);
        String respStr = lo.hexStr2Str(pack);
        System.out.println(respStr);
        Thread.sleep(800);
    }


    public static byte[] connServer(byte[] reqPack) throws Exception {
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            String ipAddress ="127.0.0.1";
            int port = 2323;
            socket = new Socket(); // 建立与服务器端的链接
            socket.connect(new InetSocketAddress(ipAddress, port), 60000); // 连接超时设置
            socket.setSoTimeout(60000); // 读写超时设置
            os = socket.getOutputStream();
            is = socket.getInputStream();
            os.write(reqPack);
            System.out.println("请求包：" + lo.byte2HexStr(reqPack));
            os.flush();
            // 接收服务器的响应
            byte[] lenByte = new byte[2];
            is.read(lenByte, 0, 2);
            int len = (int) lo.byteArrayToShort(lenByte);
            byte[] bb = new byte[len];
            is.read(bb, 0, len);
            byte[] respPack = new byte[lenByte.length + bb.length];
            System.arraycopy(lenByte, 0, respPack, 0, 2);
            System.arraycopy(bb, 0, respPack, lenByte.length, bb.length);
            return lo.checkByte(bb) ? null : bb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(socket, is, os);
        }
    }


    public static String getSign(Map<String, String> map) throws Exception{
        if(map.get("signature")!=null&&!"".equals(map.get("signature"))){
            map.remove("signature");
        }
        StringBuffer sb = new StringBuffer();
        map.forEach((key,value) -> sb.append(key).append("=").append(value).append("&"));
        sb.delete(sb.length()-1,sb.length());
        System.out.println(sb);
        return eu.encryptMD5(sb.toString());
    }

    public static void closeIO(Socket socket, InputStream input, OutputStream output) {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
