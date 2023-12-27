package com.multithreading;

class NewThread extends Thread{
    @Override
    public void run() {
        System.out.println("Hello from : "+ Thread.currentThread().getName());
    }
}
public class ThreadCreationSecondWay {
    public static void main(String[] args) {
        Thread thread=new NewThread();
        thread.setName("New worker thread");
        thread.start();
    }
}
