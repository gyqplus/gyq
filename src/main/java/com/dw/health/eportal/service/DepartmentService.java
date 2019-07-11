package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.Department;
import com.dw.health.eportal.entity.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    void saveDepartment(Department department);

    void updateDepartment(Department department);

    List<DepartmentDTO> selectBySearchDTO(DepartmentDTO departmentDTO);

    List<Department> listDepts();

    List<DepartmentDTO> listDeptsByPid(DepartmentDTO departmentDTO);

    Department getDeptById(String deptId);

    void deleteDepts(List<Department> depts);
}
