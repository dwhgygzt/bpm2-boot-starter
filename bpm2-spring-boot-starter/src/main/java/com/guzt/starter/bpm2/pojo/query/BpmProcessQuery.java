package com.guzt.starter.bpm2.pojo.query;

/**
 * 查询流程
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmProcessQuery extends BpmBasePageQuery {


    private static final long serialVersionUID = 1L;

    public BpmProcessQuery() {
        suspended = false;
    }

    /**
     * 按照id查询
     */
    private String defineId;

    /**
     * 按照deployId查询
     */
    private String deployId;

    /**
     * 按照分类精确查询
     */
    private String category;

    /**
     * 按照名字模糊查询
     */
    private String nameLike;

    /**
     * 按照key精准查询
     */
    private String defineKey;

    /**
     * returns true if the process instance is suspended
     * true：流程挂起     false： 流程正常， 默认false
     */
    private Boolean suspended;

    /**
     * 是否只查询最新版本，当传递 defineId 时忽略该查询参数
     */
    private Boolean justLastVersion;

    /**
     * 只查询指定版本，当传递 defineId 时忽略该查询参数
     */
    private Integer versionNumber;

    /**
     * 流程启动时候，关联的业务自定义key
     */
    private String processInstanceBusinessKey;

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getDefineKey() {
        return defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean getJustLastVersion() {
        return justLastVersion;
    }

    public void setJustLastVersion(Boolean justLastVersion) {
        this.justLastVersion = justLastVersion;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getProcessInstanceBusinessKey() {
        return processInstanceBusinessKey;
    }

    public void setProcessInstanceBusinessKey(String processInstanceBusinessKey) {
        this.processInstanceBusinessKey = processInstanceBusinessKey;
    }
}
