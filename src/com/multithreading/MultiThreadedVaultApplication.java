package com.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.multithreading.MultiThreadedVaultApplication.MAX_PASSWORD;

class Vault{
    private int password;
    public Vault(int password){
        this.password=password;
    }
    public boolean correctGuess(int guess) throws InterruptedException {
        Thread.sleep(10000);
        return this.password==guess;
    }
}

abstract class HackerThread extends Thread{
    protected Vault vault;

    public HackerThread(Vault vault){
     this.vault=vault;
     this.setName(this.getClass().getSimpleName());
     this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void start() {
        System.out.println("Starting Thread : "+ this.getName());
        super.start();
    }
}

    class AscendingHackerThread extends HackerThread{

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
           for(int guess=0;guess<MAX_PASSWORD;guess++){
               try {
                   if(vault.correctGuess(guess)){
                       System.out.println(this.getName() + "guessed the password" + guess);
                       System.exit(0);
                   }

               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    class DescendingHackerThread extends HackerThread{

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess=MAX_PASSWORD ; guess>=0 ; guess--){
                try {
                    if(vault.correctGuess(guess)){
                        System.out.println(this.getName() + "guessed the password" + guess);
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class PoliceThread extends Thread{
        @Override
        public void run() {
            for (int i=10 ; i>=0; i--){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
            System.out.println("Game over for you hacker");
            System.exit(0);
        }
    }

public class MultiThreadedVaultApplication {
    public static final int MAX_PASSWORD=9999;
    public static void main(String[] args) {
        Random random=new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD+1));

        List<Thread> threads=new ArrayList<>();

        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for(Thread thread : threads){
            thread.start();
        }
    }
}
