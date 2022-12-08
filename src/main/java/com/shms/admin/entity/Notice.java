package com.shms.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告（t_notice表对应的Java实体类）
 */

@Data
@TableName("sys_notice")
public class Notice implements Serializable {
    @TableId
    private String id;//主键
    private String notice_name;//标题
    private String notice_text;//内容
    private String notice_type;//类型

    @TableField(fill = FieldFill.INSERT)
    private Date create_time;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date update_time;
}
