package com.shms.demo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.demo.Service.RiskAreaLogService;
import com.shms.demo.entity.RiskAreaLog;
import com.shms.demo.mapper.RiskAreaLogMapper;
import org.springframework.stereotype.Service;

@Service
public class RiskAreaLogServiceImpl extends ServiceImpl<RiskAreaLogMapper, RiskAreaLog> implements RiskAreaLogService {
}
