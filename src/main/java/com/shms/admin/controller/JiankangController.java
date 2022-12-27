package com.shms.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.shms.admin.entity.Jiankang;
import com.shms.admin.service.JiankangService;
import com.shms.admin.utils.AdminUtils;
import com.shms.common.utils.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/jiankang")
@Service
public class JiankangController {

    @Autowired
    private JiankangService jiankangService;

    /**
     * 新增公告
     *
     * @param jiankang
     * @return
     */
    @PostMapping
    public Ret<String> add(@RequestBody Jiankang jiankang) {
        try {
            jiankang.setId(AdminUtils.makeuuid());
            jiankangService.save(jiankang);
            return Ret.success("新增成功");
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * 删除对应公告
     *
     * @param params
     * @return
     */

    @DeleteMapping
    public Ret<String> delete(@RequestBody JSONObject params) {
        try {
            String id = (String) params.get("id");
            jiankangService.removeById(id);
            return Ret.success("删除成功");
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * 改公告
     *
     * @param jiankang
     * @return
     */
    @PutMapping
    public Ret<String> edit(@RequestBody Jiankang jiankang) {
        try {
            jiankangService.updateById(jiankang);
            return Ret.success("修改成功");
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * 获取公告的详细信息（详情页面与编辑页面要显示该公告的详情）
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Ret<Object> get(@PathVariable String id) {
        try {
            Jiankang jiankang = jiankangService.getById(id);
            return Ret.success(jiankang);
        } catch (
                Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * @return
     */
    @GetMapping("/all")
    public Ret<List<Jiankang>> getall() {
        try {
            List<Jiankang> jiankangList = jiankangService.list();
            return Ret.success(jiankangList);
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }
}