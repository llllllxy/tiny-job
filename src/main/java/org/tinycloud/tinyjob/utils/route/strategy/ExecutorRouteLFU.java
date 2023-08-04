package org.tinycloud.tinyjob.utils.route.strategy;

import org.tinycloud.tinyjob.utils.route.ExecutorRouter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 最不经常使用
 *
 * @author liuxingyu01
 * @since 2023-08-04
 **/
public class ExecutorRouteLFU extends ExecutorRouter {
    private static final ConcurrentMap<Long, HashMap<String, Integer>> jobLfuMap = new ConcurrentHashMap<>();
    private static long CACHE_VALID_TIME = 0;

    @Override
    public String route(List<String> addressList, long jobId) {
        // 定期清除ConcurrentHashMap
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            jobLfuMap.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        }

        // lfu item init
        HashMap<String, Integer> lfuItemMap = jobLfuMap.get(jobId);     // Key排序可以用TreeMap+构造入参Compare；Value排序暂时只能通过ArrayList；
        if (lfuItemMap == null) {
            lfuItemMap = new HashMap<>();
            jobLfuMap.putIfAbsent(jobId, lfuItemMap);   // 避免重复覆盖
        }

        // put new
        for (String address : addressList) {
            if (!lfuItemMap.containsKey(address) || lfuItemMap.get(address) > 1000000) {
                lfuItemMap.put(address, new Random().nextInt(addressList.size()));  // 初始化时主动Random一次，缓解首次压力
            }
        }
        // remove old
        List<String> delKeys = new ArrayList<>();
        for (String existKey : lfuItemMap.keySet()) {
            if (!addressList.contains(existKey)) {
                delKeys.add(existKey);
            }
        }
        if (delKeys.size() > 0) {
            for (String delKey : delKeys) {
                lfuItemMap.remove(delKey);
            }
        }

        // load least used count address
        List<Map.Entry<String, Integer>> lfuItemList = new ArrayList<>(lfuItemMap.entrySet());
        lfuItemList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Map.Entry<String, Integer> addressItem = lfuItemList.get(0);
        addressItem.setValue(addressItem.getValue() + 1);
        return addressItem.getKey();
    }
}
