package com.shms.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shms.admin.entity.Jiankang;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Jiankang模块的DAO层（数据层）接口，提供增删改查等数据库操作的方法抽象
 */
@Mapper
public interface JiankangMapper extends BaseMapper<Jiankang> {
    /**
     * 增加健康表记录
     *
     * @param vo
     * @return
     */
    int doCreate(Jiankang vo);

    /**
     * 根据主键id的集合，批量删除对应的健康表记录
     *
     * @param ids
     * @return
     */
    int doRemoveBatch(Collection<Serializable> ids);

    /**
     * 更新健康表记录
     *
     * @param vo
     * @return
     */
    int doUpdate(Jiankang vo);

    /**
     * 根据主键id获取健康表记录的详情
     *
     * @param id
     * @return
     */
    Jiankang findById(Serializable id);

    /**
     * 根据条件查询健康表集合
     *
     * @param params
     * @return
     */
    List<Jiankang> findAllSplit(Map<String, Object> params);

    /**
     * 根据条件查询健康数量
     *
     * @param params
     * @return
     */
    Integer getAllCount(Map<String, Object> params);
}
