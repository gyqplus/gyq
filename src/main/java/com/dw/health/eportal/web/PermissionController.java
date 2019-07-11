/**
 * @User Administrator
 */

package com.dw.health.eportal.web;

import com.dw.health.framework.web.Result;
import com.dw.health.eportal.entity.Permission;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.dto.PermissionDTO;
import com.dw.health.eportal.service.PermissionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

/**
 *
 * @ClassName: PermissionController
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author gyq 
 * @date 2019-3-15 15:37
 * @version V1.0 
 */
@Controller
@RequestMapping(path = "/perm")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    //---------------------------- 权限相关 ------------------------

    /**
     * 新增权限
     * @param
     * @return 新增信息
     */
    @PostMapping("/addPermission")
    @ResponseBody
    public Result<String> addPermission(Permission permission){
        permissionService.addPermission(permission);
        return new Result<>("新增成功");
    }

    /**
     * 删除权限
     * @param permissions
     * @return 删除信息
     */
    @PostMapping("/deletePermission")
    @ResponseBody
    public Result<String> deletePermissionList(@RequestBody List<Permission> permissions){
        permissionService.deletePermission(permissions);
        return new Result<>("删除成功");
    }
    @PostMapping("/updatePermission")
    @ResponseBody
    public Result<String> updatePermission(Permission permission){
        permissionService.updatePermission(permission);
        return new Result<>("修改成功");
    }
    /**
     * 获取角色列表(分页)
     * @param permissionDTO
     * @return
     */
    @PostMapping("/getPermissionList")
    @ResponseBody
    public Result<List<PermissionDTO>> getPermissionList(PermissionDTO permissionDTO){
        List<PermissionDTO> permissionDTOList = permissionService.selectPermListBySearchDTO(permissionDTO);
        PageInfo pageInfo = new PageInfo(permissionDTOList);
        return new Result<>(permissionDTOList).setTotal((int) pageInfo.getTotal());
    }
    //---------------------------- 角色相关 ------------------------
    /**
     * 给权限添加角色
     * @param permDTO
     * @return
     */
    @PostMapping("/addRoleList")
    @ResponseBody
    public Result<String> addRoleList(@RequestBody PermissionDTO permDTO){
        permissionService.addRoleList(permDTO.getRoleList(), permDTO.getPermId());
        return new Result<>("新增成功");
    }

    /**
     * 权限删除角色
     * @param permDTO
     * @return
     */
    @PostMapping("/deleteRoleList")
    @ResponseBody
    public Result<String> deleteRoleList(@RequestBody PermissionDTO permDTO){
        permissionService.deleteRoleList(permDTO.getRoleList(), permDTO.getPermId());
        return new Result<>("删除成功");
    }

    /**
     * 获取已经分配的角色
     * @param permDTO
     * @return
     */
    @PostMapping("/getDistributeRoleList")
    @ResponseBody
    public Result<List<Role>> getDistributeRoleList(PermissionDTO permDTO){
        List<Role> roleList = permissionService.selectRoleList(permDTO);
        PageInfo pageInfo = new PageInfo(roleList);
        return new Result<>(roleList).setTotal((int) pageInfo.getTotal());
    }

    /**
     * 获取未分配的角色
     * @param permDTO
     * @return
     */
    @PostMapping("/getUnDistributeRoleList")
    @ResponseBody
    public Result<List<Role>> getUnDistributeRoleList(PermissionDTO permDTO){
        List<Role> roleList = permissionService.selectNRoleList(permDTO);
        PageInfo pageInfo = new PageInfo(roleList);
        return new Result<>(roleList).setTotal((int) pageInfo.getTotal());
    }
    //---------------------------- 页面跳转 ------------------------

    /**
     * 返回新增页面
     * @return
     */
    @GetMapping("/addPermissionPage")
    public String addUser(){
        return "admin/permission/addPermission";
    }
    /**
     * 返回修改页面
     * @param permId
     * @return
     */
    @GetMapping ("/updatePermissionPage")
    public ModelAndView updateUserInfo(String permId) {
        ModelAndView mv = new ModelAndView();
        PermissionDTO permissionDTO = permissionService.selectByPermissionId(permId);
        mv.setViewName("admin/permission/updatePermission");
        mv.addObject("perm",permissionDTO);
        return mv;
    }

    /**
     * 返回分配角色页面
     *
     * @return
     */
    @GetMapping("/distributeRolePage")
    public ModelAndView distributePerm(String id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/permission/distributeRole");
        mv.addObject("permId", id);
        return mv;
    }
}
