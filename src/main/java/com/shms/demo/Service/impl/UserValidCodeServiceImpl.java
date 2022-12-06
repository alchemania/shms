package com.shms.demo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.demo.Service.UserValidCodeService;
import com.shms.demo.entity.UserValidCode;
import com.shms.demo.mapper.UserValidCodeMapper;
import org.springframework.stereotype.Service;

@Service
public class UserValidCodeServiceImpl extends ServiceImpl<UserValidCodeMapper, UserValidCode> implements UserValidCodeService {
}
