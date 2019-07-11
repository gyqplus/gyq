package com.dw.health.eportal.web;

import com.dw.health.framework.web.Result;
import com.dw.health.eportal.entity.Dict;
import com.dw.health.eportal.entity.dto.DictDTO;
import com.dw.health.eportal.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping(path="/dict")
public class DictController {

    @Autowired
    DictService dictService;


    @PostMapping(path="/saveDict")
    @ResponseBody
    public Result<String> saveDict(Dict record){
        dictService.saveDict(record);
        return new Result<>("新增成功");
    }


    @PostMapping(path="/deleteDict")
    @ResponseBody
    public Result<String> deleteDict(@RequestBody List<Dict> dictList){
        dictService.deleteDict(dictList);
        return new Result<>("删除成功");
    }


    @PostMapping(path="/updateDict")
    @ResponseBody
    public Result<String> updateDict(Dict dict,String typeIdOld){
        dictService.updateDict(dict, typeIdOld);
        return new Result<>("修改成功");
    }


    @PostMapping(path="/listDict")
    @ResponseBody
    public Result<List<Dict>> listDict(DictDTO dictDTO){
        List<Dict> list = dictService.listDict(dictDTO);
        PageInfo pageInfo = new PageInfo(list);
        return new Result<>(list).setTotal((int) pageInfo.getTotal());
    }


    //------------------------页面跳转-----------------------------------

    /**
     * 返回修改页面
     * @param id
     * @return
     */
    @GetMapping("/updateDictPage")
    public ModelAndView updateDictInfo(String id) {
        ModelAndView mv = new ModelAndView();
        Dict dict = dictService.getDictByid(id);
        mv.setViewName("admin/dict/updateDict");
        mv.addObject("dict", dict);
        return mv;
    }

    /**
     * 返回添加页
     * @return
     */
    @GetMapping("/addDictPage")
    public String addDict() {
        return "admin/dict/addDict";
    }
    /**
     * 返回修改页面
     * @param id
     * @return
     */
    @GetMapping("/dictItemPage")
    public ModelAndView dictItemPage(String id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/dict/dictItemShow");
        mv.addObject("typeId", id);
        return mv;
    }

}
