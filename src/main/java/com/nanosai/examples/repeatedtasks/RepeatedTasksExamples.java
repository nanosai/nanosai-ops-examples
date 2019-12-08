package com.nanosai.examples.repeatedtasks;

import com.nanosai.threadops.threadloops.ThreadLoopPausable;

public class RepeatedTasksExamples {

    public static void main(String[] args) throws InterruptedException {

        RepeatedTaskExecutor executor = new RepeatedTaskExecutor(
                 () -> { System.out.println("Blabla"); return 1_000_000_000; }
                ,() -> { System.out.println("Second"); return   400_000_000; }
                );


        ThreadLoopPausable threadLoop = new ThreadLoopPausable(() -> {
            return executor.exec();
        });
        threadLoop.start();

        Thread.sleep(5000);

        threadLoop.stop();
    }
}
