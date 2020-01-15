package com.nanosai.examples.netops.server.echo;

import com.nanosai.netops.tcp.BytesBatch;
import com.nanosai.netops.tcp.TcpMessagePort;
import com.nanosai.netops.tcp.TcpServer;
import com.nanosai.threadops.threadloops.IThreadLoop;
import com.nanosai.threadops.threadloops.RepeatedTaskExecutorPausable;
import com.nanosai.threadops.threadloops.ThreadLoop;

import java.io.IOException;

public class EchoServer {

    public static void main(String[] args) throws IOException {

        NetOpsServerBuilder serverBuilder = new NetOpsServerBuilder();

        startTcpServer(serverBuilder);

        TcpMessagePort tcpMessagePort = serverBuilder.createTcpMessagePort();

        BytesBatch incomingMessageBatch = new BytesBatch(16, 64);

        EchoServerRepeatedTask echoServerRepeatedTask =
                new EchoServerRepeatedTask(tcpMessagePort, incomingMessageBatch);

        /*
        IRepeatedTaskPausable repeatedTask = () -> {
            echoServerRepeatedTask.exec();
            return 0;
        };
         */

        RepeatedTaskExecutorPausable repeatedTaskExecutor = new RepeatedTaskExecutorPausable(echoServerRepeatedTask);

        // echo server message processing loop:

        while(true) {
            repeatedTaskExecutor.exec();
        }



    }

    private static IThreadLoop startTcpServer(NetOpsServerBuilder serverBuilder) {

        TcpServer tcpServer = serverBuilder.createTcpServer();
        // create server
        try {
            tcpServer.init();
        } catch (IOException e) {
            System.out.println("Error initializing TcpServer: " + e.getMessage());
            throw new RuntimeException("Error initializing TcpServer: " + e.getMessage(), e);
        }

        // start threads to run it.
        return new ThreadLoop((threadLoop) -> () -> {
            try {
                tcpServer.checkForNewInboundConnections();
            } catch (IOException e) {
                System.out.println("Error checking for new connections. ");
                e.printStackTrace();
            }
        })
        .start();
    }



}
