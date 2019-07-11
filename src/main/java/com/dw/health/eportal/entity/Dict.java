package com.dw.health.eportal.entity;

import com.dw.health.framework.entity.BaseDO;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Table(schema = "EPORTAL")
public class Dict extends BaseDO<Dict> {
    @Id
    @KeySql(sql = "select sys_guid() from dual", order = ORDER.BEFORE)
    private String typeId;

    private String remark;

    private String typeName;
}