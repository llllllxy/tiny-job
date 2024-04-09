package org.tinycloud.tinyjob.constant;

/**
 * <p>
 * 是否允许并发执行的枚举
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-04-09 16:42
 */
public enum JobConcurrentEnum {
    ALLOW("0", "允许"),
    FORBID("1", "禁止");

    private String value;
    private String desc;

    JobConcurrentEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
