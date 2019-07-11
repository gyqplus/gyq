/**
 * @User gyq
 */

package com.dw.health.eportal.web;

import com.dw.health.framework.web.Result;
import com.dw.health.eportal.entity.Navigation;
import com.dw.health.eportal.entity.dto.NavigationDTO;
import com.dw.health.eportal.service.NavigationService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gyq
 * @version V1.0 
 * @ClassName: NavigationController
 * @Description: 导航
 * @date 2019-3-7 15:38
 */
@Controller
@RequestMapping(path = "/nav")
public class NavigationController {
    @Autowired
    NavigationService navService;

    @GetMapping("/getNavHtmlStr")
    @ResponseBody
    public Result<Map<String, String>> getNavHtmlStr() {
        Map<String, String> map = new HashMap();
        map.put("navHtml", navService.getNavigationHtml());
        return new Result<>(map);
    }

    /**
     * 新增菜单
     *
     * @param nav
     * @return 新增信息
     * @author qjf
     */
    @PostMapping("/addNav")
    @ResponseBody
    public Result<String> addNav(Navigation nav) {
        navService.addNav(nav);
        return new Result<>("新增成功");
    }

    /**
     * 删除菜单，含有子菜单禁止删除
     *
     * @param navs
     * @return
     * @author qjf
     */
    @PostMapping("/deleteNav")
    @ResponseBody
    public Result<String> deleteNav(@RequestBody List<Navigation> navs) {
        navService.deleteNav(navs);
        return new Result<>("删除成功");
    }

    /**
     * 删除菜单，包括子菜单
     * @param navId
     * @return
     */
    @PostMapping("/deleteNavs")
    @ResponseBody
    public Result<String> deleteNavs(String navId){
        navService.deleteNavs(navId);
        return new Result<>("删除成功");
    }
    @PostMapping("/updateNav")
    @ResponseBody
    public Result<String> updateNav(Navigation nav) {
        navService.updateNav(nav);
        return new Result<>("更新成功");
    }

    @PostMapping("/getNavListSimple")
    @ResponseBody
    public Result<List<Navigation>> getNavListSimple() {
        List<Navigation> navs = navService.getNavigationListSimple();
        return new Result<>(navs);
    }

    @PostMapping("/getNavListByPid")
    @ResponseBody
    public Result<List<NavigationDTO>> getNavListByPid(NavigationDTO navDTO) {
        List<NavigationDTO> navs = navService.getNavigationListByPid(navDTO);
        PageInfo pageInfo = new PageInfo(navs);
        return new Result<>(navs).setTotal((int) pageInfo.getTotal());
    }

    /**
     * 跳转添加页面
     *
     * @param pid
     * @return
     */
    @GetMapping("/addNavPage")
    public ModelAndView addNavPage(String pid) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/navigation/addNavigation");
        mv.addObject("pid", pid);
        return mv;
    }

    /**
     * 跳转更新页面
     *
     * @param id
     * @return
     */
    @GetMapping("/updateNavPage")
    public ModelAndView updateNavPage(String id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/navigation/updateNavigation");
        Navigation nav = navService.getNavigationById(id);
        mv.addObject("nav", nav);
        return mv;
    }

}
