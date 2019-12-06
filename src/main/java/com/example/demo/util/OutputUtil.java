package com.example.demo.util;

import com.example.demo.bean.Base;
import com.google.gson.Gson;

/**
 * Author: wangchao
 * Time: 2018-10-11
 * Description: This is 控制自动化输出对象
 */
public class OutputUtil {
    public static void log(Base o) {
        if (o == null) {
            return;
        }
        System.out.println(o.getClass().getSimpleName() + ":" + GsonUtil.formatJson(o.toString()));
    }

    public static void log(Object o) {
        if (o == null) {
            return;
        }
        Gson g = new Gson();
        String msg = g.toJson(o);
        System.out.println(o.getClass().getSimpleName() + ":" + GsonUtil.formatJson(msg));
    }

    public static void d(Object o) {
        if (o == null) {
            return;
        }
        Gson g = new Gson();
        String msg = g.toJson(o);
        System.out.println(GsonUtil.formatJson(msg));

    }


    public static String toString(Object o) {
        if (o == null) {
            return "";
        }
        Gson g = new Gson();
        String msg = g.toJson(o);
        return (o.getClass().getSimpleName() + ":" + GsonUtil.formatJson(msg));
    }

}
