package com.dw.health.eportal.entity;

import com.dw.health.framework.entity.BaseDO;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Accessors(chain = true)
@Data
@Table(schema = "EPORTAL", name = "USERS")
public class User extends BaseDO<User> {
    @Id
    @KeySql(sql = "select sys_guid() from dual", order = ORDER.BEFORE)
    private String userId;
    @NotEmpty(message = "姓名不能为空")
    private String userName;
    @NotEmpty(message = "密码不能为空")
    private String password;
    private String salt;

    private String sex;

    private String realName;

    private String nation;

    private String idCard;

    private String phone;

    private String mphone;
    @Email(message = "邮箱格式不正确")
    private String mail;

    private String locked;

    private Date lockedTime;

    private String avatar;

    private String searchHistory;
}