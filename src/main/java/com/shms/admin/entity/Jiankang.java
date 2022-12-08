package com.shms.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 健康（t_jiankang表对应的Java实体类）
 */

@Data
@TableName("usr_jiankang")
public class Jiankang implements Serializable {
    @TableId
    private String id;//主键
    private String name;//姓名
    private String userid;//学号
    private Date date;//日期
    private double jiankang_tiwen;//体温
    private boolean jiankang_huan;//是否接触过患者:是/否
    private boolean jiankang_bin;//是否有过往病史:是/否
    private boolean jiankang_yi;//是否有异常状况:是/否
    private int jiankang_jia;//综合健康状况:优/良/差 3/2/1
    private String jiankang_text;//备注
}
