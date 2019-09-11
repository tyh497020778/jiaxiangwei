package com.shuanghui.jiaxiangwei.workflow.config;

import com.google.common.collect.Lists;
import com.shuanghui.jiaxiangwei.workflow.listener.ProxyUserTaskBpmnParseHandle;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import java.util.List;

/**processEngineConfig自定义
 * Created by administration on 2019/8/12.
 */
public class ActivitiConfigurer implements ProcessEngineConfigurationConfigurer {
    /**
     *
     * @param processEngineConfiguration
     */
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration){
        List<BpmnParseHandler> parsers= Lists.newArrayList();
        ProxyUserTaskBpmnParseHandle proxyUserTaskBpmnParseHandle=new ProxyUserTaskBpmnParseHandle();
        proxyUserTaskBpmnParseHandle.setTaskListenerId("postTaskListener");
        parsers.add(proxyUserTaskBpmnParseHandle);
        processEngineConfiguration.setPostBpmnParseHandlers(parsers);
        processEngineConfiguration.setJobExecutorActivate(false);
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setDatabaseSchemaUpdate("false");
        processEngineConfiguration.setDbIdentityUsed(false);
        processEngineConfiguration.setHistory("full");
        System.out.println("ShareniuProcessEngineConfigurer###########");
        System.out.println(processEngineConfiguration.getActivityFontName());
    }
}
