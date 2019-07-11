package com.dw.health.eportal.web;

import com.dw.health.framework.web.Result;
import com.dw.health.eportal.entity.Role;
import com.dw.health.eportal.entity.User;
import com.dw.health.eportal.entity.dto.UserDTO;
import com.dw.health.eportal.service.OpenfireService;
import com.dw.health.eportal.service.UserService;
import com.dw.health.eportal.util.CurrentUserUtil;
import com.dw.health.eportal.util.EStringUtil;
import com.dw.health.eportal.util.PinYinUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by gyq on 2019-2-20.
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OpenfireService openfireService;


    /**
     * @ClassName: UserController
     * @Description: 获取当前用户
     * @author gyq 
     * @date 2019-2-28 9:06
     * @version V1.0 
     */
    @PostMapping("/getCurrentUser")
    @ResponseBody
    public Result<Map<String, Object>> getCurrentUser() {
        Map result = new HashedMap();
        result.put("userId", CurrentUserUtil.getCurrentUser().getUserId());
        result.put("userName", CurrentUserUtil.getCurrentUser().getUserName());
        result.put("realName", CurrentUserUtil.getCurrentUser().getRealName());
        result.put("deptName", userService.listDepartment(CurrentUserUtil.getCurrentUser().getUserId()));
        return new Result<>(result);
    }

    /**
     * 根据用户输入的查询条件搜索
     *
     * @param param 封装查询条件
     * @return List<User> 用户列表
     * @author qjf
     */
    @PostMapping("/getUsersByKeywords")
    @ResponseBody
    public Result<List<UserDTO>> getUsersByKeywords(@RequestParam Map<String, String> param) {
        List<UserDTO> list = userService.selectUsersByKeywords(param);
        return new Result<>(list);
    }

    /**
     * @ClassName: UserController
     * @Description: 添加搜索用户记录 (首页通讯录)
     * @author gyq 
     * @date 2019-3-4 10:26
     * @version V1.0 
     */
    @PostMapping("/addUserSearchHistory")
    @ResponseBody
    public Result<Map<String, String>> addUserSearchHistory(@RequestParam Map<String, String> param) {
        Map<String, String> result = new HashedMap();
        String searchHistoryStrAdd = param.get("searchHistoryStr");//新添历史
        User currentUser = CurrentUserUtil.getCurrentUser();
        String searchHistoryStrBase = currentUser.getSearchHistory();//旧数据库历史
        String searchHistoryStrBaseNew = EStringUtil.addStringByLengthLimit(searchHistoryStrBase,
                searchHistoryStrAdd, ":", 100);
        currentUser.setSearchHistory(searchHistoryStrBaseNew);
        userService.updateByPrimaryKeySelective(currentUser);
        result.put("successMessage", "添加历史记录成功!");
        return new Result<>(result);
    }

    /**
     * @ClassName: UserController
     * @Description: 获取搜索用户记录 (首页通讯录)
     * @author gyq 
     * @date 2019-3-4 11:30
     * @version V1.0 
     */
    @PostMapping("/findUserSearchHistory")
    @ResponseBody
    public Result<List<String>> findUserSearchHistory() {
        List<String> searchHistoryList = new ArrayList<>();
        String searchHistoryStrBase = CurrentUserUtil.getCurrentUser().getSearchHistory();
        if (searchHistoryStrBase == null) searchHistoryStrBase = "";
        String[] searchHistories = searchHistoryStrBase.split(":");
        int searchHistoryCountForShow = 15;
        if (searchHistories.length < 15) searchHistoryCountForShow = searchHistories.length;
        for (int i = searchHistories.length; i > searchHistories.length - searchHistoryCountForShow; i--) {
            searchHistoryList.add(searchHistories[i - 1]);
        }
        return new Result<>(searchHistoryList);
    }

    /**
     * @ClassName: UserController
     * @Description: 删除历史记录
     * @author gyq
     * @date 2019-3-5 8:39
     * @version V1.0 
     */
    @PostMapping("/deleteUserSearchHistory")
    @ResponseBody
    public Result<Map<String, String>> deleteUserSearchHistory() {
        Map<String, String> result = new HashedMap();
        User currentUser = CurrentUserUtil.getCurrentUser();
        currentUser.setSearchHistory(null);
        userService.updateByPrimaryKeySelective(currentUser);
        result.put("successMessage", "添加历史记录成功!");
        return new Result<>(result);
    }

    /**
     * 分页查询用户列表
     *
     * @param userDTO
     * @return
     * @author qjf
     */
    @PostMapping("/getUserList")
    @ResponseBody
    public Result<List<UserDTO>> getUserList(UserDTO userDTO) {
        List<UserDTO> users = userService.selectBySearchDTO(userDTO);
        PageInfo pageInfo = new PageInfo(users);
        return new Result<>(users).setTotal((int) pageInfo.getTotal());
    }


    /**
     * 新增用户
     * @param userDTO
     * @return
     * @author qjf
     */
    @PostMapping("/addUser")
    @ResponseBody
    public Result<String> addUser(@Valid UserDTO userDTO) {
        int insert = userService.insert(userDTO);
        if (insert > 0){
            openfireService.createUser(userDTO.getUserName(),userDTO.getRealName());
        }
        return new Result<>("新增成功");
    }

    /**
     * 逻辑删除用户，以及中间表信息
     * @param userList
     * @return
     * @author qjf
     */
    @PostMapping("/deleteUser")
    @ResponseBody
    public Result<String> deleteUser(@RequestBody List<User> userList) {
        userService.deleteUser(userList);
        return new Result<>("删除成功");
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     * @author qjf
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public Result<String> updateUser(User user) {
        userService.updateByPrimaryKeySelective(user);
        return new Result<>("修改成功");
    }

    /**
     * 重置密码
     * @param user
     * @return
     */
    @PostMapping("/resetPassword")
    @ResponseBody
    public Result<String> resetPassword(User user){
        userService.resetPassword(user);
        return new Result<>("重置成功");
    }

    /**
     * 获取用户首拼
     * @param name
     * @return
     */
    @PostMapping("/getSpByName")
    @ResponseBody
    public Result<List<String>> getSpByName(String name) {
        String firstSpell = PinYinUtil.getFirstSpell(name);
        List<String> spList = Arrays.asList(firstSpell.split(","));
        return new Result<>(spList);
    }


    @PostMapping("/unLock")
    @ResponseBody
    public Result<String> unLock(@RequestBody User user){
       userService.unLock(user);
       return new Result<>("解锁成功");
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public Result<String> changePassword(UserDTO userDTO){
        userService.changePassword(userDTO);
        return new Result<>("密码修改成功");
    }


    //---------------------------角色相关----------------------------------

    /**
     * 查询已经拥有角色
     * @param userDTO
     * @return
     */
    @PostMapping("/getDistributeRoleList")
    @ResponseBody
    public Result<List<Role>> getDistributeRoleList(UserDTO userDTO){
        List<Role> roleList = userService.selectRoleList(userDTO);
        PageInfo pageInfo = new PageInfo(roleList);
        return new Result<>(roleList).setTotal((int) pageInfo.getTotal());
    }

    /**
     * 查询未拥有角色
     * @param userDTO
     * @return
     */
    @PostMapping("/getUnDistributeRoleList")
    @ResponseBody
    public Result<List<Role>> getUnDistributeRoleList(UserDTO userDTO){
        List<Role> roleList = userService.selectNRoleList(userDTO);
        PageInfo pageInfo = new PageInfo(roleList);
        return new Result<>(roleList).setTotal((int) pageInfo.getTotal());
    }

    /**
     * 用户新增角色
     * @param userDTO
     * @return
     */
    @PostMapping("/addRoleList")
    @ResponseBody
    public Result<String> addRoleList(@RequestBody UserDTO userDTO){
        List<Role> rows = userDTO.getRoleList();
        String userId = userDTO.getUserId();
        userService.addRoleList(rows, userId);
        return new Result<>("新增成功");
    }

    /**
     * 用户删除角色
     * @param userDTO
     * @return
     */
    @PostMapping("/deleteRoleList")
    @ResponseBody
    public Result<String> deleteRoleList(@RequestBody UserDTO userDTO){
        userService.deleteRoleList(userDTO.getRoleList(), userDTO.getUserId());
        return new Result<>("删除成功");
    }


    //---------------------------页面跳转----------------------------------

    /**
     * 返回修改页面
     * @param id
     * @return
     */
    @GetMapping("/updateUserPage")
    public ModelAndView updateUserInfo(String id) {
        ModelAndView mv = new ModelAndView();
        UserDTO userDTO = userService.selectByUserId(id);
        mv.setViewName("admin/user/updateUser");
        mv.addObject("user", userDTO);
        return mv;
    }
    @GetMapping("/updatePasswordPage")
    public String updatePasswordInfo(){
        return "admin/user/updatePassword";
    }

    /**
     * 返回添加页
     * @return
     */
    @GetMapping("/addUserPage")
    public ModelAndView addUser(String deptId) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/user/addUser");
        mv.addObject("deptId", deptId);
        return mv;
    }

    /**
     * 返回分配角色页面
     * @return
     */
    @GetMapping("/distributeRolePage")
    public ModelAndView distributeRole(String id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/user/distributeRole");
        mv.addObject("userId", id);
        return mv;
    }


}
