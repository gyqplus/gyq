package com.dw.health.eportal.entity;

import com.dw.health.framework.entity.BaseDO;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Base64;

@Accessors(chain = true)
@Data
@Table(schema = "EPORTAL")
public class Application extends BaseDO<Application> {

    @Id
    @KeySql(sql = "select sys_guid() from dual", order = ORDER.BEFORE)
    private String appId;

    private String appName;

    private String url;

    private Integer seq;

    private String type;

    private byte[] icon;

    public String getIconBase64() {
        if (this.icon == null) {
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(new byte[6]);
        }
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(this.icon);
    }
}