package org.activiti.cmd;


import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;


import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLinkType;

import java.util.Map;

/**
 * Created by administration on 2019/8/12.
 */
public class CompleteTaskWithCommentCmd implements Command<Object> {
    private String taskId;
    private String comment;
    private String backReason;
    private Map<String,Object> variables;

    public CompleteTaskWithCommentCmd(String taskId,Map<String,Object> variables,String comment,String backReason){
        this.taskId=taskId;
        this.variables=variables;
        this.backReason=backReason;
        this.comment=comment;
    }

    public Object execute(CommandContext commandContext){
        //查询历史纪录中的节点，设置返回值。
//        TaskService taskService1=commandContext.getProcessEngineConfiguration().getTaskService();
//        Task task=taskService1.createTaskQuery().taskId(taskId).singleResult();
//        List<HistoricTaskInstance> list=commandContext.getProcessEngineConfiguration().getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
//        if(!CollectionUtils.isEmpty(list)){
//            list.sort(new Comparator<HistoricTaskInstance>() {
//                @Override
//                public int compare(HistoricTaskInstance o1, HistoricTaskInstance o2) {
//                    return o1.getStartTime().after(o2.getStartTime())? -1 : 1;
//                }
//            });
//            variables.put("taskName",list.get(0).getTaskDefinitionKey());
//            if(list.size()>1){
//                variables.put("taskName",list.get(1).getTaskDefinitionKey());
//            }
//        }

        TaskEntity taskEntity=commandContext.getTaskEntityManager().findTaskById(taskId);

        if(variables != null ){
            taskEntity.setExecutionVariables(this.variables);
            taskEntity.setVariablesLocal(this.variables);
        }

        boolean localSocpe=false;

        if ((taskEntity.getDelegationState()) != null && taskEntity.getDelegationState().equals(DelegationState.PENDING)){
            throw new ActivitiException("A delefated task cannot be completed,but shouId be resolved instead");//无法完成已删除的任务，但应改为解决该任务
        }

        taskEntity.fireEvent(TaskListener.EVENTNAME_COMPLETE);

        if(Authentication.getAuthenticatedUserId() != null && (taskEntity.getProcessInstance()) != null){
            taskEntity.getProcessInstance().involveUser(Authentication.getAuthenticatedUserId(),IdentityLinkType.PARTICIPANT);
        }

        if(Context.getProcessEngineConfiguration().getEventDispatcher().isEnabled()){
            Context.getProcessEngineConfiguration()
                    .getEventDispatcher()
                    .dispatchEvent(
                            ActivitiEventBuilder
                                    .createEntityWithVariablesEvent(
                                            ActivitiEventType.TASK_COMPLETED,
                                            taskEntity,variables,localSocpe));
        }

        TaskService taskService = Context.getCommandContext().getProcessEngineConfiguration().getTaskService();
        Object article = taskService.getVariable(taskEntity.getId(),"articleId");
        if(article != null ){
            taskEntity.setDescription(String.valueOf(article));
            //TODO: 没有setGategory
            //taskEntity.setGategory(this.backReason);
            Context.getCommandContext().getProcessEngineConfiguration().getTaskService().saveTask(taskEntity);
        }

        return null;
    }
}
