package com.example.demo.controller;

import com.example.demo.annotation.CurrentUser;
import com.example.demo.annotation.LoginRequired;
import com.example.demo.bean.response.Result;
import com.example.demo.bean.response.TokenResponse;
import com.example.demo.config.ConfigData;
import com.example.demo.constants.Constants;
import com.example.demo.constants.ResultCode;
import com.example.demo.controller.abs.AbsUserController;
import com.example.demo.exception.MyException;
import com.example.demo.log.LogUtil;
import com.example.demo.model.UserInfos;
import com.example.demo.model.UserLogin;
import com.example.demo.service.UserServices;
import com.example.demo.util.ModeFactory;
import com.example.demo.util.TokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: wangchao
 * Time: 2018-10-11
 * Description: This is 用户操作接口
 */
@RestController
@RequestMapping(value = Constants.UserControllerPath)
@Api(tags = "用户信息管理中心", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController implements AbsUserController {
    @Autowired
    ConfigData mConfigData;

    @Resource
    UserServices mUserServices;

    @Override
    public Result<TokenResponse> login(@RequestParam String username, @RequestParam String password) {
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

    @Override
    @LoginRequired
    public Result authorizationLogin(@CurrentUser UserInfos user) {
        logger.info(user.toString());
        return Result.success(user);
    }

    public Result authorizationLogin(@RequestParam String authorizationid) {
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


    @Override
    public Result hello() {
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
        return Result.success(mConfigData);
    }

    @Override
    public String defaulttoken() {
        return login("1111", "1111").getData().getToken();
    }

    @Override
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

    @Override
    public void makeException() {
        logger.info("makeException");
        throw new MyException(222, "自定义的错误");
    }


}
