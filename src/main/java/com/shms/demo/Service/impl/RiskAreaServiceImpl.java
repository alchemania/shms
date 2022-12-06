package com.shms.demo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.demo.Service.RiskAreaService;
import com.shms.demo.entity.RiskArea;
import com.shms.demo.mapper.RiskAreaMapper;
import org.springframework.stereotype.Service;

@Service
public class RiskAreaServiceImpl extends ServiceImpl<RiskAreaMapper, RiskArea> implements RiskAreaService {
}
