package com.guzt.starter.bpm2.pojo.form;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

import java.util.Map;

/**
 * 启动某个流程
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmStartForm extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义的id 当传递该值时，将忽略 defineKey
     */
    private String defineId;

    /**
     * 流程定义的key
     */
    private String defineKey;

    /**
     * 业务key（自己系统定义的）
     */
    private String businessKey;

    /**
     * 全局变量
     * Returns the process variables if requested in the process instance query
     */
    private Map<String, Object> processVariables;

    /**
     * 是否要开启跳过节点任务功能.
     * 当然有节点任务设置 跳过节点表达式时，请设置该值为true，默认不开启
     */
    private Boolean enableSkipExpression;

    /**
     * 流程启动者，一般可设为人员的用户名
     */
    private String startUser;

    public String getDefineKey() {
        return defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Map<String, Object> getProcessVariables() {
        return processVariables;
    }

    public void setProcessVariables(Map<String, Object> processVariables) {
        this.processVariables = processVariables;
    }

    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    public Boolean getEnableSkipExpression() {
        return enableSkipExpression;
    }

    public void setEnableSkipExpression(Boolean enableSkipExpression) {
        this.enableSkipExpression = enableSkipExpression;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }
}
