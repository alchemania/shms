package com.shms.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生信息（t_student表对应的Java实体类）
 */

@Data
@TableName("sys_student")
public class Student implements Serializable {
    @TableId
    private String student_number;//学号
    private String username; //userid
    private String student_name;//姓名
    private String student_master;//专业
    private String student_class;//班级
    private String student_phone;//联系方式
    private String student_text;//备注
}
