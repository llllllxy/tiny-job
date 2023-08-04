package org.tinycloud.tinyjob.utils.route;

import org.tinycloud.tinyjob.utils.route.strategy.*;


/**
 * 执行策略枚举
 */
public enum ExecutorRouteStrategyEnum {
    FIRST("route_first", new ExecutorRouteFirst()),
    LAST("route_last", new ExecutorRouteLast()),
    ROUND("route_round", new ExecutorRouteRound()),
    RANDOM("route_random", new ExecutorRouteRandom()),
    LFU("route_lfu", new ExecutorRouteLFU()),
    LRU("route_lru", new ExecutorRouteLRU()),

    ;

    ExecutorRouteStrategyEnum(String title, ExecutorRouter router) {
        this.title = title;
        this.router = router;
    }

    private String title;

    private ExecutorRouter router;

    public String getTitle() {
        return title;
    }

    public ExecutorRouter getRouter() {
        return router;
    }


    /**
     * 枚举匹配器，
     * ExecutorRouteStrategyEnum executorRouteStrategyEnum = ExecutorRouteStrategyEnum.match(jobInfo.getStrategy(), null);
     * 匹配成功后，调用即可
     * String address = executorRouteStrategyEnum.getRouter().route(addressList);
     * @param name 名字，即为FIRST、LAST、ROUND、RANDOM
     * @param defaultItem 默认的
     * @return
     */
    public static ExecutorRouteStrategyEnum match(String name, ExecutorRouteStrategyEnum defaultItem){
        if (name != null) {
            for (ExecutorRouteStrategyEnum item: ExecutorRouteStrategyEnum.values()) {
                if (item.name().equals(name)) {
                    return item;
                }
            }
        }
        return defaultItem;
    }
}
