package com.multithreading;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.*;

class Inventory {
    private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
    private ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
 //   private ReadLock readlock = ReentrantReadWriteLock.ReadLock();


    //one reader method
    public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
      //  reentrantLock.lock();
        try {
            Integer fromKey = priceToCountMap.ceilingKey(lowerBound);
            Integer toKey = priceToCountMap.floorKey(upperBound);

            if (toKey == null || fromKey == null) {
                return 0;
            }

            NavigableMap<Integer, Integer> rangeOfPrices = priceToCountMap.subMap(fromKey, true, toKey, true);

            int sum = 0;
            for (int numberOfItemsForPrice : rangeOfPrices.values()) {
                sum += numberOfItemsForPrice;
            }

            return sum;
        }finally {
       //  reentrantLock.unlock();
        }
    }
    // two writer methods
    public void addItem(int price) {
       // reentrantLock.lock();
        try {
            priceToCountMap.put(price, priceToCountMap.getOrDefault(price, 0) + 1);
        }finally {
         //   reentrantLock.unlock();
        }
    }

    public void removeItem(int price) {
      //  reentrantLock.lock();
        try {
            int count = priceToCountMap.getOrDefault(price, 0);
            if (count > 1) {
                priceToCountMap.put(price, count - 1);
            } else {
                priceToCountMap.remove(price);
            }
        }finally {
          //  reentrantLock.unlock();
        }
    }
}

public class ReadWriteLocksExample{
    public static final int HIGHEST_PRICE = 10000;
    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();

        Random random = new Random();

        for(int i=0 ;i<10000;i++) {
            inventory.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writer = new Thread(()->{
            while(true){
                inventory.addItem(random.nextInt(HIGHEST_PRICE));
                inventory.removeItem(random.nextInt(HIGHEST_PRICE));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        });

        writer.setDaemon(true);
        writer.start();

        int numberOfReaderThreads = 7;
        List<Thread> readerThreads = new ArrayList<>(7);
        for(int readerIndex =0 ; readerIndex<numberOfReaderThreads; readerIndex++){
            Thread reader = new Thread(()->{
                for(int i=0;i<10000;i++){
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;
                    inventory.getNumberOfItemsInPriceRange(lowerBoundPrice,upperBoundPrice);
                }
            });
            reader.setDaemon(true);
            readerThreads.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();
        for(Thread readerThread : readerThreads){
            readerThread.start();
        }

        for(Thread readerThread : readerThreads){
            readerThread.join();
        }

        long endReadingTime = System.currentTimeMillis();

        System.out.println(String.format("Reading took %d ms to read",endReadingTime-startReadingTime));

    }

}
