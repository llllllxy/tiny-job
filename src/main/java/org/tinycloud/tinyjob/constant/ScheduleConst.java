package org.tinycloud.tinyjob.constant;

/**
 * 任务调度通用常量
 *
 * @author liuxingyu01
 * @since  2023-06-27 12:50
 **/
public class ScheduleConst {

    /**
     * trigger名称前缀
     */
    public static final String TRIGGER_PREFIX = "Trigger_";

    /**
     * job名称前缀
     */
    public static final String JOB_PREFIX = "Job_";

    /**
     * 执行目标key
     */
    public static final String JOB_PROPERTIES = "JOB_PROPERTIES";

    /**
     * 任务过期策略-默认
     */
    public static final String MISFIRE_DEFAULT = "0";

    /**
     * 任务过期策略-立即触发执行（将遗漏的全部执行）
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /**
     * 任务过期策略-触发一次执行
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /**
     * 任务过期策略-不触发执行
     */
    public static final String MISFIRE_DO_NOTHING = "3";
}
