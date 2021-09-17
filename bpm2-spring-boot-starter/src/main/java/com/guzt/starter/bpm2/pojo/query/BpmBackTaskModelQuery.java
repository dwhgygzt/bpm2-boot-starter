package com.guzt.starter.bpm2.pojo.query;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

/**
 * 查询流程定义中的用户任务
 *
 * @author <a href="mailto:guzhongtaoocp@126.com">guzhongtao</a>
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
}
