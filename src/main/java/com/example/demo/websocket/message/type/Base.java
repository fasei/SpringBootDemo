package com.example.demo.websocket.message.type;

import java.lang.reflect.Field;

/**
 * Author: wangchao
 * Time: 2019-07-04
 * Description: This is
 */
public abstract class Base {
    protected int startKey;

    protected int maxKeyValue = 0;

    protected Base() {
        this.startKey = initStartKey();
        initialize();
    }

    /**
     * 获取类型起步数值
     *
     * @return
     */
    protected abstract int initStartKey();


    public void initialize() {
        this.maxKeyValue = this.startKey;
        Field[] fields = this.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            try {
                if (f.getType().isPrimitive()
                        && f.getType().toString().equals("int")
                        && (!f.getName().equals("startKey"))) {
                    int num = (Integer) f.get(this);
                    f.set(this, num + startKey);
                    int targetKey = startKey + num;
                    if (!(maxKeyValue > targetKey)) {
                        maxKeyValue = targetKey;
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean contains(int informationType) {
        return (informationType >= this.startKey && informationType <= this.maxKeyValue);
    }

}
