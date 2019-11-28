package com.nanosai.examples.netops.server.echo;

import com.nanosai.netops.iap.IapMessage;
import com.nanosai.netops.tcp.BytesBatch;
import com.nanosai.netops.tcp.TcpMessagePort;
import com.nanosai.threadops.threadloops.IThreadLoopCycle;

import java.io.IOException;

public class EchoServerThreadLoopCycle implements IThreadLoopCycle {


    private TcpMessagePort tcpMessagePort = null;
    private BytesBatch incomingMessageBatch = null;

    public EchoServerThreadLoopCycle(TcpMessagePort tcpMessagePort, BytesBatch bytesBatch) {
        this.tcpMessagePort = tcpMessagePort;
        this.incomingMessageBatch = bytesBatch;
    }


    @Override
    public long run() {

        try{
            tcpMessagePort.addSocketsFromSocketQueue();
            tcpMessagePort.readNow(incomingMessageBatch);

            for(int i=0; i < incomingMessageBatch.count; i++) {
                IapMessage incomingMessage =(IapMessage) incomingMessageBatch.blocks[i];

                //process message
                System.out.println("Processing message " + i);

                //create response
                IapMessage outgoingMessage = tcpMessagePort.getWriteMemoryBlock();
                outgoingMessage.allocate(1024);
                outgoingMessage.resetReadAndWriteIndexes();
                outgoingMessage.copyFrom(incomingMessage);
                incomingMessage.free();

                outgoingMessage.setTcpSocket(incomingMessage.getSourceTcpSocketId(), incomingMessage.getSourceTcpSocket());

                tcpMessagePort.writeNowOrEnqueue(outgoingMessage);


            }
            incomingMessageBatch.clear();

        } catch(IOException e) {
            System.out.println("Error in EchoServerThreadLoopCycle: " + e.getMessage());
            e.printStackTrace();
        }





        return 0;
    }
}

