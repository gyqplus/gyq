package com.dw.health.eportal.entity;

import com.dw.health.framework.entity.BaseDO;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: qjf
 * @Date: 2019/4/9
 */
@Accessors(chain = true)
@Data
@Table(schema = "EPORTAL")
public class RelUserDept extends BaseDO<RelUserDept> {
    @Id
    @KeySql(sql = "select sys_guid() from dual", order = ORDER.BEFORE)
    private String userDeptId;

    private String userId;

    private String deptId;
}
