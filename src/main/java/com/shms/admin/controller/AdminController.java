package com.shms.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.shms.common.Service.UserService;
import com.shms.common.entity.User;
import com.shms.common.utils.Ret;
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
    public Ret<Object> get(@RequestParam(required = false) String id) {
        try {
            if (id == null) {
                List<User> userList = userService.list();
                return Ret.success(userList);
            }
            User notice = userService.getById(id);
            return Ret.success(notice);
        } catch (
                Exception e) {
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
            return Ret.success(userList);
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }
}
