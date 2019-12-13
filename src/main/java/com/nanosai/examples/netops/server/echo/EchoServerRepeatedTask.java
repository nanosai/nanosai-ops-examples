package com.nanosai.examples.netops.server.echo;

import com.nanosai.netops.iap.IapMessage;
import com.nanosai.netops.tcp.BytesBatch;
import com.nanosai.netops.tcp.TcpMessagePort;
import com.nanosai.threadops.threadloops.IRepeatedTaskPausable;

import java.io.IOException;

public class EchoServerRepeatedTask implements IRepeatedTaskPausable {

    private TcpMessagePort tcpMessagePort = null;
    private BytesBatch incomingMessageBatch = null;

    public EchoServerRepeatedTask(TcpMessagePort tcpMessagePort, BytesBatch bytesBatch) {
        this.tcpMessagePort = tcpMessagePort;
        this.incomingMessageBatch = bytesBatch;
    }


    @Override
    public long exec() {

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
            System.out.println("Error in EchoServerRepeatedTask: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}

