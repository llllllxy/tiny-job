package org.tinycloud.tinyjob.utils.route.strategy;

import org.tinycloud.tinyjob.utils.route.ExecutorRouter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 最近最久未使用
 *
 * @author liuxingyu01
 * @since 2023-08-04
 **/
public class ExecutorRouteLRU extends ExecutorRouter {
    private static final ConcurrentMap<Long, LinkedHashMap<String, String>> jobLRUMap = new ConcurrentHashMap<>();
    private static long CACHE_VALID_TIME = 0;

    @Override
    public String route(List<String> addressList, long jobId) {
        // 定期清除ConcurrentHashMap
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            jobLRUMap.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        }

        // 初始化 lru
        LinkedHashMap<String, String> lruItem = jobLRUMap.get(jobId);
        if (lruItem == null) {
            // LinkedHashMap
            // a、accessOrder：true=访问顺序排序（get/put时排序）；false=插入顺序排期；
            // b、removeEldestEntry：新增元素时将会调用，返回true时会删除最老元素；可封装LinkedHashMap并重写该方法，比如定义最大容量，超出是返回true即可实现固定长度的LRU算法；
            lruItem = new LinkedHashMap<String, String>(16, 0.75f, true);
            jobLRUMap.putIfAbsent(jobId, lruItem);
        }

        // 放入新的
        for (String address : addressList) {
            if (!lruItem.containsKey(address)) {
                lruItem.put(address, address);
            }
        }
        // 删除旧的
        List<String> delKeys = new ArrayList<>();
        for (String existKey : lruItem.keySet()) {
            if (!addressList.contains(existKey)) {
                delKeys.add(existKey);
            }
        }
        if (delKeys.size() > 0) {
            for (String delKey : delKeys) {
                lruItem.remove(delKey);
            }
        }
        String eldestKey = lruItem.entrySet().iterator().next().getKey();
        String eldestValue = lruItem.get(eldestKey);
        return eldestValue;
    }
}
