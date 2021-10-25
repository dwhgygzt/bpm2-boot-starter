package com.guzt.starter.bpm2.config;

import com.guzt.starter.bpm2.properties.Bpn2Properties;
import com.guzt.starter.bpm2.service.BpmProcessService;
import com.guzt.starter.bpm2.service.BpmUserTaskService;
import com.guzt.starter.bpm2.service.extend.MyProcessDiagramGenerator;
import com.guzt.starter.bpm2.service.impl.FlowableBpmProcessServiceImpl;
import com.guzt.starter.bpm2.service.impl.FlowableBpmUserTaskServiceImpl;
import org.flowable.engine.*;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * BPM 相关配置
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
@Configuration
@EnableConfigurationProperties({Bpn2Properties.class})
public class Bpm2AutoConfig {


    @Configuration
    @ConditionalOnProperty(prefix = "middol.bpm2", value = "new-process-engine-config", havingValue = "false")
    public static class ExistsSpringBootStarter {
        private static final Logger logger = LoggerFactory.getLogger(ExistsSpringBootStarter.class);


        @Autowired
        private ProcessEngine processEngine;

        @Autowired
        private RepositoryService repositoryService;

        @Autowired
        private RuntimeService runtimeService;

        @Autowired
        private FormService formService;

        @Autowired
        private HistoryService historyService;

        @Autowired
        private ProcessEngineConfiguration processEngineConfiguration;

        @Autowired
        private TaskService taskService;

        @Autowired
        private ManagementService managementService;

        @Bean
        @ConditionalOnMissingBean
        public BpmProcessService bpmProcessService() {
            FlowableBpmProcessServiceImpl processService = new FlowableBpmProcessServiceImpl();

            processService.setProcessEngine(processEngine);
            processService.setRepositoryService(repositoryService);
            processService.setRuntimeService(runtimeService);
            processService.setFormService(formService);
            processService.setProcessEngineConfiguration(processEngineConfiguration);
            processService.setHistoryService(historyService);
            logger.info("【BPM2】创建 bpmProcessService springboot 完成...");
            return processService;
        }

        @Bean
        @ConditionalOnMissingBean
        @DependsOn("bpmProcessService")
        public BpmUserTaskService bpmUserTaskService() {

            processEngineConfiguration.setProcessDiagramGenerator(new MyProcessDiagramGenerator());

            FlowableBpmUserTaskServiceImpl bpmUserTaskService = new FlowableBpmUserTaskServiceImpl();
            bpmUserTaskService.setTaskService(taskService);
            bpmUserTaskService.setRuntimeService(runtimeService);
            bpmUserTaskService.setHistoryService(historyService);
            bpmUserTaskService.setRepositoryService(repositoryService);
            bpmUserTaskService.setBpmProcessService(bpmProcessService());
            bpmUserTaskService.setProcessEngineConfiguration(processEngineConfiguration);
            bpmUserTaskService.setManagementService(managementService);
            logger.info("【BPM2】创建 bpmUserTaskService springboot 完成...");
            return bpmUserTaskService;
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "middol.bpm2", value = "new-process-engine-config", havingValue = "true")
    public static class NotExistsSpringBootStarter {

        private static final Logger logger = LoggerFactory.getLogger(ExistsSpringBootStarter.class);


        @Resource
        private Bpn2Properties bpn2Properties;

        @Resource
        private DataSource dataSource;

        @Resource
        private PlatformTransactionManager platformTransactionManager;

        @Bean
        @ConditionalOnMissingBean
        public ProcessEngine bpmProcessEngine() {
            SpringProcessEngineConfiguration cfg = (SpringProcessEngineConfiguration) new SpringProcessEngineConfiguration()
                    .setDataSource(dataSource)
                    .setDatabaseSchemaUpdate(bpn2Properties.getDatabaseSchemaUpdate())
                    .setAsyncExecutorActivate(bpn2Properties.getAsyncExecutorActivate());
            cfg.setTransactionManager(platformTransactionManager);
            return cfg.buildProcessEngine();
        }

        @Bean
        @ConditionalOnMissingBean
        public RuntimeService bpmRuntimeService() {
            return bpmProcessEngine().getRuntimeService();
        }

        @Bean
        @ConditionalOnMissingBean
        public IdentityService bpmIdentityService() {
            return bpmProcessEngine().getIdentityService();
        }

        @Bean
        @ConditionalOnMissingBean
        public TaskService bpmTaskService() {
            return bpmProcessEngine().getTaskService();
        }

        @Bean
        @ConditionalOnMissingBean
        public ManagementService bpmManagementService() {
            return bpmProcessEngine().getManagementService();
        }

        @Bean
        @ConditionalOnMissingBean
        public RepositoryService bpmRepositoryService() {
            return bpmProcessEngine().getRepositoryService();
        }

        @Bean
        @ConditionalOnMissingBean
        public HistoryService bpmHistoryService() {
            return bpmProcessEngine().getHistoryService();
        }

        @Bean
        @ConditionalOnMissingBean
        public FormService bpmFormService() {
            return bpmProcessEngine().getFormService();
        }

        @Bean
        @ConditionalOnMissingBean
        public DynamicBpmnService bpmDynamicBpmnService() {
            return bpmProcessEngine().getDynamicBpmnService();
        }

        @Bean
        @ConditionalOnMissingBean
        public ProcessEngineConfiguration bpmProcessEngineConfiguration() {
            ProcessEngineConfiguration processEngineConfiguration = bpmProcessEngine().getProcessEngineConfiguration();
            processEngineConfiguration.setProcessDiagramGenerator(new MyProcessDiagramGenerator());
            return processEngineConfiguration;
        }

        @Bean
        @ConditionalOnMissingBean
        public BpmProcessService bpmProcessService() {
            FlowableBpmProcessServiceImpl processService = new FlowableBpmProcessServiceImpl();
            processService.setProcessEngine(bpmProcessEngine());
            processService.setRepositoryService(bpmRepositoryService());
            processService.setRuntimeService(bpmRuntimeService());
            processService.setFormService(bpmFormService());
            processService.setProcessEngineConfiguration(bpmProcessEngineConfiguration());
            processService.setHistoryService(bpmHistoryService());
            logger.info("【BPM2】创建 bpmProcessService 完成...");
            return processService;
        }

        @Bean
        @ConditionalOnMissingBean
        @DependsOn("bpmProcessService")
        public BpmUserTaskService bpmUserTaskService() {
            FlowableBpmUserTaskServiceImpl bpmUserTaskService = new FlowableBpmUserTaskServiceImpl();
            bpmUserTaskService.setTaskService(bpmTaskService());
            bpmUserTaskService.setRuntimeService(bpmRuntimeService());
            bpmUserTaskService.setHistoryService(bpmHistoryService());
            bpmUserTaskService.setRepositoryService(bpmRepositoryService());
            bpmUserTaskService.setBpmProcessService(bpmProcessService());
            bpmUserTaskService.setProcessEngineConfiguration(bpmProcessEngineConfiguration());
            bpmUserTaskService.setManagementService(bpmManagementService());
            logger.info("【BPM2】创建 bpmUserTaskService 完成...");
            return bpmUserTaskService;
        }
    }


}
