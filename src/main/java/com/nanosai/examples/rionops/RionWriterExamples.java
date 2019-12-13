package com.nanosai.examples.rionops;

import com.nanosai.rionops.rion.write.RionWriter;

public class RionWriterExamples {

    public static void main(String[] args) {
        //writeObject();
        writeTable();


    }

    private static void writeObject() {
        RionWriter rionWriter = new RionWriter();
        rionWriter.setDestination(new byte[1024], 0);
        rionWriter.setNestedFieldStack(new int[16]);

        rionWriter.writeObjectBeginPush(2);  // reserve 2 bytes to hold object length (number of length bytes)

        rionWriter.writeUtf8("Hello World");
        rionWriter.writeInt64(123);
        rionWriter.writeFloat64(1234.5678);

        rionWriter.writeObjectEndPop();
    }

    private static void writeTable() {
        RionWriter rionWriter = new RionWriter();
        rionWriter.setDestination(new byte[1024], 0);
        rionWriter.setNestedFieldStack(new int[16]);

        rionWriter.writeObjectBeginPush(2);  // reserve 2 bytes to hold object length (number of length bytes)

        rionWriter.writeUtf8("Hello World");
        rionWriter.writeInt64(123);
        rionWriter.writeFloat64(1234.5678);

        rionWriter.writeObjectEndPop();
    }

}
