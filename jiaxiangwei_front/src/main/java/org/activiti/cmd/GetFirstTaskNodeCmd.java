package org.activiti.cmd;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import java.util.Collection;
import java.util.List;

/**
 * Created by administration on 2019/8/12.
 */
public class GetFirstTaskNodeCmd implements Command<FlowElement>{
    private static final  long serialVersionUID =1L;
    /**
     * 流程定义Id
     */
    private String processDefinitionId;

    private FlowElement flowElement=null;

    public GetFirstTaskNodeCmd(String processDefinitionId){
        this.processDefinitionId=processDefinitionId;
    }

    @Override
    public FlowElement execute(CommandContext commandContext){
        /** 获取流程定义文件*/
        BpmnModel bpmnModel=commandContext.getProcessEngineConfiguration().getCommandExecutor().execute(
                new GetBpmnModelCmd(processDefinitionId));
        /** 获取主流程的所有节点*/
        Collection <FlowElement> flowElementList=bpmnModel.getMainProcess().getFlowElements();

        /** 找到开始节点 ， 找到开始节点指向的线*/
        flowElementList.stream().filter(StartEvent.class::isInstance).forEach( flowElement -> {
            List<SequenceFlow> outgoingFlows= ((StartEvent) flowElement).getOutgoingFlows();
            for (SequenceFlow sequenceFlow : outgoingFlows){
                FlowElement targetFlowElement=bpmnModel.getFlowElement(sequenceFlow.getTargetRef());
                this.flowElement=targetFlowElement;
                break;
            }
        });
        return flowElement;
    }
}
