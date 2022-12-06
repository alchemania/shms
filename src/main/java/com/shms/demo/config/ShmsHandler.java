package com.shms.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class ShmsHandler implements MetaObjectHandler {

    //添加时填充
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("create_time", new Date());
        metaObject.setValue("update_time", new Date());
    }

    //更新时填充
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("update_time", new Date());
    }
}