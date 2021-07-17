package org.example.histogram;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author ：doc.g
 * @date ：Created in 2021/5/13 0:03
 * @description：
 */
public class HistogramsExample {


    static final MetricRegistry metricRegistry = new MetricRegistry();


    static final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

    private static final Histogram histogram = metricRegistry.histogram("sr");

    public static void main(String[] args) {

        reporter.start(10,TimeUnit.SECONDS);
        while (true){
            doSearch();
            randomSleep();

        }



    }

    private static void randomSleep(){

        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void doSearch(){

        histogram.update(ThreadLocalRandom.current().nextInt(6));
    }
}
