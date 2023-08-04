package org.tinycloud.tinyjob.utils.route.strategy;

import org.tinycloud.tinyjob.utils.route.ExecutorRouter;

import java.util.List;

public class ExecutorRouteFirst extends ExecutorRouter {

    @Override
    public String route(List<String> addressList, long jobId) {
        return addressList.get(0);
    }
}
