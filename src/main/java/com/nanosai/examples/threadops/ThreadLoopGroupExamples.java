package com.nanosai.examples.threadops;

import com.nanosai.threadops.threadloops.RepeatedTaskExecutor;
import com.nanosai.threadops.threadloops.ThreadLoop;
import com.nanosai.threadops.threadloops.ThreadLoopGroup;

public class ThreadLoopGroupExamples {


    public static void main(String[] args) throws InterruptedException {

        ThreadLoopGroup threadLoopGroup = new ThreadLoopGroup();

        threadLoopGroup.addThreadLoop(
                new ThreadLoop( (threadLoop) ->
                        new RepeatedTaskExecutor(
                        () -> System.out.println("Thread Loop 1.1") ,
                        () -> System.out.println("Thread Loop 1.1")
                    )
                ));

        threadLoopGroup.addThreadLoop(
                new ThreadLoop( (threadLoop) ->
                        new RepeatedTaskExecutor(
                        () -> System.out.println("Thread Loop 2.1") ,
                        () -> System.out.println("Thread Loop 2.2")
                    )
                ));

        threadLoopGroup.start();

        Thread.sleep(3000);

        threadLoopGroup.stop();


    }
}

