package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class RelUserDeptDTO extends BaseDTO {
    private String userDeptId;
    private String userId;
    private String deptId;
    private Boolean leafFlag;
    private String operatorName;
    private Date createTime;
    private Date modifyTime;
    private String operatorId;
    private Boolean deleteFlag;
    private Date deleteTime;
    private String hospitalId;
    private String sp;
}
