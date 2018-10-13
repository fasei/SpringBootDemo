package com.example.demo.controller;

import com.example.demo.annotation.LoginRequired;
import com.example.demo.bean.response.Result;
import com.example.demo.bean.response.TokenResponse;
import com.example.demo.config.ConfigData;
import com.example.demo.constants.ResultCode;
import com.example.demo.controller.abs.AbsUserController;
import com.example.demo.exception.MyException;
import com.example.demo.log.LogUtil;
import com.example.demo.model.UserInfos;
import com.example.demo.model.UserLogin;
import com.example.demo.service.UserInfoServices;
import com.example.demo.service.UserLoginServices;
import com.example.demo.util.ModeFactory;
import com.example.demo.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: wangchao
 * Time: 2018-10-11
 * Description: This is 用户操作接口
 */
@RestController
@RequestMapping(value = "/demo")
@Api(value = "用户信息查询", description = "用户基本信息操作API", tags = "UserApi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController implements AbsUserController {
    @Autowired
    ConfigData mConfigData;

    @Resource
    UserInfoServices mUserInfoServices;

    @Resource
    UserLoginServices mUserLoginServices;

    public Result login(String username, String password) {
        UserLogin mUserLogin = mUserLoginServices.getUserInfo(username);
        if (mUserLogin == null) {
            return Result.failure(ResultCode.USER_NOT_EXIST);
        }
        if (!mUserLogin.getPsw().equals(password)) {//密码不相等
            return Result.failure(ResultCode.USER_LOGIN_ERROR_PASSWORD);
        }
        String token = TokenUtil.createJwtToken(mUserLogin.getName() + "");
        return Result.success(new TokenResponse(token));
    }

    public Result authorizationLogin(String authorizationid) {
        UserInfos user = mUserInfoServices.getUserInfo(authorizationid);
        if (user == null) {
            return Result.failure(ResultCode.USER_NOT_EXIST);
        }

        String token = TokenUtil.createJwtToken(user.getId() + "");
        return Result.success(new TokenResponse(token));
    }

    @LoginRequired
    @ApiOperation(value = "初次请求", notes = "带token请求数据", position = 2)
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public ConfigData getUserName() {
        String msg = "Spring Boot系列之Log4j2的配置及使用";
        logger.debug("debug:\n----------------\nddd" + System.getProperty("catalina.home") + "\n----------------");
        logger.info("ddd" + System.getProperty("catalina.home"));
        logger.info("info:\n----------------\nddd" + System.getProperty("catalina.home") + "\n----------------");
        logger.info(msg);
        logger.trace(msg);
        logger.debug(msg);
        logger.info(msg);
        logger.info(mConfigData.toString());
        logger.warn(msg);
        logger.error(msg);

        LogUtil.getBussinessLogger().info("LogUtils.getBussinessLogger().info");
        LogUtil.getDBLogger().info("LogUtils.getDBLogger()");
        LogUtil.getExceptionLogger().info("LogUtils.getExceptionLogger()");
        LogUtil.getPlatformLogger().info("LogUtils.getPlatformLogger()");
        return mConfigData;
    }

    @Override
    public Result registerUser(String username, String password) {
        UserInfos user = mUserInfoServices.getUserInfo(username);
        if (user != null) {
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }

        UserInfos u= ModeFactory.getNewUserInfo(username);
        int result=mUserInfoServices.setUser(u);
//        Userinfo u = new Userinfo(username, password);
//        LogUtil.getBussinessLogger().info("registerUser:" + u.toString());
//
//        int result = mapper.setUser(u);
//        logger.debug("registerUser.result:" + result);

        return Result.success();
    }


    @ApiOperation(value = "添加自定义异常", notes = "自定义异常", position = 4, nickname = "22")
    @RequestMapping(value = "/makeexception", method = RequestMethod.GET)
    public int makeException() {
        logger.info("makeException");
        throw new MyException(222, "自定义的错误");
    }
/*
    @ApiOperation(value = "添加随机异常", notes = "自定义异常", position = 5)
    @RequestMapping(value = "/makeerror", method = RequestMethod.GET)
    public int makeRandomError() {
        throw new NullPointerException("随机异常");
    }

    @ApiOperation(value = "查询用户", notes = "分页查询用户", position = 6)
    @RequestMapping(value = "/getusers/{pagenum}/{pagesize}", method = RequestMethod.GET)
    public List<Userinfo> getUsers(@PathVariable("pagenum") int pagenum, @PathVariable("pagesize") int pagesize) {
        return mapper.getUserList(pagenum, pagesize);
    }

    */

}
