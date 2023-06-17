package org.tinycloud.tinyjob.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ProjectQueryVo  implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 项目id
     */
    private Long id;

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
}
