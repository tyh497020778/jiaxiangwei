package com.shuanghui.jiaxiangwei.workflow.config;


import com.google.common.collect.Lists;
import com.shuanghui.jiaxiangwei.workflow.listener.AutoCompleteFirstTaskListener;
import com.shuanghui.jiaxiangwei.workflow.listener.ProxyTaskListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import java.util.List;

/**
 * Created by administration on 2019/8/12.
 */
public class ActivitiConfiguration {
    @Bean
    public ProcessEngineConfigurationConfigurer getConfig(){return new ActivitiConfigurer();}
    @Bean(name="postTaskListener")
    public TaskListener createGlobalTaskListener(){
        ProxyTaskListener globalTaskListener=new ProxyTaskListener();
        AutoCompleteFirstTaskListener autoCompleteFirstTaskListener=new AutoCompleteFirstTaskListener();
        List<TaskListener> taskListeners= Lists.newArrayList();
        taskListeners.add(autoCompleteFirstTaskListener);
        return globalTaskListener;
    }
}
