package com.dw.health.eportal.dao;


import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.Department;
import com.dw.health.eportal.entity.dto.DepartmentDTO;

import java.util.List;

/**
 * @Author: qjf
 * @Date: 2019/4/4
 */
public interface DepartmentMapper extends BaseMapper<Department> {
    List<DepartmentDTO> selectBySearchDTO(DepartmentDTO departmentDTO);
    
    List<String> listDeptsByUserId(String userId);
}
