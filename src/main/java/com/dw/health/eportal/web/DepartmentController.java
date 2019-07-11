package com.dw.health.eportal.web;

import com.dw.health.eportal.entity.dto.DepartmentDTO;
import com.dw.health.framework.web.Result;
import com.dw.health.eportal.entity.Department;
import com.dw.health.eportal.service.DepartmentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author: qjf
 * @Date: 2019/4/29
 */
@Controller
@RequestMapping("/dept")
public class DepartmentController {
    @Autowired
    private DepartmentService deptService;

    /**
     * 获取部门列表
     * @return Result<List<Department>>
     * @author qjf
     */
    @PostMapping("/listDepts")
    @ResponseBody
    public Result<List<Department>> listDepts(){
        List<Department> list = deptService.listDepts();
        return new Result<>(list);
    }

    /**
     * 分页查询该部门的子部门
     * @param departmentDTO
     * @return List<DepartmentDTO>
     * @author qjf
     */
    @PostMapping("/listDeptsByPid")
    @ResponseBody
    public Result<List<DepartmentDTO>> listDeptsByPid(DepartmentDTO departmentDTO){
        List<DepartmentDTO> deptDTOS = deptService.listDeptsByPid(departmentDTO);
        PageInfo pageInfo = new PageInfo(deptDTOS);
        return new Result<>(deptDTOS).setTotal((int) pageInfo.getTotal());
    }

    /**
     * 删除某个部门，含有子部门禁止删除
     * @param depts
     * @return Result<String>
     * @author qjf
     */
    @PostMapping("/deleteDept")
    @ResponseBody
    public Result<String> deleteDept(@RequestBody List<Department> depts) {
        deptService.deleteDepts(depts);
        return new Result<>("删除成功");
    }

    /**
     * 新增部门
     * @param dept
     * @return 新增信息
     * @author qjf
     */
    @PostMapping("/saveDept")
    @ResponseBody
    public Result<String> saveDept(Department dept) {
        deptService.saveDepartment(dept);
        return new Result<>("新增成功");
    }

    /**
     * 更新部门信息
     * @param dept
     * @return 更新信息
     * @author qjf
     */
    @PostMapping("/updateDept")
    @ResponseBody
    public Result<String> updateDept(Department dept) {
        deptService.updateDepartment(dept);
        return new Result<>("更新成功");
    }


    // ---------------------------页面跳转----------------------------------------

    /**
     * 跳转到更新页面
     * @param id
     * @return ModelAndView
     * @author qjf
     */
    @GetMapping("/updateDeptPage")
    public ModelAndView updateDeptPage(String id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/department/updateDepartment");
        Department dept = deptService.getDeptById(id);
        mv.addObject("dept",dept);
        return mv;
    }

    /**
     * 跳转到新增页面
     * @param pid
     * @return ModelAndView
     * @author qjf
     */
    @GetMapping("/addDeptPage")
    public ModelAndView addNavPage(String pid) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/department/addDepartment");
        mv.addObject("pid", pid);
        return mv;
    }
}
