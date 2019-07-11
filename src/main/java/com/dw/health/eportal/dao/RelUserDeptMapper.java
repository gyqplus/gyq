package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.RelUserDept;

/**
 * @Author: qjf
 * @Date: 2019/4/9
 */
public interface RelUserDeptMapper extends BaseMapper<RelUserDept>{
    void deleteRelUserDept(String userId);

    void deleteRelDeptUser(String deptId);
}
