package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: wangchao
 * Time: 2018-10-23
 * Description: This is
 */
@Component
@ConfigurationProperties(prefix = "uploadfile")
public class LocalFile {

    // 获取存放位置
    private String windows;
    private String linux;
    private String mac;

    public String getWindows() {
        return windows;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

    public String getLinux() {
        return linux;
    }

    public void setLinux(String linux) {
        this.linux = linux;
    }

    public String getBasePath() {
        String locationPath = "";

        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("win")) {
            locationPath = windows;
        } else if (os.toLowerCase().contains("mac")) {
            locationPath = mac;
        } else {
            locationPath = linux;
        }

        return locationPath;

    }
}
