package com.shms.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.shms.common.Service.UserService;
import com.shms.common.entity.User;
import com.shms.common.utils.Ret;
import com.shms.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/admin")
@Service
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * @param params
     * @return
     */
    @DeleteMapping
    public Ret<String> delete(@RequestBody JSONObject params) {
        try {
            String id = (String) params.get("id");
            userService.removeById(id);
            return Ret.success("删除成功");
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * @param user
     * @return
     */
    @PutMapping
    public Ret<String> edit(@RequestBody User user) {
        try {
            User user1 = userService.getById(user.getUserid());
            if (user1 == null) return Ret.error("用户不存在");
            if (user.getPassword() == null) user.setPassword(user1.getPassword());
            else {
                user.setPassword(SecurityUtils.encodePassword(user.getPassword()));
            }
            userService.updateById(user);
            return Ret.success("修改成功");
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * 获取user的详细信息（详情页面与编辑页面要显示该公告的详情）
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Ret<Object> get(@PathVariable(required = false) String id) {
        try {
            if (id == null) {
                List<User> userList = userService.list();
                return Ret.success(userList);
            }
            User userServiceById = userService.getById(id);
            return Ret.success(userServiceById);
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * @return
     */
    @GetMapping("/all")
    public Ret<List<User>> getall() {
        try {
            List<User> userList = userService.list();
            for (User i : userList) i.setPassword("");
            return Ret.success(userList);
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    @PostMapping("/fuzzyquery")
    public Ret<List<Object>> getfuzzy(@RequestBody JSONObject params) {
        try {
            String username = (String) params.get("username");
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(User::getUsername, username);
            lambdaQueryWrapper.select(User::getUsername);
            List<Object> userList = userService.listObjs(lambdaQueryWrapper);
            return Ret.success(userList);
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }
}
