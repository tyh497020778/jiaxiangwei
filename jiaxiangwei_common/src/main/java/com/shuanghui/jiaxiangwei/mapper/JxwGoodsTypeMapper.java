package com.shuanghui.jiaxiangwei.mapper;

import com.shuanghui.jiaxiangwei.dto.JxwGoodsTypeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JxwGoodsTypeMapper {

    /**
     * 查询全部
     * @return
     */
    List<JxwGoodsTypeDto> findAll();

    /**
     * 按类型id获取数据
     * @param id
     * @return
     */
    JxwGoodsTypeDto getGoodsTypeById(@Param("id") Integer id);

    /**
     * 添加类型
     * @param dto
     */
    void insert(JxwGoodsTypeDto dto);

    /**
     * 类型修改
     * @param dto
     */
    void update(JxwGoodsTypeDto dto);

    /**
     * 类型修改
     * @param dto
     */
    void updateById(JxwGoodsTypeDto dto);

    /**
     * 删除类别按 父节点
     * @param dto
     */
    void deleteGoodsTypeByParentId(JxwGoodsTypeDto dto);

}
