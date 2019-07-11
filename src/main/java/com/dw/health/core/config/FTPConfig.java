package com.dw.health.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * ftp服务器参数配置
 *
 * @author gyq
 * @date 2019-4-15 16:19
 */
@Configuration
public class FTPConfig {
    //ftp服务器地址
    @Value("${ftp.hostname}")
    private String hostname;
    //ftp服务器端口号默认为21
    @Value("${ftp.port}")
    private Integer port;
    //ftp登录账号
    @Value("${ftp.username}")
    private String username;
    //ftp登录密码
    @Value("${ftp.password}")
    private String password;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
