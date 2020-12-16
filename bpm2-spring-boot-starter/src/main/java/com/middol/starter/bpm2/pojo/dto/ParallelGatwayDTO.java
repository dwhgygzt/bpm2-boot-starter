package com.middol.starter.bpm2.pojo.dto;

import com.middol.starter.bpm2.pojo.entity.BpmTaskModelEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 并行网关DTO
 *
 * @author guzt
 */
public class ParallelGatwayDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前任务对应的流程定义id
     */
    protected String procDefId;

    /**
     * The tenant identifier of this process definition
     */
    protected String tenantId;

    /**
     * 该并行网关多少个分支数量
     */
    private Integer forkSize;

    /**
     * 并行网关起始网关id
     */
    private String forkId;

    /**
     * 并行网关结束id
     */
    private String joinId;

    /**
     * 该值表示当前查询的哪一个分支线上
     */
    protected String tmpForkRef;

    /**
     * 并行网关节点上的用户任务
     * key = taskDefKey  value = UserTaskModelEntity
     */
    private LinkedHashMap<String, BpmTaskModelEntity> userTaskModels;

    /**
     * 子 并行网关节点
     * 例如：嵌套并行网关
     */
    private List<ParallelGatwayDTO> childParallelGatways;

    /**
     * 父 并行网关节点
     */
    private ParallelGatwayDTO parentParallelGatwayDTO;


    public ParallelGatwayDTO() {
        childParallelGatways = new ArrayList<>(2);
        userTaskModels = new LinkedHashMap<>(4);
    }

    public Integer getForkSize() {
        return forkSize;
    }

    public void setForkSize(Integer forkSize) {
        this.forkSize = forkSize;
    }

    public String getForkId() {
        return forkId;
    }

    public void setForkId(String forkId) {
        this.forkId = forkId;
    }

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }

    public String getTmpForkRef() {
        return tmpForkRef;
    }

    public void setTmpForkRef(String tmpForkRef) {
        this.tmpForkRef = tmpForkRef;
    }

    public LinkedHashMap<String, BpmTaskModelEntity> getUserTaskModels() {
        return userTaskModels;
    }

    public void setUserTaskModels(LinkedHashMap<String, BpmTaskModelEntity> userTaskModels) {
        this.userTaskModels = userTaskModels;
    }

    public List<ParallelGatwayDTO> getChildParallelGatways() {
        return childParallelGatways;
    }

    public void setChildParallelGatways(List<ParallelGatwayDTO> childParallelGatways) {
        this.childParallelGatways = childParallelGatways;
    }

    public ParallelGatwayDTO getParentParallelGatwayDTO() {
        return parentParallelGatwayDTO;
    }

    public void setParentParallelGatwayDTO(ParallelGatwayDTO parentParallelGatwayDTO) {
        this.parentParallelGatwayDTO = parentParallelGatwayDTO;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "ParallelGatwayDTO{" +
                "procDefId='" + procDefId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", forkSize=" + forkSize +
                ", forkId='" + forkId + '\'' +
                ", joinId='" + joinId + '\'' +
                ", tmpForkRef='" + tmpForkRef + '\'' +
                ", userTaskModels=" + userTaskModels +
                ", childParallelGatways=" + childParallelGatways +
                ", parentParallelGatwayDTO=" + parentParallelGatwayDTO +
                '}';
    }
}
