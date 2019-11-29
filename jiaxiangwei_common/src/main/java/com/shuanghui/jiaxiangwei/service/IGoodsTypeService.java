package com.shuanghui.jiaxiangwei.service;

import com.shuanghui.jiaxiangwei.dto.JxwGoodsTypeDto;

import java.util.List;

/**
 * 商品类别
 */
public interface IGoodsTypeService {

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
    JxwGoodsTypeDto getGoodsTypeById(Integer id);

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
