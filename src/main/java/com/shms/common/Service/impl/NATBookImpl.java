package com.shms.common.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.common.Service.NATBookService;
import com.shms.common.entity.NATBook;
import com.shms.common.mapper.NATBookMapper;
import org.springframework.stereotype.Service;

@Service
public class NATBookImpl extends ServiceImpl<NATBookMapper, NATBook> implements NATBookService {
}
