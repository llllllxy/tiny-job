package org.tinycloud.tinyjob.utils.route.strategy;


import org.tinycloud.tinyjob.utils.route.ExecutorRouter;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询
 *
 * @author liuxingyu01
 * @since 2023-08-04
 **/
public class ExecutorRouteRound extends ExecutorRouter {
    private static final ConcurrentMap<Long, AtomicInteger> routeCountEachJob = new ConcurrentHashMap<>();

    private static long CACHE_VALID_TIME = 0;

    @Override
    public String route(List<String> addressList, long jobId) {
        return addressList.get(increment(jobId) % addressList.size());
    }

    private static int increment(long jobId) {
        // 定期清除ConcurrentHashMap
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            routeCountEachJob.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        }
        AtomicInteger count = routeCountEachJob.get(jobId);
        if (count == null || count.get() > 1000000) {
            // 初始化时主动Random一次，缓解首次压力
            count = new AtomicInteger(new Random().nextInt(100));
        } else {
            // count++
            count.addAndGet(1);
        }
        routeCountEachJob.put(jobId, count);
        return count.get();
    }
}
