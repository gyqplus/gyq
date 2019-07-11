package com.dw.health.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by gyq on 2019-4-16.
 */
@Data
@Component
@ConfigurationProperties(prefix="nginx")
public class NginxConfig {
    private String hostname;
    private String port;
    private String avatar_url;
}
