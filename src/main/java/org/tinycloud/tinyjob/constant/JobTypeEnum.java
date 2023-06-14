package org.tinycloud.tinyjob.constant;

public enum JobTypeEnum {

    GET("GET"),
    POST("POST"),
    POST_JSON("POST_JSON"),;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    JobTypeEnum(String code) {
        this.code = code;
    }
}
