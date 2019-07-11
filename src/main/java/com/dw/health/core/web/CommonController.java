package com.dw.health.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("coreCommonController")
@RequestMapping(path = "/core/common")
public class CommonController {
    /**
     * 跳转无权限页面
     */
    @RequestMapping(path = "401", method = {RequestMethod.GET, RequestMethod.POST})
    public String http401() {
        return "core/common/error/401";
    }

    /**
     * 跳转地址不存在页面
     */
    @RequestMapping(path = "404", method = {RequestMethod.GET, RequestMethod.POST})
    public String http404() {
        return "core/common/error/404";
    }

    /**
     * 跳转内部错误页面
     */
    @RequestMapping(path = "/500", method = {RequestMethod.GET, RequestMethod.POST})
    public String http500() {
        return "core/common/error/500";
    }
}