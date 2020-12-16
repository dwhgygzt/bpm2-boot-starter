package com.middol.starter.bpm2.service;

import com.middol.starter.bpm2.pojo.dto.UserTaskModelDTO;
import com.middol.starter.bpm2.pojo.entity.BpmProcessDefineEntity;
import com.middol.starter.bpm2.pojo.entity.BpmProcessInstanceEntity;
import com.middol.starter.bpm2.pojo.entity.BpmTaskMinModelEntity;
import com.middol.starter.bpm2.pojo.entity.BpmTaskModelEntity;
import com.middol.starter.bpm2.pojo.form.*;
import com.middol.starter.bpm2.pojo.query.BpmDirgarmQuery;
import com.middol.starter.bpm2.pojo.query.BpmProcessQuery;
import com.middol.starter.bpm2.pojo.query.BpmTaskModelQuery;

import java.io.InputStream;
import java.util.List;

/**
 * 流程管理
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public interface BpmProcessService {

    /**
     * 部署一个流程
     *
     * @param bytesForm xml文件流转换成的二进制
     */
    void deploy(BpmDeployBytesForm bytesForm);

    /**
     * 部署一个流程
     *
     * @param classResourceForm classResource 下的 xml文件路径
     */
    void deploy(BpmDeployClassResourceForm classResourceForm);

    /**
     * 部署一个流程
     *
     * @param inputStreamForm xml文件流
     */
    void deploy(BpmDeployInputStreamForm inputStreamForm);

    /**
     * 部署一个流程
     *
     * @param textForm xml格式的文件内容
     */
    void deploy(BpmDeployTextForm textForm);

    /**
     * 删除一个已经发布的流程定义
     *
     * @param form 删除参数
     */
    void deleteDeploy(BpmDeployDeleteForm form);

    /**
     * 查看流程定义列表
     *
     * @param query BpmProcessQuery
     * @return BpmProcessDefineEntity
     */
    List<BpmProcessDefineEntity> listDefines(BpmProcessQuery query);

    /**
     * 根据id查看流程定义实体
     *
     * @param defineId id
     * @return BpmProcessDefineEntity
     */
    BpmProcessDefineEntity getDefineById(String defineId);

    /**
     * 启动一个流程
     *
     * @param form BpmStartForm
     * @return Entity
     */
    BpmProcessInstanceEntity start(BpmStartForm form);

    /**
     * 查看运行中的流程实例列表
     *
     * @param query BpmProcessQuery
     * @return BpmProcessInstanceEntity
     */
    List<BpmProcessInstanceEntity> listInstances(BpmProcessQuery query);

    /**
     * 根据ID查看运行中的流程实例
     *
     * @param instanceId id
     * @return BpmProcessInstanceEntity
     */
    BpmProcessInstanceEntity getInstanceById(String instanceId);

    /**
     * 根据流程定义id和流程实例id获得流程图对应的BASE64码
     *
     * @param query BpmDirgarmQuery
     * @return 流程图对应的BASE64码
     */
    String generateDiagram(BpmDirgarmQuery query);

    /**
     * 根据流程定义id和流程实例id获得流程图对应的 InputStream
     *
     * @param query BpmDirgarmQuery
     * @return 流程图对应的InputStream
     */
    InputStream generateStreamDiagram(BpmDirgarmQuery query);

    /**
     * 挂起一个流程定义
     *
     * @param processDefinitionId 流程定义ID
     */
    void suspendProcessDefine(String processDefinitionId);

    /**
     * 激活一个流程定义
     *
     * @param processDefinitionId 流程定义ID
     */
    void activeProcessDefin(String processDefinitionId);

    /**
     * 挂起一个流程实例，所有的运行任务都将被挂起
     *
     * @param processInstanceId 流程实例ID
     */
    void suspendProcessInstance(String processInstanceId);

    /**
     * 激活一个流程实例，所有的运行任务都将被激活
     *
     * @param processInstanceId 流程实例ID
     */
    void activeProcessInstance(String processInstanceId);

    /**
     * 查询流程所有的用户任务节点信息, 简单列表展示，不区分并行网关节点和非并行网关节点
     *
     * @param query 查询参数
     * @return List BpmTaskMinModelEntity
     */
    List<BpmTaskMinModelEntity> simpleListUserTaskModels(BpmTaskModelQuery query);

    /**
     * 查询流程所有的用户任务节点信息，分并行网关节点和非并行网关节点
     *
     * @param query 查询参数
     * @return List BpmTaskModelEntity
     */
    List<BpmTaskModelEntity> listUserTaskModels(BpmTaskModelQuery query);

    /**
     * 查询流程所有的用户任务节点信息，分并行网关节点和非并行网关节点
     *
     * @param query 查询参数
     * @return List BpmTaskModelEntity
     */
    UserTaskModelDTO getUserTaskModelDto(BpmTaskModelQuery query);

    /**
     * 查询历史流程实例列表
     *
     * @param query BpmProcessQuery
     * @return BpmProcessInstanceEntity
     */
    List<BpmProcessInstanceEntity> listHisInstances(BpmProcessQuery query);

    /**
     * 终止、删除一个在运行的流程
     *
     * @param processInstanceId 流程实例ID
     */
    void deleteProcessInstance(String processInstanceId);

    /**
     * 删除历史流程数据
     *
     * @param processInstanceId 流程实例ID
     */
    void deleteHisProcessInstance(String processInstanceId);

}
