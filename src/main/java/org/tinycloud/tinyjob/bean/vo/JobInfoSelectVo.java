package org.tinycloud.tinyjob.bean.vo;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JobInfoSelectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    private Long id;

    /**
     * 任务名称
     */
    private String jobName;
}
