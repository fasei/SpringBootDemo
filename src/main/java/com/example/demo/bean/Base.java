package com.example.demo.bean;

import com.google.gson.Gson;

/**
 * Author: wangchao
 * Time: 2018-10-11
 * Description: This is
 */
public class Base<T> {

    @Override
    public String toString() {
        Gson g = new Gson();
        return g.toJson(this);
    }
}
