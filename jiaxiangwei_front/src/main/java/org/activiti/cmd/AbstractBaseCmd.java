package org.activiti.cmd;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * Created by administration on 2019/8/10.
 */
public abstract class AbstractBaseCmd<Object> implements Command<Object> {
    protected RepositoryService repositoryService;

    protected RuntimeService runtimeService;

    protected HistoryService historyService;

    protected ManagementService managementService;

    protected TaskService taskService;

    protected TaskEntityManager taskEntityManager;

    protected ExecutionEntityManager executionEntityManager;

    protected ProcessDefinitionEntityManager processDefinitionEntityManager;

    protected HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager;

    @Override
    public Object execute(CommandContext commandContext){
        ProcessEngineConfigurationImpl processEngineConfiguration=commandContext.getProcessEngineConfiguration();
        this.runtimeService=processEngineConfiguration.getRuntimeService();

        this.runtimeService=processEngineConfiguration.getRuntimeService();

        this.historyService=processEngineConfiguration.getHistoryService();

        this.managementService=processEngineConfiguration.getManagementService();

        this.taskService=processEngineConfiguration.getTaskService();

        this.taskEntityManager=commandContext.getTaskEntityManager();

        this.executionEntityManager=commandContext.getExecutionEntityManager();

        this.processDefinitionEntityManager=commandContext.getProcessDefinitionEntityManager();

        this.historicProcessInstanceEntityManager=commandContext.getHistoricProcessInstanceEntityManager();

        return execute(commandContext, processEngineConfiguration);
    }
    protected ProcessDefinitionEntity getProcessDefinitionEntity(String processDefinitionId){
        return managementService.executeCommand(new GetDeploymentProcessDefinitionCmd(processDefinitionId));
    }

    protected ProcessInstance getProcessInstance(String processInstanceId){
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    abstract Object execute(CommandContext commandContext,ProcessEngineConfigurationImpl processEngineConfiguration);
}
