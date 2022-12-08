package com.shms.common.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.common.Service.RiskAreaService;
import com.shms.common.entity.RiskArea;
import com.shms.common.mapper.RiskAreaMapper;
import org.springframework.stereotype.Service;

@Service
public class RiskAreaServiceImpl extends ServiceImpl<RiskAreaMapper, RiskArea> implements RiskAreaService {
}
