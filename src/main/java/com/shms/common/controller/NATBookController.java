package com.shms.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shms.common.Service.NATBookService;
import com.shms.common.entity.NATBook;
import com.shms.common.utils.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/natbook")
@Service
public class NATBookController {

    @Autowired
    private NATBookService natBookService;

    /*
    查询所有接口
    测试专用
     */
    @GetMapping("/all")
    public Ret<List<NATBook>> list() {
        List<NATBook> natBookList = natBookService.list();
        return Ret.success(natBookList);
    }

    /*
    查询核酸预约，可用于生产环境
     */
    @GetMapping("/{id}")
    public Ret<List<NATBook>> getByid(@PathVariable String id) {
        LambdaQueryWrapper<NATBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id != null, NATBook::getUserid, id);
        List<NATBook> natBookList = natBookService.list(lambdaQueryWrapper);
        if (natBookList == null) {
            return Ret.error("id is illegal");
        }
        return Ret.success(natBookList);
    }

    /*
    新建核酸预约
     */
    @PutMapping
    public Ret<NATBook> createNewBook(@RequestBody NATBook natBook) {
        log.debug("natbook:{}", natBook);
        natBook.setCreate_time(new Date());
        natBook.setNatid(UUID.randomUUID().toString().replace("-", ""));
        try {
            natBookService.save(natBook);
        } catch (Exception e) {
            return Ret.error(e.toString());
        }
        return Ret.success(natBook);
    }

    /*
    删除预约
     */
    @DeleteMapping
    public Ret<String> deleteMyBook(@RequestParam String natid) {
        return Ret.error("not developed yet,natid=" + natid);
    }


}
