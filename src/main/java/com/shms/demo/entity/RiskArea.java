package com.shms.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_riskarea")
public class RiskArea {
    private String logid;
    private int type;
    private String province;
    private String city;
    private String county;
    private String area_name;
    private String community;
}
