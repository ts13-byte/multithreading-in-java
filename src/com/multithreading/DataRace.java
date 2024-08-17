package com.multithreading;

class SharedClass{
    volatile int x = 0;
    volatile int y = 0;

    public void increment(){
        x++;
        y++;
    }

    public void checkForDataRace() {
        if(y>x){
            System.out.println("Data race occurred -> y>x" );
        }
    }
}
//thread 1 - that performs the increment operation on both the vars --> write operation
class IncrementThread extends Thread {
    SharedClass sharedClass;

    IncrementThread(SharedClass sharedClass){
        this.sharedClass = sharedClass;
    }

    @Override
    public void run() {
        while(true) {
            sharedClass.increment();
        }
    }
}
// thread 2 - that checks whether the data race occurred or not --> read operation
class CheckForDataRaceThread extends Thread {
    SharedClass sharedClass;

    CheckForDataRaceThread(SharedClass sharedClass){
        this.sharedClass = sharedClass;
    }

    @Override
    public void run() {

            while(true) {
                sharedClass.checkForDataRace();
            }

    }
}
public class DataRace {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        IncrementThread incrementThread = new IncrementThread(sharedClass);
        CheckForDataRaceThread checkForDataRaceThread = new CheckForDataRaceThread(sharedClass);
        // ideally x should always be greater than y no matter how they are interleaved
        incrementThread.start();
        checkForDataRaceThread.start();
    }
}
