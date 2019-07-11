package com.dw.health.eportal.openapi;

import com.dw.health.framework.web.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.*;

/**
 * 接口模拟
 * @Author: qjf
 * @Date: 2019/2/27
 */
@Controller
@RequestMapping("/homePage")
public class HomePageController {
    
    /**
     *
     * @ClassName: HomePageController
     * @Description: 获取待办事项
     * @author gyq 
     * @date 2019-2-28 10:47
     * @version V1.0 
     */
    @PostMapping("/getSchedule")
    @ResponseBody
    public Result<List<Map>> getSchedule(@RequestParam Map<String,String> param){
        String userId = param.get("userId");
        int limit = Integer.parseInt(param.get("limit"));
        List<Map> list = new ArrayList();
        Map map = new HashMap();
        map.put("title", "您【2018-11-30】日【上午】有【20位】患者待诊");
        map.put("dotime", DateFormat.getDateInstance().format(new Date()));
        Map map1 = new HashMap();
        map1.put("title", "患者【史努比】的【血常规】报告已出");
        map1.put("dotime", DateFormat.getDateInstance().format(new Date()));
        Map map2 = new HashMap();
        map2.put("title", "您有【2】位患者的【MR|影像】已出");
        map2.put("dotime", DateFormat.getDateInstance().format(new Date()));
        Map map3 = new HashMap();
        map3.put("title", "您下周的排版计划已完成");
        map3.put("dotime", DateFormat.getDateInstance().format(new Date()));
        Map map4 = new HashMap();
        map4.put("title", "您【单倍型移植体系研究】需要提交月报");
        map4.put("dotime", DateFormat.getDateInstance().format(new Date()));
        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        return new Result<>(list);
    }


    /**
     *
     * @ClassName: HomePageController
     * @Description 获取通知公告等 
     * @author gyq 
     * @date 2019-2-28 10:53
     * @version V1.0 
     */
    @PostMapping("/getMessage")
    @ResponseBody
    public Result<List<Map>> getMessage(@RequestParam Map<String,String> param){
        //limit = 10;
        String type = param.get("type");
        int limit = Integer.parseInt(param.get("limit"));
        String title = "未设置type";
        if ("2".equals(type)){
            title="新闻";
        }
        if ("3".equals(type)){
            title="公告";
        }
        List<Map> list = new ArrayList();
        Map map = new HashMap();
        map.put("title",title+"我院全体工会干部学习第十五次代表大会精神");
        map.put("url","http://10.1.60.24:8083/portal/%E6%96%B0%E9%97%BB-%E5%8D%81%E4%BA%94%E5%A4%A7.html");
        map.put("dotime", DateFormat.getDateInstance().format(new Date()));
        list.add(map);
        Map map1 = new HashMap();
        map1.put("title",title+"我院入选首批国家临床教学培训示范中心");
        map1.put("url","http://10.1.60.24:8083/portal/%E6%96%B0%E9%97%BB-%E7%A4%BA%E8%8C%83%E4%B8%AD%E5%BF%83.html");
        map1.put("dotime", DateFormat.getDateInstance().format(new Date()));
        list.add(map1);
        Map map2 = new HashMap();
        map2.put("title",title+"我院入选首批国家临床教学培训示范中心");
        map2.put("url","http://10.1.60.24:8083/portal/%E6%96%B0%E9%97%BB-%E5%8D%81%E4%BA%94%E5%A4%A7.html");
        map2.put("dotime", DateFormat.getDateInstance().format(new Date()));
        list.add(map2);
        Map map3 = new HashMap();
        map3.put("title",title+"我院第二届男子足球联赛顺利落下帷幕");
        map3.put("url","http://10.1.60.24:8083/portal/%E6%96%B0%E9%97%BB-%E8%B6%B3%E7%90%83%E8%B5%9B.html");
        map3.put("dotime", DateFormat.getDateInstance().format(new Date()));
        list.add(map3);
        Map map4 = new HashMap();
        map4.put("title",title+"我院召开全体工会干部会议");
        map4.put("url","http://10.1.60.24:8083/portal/%E6%96%B0%E9%97%BB-%E5%8D%81%E4%BA%94%E5%A4%A7.html");
        map4.put("dotime", DateFormat.getDateInstance().format(new Date()));
        list.add(map4);

        return new Result<>(list);
    }
}
