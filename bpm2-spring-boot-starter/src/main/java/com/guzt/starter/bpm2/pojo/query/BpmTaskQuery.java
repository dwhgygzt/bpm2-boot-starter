package com.guzt.starter.bpm2.pojo.query;

import java.util.Date;

/**
 * 查询流程定义中的用户任务
 *
 * @author guzt
 */
public class BpmTaskQuery extends BpmBasePageQuery {

    private static final long serialVersionUID = 1L;

    public BpmTaskQuery() {
        suspended = false;
    }


    /**
     * 按照流程实例id查询
     */
    private String processInstanceId;

    /**
     * 用户任务定义的key
     */
    private String taskDefKey;

    /**
     * 按照任务分类精确查询
     */
    private String category;

    /**
     * 查找在运行中的任务时，用到
     * returns true if the process instance is suspended
     * true：流程挂起     false： 流程正常， 默认false
     */
    private Boolean suspended;

    /**
     * 查询历史任务时，用到
     */
    private Boolean processFinished;

    /**
     * 任务待处理人，或待处理组织 （固定人员、候选人、组）
     */
    private String candidateUser;

    /**
     * 目前只适应于查询待处理任务.
     * 查询到期日在 dueDateAfter 之后的任务
     */
    private Date dueDateAfter;

    /**
     * 目前只适应于查询待处理任务.
     * 查询到期日在 dueDateAfter 之前的任务
     */
    private Date dueDateBefore;

    /**
     * 目前只适应于查询待处理任务.
     * 查询到期日是 dueDate 的任务
     */
    private Date dueDate;

    /**
     * 目前只适应于查询待处理任务.
     * 是否查询超期的任务， true 查询超期  ,  false 查询未超期 , null不进行筛选
     */
    private Boolean alreadyOvertime;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean getProcessFinished() {
        return processFinished;
    }

    public void setProcessFinished(Boolean processFinished) {
        this.processFinished = processFinished;
    }

    public String getCandidateUser() {
        return candidateUser;
    }

    public void setCandidateUser(String candidateUser) {
        this.candidateUser = candidateUser;
    }

    public Date getDueDateAfter() {
        return dueDateAfter;
    }

    public void setDueDateAfter(Date dueDateAfter) {
        this.dueDateAfter = dueDateAfter;
    }

    public Date getDueDateBefore() {
        return dueDateBefore;
    }

    public void setDueDateBefore(Date dueDateBefore) {
        this.dueDateBefore = dueDateBefore;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getAlreadyOvertime() {
        return alreadyOvertime;
    }

    public void setAlreadyOvertime(Boolean alreadyOvertime) {
        this.alreadyOvertime = alreadyOvertime;
    }
}
