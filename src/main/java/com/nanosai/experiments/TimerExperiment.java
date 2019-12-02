package com.nanosai.experiments;

public class TimerExperiment {

    public static void main(String[] args) {

        //millisTimerExperiment();
        nanosTimerExperiment();
    }

    private static void millisTimerExperiment() {
        long prevTime = System.currentTimeMillis();
        for(int i=0; i<1_000_000; i++) {
            long time = System.currentTimeMillis();

            if(time > prevTime){
                System.out.println(time);
                prevTime = time;
            }
        }
    }

    private static void nanosTimerExperiment() {
        long prevTime = System.nanoTime();
        for(int i=0; i<1_000_000; i++) {
            long time = System.nanoTime();

            if(time > prevTime){
                System.out.println(time);
                prevTime = time;
            }
        }
    }

}
