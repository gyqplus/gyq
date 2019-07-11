package com.dw.health.eportal.web;

import com.dw.health.eportal.service.NavigationService;
import com.dw.health.eportal.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {
    @Autowired
    private NavigationService navigationService;

    @GetMapping("/")
    public String adminPage(Model model) {
        model.addAttribute("navjson", navigationService.getNavigation());
        model.addAttribute("user", CurrentUserUtil.getCurrentUser());
        return "admin/admin";
    }

    @GetMapping("/adminHome")
    public String adminHome() {
        return "admin/homepage";
    }

    @GetMapping("/userShow")
    public String userShow() {
        return "admin/user/userShow";
    }

    @GetMapping("/dictShow")
    public String dictShow() {
        return "admin/dict/dictShow";
    }

    @GetMapping("/dictShow2")
    public String dictShow2() {
        return "admin/dict/dictShow2";
    }

    @GetMapping("/dictShow3")
    public String dictShow3() {
        return "admin/dict/dictShow3";
    }

    @GetMapping("/navigationShow")
    public String navigationShow() {
        return "admin/navigation/navigationShow";
    }

    @GetMapping("/roleShow")
    public String roleShow() {
        return "admin/role/roleShow";
    }

    @GetMapping("/deptShow")
    public String deptShow(){
        return "admin/department/departmentShow";
    }

    @GetMapping("/permissionShow")
    public String permissionShow() {
        return "admin/permission/permissionShow";
    }

    @GetMapping("/appShow")
    public String appShow() {
        return "admin/application/appShow";
    }

    @GetMapping("/apiShow")
    public String apiShow() {
        return "redirect:/swagger-ui.html";
    }

    @GetMapping("/pageUserList")
    public String pageUserList() {
        return "admin/user/pageUserList";
    }
}
