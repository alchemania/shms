package com.shms.demo.utils;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 * @param <T>
 */
@Data
@ApiModel("返回结果")
public class Ret<T> implements Serializable {

    private Integer code; //http编码：200成功

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> Ret<T> success(T object) {
        Ret<T> ret = new Ret<T>();
        ret.data = object;
        ret.code = 200;
        return ret;
    }

    public static <T> Ret<T> error(String msg) {
        Ret ret = new Ret();
        ret.msg = msg;
        ret.code = 400;
        return ret;
    }

    public Ret<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
