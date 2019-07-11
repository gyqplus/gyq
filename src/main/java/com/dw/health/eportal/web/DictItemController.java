package com.dw.health.eportal.web;

import com.dw.health.framework.web.Result;
import com.dw.health.eportal.entity.DictItem;
import com.dw.health.eportal.entity.dto.DictItemDTO;
import com.dw.health.eportal.service.DictItemService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 字典项目controller
 * @Author: qjf
 * @Date: 2019/3/19
 */
@Controller
@RequestMapping("/dictItem")
public class DictItemController {
    @Autowired
    private DictItemService dictItemService;

    /**
     * 新增字典项
     * @param dictItem
     * @return
     */
    @PostMapping("/saveDictItem")
    @ResponseBody
    public Result<String> saveDictItem(DictItem dictItem){
        dictItemService.save(dictItem);
        return new Result<>("保存成功");
    }

    /**
     * 删除字典项
     * @param dictItemList
     * @return
     */
    @PostMapping("/deleteDictItem")
    @ResponseBody
    public Result<String> deleteDictItem(@RequestBody List<DictItem> dictItemList){
        dictItemService.deleteDictItem(dictItemList);
        return new Result<>("删除成功");
    }

    /**
     * 更新字典项
     * @param dictItem
     * @return
     */
    @PostMapping("/updateDictItem")
    @ResponseBody
    public Result<String> updateDictItem(DictItem dictItem){
        dictItemService.updateDictItem(dictItem);
        return new Result<>("更新成功");
    }

    @PostMapping("/listDictItem")
    @ResponseBody
    public Result<List<DictItem>> listDictItem(DictItemDTO dictItemDTO){
        if (dictItemDTO.getTypeId() == null){
            return null;
        }
        List<DictItem> dictItemList = dictItemService.listDictItem(dictItemDTO);
        PageInfo pageInfo = new PageInfo(dictItemList);
        return new Result<>(dictItemList).setTotal((int) pageInfo.getTotal());
    }

    //--------------------------页面跳转---------------------------------------

    /**
     * 返回修改页面
     * @param id
     * @return
     */
    @GetMapping("/updateDictItemPage")
    public ModelAndView updateDictItemInfo(String id) {
        ModelAndView mv = new ModelAndView();
        DictItem dictItem = dictItemService.getDictItemById(id);
        mv.setViewName("admin/dict/updateDictItem");
        mv.addObject("dictItem", dictItem);
        return mv;
    }

    /**
     * 返回添加页
     * @return
     */
    @GetMapping("/addDictItemPage")
    public ModelAndView addDictItem(String id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/dict/addDictItem");
        mv.addObject("typeId", id);
        return mv;
    }

}
