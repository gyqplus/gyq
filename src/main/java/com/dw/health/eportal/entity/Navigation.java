package com.dw.health.eportal.entity;

import com.dw.health.framework.entity.BaseDO;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

@Accessors(chain = true)
@Data
@Table(schema = "EPORTAL")
public class Navigation extends BaseDO<Navigation> {
    @Id
    @KeySql(sql = "select sys_guid() from dual", order = ORDER.BEFORE)
    private String navId;
    private String pid;
    private String name;
    private Boolean leafFlag;
    private String url;
    private String icon;
    @OrderBy("ASC")
    private Integer seq;
    private List<Navigation> children;

    public String getTargetType() {
        if (leafFlag) {
            return "iframe-tab";
        }
        return "";
    }
}