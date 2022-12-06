package com.shms.demo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.demo.Service.NATBookService;
import com.shms.demo.entity.NATBook;
import com.shms.demo.mapper.NATBookMapper;
import org.springframework.stereotype.Service;

@Service
public class NATBookImpl extends ServiceImpl<NATBookMapper, NATBook> implements NATBookService {
}
