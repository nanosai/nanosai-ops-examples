package com.nanosai.examples.netops.server.echo;

import com.nanosai.netops.iap.IapMessage;
import com.nanosai.netops.iap.IapMessageBase;
import com.nanosai.netops.tcp.BytesBatch;
import com.nanosai.netops.tcp.TcpMessagePort;
import com.nanosai.netops.tcp.TcpSocket;
import com.nanosai.rionops.rion.write.RionWriter;
import com.nanosai.threadops.threadloops.IRepeatedTask;

public class EchoClientRepeatedTask implements IRepeatedTask {

    private TcpMessagePort tcpMessagePort = null;
    private TcpSocket      clientSocket = null;

    private RionWriter rionWriter = new RionWriter().setNestedFieldStack(new int[2]);

    private int counter = 0;

    private BytesBatch responses = new BytesBatch(10);


    public EchoClientRepeatedTask(TcpMessagePort tcpMessagePort, TcpSocket clientSocket){
        this.tcpMessagePort = tcpMessagePort;
        this.clientSocket   = clientSocket;

    }


    @Override
    public void exec() {
        try{
            IapMessage request = tcpMessagePort.getWriteMemoryBlock();
            request.allocate(1024);
            request.resetReadAndWriteIndexes();

            int freeWriteBlockStartIndex = tcpMessagePort.getWriteBytesAllocator().freeBlockStartIndex(0);
            int freeWriteBlockEndIndex   = tcpMessagePort.getWriteBytesAllocator().freeBlockEndIndex(0);

            int freeReadBlockStartIndex  = tcpMessagePort.getReadBytesAllocator().freeBlockStartIndex(0);
            int freeReadBlockEndIndex    = tcpMessagePort.getReadBytesAllocator().freeBlockEndIndex(0);

            generateIAPMessage(request, rionWriter);

            request.setTcpSocket(0, clientSocket);

            System.out.println("Sending message " + counter);
            tcpMessagePort.writeNowOrEnqueue(request);

            tcpMessagePort.writeBlock();
            System.out.println("  Message Sent");

            //request.free();
            //sleep(100);

            //try reading from socketsProxy
            System.out.println("Receiving responses");
            int messagesRead = tcpMessagePort.readBlock(responses);

            System.out.println(  "messagesRead = " + messagesRead);

            for(int i=0; i<responses.count; i++) {
                IapMessage iapMessage = (IapMessage) responses.blocks[i];

                System.out.println("  Processing response " + i);
                iapMessage.free();
            }

            System.out.println("");

            Thread.sleep(200);
            responses.clear();

            counter++;

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    private static void generateIAPMessage(IapMessage request, RionWriter ionWriter) {
        ionWriter.setDestination(request);

        ionWriter.writeObjectBeginPush(1);

        IapMessageBase messageBase = new IapMessageBase();
        messageBase.setReceiverNodeId         (new byte[]{33});
        messageBase.setSemanticProtocolId     (new byte[]{22});
        messageBase.setSemanticProtocolVersion(new byte[] {0});
        messageBase.setMessageType            (new byte[]{11});

        messageBase.write(ionWriter);

        ionWriter.writeObjectEndPop();

        request.writeIndex = ionWriter.index;

        System.out.println("length = " + request.lengthWritten());
    }

}
