package com.guzt.starter.bpm2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * bpm2 配置文件
 *
 * @author guzt
 */
@ConfigurationProperties(prefix = "guzt.bpm2")
public class Bpn2Properties {


    /**
     * true: 没有引入 flowable-spring-boot-starter，使用本starter进行流程引擎的配置，例如自动创建 flowable表
     * false: 引入 flowable-spring-boot-starter 或 业务系统已经自己配置了流程引擎，例如 Flowable SpringBean
     */
    private Boolean newProcessEngineConfig;

    /**
     * 是否启动springboot时候就创建 flowable的表
     */
    private String databaseSchemaUpdate;

    /**
     * 是否启用异步执行器
     */
    private Boolean asyncExecutorActivate;

    public String getDatabaseSchemaUpdate() {
        return databaseSchemaUpdate;
    }

    public void setDatabaseSchemaUpdate(String databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }

    public Boolean getAsyncExecutorActivate() {
        return asyncExecutorActivate;
    }

    public void setAsyncExecutorActivate(Boolean asyncExecutorActivate) {
        this.asyncExecutorActivate = asyncExecutorActivate;
    }

    public Boolean getNewProcessEngineConfig() {
        return newProcessEngineConfig;
    }

    public void setNewProcessEngineConfig(Boolean newProcessEngineConfig) {
        this.newProcessEngineConfig = newProcessEngineConfig;
    }
}
