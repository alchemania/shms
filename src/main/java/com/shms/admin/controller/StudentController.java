package com.shms.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shms.admin.entity.Notice;
import com.shms.admin.service.StudentService;
import com.shms.admin.entity.Student;
import com.shms.admin.util.AdminUtils;
import com.shms.common.utils.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/student")
@Service
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 新增学生
     *
     * @param student
     * @return
     */
    @PostMapping
    public Ret<String> add(@RequestBody Student student) {
        try {
            studentService.save(student);
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
            studentService.removeById(id);
            return Ret.success("删除成功");
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * 改学生
     *
     * @param student
     * @return
     */
    @PutMapping
    public Ret<String> edit(@RequestBody Student student) {
        try {
            studentService.updateById(student);
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
    public Ret<Object> get(@RequestParam String id) {
        try {
            Student student = studentService.getById(id);
            return Ret.success(student);
        } catch (
                Exception e) {
            return Ret.error(e.getMessage());
        }
    }

    /**
     * @return
     */
    @GetMapping("/all")
    public Ret<List<Student>> getall() {
        try {
            List<Student> studentList = studentService.list();
            return Ret.success(studentList);
        } catch (Exception e) {
            return Ret.error(e.getMessage());
        }
    }
}
