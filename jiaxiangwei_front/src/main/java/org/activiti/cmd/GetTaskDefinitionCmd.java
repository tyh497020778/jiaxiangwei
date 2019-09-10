package org.activiti.cmd;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.entity.ITaskDefinition;
import org.activiti.entity.TaskDefinitionEntity;

/**
 * Created by administration on 2019/8/12.
 */
public class GetTaskDefinitionCmd extends AbstractBaseCmd<ITaskDefinition>{
    private String taskDefinitionKey;
    private String processDefinitionId;
    public GetTaskDefinitionCmd(String taskDefinitionKey,String processDefinitionId){
        this.taskDefinitionKey=taskDefinitionKey;
        this.processDefinitionId=processDefinitionId;
    }
    @Override
    ITaskDefinition execute(CommandContext commandContext, ProcessEngineConfigurationImpl processEngineConfiguration){
        ProcessDefinitionEntity processDefinitionEntity=managementService.executeCommand(new GetDeploymentProcessDefinitionCmd(processDefinitionId));
        ActivityImpl activity=processDefinitionEntity.findActivity(taskDefinitionKey);
        return new TaskDefinitionEntity(activity);
    }
}
