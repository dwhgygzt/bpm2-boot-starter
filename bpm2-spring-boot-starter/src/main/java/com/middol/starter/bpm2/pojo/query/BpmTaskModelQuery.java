package com.middol.starter.bpm2.pojo.query;

import com.middol.starter.bpm2.pojo.BpmBaseParam;

/**
 * 查询流程定义中的用户任务
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmTaskModelQuery extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 【必填字段】按照id查询
     */
    private String defineId;

    /**
     * 用户任务定义的key
     */
    private String taskDefKey;

    /**
     * 按照任务分类精确查询
     */
    private String category;

    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
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
}
