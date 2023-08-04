package org.tinycloud.tinyjob.utils.route.strategy;

import org.tinycloud.tinyjob.utils.route.ExecutorRouter;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ExecutorRouteRandom extends ExecutorRouter {

    @Override
    public String route(List<String> addressList, long jobId) {
        return addressList.get(ThreadLocalRandom.current().nextInt(addressList.size()));
    }
}
