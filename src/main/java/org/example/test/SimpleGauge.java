package org.example.test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


/**
 * @author ：doc.g
 * @date ：Created in 2021/5/9 22:21
 * @description：
 */
public class SimpleGauge {

    static final MetricRegistry metrics = new MetricRegistry();


    static final  ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
    private static final BlockingQueue<Long> quene = new LinkedBlockingDeque<>(1000);



    private static void randomSleep(){

        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        metrics.register(MetricRegistry.name(SimpleGauge.class, "q-s"), new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return quene.size();
            }
        });

        reporter.start(1,TimeUnit.SECONDS);

        new Thread(()->{
            for (;;){
                //randomSleep();
                quene.add(System.nanoTime());
            }

        }).start();

        new Thread(()->{
            for (;;){
                //randomSleep();
                quene.poll();
            }
        }).start();
    }
}
