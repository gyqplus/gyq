package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 前台展示
 * by yy
 * 2019.4.9
 */
@Accessors(chain = true)
@Data
public class DepartmentDTO extends BaseDTO {
    private String deptId;
    private String name;
    private String pid;
    private Boolean leafFlag;
    private String type;
    private String operatorName;
    private Date createTime;
    private Date modifyTime;
    private String operatorId;
    private Boolean deleteFlag;
    private Date deleteTime;
    private String hospitalId;
    private String sp;
    private Integer seq;
}
