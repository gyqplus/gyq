package com.dw.health.eportal.entity.openfire;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: OfUser
 * @Description: TODO
 * @Author: lyx
 * @Date: 2019/4/13 14:43
 */
@Accessors(chain = true)
@Data
public class OfUser {
    private String username;
    private String id;
    private String status = "online";
    private String avatar;
}
