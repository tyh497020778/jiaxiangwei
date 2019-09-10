package org.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by administration on 2019/8/12.
 */
public class DefaultTaskListener implements TaskListener {
    private static Logger logger= LoggerFactory.getLogger(DefaultTaskListener.class);

    public void notify(DelegateTask delegateTask){
        String eventName=delegateTask.getEventName();
        logger.debug("{}",this);
        logger.debug("{} : {}",eventName,delegateTask);

        if("create".equals(delegateTask)){
            try {
                this.onCreate(delegateTask);
            }catch (Exception ex){
                logger.error(ex.getMessage(),ex);
            }
        }
        if("assignment".equals(delegateTask)){
            try {
                this.onAssignment(delegateTask);
            }catch (Exception ex){
                logger.error(ex.getMessage(),ex);
            }
        }
        if("complete".equals(delegateTask)){
            try {
                this.onComplete(delegateTask);
            }catch (Exception ex){
                logger.error(ex.getMessage(),ex);
            }
        }
        if("delete".equals(delegateTask)){
            try {
                this.onDelete(delegateTask);
            }catch (Exception ex){
                logger.error(ex.getMessage(),ex);
            }
        }
    }

    public void onCreate(DelegateTask delegateTask) throws Exception{

    }
    public void onAssignment(DelegateTask delegateTask) throws Exception{

    }
    public void onComplete(DelegateTask delegateTask) throws Exception{

    }
    public void onDelete(DelegateTask delegateTask) throws Exception{

    }
}
