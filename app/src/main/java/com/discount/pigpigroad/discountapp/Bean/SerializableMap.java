package com.discount.pigpigroad.discountapp.Bean;

/**
 * Created by PIGROAD on 2018/4/16.
 * Email:920015363@qq.com
 */

import java.io.Serializable;
import java.util.Map;

/**
 * 序列化map供Bundle传递map使用
 */

public class SerializableMap implements Serializable {

    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}