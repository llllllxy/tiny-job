package org.tinycloud.tinyjob.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class HostsSelectVo implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 主机id
     */
    private Long id;

    /**
     * 主机名称
     */
    private String hostName;
}
