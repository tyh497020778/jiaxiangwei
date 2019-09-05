package com.shuanghui.jiaxiangwei.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.shuanghui.jiaxiangwei.dto.EmployeeDto;
import com.shuanghui.jiaxiangwei.mapper.EmployeeMapper;
import com.shuanghui.jiaxiangwei.service.IEmployeeService;
import com.shuanghui.jiaxiangwei.util.RedisUtil;
import com.shuanghui.jiaxiangwei.aspect.ServerLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询全部
     * @return
     */
    @ServerLog
    @Override
    public List<EmployeeDto> findAll() {
        String key="findAll";
        List<EmployeeDto> list = Lists.newArrayList();
        System.out.println(redisUtil.getExpire(key));
        if(redisUtil.hasKey(key)){
            list = (List<EmployeeDto>)JSONObject.parseArray(redisUtil.get(key),EmployeeDto.class);
        }else {
            list = employeeMapper.findAll();
            redisUtil.set(key,JSONObject.toJSONString(list),60);
        }
        return list;
    }
}
