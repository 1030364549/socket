package com.umf.socket;

import com.umf.config.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Component
public class UMF_proRun implements CommandLineRunner {

    @Autowired
    public SocketServer socketServer;

    @Autowired
    private Properties properties;

    @Override
    public void run(String... args) throws Exception {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(properties.getPort());
        System.out.println("设备服务器已经开启, 监听端口:" + properties.getPort());
        ExecutorService executorService = Executors.newFixedThreadPool(properties.getThreadPool());   // 创建固定大小线程池
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                executorService.execute(socketServer.createTask(socket));
            } catch (Exception e) {
//                log.errinfoS(e);
            }
        }
    }




}