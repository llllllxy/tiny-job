package org.tinycloud.tinyjob.utils.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class ExecutorRouter {
    protected static Logger logger = LoggerFactory.getLogger(ExecutorRouter.class);

    /**
     * route address
     *
     * @param addressList
     * @return ReturnT.content=address
     */
    public abstract String route(List<String> addressList);


}
