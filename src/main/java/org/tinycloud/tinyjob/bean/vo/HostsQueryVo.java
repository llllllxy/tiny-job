package org.tinycloud.tinyjob.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class HostsQueryVo implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主机id（主键id）
     */
    private Long id;

    /**
     * 主机名称
     */
    private String hostName;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 创建者
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 备注信息
     */
    private String hostAddrs;
}
