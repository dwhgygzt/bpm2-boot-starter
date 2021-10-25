package com.guzt.starter.bpm2.pojo.query;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

/**
 * 查询流程定义中的用户任务
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmTaskModelQuery extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 按照id查询
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

    /**
     * 任务的操作类型  提交还是审核 对应画图中的 任务类型 “COMMIT  AUDIT”
     */
    private String nodeType;

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

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}
