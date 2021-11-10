package com.guzt.starter.bpm2.pojo.query;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

/**
 * 查询流程定义中的用户任务
 *
 * @author guzt
 */
public class BpmBackTaskModelQuery extends BpmBaseParam {

    /**
     * 当前用户任务id
     */
    private String taskId;

    /**
     * 按照任务分类精确查询
     */
    private String category;

    /**
     * 任务的操作类型  提交还是审核 对应画图中的 任务类型 “COMMIT  AUDIT”
     */
    private String nodeType;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}
