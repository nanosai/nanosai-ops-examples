package com.nanosai.examples.netops.server.echo;

import com.nanosai.memops.bytes.BytesAllocatorAutoDefrag;
import com.nanosai.netops.tcp.IMessageReaderFactory;
import com.nanosai.netops.tcp.IapMessageReaderFactory;
import com.nanosai.netops.tcp.TcpMessagePort;
import com.nanosai.netops.tcp.TcpServer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetOpsBuilderBase {

    public static final int DEFAULT_IAP_TCP_PORT          = 1111;

    protected BlockingQueue socketQueue = null;

    protected TcpMessagePort tcpMessagePort = null;
    protected IMessageReaderFactory messageReaderFactory = null;
    protected BytesAllocatorAutoDefrag inboundMessageBytesAllocator  = null;
    protected BytesAllocatorAutoDefrag outboundMessageBytesAllocator = null;


    public BlockingQueue createSocketQueue(int capacity) {
        this.socketQueue = new ArrayBlockingQueue(capacity);
        return this.socketQueue;
    }

    public BlockingQueue getSocketQueue() {
        return this.socketQueue;
    }

    public BlockingQueue getOrCreateSocketQueue(int capacity) {
        if(this.socketQueue != null) {
            return this.socketQueue;
        }

        return createSocketQueue(capacity);
    }


    public BytesAllocatorAutoDefrag setInboundMessageBytesAllocator(BytesAllocatorAutoDefrag bytesAllocator) {
        this.inboundMessageBytesAllocator = bytesAllocator;
        return this.inboundMessageBytesAllocator;
    }

    public IMessageReaderFactory getOrCreateMessageReaderFactory() {
        if(this.messageReaderFactory != null) {
            return this.messageReaderFactory;
        }

        return setMessageReaderFactory(new IapMessageReaderFactory());
    }

    public IMessageReaderFactory setMessageReaderFactory(IMessageReaderFactory messageReaderFactory) {
        this.messageReaderFactory = messageReaderFactory;
        return this.messageReaderFactory;
    }


    public BytesAllocatorAutoDefrag getInboundMessageBytesAllocator() {
        return this.inboundMessageBytesAllocator;
    }

    public BytesAllocatorAutoDefrag createInboundMessageBytesAllocator(int capacity) {
        return setInboundMessageBytesAllocator(new BytesAllocatorAutoDefrag(new byte[capacity]));
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

    public BytesAllocatorAutoDefrag getOrCreateOutboundMessageBytesAllocator(int capacity) {
        if(this.outboundMessageBytesAllocator != null) {
            return this.outboundMessageBytesAllocator;
        }
        return createOutboundMessageBytesAllocator(capacity);
    }

}
