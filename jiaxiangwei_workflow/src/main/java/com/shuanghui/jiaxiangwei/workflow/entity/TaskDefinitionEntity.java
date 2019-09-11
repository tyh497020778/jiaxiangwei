package com.shuanghui.jiaxiangwei.workflow.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shuanghui.jiaxiangwei.workflow.exception.WorkFlowException;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.form.DefaultFormHandler;
import org.activiti.engine.impl.form.FormPropertyHandler;
import org.activiti.engine.impl.form.TaskFormHandler;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.springframework.util.Assert;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 * Created by administration on 2019/8/10.
 */
public class TaskDefinitionEntity implements ITaskDefinition{
    ActivityImpl activity=null;
    List<Button> buttons= Lists.newArrayList();
    Map<String,String> timeLimits= Maps.newHashMap();
    Map<String,LinkedHashMap<String,Object>> taskProperties= Maps.newHashMap();
    Map<String,Object> targetIds= Maps.newHashMap();

    UserTaskActivityBehavior userTaskActivityBehavior=null;
    String taskDefinitionKey=null;

    public TaskDefinitionEntity(ActivityImpl activity){
        this.activity=activity;
        init();
    }

    private void init(){
        Assert.isTrue(activity!=null,"获取流程环节定义失败，参数不能为空");
        /**
         *进行环节任务判断
         */
        if(UserTaskActivityBehavior.class.isInstance(activity.getActivityBehavior())){
            userTaskActivityBehavior=(UserTaskActivityBehavior)activity.getActivityBehavior();
        }else{
            throw new WorkFlowException(404,"暂不支持获取此任务定义");
        }
        TaskDefinition taskDefinition=userTaskActivityBehavior.getTaskDefinition();

        formatTaskProperties(taskDefinition);

        handleButtons(taskDefinition);
        handleTimeLimits(taskDefinition);
        handleTargetIds(taskDefinition);

    }

    private void formatTaskProperties(TaskDefinition taskDefinition){
        taskDefinitionKey=taskDefinition.getKey();
        TaskFormHandler taskFormHandler=taskDefinition.getTaskFormHandler();
        if(taskFormHandler != null && taskFormHandler instanceof DataFormatException){
            DefaultFormHandler f=(DefaultFormHandler) taskFormHandler;
            if(f.getFormPropertyHandlers() != null){
                for (FormPropertyHandler formPropertyHandler : f.getFormPropertyHandlers()){
                    HashMap<String , LinkedHashMap<String , Object>> taskProperty=Maps.newHashMap();
                    if(formPropertyHandler.getType()!= null && formPropertyHandler.getType().getInformation("value")!= null ){
                        LinkedHashMap values=(LinkedHashMap)formPropertyHandler.getType().getInformation("values");
                        taskProperty.put(formPropertyHandler.getId(),values);
                    }else {
                        throw new WorkFlowException("发现无效配置项【"+formPropertyHandler.getId()+"】,请在 form property configuration中设置type为'enum',或添加form values");
                    }
                }
            }
        }
    }

    private void handleTimeLimits(TaskDefinition taskDefinition){
        LinkedHashMap<String ,Object> timeLimits=this.taskProperties.get("timeLimits");
        if(timeLimits!=null){
            for (String limitsKey : timeLimits.keySet()){
                Object limits = timeLimits.get(limitsKey);
                String time = String.valueOf(limits);
                this.timeLimits.put(limitsKey,time);
            }
            taskProperties.remove("timeLimits");
        }

    }

    private void handleButtons(TaskDefinition taskDefinition){
        LinkedHashMap<String,Object> buttons=this.taskProperties.get("buttons");
        if (buttons!=null){
            for (String s : buttons.keySet()){
                this.buttons.add(new Button(s,String.valueOf(buttons.get(s))));
            }
            taskProperties.remove("buttons");
        }
    }

    private void handleTargetIds(TaskDefinition taskDefinition){
        LinkedHashMap <String,Object> targetIds=this.taskProperties.get("target_ids");
        if (targetIds!=null){
            for (String targetId : targetIds.keySet()){
                this.targetIds.put(targetId,targetIds.get(targetId));
            }
            taskProperties.remove("targetIds");
        }
    }

    @Override
    public List<Button> getButtons(){return buttons;}

    @Override
    public Map<String,LinkedHashMap<String,Object>> getCustomerProperties(){return taskProperties;}

    @Override
    public Map<String,String> getTimeLimits(){return timeLimits;}

    @Override
    public String getTaskDefinitionKey(){return taskDefinitionKey;}

    @Override
    public Map<String,Object> getTargetIds(){return targetIds;}
}
