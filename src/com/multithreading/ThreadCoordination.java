package com.multithreading;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FactorialThread extends Thread{

    private long inputNumber;
    private BigInteger result = BigInteger.ZERO;
    private boolean isFinished=false;

    FactorialThread(long inputNumber){
        this.inputNumber=inputNumber;
    }

    @Override
    public void run() {
        this.result=calcFactorial(inputNumber);
        this.isFinished=true;
    }

    public BigInteger calcFactorial(long number){
        BigInteger tempResult=BigInteger.ONE;

        for(long i=number;i > 0;i--){
            tempResult=tempResult.multiply(new BigInteger(Long.toString(i)));
        }

        return tempResult;
    }

    public Boolean isFinished(){
        return isFinished;
    }

    public BigInteger getResult(){
        return result;
    }

}
public class ThreadCoordination {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers= Arrays.asList(1000000000000L,345L,3545L,234L,4665L,23L,5566L);

        List<FactorialThread> threads=new ArrayList<>();

        for(Long number: inputNumbers){
            threads.add(new FactorialThread(number));
        }

        for(Thread thread:threads){
            thread.setDaemon(true);
            thread.start();
        }

        for(Thread thread : threads){
            thread.join(2000);
        }

        for(int i=0;i<inputNumbers.size();i++){
          FactorialThread factorialThread=threads.get(i);
          if(factorialThread.isFinished()){
              System.out.println("Factorial of "+ inputNumbers.get(i)+" is "+ factorialThread.getResult());
          }else{
              System.out.println("calculation for " + inputNumbers.get(i)+ " is still in progress!!");
          }
        }
    }
}
