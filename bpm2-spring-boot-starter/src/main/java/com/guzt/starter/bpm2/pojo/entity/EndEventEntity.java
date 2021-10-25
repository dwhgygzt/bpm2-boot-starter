package com.guzt.starter.bpm2.pojo.entity;

import java.io.Serializable;

/**
 * 流程图中的开始节点
 *
 * @author guzt
 */
public class EndEventEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点id
     */
    protected String taskDefKey;

    /**
     * 节点名称
     */
    protected String taskName;




    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
