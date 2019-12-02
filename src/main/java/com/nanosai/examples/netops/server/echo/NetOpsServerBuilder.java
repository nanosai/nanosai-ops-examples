package com.nanosai.examples.netops.server.echo;

import com.nanosai.memops.bytes.BytesAllocatorAutoDefrag;
import com.nanosai.netops.tcp.IMessageReaderFactory;
import com.nanosai.netops.tcp.TcpMessagePort;
import com.nanosai.netops.tcp.TcpServer;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class NetOpsServerBuilder extends NetOpsBuilderBase {

    public static final int DEFAULT_SOCKET_QUEUE_CAPACITY = 1024;

    public static final int DEFAULT_INBOUND_MESSAGE_BYTES_ALLOCATOR_CAPACITY  = 16 * 1024 * 1024;
    public static final int DEFAULT_OUTBOUND_MESSAGE_BYTES_ALLOCATOR_CAPACITY = 16 * 1024 * 1024;

    private TcpServer tcpServer = null;

    private TcpMessagePort tcpMessagePort = null;
    private IMessageReaderFactory messageReaderFactory = null;
    private BytesAllocatorAutoDefrag inboundMessageBytesAllocator  = null;
    private BytesAllocatorAutoDefrag outboundMessageBytesAllocator = null;


    public void createSocketQueue() {
        createSocketQueue(DEFAULT_SOCKET_QUEUE_CAPACITY);
    }

    public BlockingQueue getOrCreateSocketQueue() {
        if(this.socketQueue != null) {
            return this.socketQueue;
        }

        return createSocketQueue(DEFAULT_SOCKET_QUEUE_CAPACITY);
    }

    public TcpServer createTcpServer() {
        return createTcpServer(DEFAULT_IAP_TCP_PORT);
    }

    public TcpServer createTcpServer(int tcpPort) {
        this.tcpServer = new TcpServer(tcpPort, getOrCreateSocketQueue());

        return this.tcpServer;
    }

    public TcpServer getTcpServer() {
        return this.tcpServer;
    }

    public BytesAllocatorAutoDefrag getOrCreateInboundMessageBytesAllocator() {
        return getOrCreateInboundMessageBytesAllocator(DEFAULT_INBOUND_MESSAGE_BYTES_ALLOCATOR_CAPACITY);
    }

    public BytesAllocatorAutoDefrag getOrCreateOutboundMessageBytesAllocator() {
        return getOrCreateOutboundMessageBytesAllocator(DEFAULT_OUTBOUND_MESSAGE_BYTES_ALLOCATOR_CAPACITY);
    }

    public TcpMessagePort createTcpMessagePort() throws IOException {
        this.tcpMessagePort =
                new TcpMessagePort(
                        getOrCreateSocketQueue(),
                        getOrCreateMessageReaderFactory(),
                        getOrCreateInboundMessageBytesAllocator(),
                        getOrCreateOutboundMessageBytesAllocator()
                );

        return this.tcpMessagePort;
    }

    public TcpMessagePort getTcpMessagePort() {
        return this.tcpMessagePort;
    }

    public TcpMessagePort getOrCreateTcpMessagePort() throws IOException {
        if(this.tcpMessagePort != null) {
            return this.tcpMessagePort;
        }
        return createTcpMessagePort();
    }
}
