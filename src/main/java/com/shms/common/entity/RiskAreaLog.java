package com.shms.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_riskarea_log")
public class RiskAreaLog {
    private String logid;
    private int hcount;
    private int lcount;
    private Date time;
    private Boolean status;
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;

    @TableField(fill = FieldFill.UPDATE)
    private Date update_time;
}
