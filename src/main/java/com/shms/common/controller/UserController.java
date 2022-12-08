package com.shms.common.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shms.common.Service.HealthInfoService;
import com.shms.common.Service.UserService;
import com.shms.common.Service.UserValidCodeService;
import com.shms.common.entity.HealthInfo;
import com.shms.common.entity.User;
import com.shms.common.entity.UserValidCode;
import com.shms.common.utils.Ret;
import com.shms.common.utils.SecurityUtils;
import com.shms.common.utils.UserUtils;
import com.shms.common.utils.ValidCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Service
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HealthInfoService healthInfoService;

    @Autowired
    private UserValidCodeService userValidCodeService;

    /**
     * 创建用户，密码加盐，盐值为
     *
     * @param user
     * @return
     */
    @PostMapping
    public Ret<User> createUser(@RequestBody User user) {
        try {
            user.setUserid(UUID.randomUUID().toString().replace("-", ""));
            user.setPassword(SecurityUtils.encodePassword(user.getPassword()));
            user.setPrivilege(0);
            userService.save(user);
        } catch (Exception e) {
            return Ret.error("failed");
        } finally {
            HealthInfo healthInfo = new HealthInfo();
            healthInfo.setUserid(user.getUserid());
            healthInfoService.save(healthInfo);
            user.setPassword("******");
        }
        return Ret.success(user);
    }

    @PostMapping("/login")
    public Ret<User> login(@RequestBody Map<String, String> params) {
        log.debug(params.toString());
        String username = params.get("username");
        String password = params.get("password");
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        User user = userService.getOne(lambdaQueryWrapper);
        if (user == null) {
            return Ret.error("no user");
        } else if (!user.validate(username, password)) {
            return Ret.error("password validate failed");
        } else {
            UserUtils.setCurrentId(user.getUserid());
            user.setPassword("******");
            return Ret.success(user);
        }
    }

    @GetMapping
    public Ret<String> logout() {
        try {
            UserUtils.setCurrentId(null);
        } catch (Exception e) {
            return Ret.error("服务器异常");
        }
        return Ret.success("登出成功");
    }

    @PostMapping("/code")
    public Ret<String> sendEmail(@RequestBody JSONObject params) {
        //获取邮箱账号
        String username = (String) params.get("username");
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        User user = userService.getOne(lambdaQueryWrapper);

        String subject = "密码重置验证码";

        if (user != null && user.getEmail() != null) {
            String code = ValidCodeUtils.makecode();
            String context = "密码重置验证码为：" + code + ",五分钟内有效，请妥善保管";
            log.info("code={}", code);

            //发送邮箱验证码
            userService.sendSimpleMail(user.getEmail(), subject, context);

            //验证码加密保存到数据库中，并且设置验证码有效时间为5分钟
            Date expire_time = new Date(new Date().getTime() + 300000);
            UserValidCode userValidCode = new UserValidCode();
            userValidCode.setUserid(user.getUserid());
            userValidCode.setCode(ValidCodeUtils.securecode(code));
            userValidCode.setExpire_time(expire_time);
            userValidCode.setIsused(false);
            userValidCodeService.save(userValidCode);

            return Ret.success("验证码发送成功！");
        }
        return Ret.error("验证码发送失败");
    }

    @PutMapping("/code")
    public Ret<String> validCode(@RequestBody JSONObject params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        String validcode = (String) params.get("validcode");
        //做好修改准备
        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(User::getUsername, username);
        User user = userService.getOne(lambdaQueryWrapper2);
        user.setPassword(SecurityUtils.encodePassword(password));

        LambdaQueryWrapper<UserValidCode> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserValidCode::getUserid, user.getUserid());
        lambdaQueryWrapper.orderByDesc(UserValidCode::getExpire_time);
        lambdaQueryWrapper.last("limit 1");
        UserValidCode userValidCode = userValidCodeService.getOne(lambdaQueryWrapper);
        userValidCode.setIsused(true);
        //执行修改
        if (ValidCodeUtils.validcode(validcode, userValidCode.getCode()) && userValidCode.getExpire_time().compareTo(new Date()) >= 0) {
            userService.update(user, lambdaQueryWrapper2);
            userValidCodeService.update(userValidCode, lambdaQueryWrapper);
            return Ret.success("修改成功！");
        } else if (!ValidCodeUtils.validcode(validcode, userValidCode.getCode())) {
            return Ret.error("验证码不正确，请重新输入");
        } else {
            return Ret.error("验证码已过期，请重新获取");
        }
    }

}
