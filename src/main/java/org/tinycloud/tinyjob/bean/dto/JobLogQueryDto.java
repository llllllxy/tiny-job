package org.tinycloud.tinyjob.bean.dto;


import lombok.Getter;
import lombok.Setter;
import org.tinycloud.tinyjob.model.BasePageDto;

@Getter
@Setter
public class JobLogQueryDto extends BasePageDto {
    private static final long serialVersionUID = -1L;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 主机id
     */
    private Long hostId;

    /**
     * 任务id
     */
    private Long jobId;
}
