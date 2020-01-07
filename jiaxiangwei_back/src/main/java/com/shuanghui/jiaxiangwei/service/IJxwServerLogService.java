package com.shuanghui.jiaxiangwei.service;

import com.shuanghui.jiaxiangwei.dto.JxwServerLogDto;
import java.util.List;

public interface IJxwServerLogService {
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

    public  Integer test(JxwServerLogDto jxwServerLog);

}
