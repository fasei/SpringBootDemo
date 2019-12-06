package com.example.demo.websocket.message.type;

/**
 * Author: wangchao
 * Time: 2019-07-04
 * Description: This is
 */
public class BaseMessageType extends Base {

    private int login = 1;
    private int loginResult = 2;

    private int register = 3;
    private int registerResult = 4;


    @Override
    protected int initStartKey() {
        return 10;
    }


    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(int loginResult) {
        this.loginResult = loginResult;
    }

    public int getRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult(int registerResult) {
        this.registerResult = registerResult;
    }
}
