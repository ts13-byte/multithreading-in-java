package com.multithreading;
//challenging multithreaded application:-
class IncrementingThread extends Thread {

    InventoryCounter inventoryCounter;

    IncrementingThread(InventoryCounter inventoryCounter){
     this.inventoryCounter = inventoryCounter;
    }

    @Override
    public void run() {
        for(int i=0;i<1000;i++){
            inventoryCounter.increment();
        }
    }
}

class DecrementingThread extends Thread{

    InventoryCounter inventoryCounter;

    DecrementingThread(InventoryCounter inventoryCounter){
        this.inventoryCounter = inventoryCounter;
    }

    @Override
    public void run() {
        for(int i=0;i<1000;i++){
            inventoryCounter.decrement();
        }
    }
}

 class InventoryCounter{
    private int items = 0 ;

    Object lock = new Object();

    public void increment() {
        synchronized (this.lock) {
            this.items++;
        }
    }

    public void decrement() {
        synchronized (this.lock) {
            this.items--;
        }
    }

    public int getItems(){
        synchronized (this.lock) {
            return this.items;
        }
    }

 }
public class ResourceSharingExample {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        Thread incrementingThread = new IncrementingThread(inventoryCounter);
        Thread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();

        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("current number of items in inventory are " + inventoryCounter.getItems());



    }
}
