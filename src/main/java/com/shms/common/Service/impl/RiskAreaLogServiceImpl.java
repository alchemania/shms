package com.shms.common.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.common.Service.RiskAreaLogService;
import com.shms.common.entity.RiskAreaLog;
import com.shms.common.mapper.RiskAreaLogMapper;
import org.springframework.stereotype.Service;

@Service
public class RiskAreaLogServiceImpl extends ServiceImpl<RiskAreaLogMapper, RiskAreaLog> implements RiskAreaLogService {
}
