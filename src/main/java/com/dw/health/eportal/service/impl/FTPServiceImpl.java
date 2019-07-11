/**
 * @User Administrator
 */

package com.dw.health.eportal.service.impl;

import com.dw.health.core.config.FTPConfig;
import com.dw.health.core.config.NginxConfig;
import com.dw.health.eportal.service.FTPService;
import com.dw.health.framework.consts.GlobalNames;
import com.jcraft.jsch.*;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * Created by gyq on 2019-4-15.
 */
@Service
public class FTPServiceImpl implements FTPService {
    @Autowired
    FTPConfig ftpConfig;
    @Autowired
    NginxConfig nginxConfig;

    private FTPClient ftpClient;


    private ChannelSftp sftp;

    private Session session;
    /** SFTP 登录用户名*/
    private String username;
    /** SFTP 登录密码*/
    private String password;
    /** 私钥 */
    private String privateKey;
    /** SFTP 服务器地址IP地址*/
    private String host;
    /** SFTP 端口*/
    private int port;

    @Override
    public void login(){
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }

            session = jsch.getSession(ftpConfig.getUsername(), ftpConfig.getHostname(), ftpConfig.getPort());
            if (ftpConfig.getPassword() != null) {
                session.setPassword(ftpConfig.getPassword());
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String sftpUpload(MultipartFile mFile, String pathType) {
        StringBuffer uploadedPath = new StringBuffer();//已上传到的文件地址
        InputStream inputStream = null;
        String fileName = null;
        String pathname = parsePath(mFile, pathType);
        uploadedPath.append("http://");
        uploadedPath.append(ftpConfig.getHostname());
        uploadedPath.append(":");
        uploadedPath.append(nginxConfig.getPort());
        uploadedPath.append("/");
        uploadedPath.append(pathname);
        uploadedPath.append("/");
        uploadedPath.append(mFile.getOriginalFilename());
        login();
        //boolean flag = false;
        try {
            inputStream = mFile.getInputStream();
            fileName = mFile.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cdPath("/home/sftpUpload",pathname);
        try {
            sftp.put(inputStream, fileName);  //上传文件
            sftp.chmod(509,fileName);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return uploadedPath.toString();
    }

    /**
     * 进入指定文件夹(没有创建)
     * @param basePath
     * @param pathAdd
     */
    private void cdPath(String basePath,String pathAdd){
        try {
            sftp.cd(basePath);
            sftp.cd(pathAdd);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String [] dirs=pathAdd.split("/");
            String tempPath="/home/sftpUpload";
            for(String dir:dirs){
                if(null== dir || "".equals(dir)) continue;
                tempPath+="/"+dir;
                try{
                    sftp.cd(tempPath);
                }catch(SftpException ex){
                    try {
                        sftp.mkdir(tempPath);
                        sftp.cd(tempPath);
                    } catch (SftpException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

    }
    /**
     * 解析上传分类路径 生成时间file
     *
     * @param file
     * @param pathType
     * @return
     */
    public String parsePath(MultipartFile file, String pathType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar now = Calendar.getInstance();
        //聊天文件加时间文件夹
        if (GlobalNames.PATH_CHAT_FILES.equals(pathType))
            pathType = pathType + "/" + sdf.format(now.getTime());
        return pathType;
    }
}
