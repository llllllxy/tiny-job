package org.tinycloud.tinyjob.utils.route.strategy;


import org.tinycloud.tinyjob.utils.route.ExecutorRouter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorRouteRound extends ExecutorRouter {
    private static final AtomicInteger index = new AtomicInteger(0);

    @Override
    public String route(List<String> addressList) {
        String address = addressList.get((Math.abs(index.getAndAdd(1) % addressList.size())));
        return address;
    }
}
