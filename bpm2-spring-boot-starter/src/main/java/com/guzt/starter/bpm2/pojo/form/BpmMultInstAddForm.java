package com.guzt.starter.bpm2.pojo.form;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

/**
 * 多实例任务 加签
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmMultInstAddForm extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 当前待加签的多实例任务id
     */
    private String multiInstanceTaskId;

    /**
     * 加签的人员信息，例如-业务系统中的用户名
     */
    private String assignee;


    public String getMultiInstanceTaskId() {
        return multiInstanceTaskId;
    }

    public void setMultiInstanceTaskId(String multiInstanceTaskId) {
        this.multiInstanceTaskId = multiInstanceTaskId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
