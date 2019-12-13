package com.nanosai.examples.threadops;

import com.nanosai.threadops.threadloops.RepeatedTaskExecutor;
import com.nanosai.threadops.threadloops.ThreadLoop;
import com.nanosai.threadops.threadloops.ThreadLoopPausable;
import com.nanosai.threadops.threadloops.RepeatedTaskExecutorPausable;

public class RepeatedTasksExamples {

    public static void main(String[] args) throws InterruptedException {

        repeatedTaskExample();
        //repeatedTaskPausableExample();
    }

    private static void repeatedTaskExample() throws InterruptedException {
        RepeatedTaskExecutor executor = new RepeatedTaskExecutor(
                () -> { System.out.println("Blabla"); }
                ,() -> { System.out.println("Second"); }
        );

        ThreadLoop threadLoop = new ThreadLoop(executor);
        threadLoop.start();

        Thread.sleep(2000);

        threadLoop.stop();
    }


    private static void repeatedTaskPausableExample() throws InterruptedException {
        RepeatedTaskExecutorPausable executor = new RepeatedTaskExecutorPausable(
                 () -> { System.out.println("Blabla"); return 1_000_000_000; }
                ,() -> { System.out.println("Second"); return   400_000_000; }
                );

        ThreadLoopPausable threadLoop = new ThreadLoopPausable(executor);
        threadLoop.start();

        Thread.sleep(5000);

        threadLoop.stop();
    }

}
