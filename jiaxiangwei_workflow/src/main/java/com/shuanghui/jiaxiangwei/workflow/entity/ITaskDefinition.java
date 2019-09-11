package com.shuanghui.jiaxiangwei.workflow.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by administration on 2019/8/10.
 */
public interface ITaskDefinition {
    /**
     * 获取环节按钮
     * @return
     */
    List<Button> getButtons();

    /**
     * 获取环节自定义属性
     * @return
     */
    Map<String,LinkedHashMap<String,Object>> getCustomerProperties();

    /**
     * 获取环节时限
     * @return
     */
    Map<String,String> getTimeLimits();

    /**
     * 获取任务定义的key
     * @return
     */
    String getTaskDefinitionKey();

    /**
     *  获取targetids
     * @return
     */
    Map<String,Object> getTargetIds();
}
