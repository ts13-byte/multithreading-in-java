package com.multithreading;

import java.math.BigInteger;

class calcExponent extends Thread{
    BigInteger base;
    BigInteger power;
    BigInteger result=BigInteger.ONE;

    calcExponent(BigInteger base, BigInteger power){
        this.base=base;
        this.power=power;
    }
    @Override
    public void run() {
        for(BigInteger i=BigInteger.ZERO ; i.compareTo(power)!=0;i=i.add(BigInteger.ONE)){
            result=result.multiply(base);
        }
    }

    public BigInteger getResult(){
        return this.result;
    }
}
public class MultithreadedCalculation {
    public static void main(String[] args) {
      BigInteger base1=new BigInteger("2");
      BigInteger base2=new BigInteger("400000000000");
      BigInteger exp1 = new BigInteger("10");
      BigInteger exp2 = new BigInteger("1200000000000");

    calcExponent thread1=new calcExponent(base1,exp1);
    calcExponent thread2=new calcExponent(base2,exp2);

     thread1.start();
     thread2.start();

     try{
        thread1.join(2000);
        thread2.join(2000);
     }catch (InterruptedException e) {
         e.printStackTrace();
     }


  BigInteger result= thread1.getResult().add(thread2.getResult());
        System.out.println(result);
    }
}
