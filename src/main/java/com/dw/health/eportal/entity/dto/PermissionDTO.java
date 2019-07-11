package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import com.dw.health.eportal.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 组合权限相关信息，用于前台展示
 * @Author: qjf
 * @Date: 2019/3/15
 */
@Accessors(chain = true)
@Data
public class PermissionDTO extends BaseDTO{
    private String permId;

    private String permFlag;

    private String permDescription;

    private Date createTime;

    private Date modifyTime;

    private String operatorId;

    private String operatorName;

    private Boolean deleteFlag;

    private Date deleteTime;

    private String hospitalId;

    private String hospitalName;

    private String sp;

    private List<Role> roleList;
}
