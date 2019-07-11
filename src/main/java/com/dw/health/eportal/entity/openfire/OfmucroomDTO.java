package com.dw.health.eportal.entity.openfire;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: OfmucroomDTO
 * @Description: TODO
 * @Author: lyx
 * @Date: 2019/4/16 10:26
 */
@Accessors(chain = true)
@Data
public class OfmucroomDTO {

    private Integer roomid;

    private String jid;

    private String name;

    private String naturalname;
}
