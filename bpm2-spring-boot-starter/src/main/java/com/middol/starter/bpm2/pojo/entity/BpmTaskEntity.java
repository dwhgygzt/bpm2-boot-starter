package com.middol.starter.bpm2.pojo.entity;


import java.util.Date;

/**
 * 流程图中的某个用户任务节点
 *
 * @author guzt
 */
public class BpmTaskEntity extends BpmTaskMinModelEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 当前任务id
     */
    private String id;

    /**
     * 当前任务对应的流程实例id
     */
    private String procInstId;

    /**
     * 当前任务对应的流程运行id
     */
    private String executionId;

    /**
     * the start time of this process instance.
     */
    private Date startTime;

    /**
     * the start time of this process instance.
     */
    private Date endTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
