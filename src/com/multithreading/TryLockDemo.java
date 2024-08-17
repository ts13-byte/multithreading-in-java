package com.multithreading;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * This demonstrates a fin - assets dashboard - monitor cryptocurrencies and anything about money.
 * Two threads - one responds to UI events, displays animation, current price.
 * second thread - network call to exchanges and up-to-date prices for those assets periodically
 * we will need a shared resource between both of thread where the worker thread can update
 * all those prices and the UI thread is going to display those updated prices.
 */

class PricesContainer {
    private Lock lockObject = new ReentrantLock();
    private double bitcoinPrice;
    private double etherPrice;
    private double liteCoinPrice;
    private double bitCoinCashPrice;
    private double ripplePrice;

    public Lock getLockObject() {
        return lockObject;
    }

    public void setLockObject(Lock lockObject) {
        this.lockObject = lockObject;
    }

    public double getBitcoinPrice() {
        return bitcoinPrice;
    }

    public void setBitcoinPrice(double bitcoinPrice) {
        this.bitcoinPrice = bitcoinPrice;
    }

    public double getEtherPrice() {
        return etherPrice;
    }

    public void setEtherPrice(double etherPrice) {
        this.etherPrice = etherPrice;
    }

    public double getLiteCoinPrice() {
        return liteCoinPrice;
    }

    public void setLiteCoinPrice(double liteCoinPrice) {
        this.liteCoinPrice = liteCoinPrice;
    }

    public double getBitCoinCashPrice() {
        return bitCoinCashPrice;
    }

    public void setBitCoinCashPrice(double bitCoinCashPrice) {
        this.bitCoinCashPrice = bitCoinCashPrice;
    }

    public double getRipplePrice() {
        return ripplePrice;
    }

    public void setRipplePrice(double ripplePrice) {
        this.ripplePrice = ripplePrice;
    }
}

/**
 * worker thread class
 */
class PricesUpdater extends Thread {
    private PricesContainer pricesContainer;
    // to simulate the real-time prices of the currencies
    private Random random = new Random();
    PricesUpdater(PricesContainer pricesContainer) {
        this.pricesContainer = pricesContainer;
    }
    // all the prices should be in sync so that the user does not see the old prices for some
    public void run() {
        while(true) {
            pricesContainer.getLockObject().lock();
            try{
                // to simulate the network call
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                }
                pricesContainer.setBitcoinPrice(random.nextInt(20000));
                pricesContainer.setEtherPrice(random.nextInt(100000));
                pricesContainer.setRipplePrice(random.nextInt(10000));
                pricesContainer.setLiteCoinPrice(random.nextInt(10000));
                pricesContainer.setBitCoinCashPrice(random.nextInt(100000));
            } finally {
                pricesContainer.getLockObject().unlock();
            }
            // to simulate other logic
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
            }
        }
    }
}
public class TryLockDemo {

}
