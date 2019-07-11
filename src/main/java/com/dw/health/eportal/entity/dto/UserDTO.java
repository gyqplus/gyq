package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import com.dw.health.eportal.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 组合用户相关数据
 * @Author: qjf
 * @Date: 2019/3/7
 */
@Accessors(chain = true)
@Data
public class UserDTO extends BaseDTO{
    private String userId;

    private String userName;

    private String password;

    private String newPassword;

    private String confirmPassword;

    private String sex;

    private String realName;

    private String idCard;

    private String phone;

    private String mphone;

    private String mail;

    private String locked;

    private Date lockedTime;

    private String nation;

    private String deptId;

    private String deptName;

    private Date createTime;

    private Date modifyTime;

    private String operatorId;

    private String operatorName;

    private Boolean deleteFlag;

    private Date deleteTime;

    private String hospitalId;

    private String hospitalName;

    private String sp;

    private String avatar;

    private List<Role> roleList;

}
