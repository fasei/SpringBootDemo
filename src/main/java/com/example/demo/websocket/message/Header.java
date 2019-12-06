package com.example.demo.websocket.message;

/**
 * Author: wangchao
 * Time: 2019-07-03
 * Description: This is
 */
public class Header {

    public static final String NowVersion="V1.0";
    /**
     * 消息id
     */
    private int mid;
    /**
     * 命令类型
     */
    private int command;
    /**
     * 版本号
     */
    private String version;

    public Header() {
    }

    public Header(int mid, int command, String version) {
        this.mid = mid;
        this.command = command;
        this.version = version;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
