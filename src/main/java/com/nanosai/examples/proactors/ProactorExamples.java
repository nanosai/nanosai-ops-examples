package com.nanosai.examples.proactors;

public class ProactorExamples {

    public static void main(String[] args) {

        ProactorExecutor executor = new ProactorExecutor(
                 () -> { System.out.println("Blabla"); return 1_000_000_000; }
                ,() -> { System.out.println("Second"); return   400_000_000; }
                );

        long startTime = System.currentTimeMillis();
        while(true) {
            long nextExecutionDelay = executor.exec();

            long millis = nextExecutionDelay / 1_000_000L;
            int  nanos  = (int) (nextExecutionDelay % 1_000_000L);

            System.out.println("sleep time (millis / nanos) = " + millis + " / " + nanos);
            try {
                Thread.sleep(millis, nanos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(System.currentTimeMillis() - startTime > 5000){
                break;
            }
        }

    }
}
