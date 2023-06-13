package org.tinycloud.tinyjob.utils.route.strategy;

import org.tinycloud.tinyjob.utils.route.ExecutorRouter;

import java.util.List;
import java.util.Random;

public class ExecutorRouteRandom extends ExecutorRouter {
    private static final Random localRandom = new Random();

    @Override
    public String route(List<String> addressList) {
        String address = addressList.get(localRandom.nextInt(addressList.size()));
        return address;
    }
}
