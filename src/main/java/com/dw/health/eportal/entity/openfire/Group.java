package com.dw.health.eportal.entity.openfire;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: Group
 * @Description: TODO
 * @Author: lyx
 * @Date: 2019/4/13 14:58
 */
@Accessors(chain = true)
@Data
public class Group {

    private String groupname;
    private String id;
    private String avatar;
}
