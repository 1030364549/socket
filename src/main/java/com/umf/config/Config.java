package com.umf.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "config")
public class Config {
    private Integer port;
    private Integer threadPool;
    private Integer timeOut;
    private String tax;
    private String dqtax;
    private String[] ip;
    private String app_key;
    private String master_secret;
    private String app_keyios;
    private String master_secretios;
}
