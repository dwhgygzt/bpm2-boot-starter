package com.middol.starter.bpm2.pojo.dto;

import com.middol.starter.bpm2.pojo.entity.BpmTaskModelEntity;
import org.flowable.bpmn.model.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程图中的用户任务集合 DTO
 *
 * @author guzt
 */
public class UserTaskModelDTO {

    /**
     * 所有的任务节点
     */
    private List<BpmTaskModelEntity> allUserTaskModels;

    /**
     * 所有的（不分父子网关）并行网关
     */
    private Map<String, ParallelGatwayDTO> allForkGatewayMap;


    public UserTaskModelDTO() {
        allForkGatewayMap = new HashMap<>(2);
        allUserTaskModels = new ArrayList<>(8);
    }

    /**
     * 本次Process对象
     */
    private Process process;

    public List<BpmTaskModelEntity> getAllUserTaskModels() {
        return allUserTaskModels;
    }

    public void setAllUserTaskModels(List<BpmTaskModelEntity> allUserTaskModels) {
        this.allUserTaskModels = allUserTaskModels;
    }

    public Map<String, ParallelGatwayDTO> getAllForkGatewayMap() {
        return allForkGatewayMap;
    }

    public void setAllForkGatewayMap(Map<String, ParallelGatwayDTO> allForkGatewayMap) {
        this.allForkGatewayMap = allForkGatewayMap;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "UserTaskModelDTO{" +
                "allUserTaskModels=" + allUserTaskModels +
                ", allForkGatewayMap=" + allForkGatewayMap +
                '}';
    }
}
