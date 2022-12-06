package com.shms.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("usr_validcode_log")
public class UserValidCode {
    private String userid;
    private String code;
    private Date expire_time;
    private boolean isused;
}
