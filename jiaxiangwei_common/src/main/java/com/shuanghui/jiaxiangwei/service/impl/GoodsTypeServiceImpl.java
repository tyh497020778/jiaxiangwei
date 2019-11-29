package com.shuanghui.jiaxiangwei.service.impl;

import com.shuanghui.jiaxiangwei.dto.JxwGoodsTypeDto;
import com.shuanghui.jiaxiangwei.mapper.JxwGoodsTypeMapper;
import com.shuanghui.jiaxiangwei.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService {
    @Autowired
    private JxwGoodsTypeMapper jxwGoodsTypeMapper;
    @Override
    public List<JxwGoodsTypeDto> findAll() {
        return jxwGoodsTypeMapper.findAll();
    }

    @Override
    public JxwGoodsTypeDto getGoodsTypeById(Integer id) {
        return jxwGoodsTypeMapper.getGoodsTypeById(id);
    }

    @Override
    public void insert(JxwGoodsTypeDto dto) {
        jxwGoodsTypeMapper.insert(dto);
    }

    @Override
    public void update(JxwGoodsTypeDto dto) {
        jxwGoodsTypeMapper.update(dto);
    }

    @Override
    public void updateById(JxwGoodsTypeDto dto) {
        jxwGoodsTypeMapper.updateById(dto);
    }

    @Override
    public void deleteGoodsTypeByParentId(JxwGoodsTypeDto dto) {
        jxwGoodsTypeMapper.deleteGoodsTypeByParentId(dto);
    }
}
