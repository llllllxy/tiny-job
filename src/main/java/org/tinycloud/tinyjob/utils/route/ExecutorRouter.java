package org.tinycloud.tinyjob.utils.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class ExecutorRouter {
    protected static Logger logger = LoggerFactory.getLogger(ExecutorRouter.class);

    /**
     * route address
     *
     * @param addressList 主机地址列表
     * @param jobId 任务ID
     * @return String 选举出来的最终地址
     */
    public abstract String route(List<String> addressList, long jobId);
}
