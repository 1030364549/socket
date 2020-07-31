package com.umf.socket;

import com.umf.config.Config;
import com.umf.socket.server.SocketServer;
import com.umf.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Component
@Order(value = 1)
public class UMF_proRun implements CommandLineRunner {

    @Autowired
    public LogUtil log;
    @Autowired
    public SocketServer socketServer;
    @Autowired
    private Config config;

    @Override
    public void run(String... args) throws Exception {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(config.getPort());
        System.out.println("设备服务器已经开启, 监听端口:" + config.getPort());
        // 创建对应的线程池的大小
        ExecutorService executorService = Executors.newFixedThreadPool(config.getThreadPool());   // 创建固定大小线程池
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                executorService.execute(socketServer.createTask(socket));

            } catch (Exception e) {
                e.printStackTrace();
                log.errorE(e);
            }
        }
    }
}