package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class DictDTO extends BaseDTO{
    private String typeId;

    private String remark;

    private String typeName;

    private Date createTime;

    private Date modifyTime;

    private String operatorId;

    private String operatorName;

    private Boolean deleteFlag;

    private Date deleteTime;

    private String hospitalId;

    private String hospitalName;

    private String sp;
}
