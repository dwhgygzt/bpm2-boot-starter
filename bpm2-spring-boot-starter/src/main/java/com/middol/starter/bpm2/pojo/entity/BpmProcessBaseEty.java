package com.middol.starter.bpm2.pojo.entity;

import java.io.Serializable;

/**
 * 流程实例对象
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmProcessBaseEty implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * unique identifier
     */
    private String defineId;

    /**
     * 版本号
     */
    private Integer defineVersion;


    /**
     * label used for display purposes
     */
    private String defineName;


    /**
     * description of this process
     **/
    private String description;


    /**
     * unique name for all versions this process definitions
     */
    private String defineKey;

    /**
     * The deployment in which this process definition is contained.
     */
    private String deploymentId;

    /**
     * The tenant identifier of this process definition
     */
    private String tenantId;

    /**
     * returns true if the process instance is suspended
     * true：process is suspended     false： process is activeProcessDefin
     */
    private Boolean suspended;

    /**
     * startFormKey
     */
    private String startFormKey;

    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    public Integer getDefineVersion() {
        return defineVersion;
    }

    public void setDefineVersion(Integer defineVersion) {
        this.defineVersion = defineVersion;
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefineKey() {
        return defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public String getStartFormKey() {
        return startFormKey;
    }

    public void setStartFormKey(String startFormKey) {
        this.startFormKey = startFormKey;
    }
}
