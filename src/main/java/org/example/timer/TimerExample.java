package org.example.timer;

import com.codahale.metrics.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author ：doc.g
 * @date ：Created in 2021/5/14 0:13
 * @description：
 */
public class TimerExample {

    static final MetricRegistry metricRegistry = new MetricRegistry();


    static final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();


    private static final Timer timer = metricRegistry.timer("request",Timer::new);

    public static void main(String[] args) {

        reporter.start(10,TimeUnit.SECONDS);

        for (;;){
            bussiness();
        }

    }

    private static void bussiness(){

        Timer.Context context = timer.time();

        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            context.stop();
        }
    }

}
