package com.multithreading;

import java.util.Random;



class MetricsExample{
    private long count = 0;
    private volatile double average = 0.0;
    private volatile long min;
    private volatile long max;

    public MetricsExample(){
        this.max = Long.MIN_VALUE;
        this.min = Long.MAX_VALUE;
    }


    public synchronized void addSample(long sample) {
        synchronized (this) {
            this.min = Math.min(min,sample);
            this.max = Math.max(max,sample);
            double currSum = average * count;
            count++;
            average = (currSum + sample) / count;
        }
    }

    public double getAverage() {
        return average;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}

class BusinessLogic extends Thread{

    public MetricsExample metricsExample;
    private Random random = new Random();

    public BusinessLogic(MetricsExample metricsExample) {
        this.metricsExample = metricsExample;
    }

    @Override
    public void run() {
        while(true) {
            long start = System.currentTimeMillis();

            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();

            metricsExample.addSample(end - start);
        }
    }
}

class MetricsPrinter extends Thread {
    private MetricsExample metricsExample;

    public MetricsPrinter(MetricsExample metricsExample) {
        this.metricsExample = metricsExample;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double currentAverage = metricsExample.getAverage();
            System.out.println("current average is : " + currentAverage);
        }
    }
}
public class Metrics {
    public static void main(String[] args) {
        MetricsExample metricsExample = new MetricsExample();

        BusinessLogic businessLogicThread1 = new BusinessLogic(metricsExample);
        BusinessLogic businessLogicThread2 = new BusinessLogic(metricsExample);

        MetricsPrinter metricsPrinter = new MetricsPrinter(metricsExample);

        businessLogicThread1.start();
        businessLogicThread2.start();
        metricsPrinter.start();

    }

}
