package com.shms.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shms.common.utils.SecurityUtils;
import lombok.Data;

import java.util.Objects;

@Data
@TableName("sys_usr")
public class User {
    @TableId
    private String userid;
    private String username;
    private String password;
    private String email;
    private int privilege;

    public boolean validate(String username, String password) {
        return Objects.equals(username, this.username) && SecurityUtils.matchesPassword(password, this.password);
    }
}
