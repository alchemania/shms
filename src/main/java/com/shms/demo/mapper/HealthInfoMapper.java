package com.shms.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shms.demo.entity.HealthInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthInfoMapper extends BaseMapper<HealthInfo> {
}
