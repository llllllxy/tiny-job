package org.tinycloud.tinyjob.constant;

public enum JobTriggerEnum {
    /**
     * cron表达式触发器
     */
    CRON("CRON"),
    /**
     * 简单触发器
     */
    SIMPLE("SIMPLE"),
    ;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    JobTriggerEnum(String code) {
        this.code = code;
    }
}
