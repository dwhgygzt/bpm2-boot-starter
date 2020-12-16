package com.middol.starter.bpm2.pojo.dto;

import java.io.Serializable;

/**
 * 多实例用户信息列表，用于减签选择
 *
 * @author guzt
 */
public class MultiInstanceUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 受理人信息
     */
    private String assignee;

    /**
     * 用于 deleteMultiInstanceExecution
     */
    private String executionId;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    @Override
    public String toString() {
        return "MultiInstanceUserDTO{" +
                "assignee='" + assignee + '\'' +
                ", executionId='" + executionId + '\'' +
                '}';
    }
}
