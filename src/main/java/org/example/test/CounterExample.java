package org.example.test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author ：doc.g
 * @date ：Created in 2021/5/12 0:28
 * @description：
 */
public class CounterExample {

    static final MetricRegistry metricRegistry = new MetricRegistry();


    static final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();
    private static final BlockingQueue<Long> quene = new LinkedBlockingDeque<>(1000);


    public static void main(String[] args) {

        reporter.start(10,TimeUnit.SECONDS);

        Counter counter = metricRegistry.counter("quene", Counter::new);


        new Thread(()->{
            for (;;){
                randomSleep();
                quene.add(System.nanoTime());
                counter.inc();
            }

        }).start();

        new Thread(()->{
            for (;;){
                randomSleep();
                quene.poll();
                counter.dec();
            }
        }).start();

    }


    private static void randomSleep(){

        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
