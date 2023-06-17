package org.tinycloud.tinyjob.bean.dto;

import lombok.Getter;
import lombok.Setter;
import org.tinycloud.tinyjob.model.BasePageDto;

@Getter
@Setter
public class ProjectQueryDto extends BasePageDto {
    private static final long serialVersionUID = -1L;

    /**
     * 项目名称
     */
    private String projectName;
}
