package com.guzt.starter.bpm2.pojo.entity;

import java.util.Date;
import java.util.Map;

/**
 * 流程实例对象
 *
 * @author <a href="mailto:guzhongtaoocp@126.com">guzhongtao</a>
 */
public class BpmProcessInstanceEntity extends BpmProcessBaseEty {


    private static final long serialVersionUID = 1L;

    /**
     * 启动实例id
     */
    private String instanceId;

    /**
     * The business key of this process instance.
     */
    private String businessKey;

    /**
     * the start time of this process instance.
     */
    private Date startTime;

    /**
     * the start time of this process instance.
     */
    private Date endTime;

    /**
     * the name of this process instance.
     */
    private String name;

    /**
     * Returns the process variables if requested in the process instance query
     */
    private Map<String, Object> processVariables;

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getProcessVariables() {
        return processVariables;
    }

    public void setProcessVariables(Map<String, Object> processVariables) {
        this.processVariables = processVariables;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
