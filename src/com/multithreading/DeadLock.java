package com.multithreading;

import java.util.Objects;

class Intersection {
    private Object road1 = new Object();
    private Object road2 = new Object();

    public void takeRoad1() {
        synchronized (road1) {
            System.out.println("Road 1 is locked by thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (road2) {
                System.out.println("Train is passing through Road1 ");
            }
        }
    }

    public void takeRoad2() {
        synchronized (road1) {
            System.out.println("Road 2 is locked by thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (road2) {
                System.out.println("Train is passing through Road2 ");
            }
        }
    }

}

class Train2 implements Runnable {

    private Intersection intersection;

    Train2(Intersection intersection) {
        this.intersection = intersection;
    }

    @Override
    public void run() {
        intersection.takeRoad2();
    }
}

class Train1 implements Runnable {

    private Intersection intersection;

    Train1(Intersection intersection) {
        this.intersection = intersection;
    }

    @Override
    public void run() {
        intersection.takeRoad1();
    }
}

public class DeadLock {
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainThread1 = new Thread(new Train1(intersection));
        Thread trainThread2 = new Thread(new Train2(intersection));

        trainThread1.start();
        trainThread2.start();
    }
}
