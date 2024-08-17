package com.multithreading;

import java.util.concurrent.Semaphore;

public class Semaphores {
    Semaphore semaphore = new Semaphore(1);
    void function1() throws InterruptedException {
        semaphore.acquire();
        semaphore.acquire();
    }

    public static void main(String[] args) {

    }
}


