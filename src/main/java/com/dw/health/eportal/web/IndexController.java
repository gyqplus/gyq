package com.dw.health.eportal.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author gyq
 * @version V1.0 
 * @ClassName: IndexController
 * @Description: 用于处理系统请求 如主页跳转等
 * @date 2019-2-22 14:08
 */
@Controller
public class IndexController {
    @GetMapping(path = "/")
    public String getIndex() {
        return "redirect:/index";
    }

    @RequestMapping(path = "/index")
    public String getIndexPage() {return "index"; }
}
