package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 组合角色相关信息
 * @Author: qjf
 * @Date: 2019/3/12
 */
@Accessors(chain = true)
@Data
public class RoleDTO extends BaseDTO {
    private String roleId;

    private String roleFlag;

    private String roleDescription;

    private Date createTime;

    private Date modifyTime;

    private String operatorId;

    private String operatorName;

    private Boolean deleteFlag;

    private Date deleteTime;

    private String hospitalId;

    private String hospitalName;

    private String sp;

    private List<User> userList;

    private List<Permission> permList;
}
