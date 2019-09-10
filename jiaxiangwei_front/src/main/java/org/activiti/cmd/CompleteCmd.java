package org.activiti.cmd;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.AddCommentCmd;
import org.activiti.engine.impl.cmd.CompleteTaskCmd;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.exception.WorkFlowException;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/10 0010.
 * <p>
 * 提交命令.
 */
public class CompleteCmd extends AbstractBaseCmd<Object> {

    /**
     * 提交的任务id.
     */
    private String taskId;
    /**
     * 提交时填写的意见.
     */
    private String comment = "无审批意见";

    /**
     * 审批意见类型.
     */
    private String commentType = "comment";
    /**
     * 提交的参数.
     */
    private Map<String, Object> variables;

    /**
     * 提交到本地还是流程里面.
     */
    private boolean localScope = false;

    /**
     * 任务实际提交人.
     */
    private String userId;

    /**
     * init.
     *
     * @param taskId     任务id.
     * @param userId     用户id.
     * @param variables  参数.
     * @param comment    评论.
     * @param localScope 是否存贮在本地.
     */
    public CompleteCmd(String taskId, String userId, String comment, boolean localScope, Map<String, Object> variables) {
        this.taskId = taskId;
        this.userId = userId;
        this.variables = variables;
        this.comment = comment;
        this.localScope = localScope;
    }

    /**
     * init.
     *
     * @param taskId     任务id.
     * @param userId     用户id.
     * @param variables  参数.
     * @param localScope 是否存贮在本地.
     */
    public CompleteCmd(String taskId, String userId, boolean localScope, Map<String, Object> variables) {
        this.taskId = taskId;
        this.userId = userId;
        this.variables = variables;
        this.localScope = localScope;
    }

    /**
     * init.
     *
     * @param taskId    任务id.
     * @param userId    用户id.
     * @param variables 参数.
     */
    public CompleteCmd(String taskId, String userId, Map<String, Object> variables) {
        this.taskId = taskId;
        this.userId = userId;
        this.variables = variables;
    }


    @Override
    public Object execute(CommandContext commandContext, ProcessEngineConfigurationImpl processEngineConfiguration) {

        TaskEntity taskEntity = commandContext.getTaskEntityManager().findTaskById(taskId);

        if (taskEntity == null) {
            throw new WorkFlowException("task不能为null.");
        }

        //签收任务.
        taskService.claim(taskEntity.getId(), userId);

        /*设置参与人*/
        if (Authentication.getAuthenticatedUserId() != null && taskEntity.getProcessInstanceId() != null) {
            taskEntity.getProcessInstance().involveUser(Authentication.getAuthenticatedUserId(), IdentityLinkType.PARTICIPANT);
        }

        //保存审批意见.
        AddCommentCmd addCommentCmd = new AddCommentCmd(taskEntity.getId(), taskEntity.getProcessInstanceId(), commentType, comment);
        managementService.executeCommand(addCommentCmd);

        //提交任务.
        CompleteTaskCmd completeTaskCmd = new CompleteTaskCmd(taskId, variables, localScope);
        managementService.executeCommand(completeTaskCmd);

        return true;
    }
}
