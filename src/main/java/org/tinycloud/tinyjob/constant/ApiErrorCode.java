package org.tinycloud.tinyjob.constant;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-02 15:13
 */
public enum ApiErrorCode {

    USERNAME_OR_PASSWORD_MISMATCH("1001", "用户名或密码错误！"),
    USER_IS_DISABLED("1002", "用户已被停用！"),

    CRON_EXPRESSION_ERROR("4001", "Cron表达式不正确！"),
    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private ApiErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private ApiErrorCode() {
    }

}
