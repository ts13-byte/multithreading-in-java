package com.multithreading;


public class DaemonThreads {
    public static void main(String[] args) {

        Thread daemonThread=new Thread(new Runnable() {
            @Override
            public void run() {
                int count=1;
                while (true) {
                    System.out.println("Daemon Thread is running..."+ count++ + " times");
                    try {
                        Thread.sleep(1000); // Sleep for 1 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        daemonThread.setDaemon(true);

        daemonThread.start();

        try {
            Thread.sleep(10000); // Sleep for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread exiting...");
    }
}
