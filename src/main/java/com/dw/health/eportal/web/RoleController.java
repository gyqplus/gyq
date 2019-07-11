package com.dw.health.eportal.web;

import com.dw.health.framework.web.Result;
import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.User;
import com.dw.health.eportal.entity.dto.RoleDTO;
import com.dw.health.eportal.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

/**
 * 角色增删改查
 * @Author: qjf
 * @Date: 2019/3/12
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    //---------------------------- 角色相关 ------------------------
    /**
     * 新增角色
     * @param role
     * @return 新增信息
     */
    @PostMapping("/addRole")
    @ResponseBody
    public Result<String> addRole(Role role){
        roleService.addRole(role);
        return new Result<>("新增成功");
    }

    /**
     * 删除角色
     * @param roles
     * @return 删除信息
     */
    @PostMapping("/deleteRole")
    @ResponseBody
    public Result<String> deleteRoleList(@RequestBody List<Role> roles){
        roleService.deleteRoleList(roles);
        return new Result<>("删除成功");
    }
    @PostMapping("/updateRole")
    @ResponseBody
    public Result<String> updateRole(Role role){
        roleService.updateRole(role);
        return new Result<>("修改成功");
    }
    /**
     * 获取角色列表(分页)
     * @param roleDTO
     * @return
     */
    @PostMapping("/getRoleList")
    @ResponseBody
    public Result<List<RoleDTO>> getRoleList(RoleDTO roleDTO){
        List<RoleDTO> roleDTOList = roleService.selectRoleListBySearchDTO(roleDTO);
        PageInfo pageInfo = new PageInfo(roleDTOList);
        return new Result<>(roleDTOList).setTotal((int) pageInfo.getTotal());
    }

    //---------------------------- 用户相关 ------------------------

    /**
     * 给角色添加用户
     * @param roleDTO
     * @return
     */
    @PostMapping("/addUserList")
    @ResponseBody
    public Result<String> addUserList(@RequestBody RoleDTO roleDTO){
        roleService.addUserList(roleDTO.getUserList(), roleDTO.getRoleId());
        return new Result<>("新增成功");
    }

    /**
     * 角色删除用户
     * @param roleDTO
     * @return
     */
    @PostMapping("/deleteUserList")
    @ResponseBody
    public Result<String> deleteUserList(@RequestBody RoleDTO roleDTO){
        roleService.deleteUserList(roleDTO.getUserList(), roleDTO.getRoleId());
        return new Result<>("删除成功");
    }

    /**
     * 获取已经分配的用户
     * @param roleDTO
     * @return
     */
    @PostMapping("/getDistributeUserList")
    @ResponseBody
    public Result<List<User>> getDistributeUserList(RoleDTO roleDTO){
        List<User> userList = roleService.selectUserList(roleDTO);
        PageInfo pageInfo = new PageInfo(userList);
        return new Result<>(userList).setTotal((int) pageInfo.getTotal());
    }

    /**
     * 获取未分配的用户
     * @param roleDTO
     * @return
     */
    @PostMapping("/getUnDistributeUserList")
    @ResponseBody
    public Result<List<User>> getUnDistributeUserList(RoleDTO roleDTO){
        List<User> userList = roleService.selectNUserList(roleDTO);
        PageInfo pageInfo = new PageInfo(userList);
        return new Result<>(userList).setTotal((int) pageInfo.getTotal());
    }

    //---------------------------- 权限相关 -------------------------

    /**
     * 给角色添加权限
     * @param roleDTO
     * @return
     */
    @PostMapping("/addPermList")
    @ResponseBody
    public Result<String> addPermList(@RequestBody RoleDTO roleDTO){
        roleService.addPermList(roleDTO.getPermList(), roleDTO.getRoleId());
        return new Result<>("新增成功");
    }

    /**
     * 角色删除权限
     * @param roleDTO
     * @return
     */
    @PostMapping("/deletePermList")
    @ResponseBody
    public Result<String> deletePermList(@RequestBody RoleDTO roleDTO){
        roleService.deletePermList(roleDTO.getPermList(), roleDTO.getRoleId());
        return new Result<>("删除成功");
    }

    /**
     * 获取已经分配的用户
     * @param roleDTO
     * @return
     */
    @PostMapping("/getDistributePermList")
    @ResponseBody
    public Result<List<Permission>> getDistributePermList(RoleDTO roleDTO){
        List<Permission> permList = roleService.selectPermList(roleDTO);
        PageInfo pageInfo = new PageInfo(permList);
        return new Result<>(permList).setTotal((int) pageInfo.getTotal());
    }

    /**
     * 获取未分配的用户
     * @param roleDTO
     * @return
     */
    @PostMapping("/getUnDistributePermList")
    @ResponseBody
    public Result<List<Permission>> getUnDistributePermList(RoleDTO roleDTO){
        List<Permission> permList = roleService.selectNPermList(roleDTO);
        PageInfo pageInfo = new PageInfo(permList);
        return new Result<>(permList).setTotal((int) pageInfo.getTotal());
    }



    //---------------------------- 页面跳转 -------------------------

    /**
     * 返回新增页面
     * @return
     */
    @GetMapping("/addRolePage")
    public String addUser(){
        return "admin/role/addRole";
    }
    /**
     * 返回修改页面
     * @param roleId
     * @return
     */
    @GetMapping ("/updateRolePage")
    public ModelAndView updateUserInfo(String roleId) {
        ModelAndView mv = new ModelAndView();
        RoleDTO roleDTO = roleService.selectByRoleId(roleId);
        mv.setViewName("admin/role/updateRole");
        mv.addObject("role",roleDTO);
        return mv;
    }

    /**
     * 返回分配用户页面
     *
     * @return
     */
    @GetMapping("/distributeUserPage")
    public ModelAndView distributeUser(String id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/role/distributeUser");
        mv.addObject("roleId", id);
        return mv;
    }

    /**
     * 返回分配权限页面
     *
     * @return
     */
    @GetMapping("/distributePermPage")
    public ModelAndView distributePerm(String id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/role/distributePerm");
        mv.addObject("roleId", id);
        return mv;
    }
}
