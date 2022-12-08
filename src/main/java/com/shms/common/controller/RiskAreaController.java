package com.shms.common.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shms.common.Service.RiskAreaLogService;
import com.shms.common.Service.RiskAreaService;
import com.shms.common.entity.RiskArea;
import com.shms.common.entity.RiskAreaLog;
import com.shms.common.utils.Ret;
import com.shms.common.utils.RiskAreaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/riskarea")
@Service
public class RiskAreaController {
    @Autowired
    RiskAreaService riskAreaService;

    @Autowired
    RiskAreaLogService riskAreaLogService;

    /**
     * 取得疫情信息
     * @return
     */
    @GetMapping
    public Ret<RiskAreaLog> getInfo(){
        try {
            QueryWrapper<RiskAreaLog> queryWrapper=new QueryWrapper<>();
            queryWrapper.last("limit 1");
            queryWrapper.orderByDesc("create_time");
            RiskAreaLog riskAreaLog=riskAreaLogService.getOne(queryWrapper);
            return Ret.success(riskAreaLog);
        }catch (Exception e){
            return Ret.error("Internal Error.");
        }
    }

    /**
     * 刷新专用
     * @return
     */

    @GetMapping("/refresh")
    public Ret<RiskAreaLog> refreshall() {
        try {
            Map<String, Object> map = RiskAreaUtils.getallInfo();
            RiskAreaLog riskAreaLog = (RiskAreaLog) map.get("log");
            riskAreaLog.setStatus(true);
            riskAreaLogService.save(riskAreaLog);
            //清空表
            riskAreaService.remove(new QueryWrapper<>());
            riskAreaService.saveBatch((List<RiskArea>) map.get("hl"));
            riskAreaService.saveBatch((List<RiskArea>) map.get("ll"));
            return Ret.success(riskAreaLog);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Ret.error("Intenal Error");
        }
    }

    /**
     * 省市区三级=>查风险地区
     */
    @PostMapping
    public Ret<List<RiskArea>> getBatch(@RequestBody JSONObject params) {
        try {
            String province = (String) params.get("province");
            String city = (String) params.get("city");
            String county = (String) params.get("county");
            LambdaQueryWrapper<RiskArea> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RiskArea::getArea_name, province + " " + city + " " + county);
            List<RiskArea> riskAreaList = riskAreaService.list(lambdaQueryWrapper);
            return Ret.success(riskAreaList);
        } catch (Exception e) {
            return Ret.error("查询失败");
        }
    }

    /**
     * 查列表，一二三级菜单
     */
    @PostMapping("/menu")
    public Ret<List<Object>> getMenu(@RequestBody JSONObject params) {
        try {
            String province = (String) params.get("province");
            String city = (String) params.get("city");
            if (province == null && city == null) { //第一级菜单查询
                QueryWrapper<RiskArea> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("DISTINCT province");
                List<Object> list = riskAreaService.listObjs(queryWrapper);
                return Ret.success(list);
            } else if (province != null && city == null) {
                QueryWrapper<RiskArea> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("DISTINCT city");
                queryWrapper.eq("province",province);
                List<Object> list = riskAreaService.listObjs(queryWrapper);
                return Ret.success(list);
            } else {
                QueryWrapper<RiskArea> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("DISTINCT county");
                queryWrapper.eq("province",province);
                queryWrapper.eq("city",city);
                List<Object> list = riskAreaService.listObjs(queryWrapper);
                return Ret.success(list);
            }
        } catch (Exception e) {
            return Ret.error("Internal Error.");
        }
    }

}