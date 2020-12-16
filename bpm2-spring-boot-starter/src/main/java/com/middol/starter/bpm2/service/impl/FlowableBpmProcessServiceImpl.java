package com.middol.starter.bpm2.service.impl;

import com.middol.starter.bpm2.exception.BpmBusinessException;
import com.middol.starter.bpm2.pojo.context.HighlightColorContext;
import com.middol.starter.bpm2.pojo.dto.ParallelGatwayDTO;
import com.middol.starter.bpm2.pojo.dto.UserTaskModelDTO;
import com.middol.starter.bpm2.pojo.entity.BpmProcessDefineEntity;
import com.middol.starter.bpm2.pojo.entity.BpmProcessInstanceEntity;
import com.middol.starter.bpm2.pojo.entity.BpmTaskMinModelEntity;
import com.middol.starter.bpm2.pojo.entity.BpmTaskModelEntity;
import com.middol.starter.bpm2.pojo.form.*;
import com.middol.starter.bpm2.pojo.query.BpmDirgarmQuery;
import com.middol.starter.bpm2.pojo.query.BpmProcessQuery;
import com.middol.starter.bpm2.pojo.query.BpmTaskModelQuery;
import com.middol.starter.bpm2.service.BpmProcessService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Flowable 6 - 流程管理
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class FlowableBpmProcessServiceImpl implements BpmProcessService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ProcessEngine processEngine;

    private RepositoryService repositoryService;

    private RuntimeService runtimeService;

    private FormService formService;

    private HistoryService historyService;

    private ProcessEngineConfiguration processEngineConfiguration;

    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void setFormService(FormService formService) {
        this.formService = formService;
    }

    public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deploy(BpmDeployBytesForm bytesForm) {
        processEngine.getProcessEngineConfiguration()
                .getRepositoryService()
                .createDeployment()
                .addBytes(bytesForm.getDeployResourceName(), bytesForm.getDeployBytes())
                .tenantId(bytesForm.getTenantId())
                .deploy();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deploy(BpmDeployClassResourceForm classResourceForm) {
        processEngine.getProcessEngineConfiguration()
                .getRepositoryService()
                .createDeployment()
                .addClasspathResource(classResourceForm.getResource())
                .tenantId(classResourceForm.getTenantId())
                .deploy();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deploy(BpmDeployInputStreamForm inputStreamForm) {
        processEngine.getProcessEngineConfiguration()
                .getRepositoryService()
                .createDeployment()
                .addInputStream(inputStreamForm.getDeployResourceName(), inputStreamForm.getInputStream())
                .tenantId(inputStreamForm.getTenantId())
                .deploy();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deploy(BpmDeployTextForm textForm) {
        processEngine.getProcessEngineConfiguration()
                .getRepositoryService()
                .createDeployment()
                .addString(textForm.getDeployResourceName(), textForm.getText())
                .tenantId(textForm.getTenantId())
                .deploy();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteDeploy(BpmDeployDeleteForm form) {
        repositoryService.deleteDeployment(form.getDeployId(), form.isCascade());
    }

    @Override
    public BpmProcessDefineEntity getDefineById(String defineId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(defineId).singleResult();
        if (processDefinition != null) {
            BpmProcessDefineEntity entity = new BpmProcessDefineEntity();
            flowableDefinitionToBpmEntity(processDefinition, entity);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public List<BpmProcessDefineEntity> listDefines(BpmProcessQuery query) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotBlank(query.getTenantId())) {
            processDefinitionQuery.processDefinitionTenantId(query.getTenantId());
        }
        if (StringUtils.isNotBlank(query.getDefineKey())) {
            processDefinitionQuery.processDefinitionKey(query.getDefineKey());
        }
        if (StringUtils.isNotBlank(query.getNameLike())) {
            processDefinitionQuery.processDefinitionNameLike("%" + query.getNameLike() + "%");
        }
        if (StringUtils.isNotBlank(query.getDefineId())) {
            processDefinitionQuery.processDefinitionId(query.getDefineId());
        }
        if (StringUtils.isNotBlank(query.getDeployId())) {
            processDefinitionQuery.deploymentId(query.getDeployId());
        }
        if (StringUtils.isNotBlank(query.getCategory())) {
            processDefinitionQuery.processDefinitionCategory(query.getCategory());
        }
        if (query.getSuspended() != null) {
            if (query.getSuspended()) {
                processDefinitionQuery.suspended();
            } else {
                processDefinitionQuery.active();
            }
        }
        if (StringUtils.isBlank(query.getDefineId()) && query.getJustLastVersion() != null) {
            if (query.getJustLastVersion()) {
                processDefinitionQuery.latestVersion();
            }
        }
        if (StringUtils.isBlank(query.getDefineId()) && query.getVersionNumber() != null) {
            processDefinitionQuery.processDefinitionVersion(query.getVersionNumber());
        }
        processDefinitionQuery.orderByProcessDefinitionName().asc();
        List<ProcessDefinition> list;
        if (query.getPageNo() != null && query.getPageSize() != null) {
            list = processDefinitionQuery.listPage(query.getPageNo(), query.getPageSize());
        } else {
            list = processDefinitionQuery.list();
        }

        List<BpmProcessDefineEntity> entities = new ArrayList<>(4);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProcessDefinition item : list) {
                BpmProcessDefineEntity entity = new BpmProcessDefineEntity();
                flowableDefinitionToBpmEntity(item, entity);

                entities.add(entity);
            }
        }
        return entities;
    }

    private void flowableDefinitionToBpmEntity(ProcessDefinition item, BpmProcessDefineEntity entity) {
        entity.setDefineId(item.getId());
        entity.setDefineName(item.getName());
        entity.setDefineKey(item.getKey());
        entity.setCategory(item.getCategory());
        entity.setDeploymentId(item.getDeploymentId());
        entity.setTenantId(item.getTenantId());
        entity.setDescription(item.getDescription());
        entity.setDiagramResourceName(item.getDiagramResourceName());
        entity.setResourceName(item.getResourceName());
        entity.setDefineVersion(item.getVersion());
        entity.setSuspended(item.isSuspended());
        if (item.hasStartFormKey()) {
            entity.setStartFormKey(formService.getStartFormKey(item.getId()));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BpmProcessInstanceEntity start(BpmStartForm form) {
        BpmProcessInstanceEntity instanceEntity = null;
        ProcessInstance processInstance;
        if (form.getEnableSkipExpression() != null && form.getEnableSkipExpression()) {
            Map<String, Object> processVariables = form.getProcessVariables();
            if (CollectionUtils.isEmpty(processVariables)) {
                processVariables = new HashMap<>(2);
            }
            processVariables.put("_FLOWABLE_SKIP_EXPRESSION_ENABLED", true);
        }
        if (StringUtils.isNotBlank(form.getDefineId())) {
            processInstance = runtimeService.startProcessInstanceById(
                    form.getDefineKey(),
                    form.getBusinessKey(),
                    form.getProcessVariables());
        } else {
            if (StringUtils.isNotBlank(form.getTenantId())) {
                processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(
                        form.getDefineKey(),
                        form.getBusinessKey(),
                        form.getProcessVariables(),
                        form.getTenantId());
            } else {
                processInstance = runtimeService.startProcessInstanceByKey(
                        form.getDefineKey(),
                        form.getBusinessKey(),
                        form.getProcessVariables());
            }
        }

        if (processInstance != null) {
            instanceEntity = new BpmProcessInstanceEntity();
            flowableInstanceToBpmEntity(processInstance, instanceEntity);
        }

        return instanceEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteProcessInstance(String processInstanceId) {
        runtimeService.deleteProcessInstance(processInstanceId, "");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteHisProcessInstance(String processInstanceId) {
        historyService.deleteHistoricProcessInstance(processInstanceId);
    }

    private void flowableInstanceToBpmEntity(ProcessInstance processInstance, BpmProcessInstanceEntity instanceEntity) {
        instanceEntity.setInstanceId(processInstance.getId());
        instanceEntity.setDefineId(processInstance.getProcessDefinitionId());
        instanceEntity.setBusinessKey(processInstance.getBusinessKey());
        instanceEntity.setDefineKey(processInstance.getProcessDefinitionKey());
        instanceEntity.setDefineName(processInstance.getProcessDefinitionName());
        instanceEntity.setName(processInstance.getName());
        instanceEntity.setDefineVersion(processInstance.getProcessDefinitionVersion());
        instanceEntity.setDescription(processInstance.getDescription());
        instanceEntity.setStartTime(processInstance.getStartTime());
        instanceEntity.setSuspended(processInstance.isSuspended());
        instanceEntity.setDeploymentId(processInstance.getDeploymentId());
        instanceEntity.setTenantId(processInstance.getTenantId());
        instanceEntity.setProcessVariables(processInstance.getProcessVariables());
        instanceEntity.setStartFormKey(formService.getStartFormKey(processInstance.getProcessDefinitionId()));
    }

    private void flowableInstanceToBpmEntity(HistoricProcessInstance hisProcessInstance, BpmProcessInstanceEntity instanceEntity) {
        instanceEntity.setBusinessKey(hisProcessInstance.getBusinessKey());
        instanceEntity.setDefineId(hisProcessInstance.getProcessDefinitionId());
        instanceEntity.setDefineVersion(hisProcessInstance.getProcessDefinitionVersion());
        instanceEntity.setDefineKey(hisProcessInstance.getProcessDefinitionKey());
        instanceEntity.setDefineName(hisProcessInstance.getProcessDefinitionName());
        instanceEntity.setName(hisProcessInstance.getName());
        instanceEntity.setDescription(hisProcessInstance.getDescription());
        instanceEntity.setStartTime(hisProcessInstance.getStartTime());
        instanceEntity.setEndTime(hisProcessInstance.getEndTime());
        instanceEntity.setDeploymentId(hisProcessInstance.getDeploymentId());
        instanceEntity.setTenantId(hisProcessInstance.getTenantId());
        instanceEntity.setProcessVariables(hisProcessInstance.getProcessVariables());
        instanceEntity.setStartFormKey(formService.getStartFormKey(hisProcessInstance.getProcessDefinitionId()));
        instanceEntity.setInstanceId(hisProcessInstance.getId());
    }

    @Override
    public BpmProcessInstanceEntity getInstanceById(String instanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance != null) {
            BpmProcessInstanceEntity entity = new BpmProcessInstanceEntity();
            flowableInstanceToBpmEntity(processInstance, entity);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public List<BpmProcessInstanceEntity> listInstances(BpmProcessQuery query) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        if (StringUtils.isNotBlank(query.getNameLike())) {
            processInstanceQuery.processInstanceNameLike("%" + query.getNameLike() + "%");
        }
        if (StringUtils.isNotBlank(query.getTenantId())) {
            processInstanceQuery.processInstanceTenantId(query.getTenantId());
        }
        if (StringUtils.isNotBlank(query.getDefineKey())) {
            processInstanceQuery.processDefinitionKey(query.getDefineKey());
        }
        if (StringUtils.isNotBlank(query.getDefineId())) {
            processInstanceQuery.processDefinitionId(query.getDefineId());
        }
        if (StringUtils.isNotBlank(query.getCategory())) {
            processInstanceQuery.processDefinitionCategory(query.getCategory());
        }
        if (StringUtils.isNotBlank(query.getDeployId())) {
            processInstanceQuery.deploymentId(query.getDeployId());
        }
        if (StringUtils.isNotBlank(query.getProcessInstanceBusinessKey())) {
            processInstanceQuery.processInstanceBusinessKey(query.getProcessInstanceBusinessKey());
        }
        if (query.getSuspended() != null) {
            if (query.getSuspended()) {
                processInstanceQuery.suspended();
            } else {
                processInstanceQuery.active();
            }
        }
        processInstanceQuery.orderByStartTime().desc();
        List<ProcessInstance> list;
        if (query.getPageNo() != null && query.getPageSize() != null) {
            list = processInstanceQuery.listPage(query.getPageNo(), query.getPageSize());
        } else {
            list = processInstanceQuery.list();
        }
        List<BpmProcessInstanceEntity> entities = new ArrayList<>(4);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProcessInstance item : list) {
                BpmProcessInstanceEntity entity = new BpmProcessInstanceEntity();
                flowableInstanceToBpmEntity(item, entity);
                entities.add(entity);
            }
        }

        return entities;
    }

    @Override
    public List<BpmProcessInstanceEntity> listHisInstances(BpmProcessQuery query) {
        HistoricProcessInstanceQuery hisProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        if (StringUtils.isNotBlank(query.getTenantId())) {
            hisProcessInstanceQuery.processInstanceTenantId(query.getTenantId());
        }
        if (StringUtils.isNotBlank(query.getNameLike())) {
            hisProcessInstanceQuery.processInstanceNameLike("%" + query.getNameLike() + "%");
        }
        if (StringUtils.isNotBlank(query.getCategory())) {
            hisProcessInstanceQuery.processDefinitionCategory(query.getCategory());
        }
        if (StringUtils.isNotBlank(query.getDefineKey())) {
            hisProcessInstanceQuery.processDefinitionKey(query.getDefineKey());
        }
        if (StringUtils.isNotBlank(query.getDefineId())) {
            hisProcessInstanceQuery.processDefinitionId(query.getDefineId());
        }
        if (StringUtils.isNotBlank(query.getDeployId())) {
            hisProcessInstanceQuery.deploymentId(query.getDeployId());
        }
        if (StringUtils.isNotBlank(query.getProcessInstanceBusinessKey())) {
            hisProcessInstanceQuery.processInstanceBusinessKey(query.getProcessInstanceBusinessKey());
        }

        hisProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
        List<HistoricProcessInstance> list;
        if (query.getPageNo() != null && query.getPageSize() != null) {
            list = hisProcessInstanceQuery.listPage(query.getPageNo(), query.getPageSize());
        } else {
            list = hisProcessInstanceQuery.list();
        }
        List<BpmProcessInstanceEntity> entities = new ArrayList<>(4);
        if (!CollectionUtils.isEmpty(list)) {
            for (HistoricProcessInstance item : list) {
                BpmProcessInstanceEntity entity = new BpmProcessInstanceEntity();
                flowableInstanceToBpmEntity(item, entity);
                entities.add(entity);
            }
        }

        return entities;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void suspendProcessDefine(String processDefinitionId) {
        repositoryService.suspendProcessDefinitionById(processDefinitionId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void activeProcessDefin(String processDefinitionId) {
        repositoryService.activateProcessDefinitionById(processDefinitionId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void suspendProcessInstance(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void activeProcessInstance(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
    }

    @Override
    public InputStream generateStreamDiagram(BpmDirgarmQuery query) {
        String diagramFormat = query.getDiagramFormat();
        String diagramFont = query.getDiagramFont();
        List<String> activeActivityIds = new ArrayList<>();
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(query.getDefineId());
        if (StringUtils.isNotBlank(query.getInstanceId())) {
            // 判断流程是否结束
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(query
                            .getInstanceId()).singleResult();
            if (processInstance != null) {
                activeActivityIds = runtimeService.getActiveActivityIds(query.getInstanceId());
            }
        }

        if (processDefinition != null && processDefinition.hasGraphicalNotation()) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            try {
                HighlightColorContext.setHighlightColorThreadLocal(query.getHighlightColor());

                return diagramGenerator.generateDiagram(bpmnModel, diagramFormat,
                        activeActivityIds,
                        Collections.emptyList(),
                        diagramFont, diagramFont, diagramFont,
                        processEngineConfiguration.getClassLoader(), 1.0,
                        processEngineConfiguration.isDrawSequenceFlowNameWithNoLabelDI());
            } catch (Exception e) {
                logger.error("获取流程图异常 definitionId={}, instanceId={}", query.getDefineId(), query.getInstanceId(), e);
                return null;
            } finally {
                HighlightColorContext.remove();
            }
        } else {
            logger.error("获取流程图异常, 流程不存在 definitionId={}, instanceId={}", query.getDefineId(), query.getInstanceId());
            return null;
        }
    }

    @Override
    public String generateDiagram(BpmDirgarmQuery query) {
        InputStream resource = generateStreamDiagram(query);
        Base64.Encoder encoder = Base64.getEncoder();
        if (resource != null) {
            try {
                return encoder.encodeToString(readToBytes(resource));
            } catch (IOException e) {
                logger.error("获取流程图异常 readToBytes definitionId={}, instanceId={}", query.getDefineId(), query.getInstanceId(), e);
                return "";
            }
        } else {
            return "";
        }
    }

    private byte[] readToBytes(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Override
    public List<BpmTaskMinModelEntity> simpleListUserTaskModels(BpmTaskModelQuery query) {
        if (StringUtils.isBlank(query.getDefineId())) {
            BpmBusinessException.createByErrorMsg("param defineId is null");
        }

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(query.getDefineId()).singleResult();

        if (processDefinition == null) {
            return new ArrayList<>(2);
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        Process process = bpmnModel.getProcesses().get(0);
        return queryMinUserTasks(query, process, processDefinition);
    }

    private List<BpmTaskMinModelEntity> queryMinUserTasks(
            BpmTaskModelQuery query, Process process, ProcessDefinition processDefinition) {
        List<BpmTaskMinModelEntity> list = new ArrayList<>(8);
        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
        for (UserTask task : userTasks) {
            if (StringUtils.isNotBlank(query.getCategory()) && !task.getCategory().equals(query.getCategory())) {
                continue;
            }
            if (StringUtils.isNotBlank(query.getTaskDefKey()) && !task.getId().equals(query.getTaskDefKey())) {
                continue;
            }

            BpmTaskMinModelEntity userTaskModelEntity = new BpmTaskModelEntity();
            userTaskModelEntity.setTenantId(processDefinition.getTenantId());
            userTaskModelEntity.setProcDefId(processDefinition.getId());
            userTaskModelEntity.setTaskDefKey(task.getId());
            userTaskModelEntity.setTaskName(task.getName());
            userTaskModelEntity.setAssignee(task.getAssignee());
            userTaskModelEntity.setCategory(task.getCategory());
            userTaskModelEntity.setFormKey(task.getFormKey());
            userTaskModelEntity.setSkipExpression(task.getSkipExpression());
            userTaskModelEntity.setHasMultiInstance(task.hasMultiInstanceLoopCharacteristics());
            if (task.hasMultiInstanceLoopCharacteristics()) {
                userTaskModelEntity.setSequential(task.getLoopCharacteristics().isSequential());
            } else {
                userTaskModelEntity.setSequential(false);
            }

            list.add(userTaskModelEntity);
        }


        return list;
    }

    @Override
    public UserTaskModelDTO getUserTaskModelDto(BpmTaskModelQuery query) {
        UserTaskModelDTO dto = new UserTaskModelDTO();
        if (StringUtils.isBlank(query.getDefineId())) {
            BpmBusinessException.createByErrorMsg("param defineId is null");
        }
        List<BpmTaskModelEntity> resultUserTaskModels = new ArrayList<>(4);
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(query.getDefineId()).singleResult();
        if (processDefinition == null) {
            return dto;
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        Process process = bpmnModel.getProcesses().get(0);
        dto.setProcess(process);
        // 先查询出所有符合条件的用户任务
        List<BpmTaskMinModelEntity> allUserTasks = queryMinUserTasks(query, process, processDefinition);
        if (CollectionUtils.isEmpty(allUserTasks)) {
            return dto;
        }

        // 查询出所有并行网关中的用户任务
        Map<String, ParallelGatwayDTO> forkGatewayMap = getAllParallelGatewayUserTask(query, processDefinition);

        // 并行网关的用户任务节点
        Map<String, BpmTaskModelEntity> pGatewayUserTaskModelsMap = new LinkedHashMap<>(4);
        forkGatewayMap.forEach((k, v) -> pGatewayUserTaskModelsMap.putAll(v.getUserTaskModels()));

        // 非并行网关的用户任务节点
        List<BpmTaskModelEntity> aloneUserTaskModels = new ArrayList<>(4);
        allUserTasks.stream()
                .filter(item -> !pGatewayUserTaskModelsMap.containsKey(item.getTaskDefKey()))
                .collect(Collectors.toList())
                .forEach(item -> aloneUserTaskModels.add((BpmTaskModelEntity) item));

        // 合并两个结果集
        resultUserTaskModels.addAll(aloneUserTaskModels);
        resultUserTaskModels.addAll(new ArrayList<>(pGatewayUserTaskModelsMap.values()));
        dto.setAllUserTaskModels(resultUserTaskModels);
        dto.setAllForkGatewayMap(forkGatewayMap);
        return dto;
    }

    @Override
    public List<BpmTaskModelEntity> listUserTaskModels(BpmTaskModelQuery query) {
        return getUserTaskModelDto(query).getAllUserTaskModels();
    }

    /**
     * 获取所有并行网关内的节点 和 并行网关之间的关系
     */
    protected Map<String, ParallelGatwayDTO> getAllParallelGatewayUserTask(
            BpmTaskModelQuery limitQuery, ProcessDefinition processDefinition) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        Process process = bpmnModel.getProcesses().get(0);
        Map<String, FlowElement> flowElementMap = process.getFlowElementMap();

        List<ParallelGateway> parallelGateways = process.findFlowElementsOfType(ParallelGateway.class);
        List<InclusiveGateway> inclusiveGateways = process.findFlowElementsOfType(InclusiveGateway.class);

        List<Gateway> allParallelGateways = new ArrayList<>(4);
        allParallelGateways.addAll(parallelGateways);
        allParallelGateways.addAll(inclusiveGateways);


        Map<String, ParallelGatwayDTO> forkGatewayMap = new HashMap<>(4);

        for (Gateway gateway : allParallelGateways) {
            int outgoingFlowsSize = gateway.getOutgoingFlows().size();
            // 从 fork网关节点开始查找
            if (outgoingFlowsSize > 1 && !forkGatewayMap.containsKey(gateway.getId())) {
                ParallelGatwayDTO dto = new ParallelGatwayDTO();
                dto.setForkSize(outgoingFlowsSize);
                dto.setForkId(gateway.getId());
                dto.setTenantId(processDefinition.getTenantId());
                dto.setProcDefId(processDefinition.getId());
                forkGatewayMap.put(gateway.getId(), dto);

                loopForkParallelGateway(limitQuery, dto, gateway.getOutgoingFlows(), forkGatewayMap, flowElementMap);
            }
        }

        // 设置一些并行网关附加信息，用于跳转业务逻辑的判定
        forkGatewayMap.forEach((k, v) -> {
            Set<String> childForkParallelGatwayIds = new HashSet<>(2);
            getChildForkParallelGatwayIds(v.getChildParallelGatways(), childForkParallelGatwayIds);
            v.getUserTaskModels().forEach((k1, v1) -> v1.setChildForkParallelGatewayIds(childForkParallelGatwayIds));
        });
        return forkGatewayMap;
    }

    private void getChildForkParallelGatwayIds(List<ParallelGatwayDTO> childGws, Set<String> childForkParallelGatwayIds) {
        if (!CollectionUtils.isEmpty(childGws)) {
            for (ParallelGatwayDTO item : childGws) {
                childForkParallelGatwayIds.add(item.getForkId());

                getChildForkParallelGatwayIds(item.getChildParallelGatways(), childForkParallelGatwayIds);
            }
        }
    }

    private void convorBpmTaskModelEntity(BpmTaskModelEntity userTaskModelEntity,
                                          ParallelGatwayDTO dto,
                                          UserTask task) {
        userTaskModelEntity.setTenantId(dto.getTenantId());
        userTaskModelEntity.setProcDefId(dto.getProcDefId());
        userTaskModelEntity.setTaskDefKey(task.getId());
        userTaskModelEntity.setTaskName(task.getName());
        userTaskModelEntity.setAssignee(task.getAssignee());
        userTaskModelEntity.setCategory(task.getCategory());
        userTaskModelEntity.setFormKey(task.getFormKey());
        userTaskModelEntity.setInParallelGateway(true);
        userTaskModelEntity.setParallelGatewayForkRef(dto.getTmpForkRef());
        userTaskModelEntity.setForkParallelGatewayId(dto.getForkId());
        userTaskModelEntity.setHasMultiInstance(task.hasMultiInstanceLoopCharacteristics());
        userTaskModelEntity.setSkipExpression(task.getSkipExpression());
        if (task.hasMultiInstanceLoopCharacteristics()) {
            userTaskModelEntity.setSequential(task.getLoopCharacteristics().isSequential());
        } else {
            userTaskModelEntity.setSequential(false);
        }
    }

    /**
     * 递归遍历所有并行分支上的
     *
     * @param limitQuery     查询限制
     * @param dto            ParallelGatwayDTO
     * @param outgoingFlows  List<SequenceFlow>
     * @param forkGatewayMap Map<String, ParallelGatwayDTO>
     * @param flowElementMap Map<String, FlowElement>
     */
    private void loopForkParallelGateway(
            BpmTaskModelQuery limitQuery, ParallelGatwayDTO dto, List<SequenceFlow> outgoingFlows,
            Map<String, ParallelGatwayDTO> forkGatewayMap, Map<String, FlowElement> flowElementMap) {
        if (CollectionUtils.isEmpty(outgoingFlows)) {
            return;
        }
        for (SequenceFlow item : outgoingFlows) {
            FlowElement refFlowElement = flowElementMap.get(item.getSourceRef());
            FlowElement targetFlowElement = flowElementMap.get(item.getTargetRef());
            // 设置当前查询的哪一个分支线
            if (refFlowElement instanceof ParallelGateway || refFlowElement instanceof InclusiveGateway) {
                dto.setTmpForkRef(item.getTargetRef());
            }
            if (targetFlowElement instanceof UserTask) {
                UserTask task = (UserTask) targetFlowElement;
                boolean eligible = true;
                if (dto.getUserTaskModels().containsKey(task.getId())) {
                    eligible = false;
                }
                if (StringUtils.isNotBlank(limitQuery.getCategory()) && !task.getCategory().equals(limitQuery.getCategory())) {
                    eligible = false;
                }
                if (StringUtils.isNotBlank(limitQuery.getTaskDefKey()) && !task.getId().equals(limitQuery.getTaskDefKey())) {
                    eligible = false;
                }
                if (eligible) {
                    BpmTaskModelEntity userTaskModelEntity = new BpmTaskModelEntity();
                    // 设置 BpmTaskModelEntity 值
                    convorBpmTaskModelEntity(userTaskModelEntity, dto, task);
                    dto.getUserTaskModels().put(task.getId(), userTaskModelEntity);
                }
                // 递归取下面的节点
                loopForkParallelGateway(limitQuery, dto, ((FlowNode) targetFlowElement).getOutgoingFlows(), forkGatewayMap, flowElementMap);
            }
            if (targetFlowElement instanceof ParallelGateway || targetFlowElement instanceof InclusiveGateway) {
                Gateway gateway = (Gateway) targetFlowElement;
                // 遇到新的并行网关节点
                // 从 fork网关节点开始查找
                if (gateway.getOutgoingFlows().size() > 1) {
                    ParallelGatwayDTO childDto = forkGatewayMap.get(gateway.getId());
                    if (childDto == null) {
                        childDto = new ParallelGatwayDTO();
                        childDto.setTenantId(dto.getTenantId());
                        childDto.setProcDefId(dto.getProcDefId());
                    }
                    childDto.setForkSize(gateway.getOutgoingFlows().size());
                    childDto.setForkId(targetFlowElement.getId());
                    dto.getChildParallelGatways().add(childDto);
                    childDto.setParentParallelGatwayDTO(dto);
                    forkGatewayMap.put(targetFlowElement.getId(), childDto);
                    // 递归取下面的节点
                    loopForkParallelGateway(limitQuery, childDto, gateway.getOutgoingFlows(), forkGatewayMap, flowElementMap);
                } else if (gateway.getIncomingFlows().size() > 1 && gateway.getOutgoingFlows().size() == 1) {
                    // 遇到新的join类型的并行网关节点，此时dto为前面与之对应的fork并行网关节点
                    dto.setJoinId(gateway.getId());
                    dto.getUserTaskModels().forEach((k, v) -> v.setJoinParallelGatewayId(gateway.getId()));

                    if (dto.getParentParallelGatwayDTO() == null) {
                        // 本并行网关里面的用户任务递归完毕
                        break;
                    }
                    // 继续父并行网关的递归取
                    loopForkParallelGateway(limitQuery, dto.getParentParallelGatwayDTO(), gateway.getOutgoingFlows(), forkGatewayMap, flowElementMap);
                }
            }
        }
    }
}
