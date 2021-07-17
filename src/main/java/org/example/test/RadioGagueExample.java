package org.example.test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author ：doc.g
 * @date ：Created in 2021/5/10 23:28
 * @description：
 */
public class RadioGagueExample {

    static final MetricRegistry metricRegistry = new MetricRegistry();


    static final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();


    private static final Meter total = new Meter();
    private static final  Meter success = new Meter();

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
        total.mark();
        try {
            int x = 10 / ThreadLocalRandom.current().nextInt(6);
            success.mark();
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
