package com.example.demo.controller;

import com.example.demo.annotation.LoginRequired;
import com.example.demo.bean.response.Result;
import com.example.demo.bean.response.TokenResponse;
import com.example.demo.config.ConfigData;
import com.example.demo.constants.Constants;
import com.example.demo.constants.ResultCode;
import com.example.demo.exception.MyException;
import com.example.demo.log.LogUtil;
import com.example.demo.model.UserInfos;
import com.example.demo.model.UserLogin;
import com.example.demo.service.UserServices;
import com.example.demo.util.ModeFactory;
import com.example.demo.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Author: wangchao
 * Time: 2018-10-11
 * Description: This is 用户操作接口
 */
@RestController
@RequestMapping(value = Constants.UserControllerPath)
@Api(tags = "用户信息管理中心", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController extends BaseController {
    @Autowired
    ConfigData mConfigData;

    @Resource
    UserServices mUserServices;

    @ApiOperation(value = "普通登录", notes = "根据用户名和密码来登录获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public Result<TokenResponse> login(@Size(min = 3, max = 20, message = "用户名最少为3位") String username, @Size(min = 6, max = 20, message = "密码最少为6位") String password) {
        Result result = Result.getInstance();

        try {
            UserLogin mUserLogin = mUserServices.getUserLogin(username);
            if (mUserLogin == null) {
                return Result.failure(ResultCode.USER_NOT_EXIST);
            }
            if (!mUserLogin.getPsw().equals(password)) {//密码不相等
                return Result.failure(ResultCode.USER_LOGIN_ERROR_PASSWORD);
            }
            String token = TokenUtil.createJwtToken(mUserLogin.getId() + "");
            return Result.success(new TokenResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.DATA_IS_WRONG);
        }
        return result;
    }

    @LoginRequired
    @ApiOperation(value = "授权登录", notes = "使用授权ID来登录获取token")
    @RequestMapping(value = "/authorizationlogin", method = RequestMethod.POST)
    public Result authorizationLogin(UserInfos user) {
        logger.info(user.toString());
        return Result.success(user);
    }

    public Result authorizationLogin(String authorizationid) {
        UserInfos user = null;
        try {
            user = mUserServices.getUserInfo(authorizationid);
            if (user == null) {
                return Result.failure(ResultCode.USER_NOT_EXIST);
            }

            String token = TokenUtil.createJwtToken(user.getId() + "");
            return Result.success(new TokenResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.failure(ResultCode.DATA_IS_WRONG);
    }

    @LoginRequired
    @ApiOperation(value = "初次请求", notes = "带token请求数据")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Result hello() {
        String msg = "Spring Boot系列之Log4j2的配置及使用";
        logger.info("catalina.home:" + System.getProperty("catalina.home"));
        logger.info(msg);
        logger.info(mConfigData.toString());

        LogUtil.getBussinessLogger().info("LogUtils.getBussinessLogger().info()");
        LogUtil.getDBLogger().info("LogUtils.getDBLogger()");
        LogUtil.getExceptionLogger().info("LogUtils.getExceptionLogger()");
        LogUtil.getPlatformLogger().info("LogUtils.getPlatformLogger()");
        return Result.success(mConfigData);
    }

    @ApiOperation(value = "默认token", notes = "默认token")
    @RequestMapping(value = "/defaulttoken", method = {GET})
    public String defaulttoken() {
        return login("1111", "1111").getData().getToken();
    }

    @ApiOperation(value = "注册用户", notes = "注册新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result registerUser(@RequestParam String username, @RequestParam String password) {
        UserInfos user = null;
        try {
            user = mUserServices.getUserInfo(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null) {
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }

        UserInfos u = ModeFactory.createNewUserInfo(username);
        UserLogin mUserLogin = ModeFactory.createNewUserLogin(username, password);
        int mUserLoginResult = 0;
        try {
            mUserLoginResult = mUserServices.addNewUser(u, mUserLogin);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.DATA_IS_WRONG);
        }

        logger.debug("registerUser.mUserLoginResult:" + mUserLoginResult);

        return Result.success();
    }

    @ApiOperation(value = "添加自定义异常", notes = "自定义异常")
    @RequestMapping(value = "/makeexception", method = {GET})
    public void makeException() {
        logger.info("makeException");
        throw new MyException(222, "自定义的错误");
    }


}
