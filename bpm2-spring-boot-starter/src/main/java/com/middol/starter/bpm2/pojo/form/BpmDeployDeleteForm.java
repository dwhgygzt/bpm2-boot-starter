package com.middol.starter.bpm2.pojo.form;

import com.middol.starter.bpm2.pojo.BpmBaseParam;

/**
 * 删除一个已经发布的流程定义
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmDeployDeleteForm extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 之前发布成功后返回的 deployId
     */
    private String deployId;

    /**
     * true: 直接级联删除运行中的流程实例和定时任务    false： 当流程已经被启动了则不能被删除
     * if there are still runtime or history process instances or jobs.
     */
    private boolean cascade = false;

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public boolean isCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }
}
