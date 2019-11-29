package com.shuanghui.jiaxiangwei.workflow.service;

import com.shuanghui.jiaxiangwei.workflow.entity.ProcessInstanceDto;
import com.shuanghui.jiaxiangwei.workflow.entity.TaskPropsDto;

import java.util.List;
import java.util.Map;

public interface WorkflowService {

    /**
     * 启动流程
     * @param processDefinitionKey
     * @param createBy 流程处理人
     * @param businessId 工单主键
     * @param formData  参数：此参数为影响流程流转的参数，不同的流程需求参数不一样，可以把表单的所有数据放到formData中
     * @return
     */
    public ProcessInstanceDto startProcess(String processDefinitionKey, String createBy, Long businessId, Map<String,Object> formData);

    /**
     * 提交任务
     * @param taskId  任务Id在待办表中
     * @param comment   审批意见
     * @param formData  参数：此参数为影响流程流转的参数，不同的流程需求参数不一样，可以把表单的所有数据放到formData中
     */
    void submitTask(String taskId,String comment,Map<String ,Object> formData);

    /**
     * 删除流程
     * @param processInstanceId 流程实例Id
     */
    void delProcess(String processInstanceId);

    /**
     * 根据当前流程的processKey获取下一环节流程自定义属性
     * @param processKey 当前流程的key
     * @param formData   参数：此参数为影响流程流转的参数，不同的流程需求参数不一样，可以把表单的所有数据放到formData中
     * @return  环节属性dto （下一流程环节可能有多个并行分支，所以有多个）
     */
    List<TaskPropsDto> queryNextTaskPropsByProcessKey(String processKey, Map<String , Object> formData);

    /**
     * 根据当前流程的processKey获取下一环节流程自定义属性
     * @param taskId 当前流环节的Id
     * @param formData 参数：此参数为影响流程流转的参数，不同的流程需求参数不一样，可以把表单的所有数据放到formData中
     * @return 环节属性dto （下一流程环节可能有多个并行分支，所以有多个）
     */
    List<TaskPropsDto> queryNextTaskPropsByProcessId(String taskId, Map<String , Object> formData);

    /**
     * 签收方法
     * @param taskId
     * @param userId
     * @param formData
     */
    void claim (String taskId,String userId,Map<String,Object> formData);
}
