package com.example.demo.bean.response;

import com.example.demo.bean.Base;
import com.example.demo.util.OutputUtil;

import java.io.Serializable;

/**
 * Author: wangchao
 * Time: 2018-10-11
 * Description: This is
 */
public class TokenResponse extends Base implements Serializable {
    private String Token;

    public TokenResponse() {
    }

    public TokenResponse(String token) {
        this.Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }

    public static void main(String a[]) {
        TokenResponse s = new TokenResponse("sssss");
        OutputUtil.log(s);
    }
}
