package org.tinycloud.tinyjob.constant;

/**
 * <p>
 * 通用全局常量
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-03-07 15:47:38
 */
public final class GlobalConstant {

    /**
     * 已删除标记
     */
    public static final Integer DELETED = 1;

    /**
     * 未删除标记
     */
    public static final Integer NOT_DELETED = 0;

    /**
     * 正常在用
     */
    public static final String ENABLED = "0";

    /**
     * 已停用
     */
    public static final String DISABLED = "1";

    public static String SESSION_KEY = "loginId";
}
