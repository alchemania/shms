package com.shms.admin.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shms.admin.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Student模块的DAO层（数据层）接口，提供增删改查等数据库操作的方法抽象
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 增加学生信息表记录
     *
     * @param vo
     * @return
     */
    int doCreate(Student vo);

    /**
     * 根据主键id的集合，批量删除对应的学生信息表记录
     *
     * @param ids
     * @return
     */
    int doRemoveBatch(Collection<Serializable> ids);

    /**
     * 更新学生信息表记录
     *
     * @param vo
     * @return
     */
    int doUpdate(Student vo);

    /**
     * 根据主键id获取学生信息表记录的详情
     *
     * @param id
     * @return
     */
    Student findById(Serializable id);

    /**
     * 根据条件查询学生信息表集合
     *
     * @param params
     * @return
     */
    List<Student> findAllSplit(Map<String, Object> params);

    /**
     * 根据条件查询学生信息数量
     *
     * @param params
     * @return
     */
    Integer getAllCount(Map<String, Object> params);
}
