package com.shuanghui.jiaxiangwei.mapper;

import com.shuanghui.jiaxiangwei.dto.JxwServerLogDto;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * 服务日志
 */
@Mapper
public interface JxwServerLogMapper {

    /**
     * 查询服务日志
     * @return
     */
    public List<JxwServerLogDto> findAll();
    /**
     * 添加服务日志
     * @param jxwServerLog
     * @return
     */
    public  Integer insert(JxwServerLogDto jxwServerLog);
}
