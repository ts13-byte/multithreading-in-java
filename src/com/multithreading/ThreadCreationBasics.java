package com.multithreading;

public class ThreadCreationBasics {
    public static void main(String[] args) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //code that will run in a new thread
                System.out.println("Inside a newly created thread : " + Thread.currentThread().getName());
                System.out.println("current thread priority is: "+ Thread.currentThread().getPriority());
            }
        });

        thread.setName("New worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("Before thread creation: "+ Thread.currentThread().getName());
        thread.start();
        System.out.println("After thread creation: "+ Thread.currentThread().getName());
    }
}
