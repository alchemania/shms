package com.shms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.admin.Mapper.JiankangMapper;
import com.shms.admin.service.JiankangService;
import com.shms.admin.entity.Jiankang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Jiankang模块的Service层（业务层）的具体实现类，对JiankangService接口中定义的抽象方法作出具体的功能实现
 */
@Service
public class JiankangServiceImpl extends ServiceImpl<JiankangMapper,Jiankang> implements JiankangService {

    @Autowired
    private JiankangMapper jiankangMapper;


    //@Override
    public boolean insert(Jiankang vo) {
        return this.jiankangMapper.doCreate(vo) == 1;
    }

    //@Override
    public boolean delete(Collection<Serializable> ids) {
        return ids.isEmpty() ? false : this.jiankangMapper.doRemoveBatch(ids) == ids.size();
    }

    //@Override
    public boolean update(Jiankang vo) {
        return this.jiankangMapper.doUpdate(vo) == 1;
    }

    //@Override
    public Jiankang get(Serializable id) {
        return this.jiankangMapper.findById(id);
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("totalCount", this.jiankangMapper.getAllCount(params));
        resultMap.put("list", this.jiankangMapper.findAllSplit(params));
        return resultMap;
    }
}
