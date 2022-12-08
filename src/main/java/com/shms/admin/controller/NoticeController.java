package com.shms.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.shms.admin.service.NoticeService;
import com.shms.admin.util.AdminUtils;
import com.shms.admin.entity.Notice;
import com.shms.common.utils.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/notice")
@Service
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 新增公告
     *
     * @param notice
     * @return
     */
    @PostMapping
    public Ret<String> add(@RequestBody Notice notice) {
        try {
            notice.setId(AdminUtils.makeuuid());
            noticeService.save(notice);
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
            noticeService.removeById(id);
            return Ret.success("删除成功");
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * 改公告
     *
     * @param notice
     * @return
     */
    @PutMapping
    public Ret<String> edit(@RequestBody Notice notice) {
        try {
            noticeService.updateById(notice);
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
    public Ret<Object> get(@RequestParam(required = false) String id) {
        try {
            if (id == null) {
                List<Notice> noticeList = noticeService.list();
                return Ret.success(noticeList);
            }
            Notice notice = noticeService.getById(id);
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
    public Ret<List<Notice>> getall() {
        try {
            List<Notice> noticeList = noticeService.list();
            return Ret.success(noticeList);
        } catch (
                Exception e) {
            return Ret.error(e.getMessage());
        }
    }
}
