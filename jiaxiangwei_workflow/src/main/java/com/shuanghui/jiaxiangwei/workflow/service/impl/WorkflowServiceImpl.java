package com.shuanghui.jiaxiangwei.workflow.service.impl;

import com.google.common.collect.Lists;
import com.shuanghui.jiaxiangwei.workflow.cmd.CompleteTaskWithCommentCmd;
import com.shuanghui.jiaxiangwei.workflow.cmd.GetNextTaskCmd;
import com.shuanghui.jiaxiangwei.workflow.entity.ITaskDefinition;
import com.shuanghui.jiaxiangwei.workflow.entity.ProcessInstanceDto;
import com.shuanghui.jiaxiangwei.workflow.entity.TaskPropsDto;
import com.shuanghui.jiaxiangwei.workflow.service.WorkflowService;
import org.activiti.engine.FormService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Service
public class WorkflowServiceImpl implements WorkflowService{

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    FormService formService;
    @Autowired
    ManagementService managementService;


    /**
     * 启动流程
     * @param processDefinitionKey  根据流程定义key，例如：咨询流程、投诉流程
     * @param createBy 流程处理人
     * @param businessId 工单主键
     * @param formData  参数：此参数为影响流程流转的参数，不同的流程需求参数不一样，可以把表单的所有数据放到formData中
     * @return
     */
    @Override
    public ProcessInstanceDto startProcess(String processDefinitionKey, String createBy, Long businessId, Map<String, Object> formData) {
        Authentication.setAuthenticatedUserId(createBy);
        ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinitionKey,String.valueOf(businessId),formData);
        ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
        processInstanceDto.setProcessInstanceId(processInstance.getProcessInstanceId());
        return processInstanceDto;
    }

    /**
     * 提交任务
     * @param taskId  任务Id在待办表中
     * @param comment   审批意见
     * @param formData  参数：此参数为影响流程流转的参数，不同的流程需求参数不一样，可以把表单的所有数据放到formData中
     */
    @Override
    public void submitTask(String taskId, String comment, Map<String, Object> formData) {
        formData.put("_comment",comment);
        managementService.executeCommand(new CompleteTaskWithCommentCmd(taskId, formData, comment,null));
    }

    /**
     * 删除流程
     * @param processInstanceId 流程实例Id
     */
    @Override
    public void delProcess(String processInstanceId) {
        runtimeService.deleteProcessInstance(processInstanceId,null);
    }

    /**
     * 根据当前流程的processKey 获取下一流程自定义的属性
     * @param processKey 当前流程的key
     * @param formData   参数：此参数为影响流程流转的参数，不同的流程需求参数不一样，可以把表单的所有数据放到formData中
     * @return
     */
    @Override
    public List<TaskPropsDto> queryNextTaskPropsByProcessKey(String processKey, Map<String, Object> formData) {
        return null;
    }

    /**
     * 根据当前节点的Id 获取下一流程自定义的属性
     * @param taskId 当前流环节的Id
     * @param formData 参数：此参数为影响流程流转的参数，不同的流程需求参数不一样，可以把表单的所有数据放到formData中
     * @return
     */
    @Override
    public List<TaskPropsDto> queryNextTaskPropsByProcessId(String taskId, Map<String, Object> formData) {
        List<TaskPropsDto> dtos= Lists.newArrayList();
        Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
        Assert.isTrue(task != null,"task not fount");
        ITaskDefinition definition=managementService.executeCommand(
                new GetNextTaskCmd(task.getProcessDefinitionId(),task.getTaskDefinitionKey(),formData));
        if(definition != null){
            TaskPropsDto dto=new TaskPropsDto();
            dtos.add(dto);
            BeanUtils.copyProperties(definition,dto);
        }
        return dtos;
    }
    /**
     * 签收方法
     * @param taskId
     * @param userId
     * @param formData
     */
    @Override
    public void claim(String taskId, String userId, Map<String, Object> formData) {
        taskService.setVariables(taskId,formData);
        taskService.claim(taskId,userId);
    }

}
