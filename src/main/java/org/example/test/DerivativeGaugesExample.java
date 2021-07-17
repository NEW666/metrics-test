package org.example.test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.DerivativeGauge;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @author ：doc.g
 * @date ：Created in 2021/5/11 23:56
 * @description：
 */
public class DerivativeGaugesExample {

    private static final LoadingCache<String,String> cache = CacheBuilder.newBuilder()
            .maximumSize(10).expireAfterAccess(5, TimeUnit.SECONDS).recordStats().build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return s.toUpperCase();
                }
            });

    static final MetricRegistry metricRegistry = new MetricRegistry();


    static final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

    public static void main(String[] args) throws InterruptedException {

        reporter.start(10,TimeUnit.SECONDS);
        Gauge<CacheStats> cacheStatsGauge = metricRegistry.gauge("cache",()->cache::stats);
        metricRegistry.register("missCount", new DerivativeGauge<CacheStats,Long>(cacheStatsGauge) {
            @Override
            protected Long transform(CacheStats cacheStats) {
                return cacheStats.missCount();
            }
        });

        metricRegistry.register("loadSuccessCount", new DerivativeGauge<CacheStats,Long>(cacheStatsGauge) {
            @Override
            protected Long transform(CacheStats cacheStats) {
                return cacheStats.loadSuccessCount();
            }
        });

        while (true){
            bussiness();
            TimeUnit.SECONDS.sleep(1);
        }

    }

    private static void bussiness() {
        cache.getUnchecked("alex");
    }
}
