package com.shuanghui.jiaxiangwei.workflow.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘钦龙 on 2019/8/12.
 */
@Data

public class ProcessInstanceDto {
    String processInstanceId;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
