/**
 * @User Administrator
 */

package com.dw.health.eportal.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dw.health.eportal.entity.Application;
import com.dw.health.eportal.entity.dto.ApplicationDTO;
import com.dw.health.eportal.service.ApplicationService;
import com.dw.health.eportal.service.FavoriteAppService;
import com.dw.health.framework.exception.BizException;
import com.dw.health.framework.web.Result;
import com.github.pagehelper.PageInfo;

/**
 * Created by gyq on 2019-2-20.
 */
@Controller
@RequestMapping(path = "/application")
public class ApplicationController {
    @Autowired
    ApplicationService appService;
    @Autowired
    FavoriteAppService favoriteAppService;

    @PostMapping("/saveApplication")
    @ResponseBody
    public Result<String> addApplication(Application application, @RequestParam("img") MultipartFile img) {
        byte[] bt;
        try {
            bt = img.getBytes();
        } catch (IOException e) {
            throw new BizException("请重新选择合适的图片!");
        }
        if (bt.length != 0) application.setIcon(bt);
        appService.saveApplication(application);
        return new Result<>("新增成功");
    }

    @PostMapping("/deleteApplication")
    @ResponseBody
    public Result<String> deleteApplication(@RequestBody List<Application> appList) {
        appService.deleteAppList(appList);
        return new Result<>("删除成功");
    }

    @PostMapping("/updateApplication")
    @ResponseBody
    public Result<String> updateApplication(Application application, @RequestParam("img") MultipartFile img) {
        byte[] bt;
        try {
            bt = img.getBytes();
        } catch (IOException e) {
            throw new BizException("请重新选择合适的图片!");
        }
        if (bt.length != 0) application.setIcon(bt);
        appService.updateApplication(application);
        return new Result<>("更新成功");
    }

    @PostMapping("/updateApplicationWithoutIcon")
    @ResponseBody
    public Result<String> updateApplicationWithoutIcon(Application application) {
        appService.updateApplication(application);
        return new Result<>("更新成功");
    }

    @PostMapping(path = "/listAppDTO")
    @ResponseBody
    public Result<List<ApplicationDTO>> ListAppDTO(ApplicationDTO appDTO) {
        List<ApplicationDTO> appDTOList = appService.listAppDTO(appDTO);
        PageInfo pageInfo = new PageInfo(appDTOList);
        return new Result<>(appDTOList).setTotal((int) pageInfo.getTotal());
    }


    /**
     * @return com.dw.health.framework.web.Result<com.dw.health.eportal.entity.Application>
     * @throws
     * @Title: getAppByTypeAndPageTestResult&nbsp;
     * @Description: 通过类型 分页查询应用(result包装)
     * @author gyq
     * @date 2019-2-25 11:48
     * @version V1.0 &nbsp;&nbsp;
     */
    @PostMapping(path = "/listAppByType")
    @ResponseBody
    public Result<List<Application>> ListAppByType(ApplicationDTO appDTO) {
        String type = appDTO.getType();
        String userId = appDTO.getUserId();
        Integer currentPage = appDTO.getCurrentPage();
        Integer pageSize = appDTO.getPageSize();
        List<Application> appList;
        if ("3".equals(appDTO.getType())) {
            appList = favoriteAppService.listFavoriteAppByUserId(userId, currentPage, pageSize);
        } else {
            appList = appService.listAppByType(currentPage, pageSize, type);
        }
        return new Result<>(appList);
    }


    /**
     * @ClassName: ApplicationController
     * @Description: 通过类型获取某类型的应用(result包装)
     * @author gyq 
     * @date 2019-2-26 19:08
     * @version V1.0 
     */
    @PostMapping(path = "/countAppByType")
    @ResponseBody
    public Result<Map<String, Object>> getAppsCountResultByType(ApplicationDTO appDTO) {
        int appsCount = 0;
        String type = appDTO.getType();
        String userId = appDTO.getUserId();
        // 3代表是用户收藏应用
        if ("3".equals(type)) {
            appsCount = favoriteAppService.countAppIdByUserId(userId);
        } else {
            appsCount = appService.countAppByType(type);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("appsCount", appsCount);
        return new Result<>(data);
    }
    // --------------------------页面跳转-------------------------------------

    /**
     * 返回修改页面
     *
     * @param id
     * @return
     */
    @GetMapping("/updateAppPage")
    public ModelAndView updateAppInfo(String id) {
        ModelAndView mv = new ModelAndView();
        ApplicationDTO appDTO = appService.getAppDTOByAppId(id);
        mv.setViewName("admin/application/updateApp");
        mv.addObject("app", appDTO);
        return mv;
    }

    /**
     * 返回添加页面
     *
     * @return
     */
    @GetMapping("/addAppPage")
    public String addApp() {
        return "admin/application/addApp";
    }
}
