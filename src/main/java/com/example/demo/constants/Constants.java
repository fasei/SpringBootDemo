package com.example.demo.constants;

/**
 * Author: wangchao
 * Time: 2018-09-28
 * Description: This is
 */
public class Constants {
    /**
     * 当前用户参数名
     */
    public final static String CURRENT_USER = "CurrentUser";


    /**
     * 基本controller的路径
     */
    public final static String BaseControllerPath = "/manager";
    /**
     * 基本Websocket路径
     */
    public final static String BaseSocketPath = "/websocket";

    public final static String UserControllerPath = BaseControllerPath + "/user";
    public final static String FileControllerPath = BaseControllerPath + "/file";

    public final static String WebSocketPath = BaseSocketPath + "/connect";
}
