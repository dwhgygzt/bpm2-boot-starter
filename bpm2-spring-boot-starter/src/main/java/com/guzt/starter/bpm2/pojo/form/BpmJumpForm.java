package com.guzt.starter.bpm2.pojo.form;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

import java.util.List;
import java.util.Map;

/**
 * 流程跳转（适用于选择历史任务步骤驳回场景，跳转指定任务，并行网关跳转）
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmJumpForm extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 当前任务节点
     */
    private String taskId;

    /**
     * 目标任务节点key
     */
    private List<String> targetTaskDefineKes;

    /**
     * 需要设置的全局相关参数，全局变量
     */
    private Map<String, Object> variables;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getTargetTaskDefineKes() {
        return targetTaskDefineKes;
    }

    public void setTargetTaskDefineKes(List<String> targetTaskDefineKes) {
        this.targetTaskDefineKes = targetTaskDefineKes;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
