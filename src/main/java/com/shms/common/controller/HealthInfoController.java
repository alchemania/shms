package com.shms.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shms.common.Service.HealthInfoService;
import com.shms.common.entity.HealthInfo;
import com.shms.common.utils.Ret;
import com.shms.common.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/healthinfo")
@Service
public class HealthInfoController {
    @Autowired
    private HealthInfoService healthInfoService;

    /**
     * 权宜之计
     */
    @GetMapping("/{userid}")
    public Ret<HealthInfo> getByid(@PathVariable String userid) {
        if (userid == null) return Ret.error("no user");
        LambdaQueryWrapper<HealthInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HealthInfo::getUserid, userid);
        HealthInfo healthInfo = healthInfoService.getOne(lambdaQueryWrapper);
        return Ret.success(healthInfo);
    }

    /**
     * 不允许通过.id形式获取，会有越权风险，仅仅允许使用系统存储的id获取
     */
    @GetMapping
    public Ret<HealthInfo> getOne() {
        String userid = UserUtils.getCurrentId();
        if (userid == null) return Ret.error("no user login");
        LambdaQueryWrapper<HealthInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HealthInfo::getUserid, userid);
        HealthInfo healthInfo = healthInfoService.getOne(lambdaQueryWrapper);
        return Ret.success(healthInfo);
    }

    /**
     * 只需要更新即可，在新建用户时即新建健康档案。
     */
    @PutMapping
    public Ret<HealthInfo> update(@RequestBody HealthInfo healthInfo) {
        String userid = healthInfo.getUserid() == null ? UserUtils.getCurrentId() : healthInfo.getUserid();
        LambdaQueryWrapper<HealthInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HealthInfo::getUserid, userid);
        healthInfoService.update(healthInfo, lambdaQueryWrapper);
        return Ret.success(healthInfo);
    }

}
