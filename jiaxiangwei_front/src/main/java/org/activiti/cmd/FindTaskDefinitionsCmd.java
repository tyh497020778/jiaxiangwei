package org.activiti.cmd;

import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取任务定义
 * Created by administration on 2019/8/12.
 */
public class FindTaskDefinitionsCmd implements Command<List<TaskDefinition>>{
    protected String processDefinitionId;

    public FindTaskDefinitionsCmd(String processDefinitionId){
        this.processDefinitionId=processDefinitionId;
    }

    public List<TaskDefinition> execute(CommandContext commandContext){
        ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(processDefinitionId).execute(commandContext);
        List<TaskDefinition> taskDefinitions = new ArrayList<TaskDefinition>();
        taskDefinitions.addAll(processDefinitionEntity.getTaskDefinitions().values());
        return taskDefinitions;
    }
}
