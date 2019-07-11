package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户前台展示
 * @Author: qjf
 * @Date: 2019/3/18
 */
@Accessors(chain = true)
@Data
public class ApplicationDTO extends BaseDTO{
    private String appId;

    private String appName;

    private String url;

    private Integer seq;

    private String type;

    private byte[] icon;

    private Date createTime;

    private Date modifyTime;

    private String operatorId;

    private String operatorName;

    private Boolean deleteFlag;

    private Date deleteTime;

    private String hospitalId;

    private String hospitalName;

    private String sp;

    //------------------------新增查询字段------------------------------
    private String userId;

}
