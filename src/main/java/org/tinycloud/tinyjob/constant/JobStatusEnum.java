package org.tinycloud.tinyjob.constant;

public enum JobStatusEnum {
    /**
     * 正常
     */
    NORMAL("0"),
    /**
     * 暂停
     */
    PAUSE("1");

    private String value;

    private JobStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
