package com.dw.health.eportal.entity.openfire;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Openfire
 * @Description: openfire系统配置
 * @Author: lyx
 * @Date: 2019/4/11 13:26
 */
@Data
@Component
@ConfigurationProperties(prefix="openfire")
public class OpenfireConfig {

    private String server_url;

    private String server_port;

    private String rest_key;

    private String bosh_service;

    private String service_name;

    private String service_password;

    public Integer getServer_port() {
        return Integer.valueOf(server_port);
    }
}
