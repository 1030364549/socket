package com.umf.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Package com.umf.socket
 * @Author:LiuYuKun
 * @Date:2020/7/8 18:09
 * @Description:
 */
public class BaseIO {

    /**
     *
     *********************************************************.<br>
     * [方法] closeIO <br>
     * [描述] TODO(关闭IO流) <br>
     * [参数] TODO(socket通讯、输入流、输出流) <br>
     * [作者] UMF
     * [时间] 2019年8月9日 下午3:47:03 <br>
     *********************************************************.<br>
     */
    protected void closeIO(Socket socket, InputStream input, OutputStream output) {
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
//            log.errinfoS(e);
        }
    }

    /**
     *
     *********************************************************.<br>
     * [方法] closeIO <br>
     * [描述] TODO(关闭IO流) <br>
     * [参数] TODO(socket通讯、输出流) <br>
     * [作者] UMF
     * [时间] 2019年8月9日 下午3:47:31 <br>
     *********************************************************.<br>
     */
    protected void closeIO(Socket socket, OutputStream output) {
        try {
            if (output != null) {
                output.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
//            log.errinfoS(e);
        }
    }
}
