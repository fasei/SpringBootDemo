package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: wangchao
 * Time: 2018-10-23
 * Description: This is
 */
@Component
@ConfigurationProperties(prefix = "localfile")
public class LocalFile {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
