package com.dw.health.eportal.entity;

import com.dw.health.framework.entity.BaseDO;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 部门类
 * @Author: qjf
 * @Date: 2019/4/4
 */
@Accessors(chain = true)
@Data
@Table(schema = "EPORTAL")
public class Department extends BaseDO<Department>{
    @Id
    @KeySql(sql = "select sys_guid() from dual", order = ORDER.BEFORE)
    private String deptId;
    private String name;
    private String pid;
    private Boolean leafFlag;
    @OrderBy("ASC")
    private Integer seq;
    private String type;
}
