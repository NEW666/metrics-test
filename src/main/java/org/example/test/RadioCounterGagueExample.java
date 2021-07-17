package org.example.test;

import com.codahale.metrics.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author ：doc.g
 * @date ：Created in 2021/5/12 0:34
 * @description：
 */
public class RadioCounterGagueExample {

    static final MetricRegistry metricRegistry = new MetricRegistry();


    static final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();


    private static final Counter total = new Counter();
    private static final  Counter success = new Counter();

    public static void main(String[] args) {

        reporter.start(1,TimeUnit.SECONDS);

        metricRegistry.gauge("success-rate",() -> new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(success.getCount(),total.getCount());
            }
        });

        for (;;){
            randomSleep();
            bussiness();
        }

    }

    private static void bussiness(){
        total.inc();
        try {
            int x = 10 / ThreadLocalRandom.current().nextInt(6);
            success.inc();
        }catch (Exception e){
            System.out.println("ERROR");
        }
    }

    private static void randomSleep(){

        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
