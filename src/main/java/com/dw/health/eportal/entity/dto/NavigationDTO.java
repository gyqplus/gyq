package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 用于前台展示
 * @Author: qjf
 * @Date: 2019/3/14
 */
@Accessors(chain = true)
@Data
public class NavigationDTO extends BaseDTO {
    private String id;
    private String navId;
    private String pid;
    private String name;
    private String text;
    private Boolean leafFlag;
    private String url;
    private String icon;
    private String hospitalName;
    private String operatorName;
    private Date createTime;
    private Date modifyTime;
    private String operatorId;
    private Boolean deleteFlag;
    private Date deleteTime;
    private String hospitalId;
    private String sp;
    private String seq;
    private List<NavigationDTO> children;

    public String getTargetType() {
        if (leafFlag) {
            return "iframe-tab";
        }

        return "";
    }
}
