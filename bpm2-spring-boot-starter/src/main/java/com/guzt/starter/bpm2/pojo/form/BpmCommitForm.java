package com.guzt.starter.bpm2.pojo.form;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

import java.util.Map;

/**
 * 暂停某一个流程
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmCommitForm extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一任务编号
     */
    private String taskId;

    /**
     * 需要传递的参数，全局变量
     */
    private Map<String, Object> variables;

    /**
     * 需要传递的local参数, 本次任务可用，localVariables，本次任务优先使用local中的变量！
     */
    private Map<String, Object> localVariables;

    /**
     * 当前处理该任务的用户是谁，
     * 候选人、组，请务必传递该值，提交任务时会先做 claim操作，然后再提交
     */
    private String claimUser;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getClaimUser() {
        return claimUser;
    }

    public void setClaimUser(String claimUser) {
        this.claimUser = claimUser;
    }

    public Map<String, Object> getLocalVariables() {
        return localVariables;
    }

    public void setLocalVariables(Map<String, Object> localVariables) {
        this.localVariables = localVariables;
    }
}
