package com.shms.demo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.demo.Service.HealthInfoService;
import com.shms.demo.entity.HealthInfo;
import com.shms.demo.mapper.HealthInfoMapper;
import org.springframework.stereotype.Service;

@Service
public class HealthInfoImpl extends ServiceImpl<HealthInfoMapper, HealthInfo> implements HealthInfoService {
}
