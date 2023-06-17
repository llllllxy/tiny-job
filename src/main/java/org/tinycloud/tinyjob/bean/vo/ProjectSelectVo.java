package org.tinycloud.tinyjob.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProjectSelectVo implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 项目id
     */
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;
}
