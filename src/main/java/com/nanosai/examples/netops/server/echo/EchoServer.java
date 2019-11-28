package com.nanosai.examples.netops.server.echo;

import com.nanosai.memops.bytes.BytesAllocatorAutoDefrag;
import com.nanosai.netops.iap.IapMessage;
import com.nanosai.netops.tcp.BytesBatch;
import com.nanosai.netops.tcp.IapMessageReaderFactory;
import com.nanosai.netops.tcp.TcpMessagePort;
import com.nanosai.netops.tcp.TcpServer;
import com.nanosai.threadops.threadloops.ThreadLoop;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EchoServer {

    public static void main(String[] args) throws IOException {

        NetOpsServerBuilder serverBuilder = new NetOpsServerBuilder();

        startTcpServer(serverBuilder);

        TcpMessagePort tcpMessagePort = createTcpMessagePort(serverBuilder);

        BytesBatch incomingMessageBatch = new BytesBatch(16, 64);

        EchoServerThreadLoopCycle echoServerThreadLoopCycle =
                new EchoServerThreadLoopCycle(tcpMessagePort, incomingMessageBatch);

        // echo server message processing loop:

        while(true) {
            echoServerThreadLoopCycle.run();
        }



    }

    private static ThreadLoop startTcpServer(NetOpsServerBuilder serverBuilder) {

        TcpServer tcpServer = serverBuilder.createTcpServer();
        // create server
        try {
            tcpServer.init();
        } catch (IOException e) {
            System.out.println("Error initializing TcpServer: " + e.getMessage());
            throw new RuntimeException("Error initializing TcpServer: " + e.getMessage(), e);
        }

        // start threads to run it.
        return new ThreadLoop(() -> {
            try {
                tcpServer.checkForNewInboundConnections();
            } catch (IOException e) {
                System.out.println("Error checking for new connections. ");
                e.printStackTrace();
            }
        })
        .start();
    }

    private static TcpMessagePort createTcpMessagePort(NetOpsServerBuilder serverBuilder) throws IOException {
        return serverBuilder.createTcpMessagePort();
    }


}
