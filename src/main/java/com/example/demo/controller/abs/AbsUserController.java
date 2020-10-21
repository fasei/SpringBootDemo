package com.example.demo.controller.abs;

import com.example.demo.annotation.CurrentUser;
import com.example.demo.annotation.LoginRequired;
import com.example.demo.bean.response.Result;
import com.example.demo.exception.MyException;
import com.example.demo.model.UserInfos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.awt.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Author: wangchao
 * Time: 2018-10-11
 * Description: This is 用户相关接口说明
 */


public interface AbsUserController {

    @ApiOperation(value = "普通登录", notes = "根据用户名和密码来登录获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    Result login(String username, String password);

    @ApiOperation(value = "注册用户", notes = "注册新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    Result registerUser(String username, String password);

    @ApiOperation(value = "授权登录", notes = "使用授权ID来登录获取token")
    @RequestMapping(value = "/authorizationlogin", method = RequestMethod.POST)
    Result authorizationLogin(@CurrentUser @ApiIgnore @ApiParam(hidden = true) UserInfos user);

    @ApiOperation(value = "初次请求", notes = "带token请求数据")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    Result hello();

    @ApiOperation(value = "默认token", notes = "默认token")
    @RequestMapping(value = "/defaulttoken", method = {GET})
    String defaulttoken();

    @ApiOperation(value = "添加自定义异常", notes = "自定义异常")
    @RequestMapping(value = "/makeexception", method = {GET})
    void makeException();

}
