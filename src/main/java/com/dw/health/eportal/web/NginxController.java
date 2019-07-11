package com.dw.health.eportal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dw.health.core.config.NginxConfig;
import com.dw.health.framework.web.Result;

/**
 * Created by gyq on 2019-4-17.
 */
@Controller
@RequestMapping("/nginx")
public class NginxController {
    @Autowired
    private NginxConfig nginxConfig;

    @PostMapping("/getNginxConfig")
    @ResponseBody
    public Result<NginxConfig> getNginxConfig() {
        return new Result<>(nginxConfig);
    }
}
