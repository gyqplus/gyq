/**
 * @User Administrator
 */

package com.dw.health.eportal;

import com.dw.health.eportal.entity.Application;
import com.dw.health.eportal.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Administrator on 2019-2-22.
 */
@Controller
public class Test {
    @Autowired
    private ApplicationService appService;
    @org.junit.Test
    public void test() throws Exception {
        //List<Application> apps = appService.getApps();
        throw new Exception();

    }

}
