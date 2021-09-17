package com.guzt.starter.bpm2.pojo.form;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

/**
 * 多实例任务 减签
 *
 * @author <a href="mailto:guzhongtaoocp@126.com">guzhongtao</a>
 */
public class BpmMultInstDeleteForm extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    public BpmMultInstDeleteForm() {
        executionIsCompleted = false;
    }

    /**
     * 当前操作的多实例任务id
     */
    private String multiInstanceTaskId;

    /**
     * 要减签哪一个用户 executionId字段，根据getMultiInstanceUsers接口查询出所有还没有处理过任务的人员
     */
    private String deleteExecutionId;

    /**
     * true 减签后标志用户任务为完成， false 相反， 默认 false
     */
    private Boolean executionIsCompleted;

    public String getDeleteExecutionId() {
        return deleteExecutionId;
    }

    public void setDeleteExecutionId(String deleteExecutionId) {
        this.deleteExecutionId = deleteExecutionId;
    }

    public Boolean getExecutionIsCompleted() {
        return executionIsCompleted;
    }

    public void setExecutionIsCompleted(Boolean executionIsCompleted) {
        this.executionIsCompleted = executionIsCompleted;
    }

    public String getMultiInstanceTaskId() {
        return multiInstanceTaskId;
    }

    public void setMultiInstanceTaskId(String multiInstanceTaskId) {
        this.multiInstanceTaskId = multiInstanceTaskId;
    }
}
