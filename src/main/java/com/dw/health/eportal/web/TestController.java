package com.dw.health.eportal.web;

import com.dw.health.eportal.dao.UserMapper;
import com.dw.health.eportal.entity.dto.UserDTO;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gyq on 2019/3/21.
 */
@Controller
@RequestMapping(path = "/test")
public class TestController {
    @Autowired
    UserMapper userMapper;
    @GetMapping(path = "/test1")
    public String test1() {
        return "test/test1";
    }
    @GetMapping(path = "/test2")
    public String test2() {
        return "test/test2";
    }

    @GetMapping(path = "/select2")
    public String select2() {
        return "test/select2";
    }

    @GetMapping(path = "/tableExport")
    public String tableExport() {
        return "test/tableExport";
    }

    @GetMapping(path = "/pageUserList")
    public String pageUserList() {
        return "admin/user/pageUserList";
    }

    @PostMapping("/getUserList")
    @ResponseBody
    public List<UserDTO> getUserList(UserDTO userDTO) {
        return userMapper.selectBySearchDTO(userDTO);
    }
    @GetMapping(path = "/testLayim")
    public String testLayim() {
        return "test/testLayim";
    }
    @PostMapping("/uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile file){
        Map<String, String> data = new HashMap<>();
        data.put("src","http://10.1.83.66:8666/img/idea_bgi1.jpg");
        String fileName = file.getName();
        System.out.print(fileName);
        JSONObject result = new JSONObject();
        result.put("code", "0");
        result.put("msg", "");
        result.put("data",data);
        System.out.println("jsonString"+result.toString());
        return result.toString();
    }
    @PostMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(MultipartFile file){
        Map<String, String> data = new HashMap<>();
        data.put("src","http://10.1.83.66:8666/img/15549723843721432977083.jar");
        String fileName = file.getName();
        System.out.print(fileName);
        JSONObject result = new JSONObject();
        result.put("code", "0");
        result.put("msg", "");
        result.put("data",data);
        System.out.println("jsonString"+result.toString());
        return result.toString();
    }
    @GetMapping(path = "/tetsV")
    public void testValue() {

    }
    @GetMapping("/getSubject")
    @ResponseBody
    public String getSubject(){
        return (String) SecurityUtils.getSubject().getSession().getId();
    }
}
