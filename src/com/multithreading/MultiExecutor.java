package com.multithreading;

import java.util.ArrayList;
import java.util.List;

class MyMultiExecutor{
    List<Runnable> tasks;
    public  MyMultiExecutor(List<Runnable> tasks){
     this.tasks = tasks;
    }

    public void executeAll(){
        List<Thread> threads = new ArrayList<>(tasks.size());

        int i = 0;

        for(Runnable task : tasks){
            Thread thread = new Thread(task);
            thread.setName("thread-"+i);
            i++;
            threads.add(thread);
        }

        for(Thread thread : threads){
            thread.start();
        }
    }
}

class MyTasks implements Runnable {

    @Override
    public void run() {
        System.out.println("Hi " + Thread.currentThread().getName());
    }
}
public class MultiExecutor {
    public static void main(String[] args) {
        MyTasks task1 = new MyTasks();
        MyTasks task2 = new MyTasks();
        MyTasks task3 = new MyTasks();
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        MyMultiExecutor myMultiExecutor = new MyMultiExecutor(tasks);
        myMultiExecutor.executeAll();
    }
}
