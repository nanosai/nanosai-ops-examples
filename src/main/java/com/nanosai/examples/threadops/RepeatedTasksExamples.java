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

        ThreadLoop threadLoop = new ThreadLoop((executingThreadLoop) -> {
            RepeatedTaskExecutor executor = new RepeatedTaskExecutor(
                    () -> { System.out.println("Blabla"); }
                    ,() -> { System.out.println("Second"); }
            );
            return executor;
        });
        threadLoop.start();

        Thread.sleep(2000);

        threadLoop.stop();
    }


    private static void repeatedTaskPausableExample() throws InterruptedException {

        ThreadLoopPausable threadLoop = new ThreadLoopPausable((executingThreadLoop) -> {
            RepeatedTaskExecutorPausable executor = new RepeatedTaskExecutorPausable(
                    () -> { System.out.println("Blabla"); return 1_000_000_000; }
                    ,() -> { System.out.println("Second"); return   400_000_000; }
            );
            return executor;
        });
        threadLoop.start();

        Thread.sleep(5000);

        threadLoop.stop();
    }

}
