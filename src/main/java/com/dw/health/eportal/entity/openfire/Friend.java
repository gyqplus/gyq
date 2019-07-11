package com.dw.health.eportal.entity.openfire;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName: Friend
 * @Description: TODO
 * @Author: lyx
 * @Date: 2019/4/13 14:47
 */
@Accessors(chain = true)
@Data
public class Friend {
    private String groupname;
    private String id;
    private List<OfUser> list;
}
