package com.shms.common.utils;

public class UserUtils {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前登录用户id
     */
    public static void setCurrentId(String id){
        threadLocal.set(id);
    }

    /**
     * 获取当前用户id
     */
    public static String getCurrentId(){
        return threadLocal.get();
    }
}