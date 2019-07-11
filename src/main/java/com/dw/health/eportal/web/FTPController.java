package com.dw.health.eportal.web;


import com.dw.health.framework.consts.GlobalNames;
import com.dw.health.eportal.service.FTPService;
import com.jcraft.jsch.SftpException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gyq 
 * @date 2019-4-15 17:08
 */
@Controller
@RequestMapping("/ftp")
public class FTPController {
    @Autowired
    private FTPService ftpService;

    @PostMapping("/uploadChatFile")
    @ResponseBody
    public String uploadChatFile(MultipartFile file) {
        String uploadedPath = null;

        uploadedPath = ftpService.sftpUpload(file, GlobalNames.PATH_CHAT_FILES);

        Map<String, String> data = new HashMap<>();
        data.put("src", uploadedPath);
        JSONObject result = new JSONObject();
        result.put("code", "0");
        result.put("msg", "");
        result.put("data", data);
        System.out.println("jsonString" + result.toString());
        return result.toString();
    }

    @PostMapping("/uploadAvatar")
    @ResponseBody
    public String uploadAvatar(MultipartFile file) {
        String uploadedPath = null;

        uploadedPath = ftpService.sftpUpload(file, GlobalNames.PATH_CHAT_AVATAR);

        Map<String, String> data = new HashMap<>();
        data.put("src", uploadedPath);
        JSONObject result = new JSONObject();
        result.put("code", "0");
        result.put("msg", "");
        result.put("data", data);
        System.out.println("jsonString" + result.toString());
        return result.toString();
    }
}
