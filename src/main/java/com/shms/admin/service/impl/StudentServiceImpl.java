package com.shms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shms.admin.Mapper.StudentMapper;
import com.shms.admin.service.StudentService;
import com.shms.admin.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Student模块的Service层（业务层）的具体实现类，对StudentService接口中定义的抽象方法作出具体的功能实现
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    //@Override
    public boolean insert(Student vo) {
        return this.studentMapper.doCreate(vo) == 1;
    }

    //@Override
    public boolean delete(Collection<Serializable> ids) {
        return ids.isEmpty() ? false : this.studentMapper.doRemoveBatch(ids) == ids.size();
    }

    //@Override
    public boolean update(Student vo) {
        return this.studentMapper.doUpdate(vo) == 1;
    }

    //@Override
    public Student get(Serializable id) {
        return this.studentMapper.findById(id);
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("totalCount", this.studentMapper.getAllCount(params));
        resultMap.put("list", this.studentMapper.findAllSplit(params));
        return resultMap;
    }
}
