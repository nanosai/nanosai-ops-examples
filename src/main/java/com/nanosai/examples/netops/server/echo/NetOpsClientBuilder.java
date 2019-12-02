package com.nanosai.examples.netops.server.echo;

import com.nanosai.memops.bytes.BytesAllocatorAutoDefrag;
import com.nanosai.netops.tcp.TcpMessagePort;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class NetOpsClientBuilder extends NetOpsBuilderBase {

    public static final int DEFAULT_SOCKET_QUEUE_CAPACITY = 16;

    public static final int DEFAULT_INBOUND_MESSAGE_BYTES_ALLOCATOR_CAPACITY  = 1 * 1024 * 1024;
    public static final int DEFAULT_OUTBOUND_MESSAGE_BYTES_ALLOCATOR_CAPACITY = 1 * 1024 * 1024;

    private TcpMessagePort tcpMessagePort = null;


    public void createSocketQueue() {
        createSocketQueue(DEFAULT_SOCKET_QUEUE_CAPACITY);
    }

    public BlockingQueue getOrCreateSocketQueue() {
        if(this.socketQueue != null) {
            return this.socketQueue;
        }

        return createSocketQueue(DEFAULT_SOCKET_QUEUE_CAPACITY);
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


    public BytesAllocatorAutoDefrag getOrCreateInboundMessageBytesAllocator() {
        return getOrCreateInboundMessageBytesAllocator(DEFAULT_INBOUND_MESSAGE_BYTES_ALLOCATOR_CAPACITY);
    }

    public BytesAllocatorAutoDefrag getOrCreateOutboundMessageBytesAllocator() {
        return getOrCreateOutboundMessageBytesAllocator(DEFAULT_OUTBOUND_MESSAGE_BYTES_ALLOCATOR_CAPACITY);
    }

}
