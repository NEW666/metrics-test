package org.example.test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author ：doc.g
 * @date ：Created in 2021/5/10 23:14
 * @description：
 */
public class JmxAttributeGaugeExample {

    static final MetricRegistry metricRegistry = new MetricRegistry();


    static final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();



    public static void main(String[] args) {
        reporter.start(10,TimeUnit.SECONDS);
        //metricRegistry.register(JmxAttributeGaugeExample.class,new JmxAttributeGauge())
    }


}
