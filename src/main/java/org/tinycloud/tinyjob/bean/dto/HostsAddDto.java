package org.tinycloud.tinyjob.bean.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class HostsAddDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主机名称
     */
    @NotEmpty(message = "主机名称不能为空")
    @Length(max = 64, min = 1, message = "主机名称不能超过64个字符")
    private String hostName;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    /**
     * 备注信息
     */
    @Length(max = 500, min = 0, message = "备注信息不能超过500个字符")
    private String remark;

    /**
     * 主机地址
     */
    private String hostAddrs;
}
