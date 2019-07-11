package com.dw.health.eportal.entity.dto;

import com.dw.health.framework.entity.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: qjf
 * @Date: 2019/3/19
 */
@Accessors(chain = true)
@Data
public class DictItemDTO extends BaseDTO {
    private String typeId;

    private String itemId;

    private String content;

    private String value;

    private Integer seq;

    private String remark;
}
