package com.example.demo.log;

public enum LogEnum {
    BUSSINESS("bussiness"),//业务性
    PLATFORM("platform"),//平台方面
    DB("db"),//数据库
    EXCEPTION("exception"),;//异常信息
    private String category;

    LogEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
