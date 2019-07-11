package com.dw.health.eportal.service.impl;

import com.dw.health.eportal.dao.DepartmentMapper;
import com.dw.health.eportal.dao.RelUserDeptMapper;
import com.dw.health.eportal.entity.Department;
import com.dw.health.eportal.entity.RelUserDept;
import com.dw.health.eportal.entity.dto.DepartmentDTO;
import com.dw.health.eportal.service.DepartmentService;
import com.dw.health.eportal.util.BaseEntityUtil;
import com.dw.health.framework.exception.BizException;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * create by yy
 * 2019.4.9
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper deptMapper;
    @Autowired
    private RelUserDeptMapper userDeptMapper;

    /**
     * 新增部门
     * @param department
     * @author qjf
     */
    @Override
    public void saveDepartment(Department department){
        int count = deptMapper.selectCount(new Department().setName(department.getName()));
        if (count > 0){
            throw new BizException("当前部门名称已经存在");
        }
        deptMapper.insertSelective(BaseEntityUtil.insertEntity(department));
    }


    @Override
    public void updateDepartment(Department department){
        deptMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(department));
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<DepartmentDTO> selectBySearchDTO(DepartmentDTO departmentDTO){
        PageHelper.startPage(departmentDTO.getCurrentPage(), departmentDTO.getPageSize());
        List<DepartmentDTO> departments = deptMapper.selectBySearchDTO(departmentDTO);
        return departments;
    }

    /**
     * 获取所有的部门
     * @return List<Department>
     * @author qjf
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Department> listDepts() {
        return deptMapper.selectAll();
    }

    /**
     * 分页查询该部门的子部门
     * @param departmentDTO
     * @return List<DepartmentDTO>
     * @author qjf
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<DepartmentDTO> listDeptsByPid(DepartmentDTO departmentDTO) {
        PageHelper.startPage(departmentDTO.getCurrentPage(), departmentDTO.getPageSize());
        return deptMapper.selectBySearchDTO(departmentDTO);
    }

    /**
     * 根据部门id查询部门
     * @param deptId
     * @return Department
     * @author qjf
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Department getDeptById(String deptId) {
        return deptMapper.selectByPrimaryKey(deptId);
    }

    /**
     * 删除部门，如果当前部门含有子部门或当前部门下还有人员，禁止删除。
     * @param depts
     * @author qjf
     */
    @Override
    public void deleteDepts(List<Department> depts) {
        for (Department dept : depts) {
            int count = deptMapper.selectCount(new Department().setPid(dept.getDeptId()));
            if (count > 0){
                throw new BizException("当前部门含有子部门，禁止删除");
            }
            int employees = userDeptMapper.selectCount(new RelUserDept().setDeptId(dept.getDeptId()));
            if (employees > 0){
                throw new BizException("当前部门含有人员信息，禁止删除");
            }
            // 逻辑删除该节点
            deptMapper.deleteByPrimaryKey(BaseEntityUtil.deleteEntity(dept));
        }
    }

}
