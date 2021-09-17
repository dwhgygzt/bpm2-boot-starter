package com.guzt.starter.bpm2.pojo.entity;

import java.io.Serializable;

/**
 * 流程图中的某个用户任务节点
 * @author guzt
 */
public class BpmTaskMinModelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前任务对应的流程定义id
     */
    protected String procDefId;

    /**
     * The tenant identifier of this process definition
     */
    protected String tenantId;

    /**
     * 节点id
     */
    protected String taskDefKey;

    /**
     * 节点名称
     */
    protected String taskName;

    /**
     * 受理人，可能为表达式
     */
    protected String assignee;

    /**
     * 关联的表单key
     */
    protected String formKey;

    /**
     * 任务分类
     */
    protected String category;

    /**
     * 是否多实例任务
     */
    protected Boolean hasMultiInstance;

    /**
     * 是否多实例串行执行
     */
    protected Boolean sequential;

    /**
     * 跳过该节点表达式
     */
    protected String skipExpression;


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

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getHasMultiInstance() {
        return hasMultiInstance;
    }

    public void setHasMultiInstance(Boolean hasMultiInstance) {
        this.hasMultiInstance = hasMultiInstance;
    }

    public Boolean getSequential() {
        return sequential;
    }

    public void setSequential(Boolean sequential) {
        this.sequential = sequential;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getSkipExpression() {
        return skipExpression;
    }

    public void setSkipExpression(String skipExpression) {
        this.skipExpression = skipExpression;
    }
}
