package com.example.demo.controller.abs;

import com.example.demo.annotation.CurrentUser;
import com.example.demo.bean.response.Result;
import com.example.demo.model.UserInfos;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    Result login(String username, String password);

    @ApiOperation(value = "授权登录", notes = "使用授权ID来登录获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户名", required = false, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/authorizationlogin", method = RequestMethod.POST)
    Result authorizationLogin(@CurrentUser UserInfos user);

    @ApiOperation(value = "注册用户", notes = "注册新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    Result registerUser(String username, String password);

}
