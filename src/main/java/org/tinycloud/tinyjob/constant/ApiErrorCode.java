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
    THE_ORIGINAL_PASSWORD_IS_WRONG("1003", "原始密码错误，请重新输入后再试！"),
    THE_NEWPASSWORD_ENTERED_TWICE_DOES_NOT_MATCH("1004", "两次输入的新密码不一致！"),

    CRON_EXPRESSION_ERROR("4001", "Cron表达式不正确！"),
    JOB_PARAM_FORMAT_ERROR("4002", "请求参数格式不正确！"),
    JOB_HEADER_FORMAT_ERROR("4003", "请求头信息格式不正确！"),
    JOB_INTERVAL_SECONDS_ERROR("4004", "简单任务的重复间隔时间（以秒为单位）不能为空！"),
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
