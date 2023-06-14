package org.tinycloud.tinyjob.constant;

public enum JobLogStatusEnum {

    /**
     * 成功
     */
    SUCCESS("0"),
    /**
     * 失败
     */
    FAILED("1");

    private String value;

    private JobLogStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
