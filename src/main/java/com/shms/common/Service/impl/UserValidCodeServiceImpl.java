package com.shms.common.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.common.Service.UserValidCodeService;
import com.shms.common.entity.UserValidCode;
import com.shms.common.mapper.UserValidCodeMapper;
import org.springframework.stereotype.Service;

@Service
public class UserValidCodeServiceImpl extends ServiceImpl<UserValidCodeMapper, UserValidCode> implements UserValidCodeService {
}
