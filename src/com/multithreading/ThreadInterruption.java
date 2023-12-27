package com.multithreading;

import java.math.BigInteger;

class BlockingOperations implements Runnable{

    BigInteger base;
    BigInteger power;

    BlockingOperations( BigInteger base , BigInteger power){
        this.base=base;
        this.power=power;
    }

    @Override
    public void run() {

            System.out.println("The power value is : " + bigIntPower(base,power));

    }

    public static BigInteger bigIntPower(BigInteger base, BigInteger exponent){
        BigInteger result = BigInteger.ONE;

        while (exponent.compareTo(BigInteger.ZERO) > 0) { // while exponent > 0
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Application Prematurely ended!!");
                return BigInteger.ZERO;
            }

            result = result.multiply(base);
            exponent = exponent.subtract(BigInteger.ONE);
        }

        return result;
    }
}

public class ThreadInterruption {
    public static void main(String[] args) {
        BigInteger base = new BigInteger("2");
        BigInteger exponent = new BigInteger("10000000");
        Thread thread=new Thread(new BlockingOperations(base,exponent));

        thread.start();
        thread.interrupt();

    }
}
