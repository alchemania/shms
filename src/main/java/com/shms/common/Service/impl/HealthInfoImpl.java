package com.shms.common.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.common.Service.HealthInfoService;
import com.shms.common.entity.HealthInfo;
import com.shms.common.mapper.HealthInfoMapper;
import org.springframework.stereotype.Service;

@Service
public class HealthInfoImpl extends ServiceImpl<HealthInfoMapper, HealthInfo> implements HealthInfoService {
}
