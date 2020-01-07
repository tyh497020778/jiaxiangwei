package com.shuanghui.jiaxiangwei.service.impl;

import com.shuanghui.jiaxiangwei.dto.JxwServerLogDto;
import com.shuanghui.jiaxiangwei.mapper.JxwServerLogMapper;
import com.shuanghui.jiaxiangwei.service.IJxwServerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JxwServerLogImpl implements IJxwServerLogService {
    @Autowired
    private JxwServerLogMapper jxwServerLogMapper;

    @Override
    public List<JxwServerLogDto> findAll() {
        return jxwServerLogMapper.findAll();
    }

    @Override
    public Integer insert(JxwServerLogDto jxwServerLog) {
        return jxwServerLogMapper.insert(jxwServerLog);
    }

}
