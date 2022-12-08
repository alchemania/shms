package com.shms.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
