package com.nanosai.examples.netops.server.echo;

import com.nanosai.memops.bytes.BytesAllocatorAutoDefrag;
import com.nanosai.netops.tcp.IMessageReaderFactory;
import com.nanosai.netops.tcp.IapMessageReaderFactory;
import com.nanosai.netops.tcp.TcpMessagePort;
import com.nanosai.netops.tcp.TcpServer;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetOpsServerBuilder {

    public static final int DEFAULT_SOCKET_QUEUE_CAPACITY = 1024;
    public static final int DEFAULT_IAP_TCP_PORT          = 1111;

    public static final int DEFAULT_INBOUND_MESSAGE_BYTES_ALLOCATOR_CAPATICY  = 16 * 1024 * 1024;
    public static final int DEFAULT_OUTBOUND_MESSAGE_BYTES_ALLOCATOR_CAPATICY = 16 * 1024 * 1024;

    private BlockingQueue socketQueue = null;

    private TcpServer tcpServer = null;

    private TcpMessagePort tcpMessagePort = null;
    private IMessageReaderFactory messageReaderFactory = null;
    private BytesAllocatorAutoDefrag inboundMessageBytesAllocator  = null;
    private BytesAllocatorAutoDefrag outboundMessageBytesAllocator = null;


    public void createSocketQueue() {
        createSocketQueue(DEFAULT_SOCKET_QUEUE_CAPACITY);
    }

    public BlockingQueue createSocketQueue(int capacity) {
        this.socketQueue = new ArrayBlockingQueue(capacity);
        return this.socketQueue;
    }

    public BlockingQueue getSocketQueue() {
        return this.socketQueue;
    }

    public BlockingQueue getOrCreateSocketQueue() {
        if(this.socketQueue != null) {
            return this.socketQueue;
        }

        return createSocketQueue(DEFAULT_SOCKET_QUEUE_CAPACITY);
    }

    public BlockingQueue getOrCreateSocketQueue(int capacity) {
        if(this.socketQueue != null) {
            return this.socketQueue;
        }

        return createSocketQueue(capacity);
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


    public IMessageReaderFactory setMessageReaderFactory(IMessageReaderFactory messageReaderFactory) {
        this.messageReaderFactory = messageReaderFactory;
        return this.messageReaderFactory;
    }

    public IMessageReaderFactory getOrCreateMessageReaderFactory() {
        if(this.messageReaderFactory != null) {
            return this.messageReaderFactory;
        }

        return setMessageReaderFactory(new IapMessageReaderFactory());
    }


    public BytesAllocatorAutoDefrag setInboundMessageBytesAllocator(BytesAllocatorAutoDefrag bytesAllocator) {
        this.inboundMessageBytesAllocator = bytesAllocator;
        return this.inboundMessageBytesAllocator;
    }

    public BytesAllocatorAutoDefrag createInboundMessageBytesAllocator(int capacity) {
        return setInboundMessageBytesAllocator(new BytesAllocatorAutoDefrag(new byte[capacity]));
    }

    public BytesAllocatorAutoDefrag getInboundMessageBytesAllocator() {
        return this.inboundMessageBytesAllocator;
    }

    public BytesAllocatorAutoDefrag getOrCreateInboundMessageBytesAllocator() {
        return getOrCreateInboundMessageBytesAllocator(DEFAULT_INBOUND_MESSAGE_BYTES_ALLOCATOR_CAPATICY);
    }

    public BytesAllocatorAutoDefrag getOrCreateInboundMessageBytesAllocator(int capacity) {
        if(this.inboundMessageBytesAllocator != null) {
            return this.inboundMessageBytesAllocator;
        }
        return createInboundMessageBytesAllocator(capacity);
    }


    public BytesAllocatorAutoDefrag setOutboundMessageBytesAllocator(BytesAllocatorAutoDefrag bytesAllocator) {
        this.outboundMessageBytesAllocator = bytesAllocator;
        return this.outboundMessageBytesAllocator;
    }

    public BytesAllocatorAutoDefrag createOutboundMessageBytesAllocator(int capacity) {
        return setOutboundMessageBytesAllocator(new BytesAllocatorAutoDefrag(new byte[capacity]));
    }

    public BytesAllocatorAutoDefrag getOutboundMessageBytesAllocator() {
        return this.outboundMessageBytesAllocator;
    }

    public BytesAllocatorAutoDefrag getOrCreateOutboundMessageBytesAllocator() {
        return getOrCreateOutboundMessageBytesAllocator(DEFAULT_OUTBOUND_MESSAGE_BYTES_ALLOCATOR_CAPATICY);
    }

    public BytesAllocatorAutoDefrag getOrCreateOutboundMessageBytesAllocator(int capacity) {
        if(this.outboundMessageBytesAllocator != null) {
            return this.outboundMessageBytesAllocator;
        }
        return createOutboundMessageBytesAllocator(capacity);
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
