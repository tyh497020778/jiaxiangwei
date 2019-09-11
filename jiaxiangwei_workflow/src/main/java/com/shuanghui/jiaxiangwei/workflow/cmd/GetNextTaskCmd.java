package com.shuanghui.jiaxiangwei.workflow.cmd;

import com.shuanghui.jiaxiangwei.workflow.entity.ITaskDefinition;
import com.shuanghui.jiaxiangwei.workflow.entity.TaskDefinitionEntity;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/4 0004.
 * <p>
 * 获取下一环节.
 */
public class GetNextTaskCmd extends AbstractBaseCmd<ITaskDefinition> {

    private final String EXCLUSIVE_GATEWAY = "exclusiveGateway";

    private final String PROPERTY_TYPE = "type";

    private final String USER_TASK = "userTask";

    private Map<String, Object> param;

    private String processDefinitionId;

    private String taskDefinitionKey;

    private ProcessDefinitionEntity processDefinitionEntity;

    private ITaskDefinition taskDefinition;

    private ActivityImpl currentActivity;

    public GetNextTaskCmd(String processDefinitionId, String taskDefinitionKey, Map<String, Object> param) {
        this.processDefinitionId = processDefinitionId;
        this.taskDefinitionKey = taskDefinitionKey;
        this.param = param;
    }

    @Override
    ITaskDefinition execute(CommandContext commandContext, ProcessEngineConfigurationImpl processEngineConfiguration) {
        //初始化上下文
        if (StringUtils.isBlank(processDefinitionId) || StringUtils.isBlank(taskDefinitionKey)) {
            this.taskDefinition = null;
        } else {
            //获取流程实例对象
            processDefinitionEntity = managementService.executeCommand(new GetDeploymentProcessDefinitionCmd((processDefinitionId)));
            //获取当前任务环节
            currentActivity = processDefinitionEntity.findActivity(taskDefinitionKey);
            //获取下一任务环节
            taskDefinition = nextTaskDefinition(currentActivity, currentActivity.getId(), null, param);
        }

        return taskDefinition;
    }

    private ITaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, Map<String, Object> param) {

        PvmActivity ac = null;

        Object condition = null;

        //如果遍历节点为用户任务并且节点不是当前节点信息直接返回
        if (USER_TASK.equals(activityImpl.getProperty(PROPERTY_TYPE)) && !activityId.equals(activityImpl.getId())) {

            return new TaskDefinitionEntity(activityImpl);
        } else {

            //获取节点所有流向线路信息
            List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
            List<PvmTransition> outTransitionsTemp;
            for (PvmTransition tr : outTransitions) {
                ac = tr.getDestination(); //获取线路的终点节点

                //终点为多实例.
                if (MultiInstanceActivityBehavior.class.isInstance(((ActivityImpl) ac).getActivityBehavior())) {
                    throw new ActivitiException(activityId + "终点为多实例");
                }
                //如果流向线路为排他网关
                if (EXCLUSIVE_GATEWAY.equals(ac.getProperty(PROPERTY_TYPE))) {
                    outTransitionsTemp = ac.getOutgoingTransitions();

                    //如果排他网关只有一条线路信息
                    if (outTransitionsTemp.size() == 1) {
                        return nextTaskDefinition((ActivityImpl) outTransitionsTemp.get(0).getDestination(), activityId, elString, param);
                    } else if (outTransitionsTemp.size() > 1) {  //如果排他网关有多条线路信息
                        for (PvmTransition tr1 : outTransitionsTemp) {
                            condition = tr1.getProperty("conditionText");  //获取排他网关线路判断条件信息
                            //判断el表达式是否成立
                            if (isCondition(StringUtils.trim((String) condition), param)) {
                                return nextTaskDefinition((ActivityImpl) tr1.getDestination(), activityId, elString, param);
                            }
                        }
                    }
                } else if (USER_TASK.equals(ac.getProperty(PROPERTY_TYPE))) {
                    return new TaskDefinitionEntity((ActivityImpl) ac);
                }
            }
            return null;
        }
    }

    protected static boolean isCondition(String el, Map<String, Object> map) {

        if (StringUtils.isBlank(el)) {
            return true;
        }

        //创建表达式解析上下文.
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();

        //获取所有的表达式.
        Set<String> keyList = map.keySet();
        for (String key : keyList) {
            context.setVariable(key, factory.createValueExpression(map.get(key), map.get(key).getClass()));
        }

        ValueExpression e = factory.createValueExpression(context, el, boolean.class);
        return (Boolean) e.getValue(context);
    }


}
