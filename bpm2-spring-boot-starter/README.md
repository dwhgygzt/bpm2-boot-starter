## 简介

公司开发业务中很多关于工作流的场景，因此开发本starter用于统一和简化开发人员的工作量，本starter基于
flowable 6.4.2 进行开发封装。

flowable 6.4.2 本身具有spring-boot-starter 为什么还要进行封装？ 
主要考虑到最低限度的依赖引入，另外将公共接口抽出有利于升级或变更流程引擎。

flowable 6.5.0 开始部分功能已经商业化了，为了稳妥起见，因此暂选用flowable 6.4.2

## 特点
- 封装整理了常用的流程操作接口（查看流程定义、启动流程）
- 封装整理常用的任务操作接口（提交、跳转、驳回到并行网关中的某几条分支）
- 暂时未支持子流程，由于日常开发很少用到这块功能

## 用法
1. pom.xml 文件引入如下配置

```xml
<!-- 对象存储通用接口 -->
<dependency>
    <groupId>com.guzt</groupId>
    <artifactId>bpm2-spring-boot-starter</artifactId>
    <version>最新版本号</version>
</dependency>

```


2. application.yml 增加如下配置，假设简单配置如下：

```yaml

##########################redisclent##############################
guzt:
  bpm2:
    new-process-engine-config: true
    database-schema-update: true
    async-executor-activate: false
##################################################################

```

**说明**
   - 参数 async-executor-activate
   
    true: 业务系统当前没有任何flowable相关的配置，引入本配置将新配置一个流程引擎
    
    false: 业务系统本身已经有flowable相关的配置（例如：flowable-spring-boot-starter），不想重复配置流程引擎
   
   - 当 async-executor-activate 为fasle
   
   new-process-engine-config 和 database-schema-update 无需配置

3. 业务代码中引入service

```java

@Service
public class MyBusinessServiceImpl {
    // 用户任务相关接口
    @Resource
    private BpmUserTaskService bpmUserTaskService;

    // 流程部署查询操作接口
    @Resource
    private BpmProcessService bpmProcessService;

}

```

## 用户任务接口预览

```java

package com.guzt.starter.bpm2.service;

import com.guzt.starter.bpm2.pojo.dto.MultiInstanceUserDTO;
import com.guzt.starter.bpm2.pojo.entity.BpmTaskEntity;
import com.guzt.starter.bpm2.pojo.entity.BpmTaskModelEntity;
import com.guzt.starter.bpm2.pojo.form.BpmCommitForm;
import com.guzt.starter.bpm2.pojo.form.BpmJumpForm;
import com.guzt.starter.bpm2.pojo.form.BpmMultInstAddForm;
import com.guzt.starter.bpm2.pojo.form.BpmMultInstDeleteForm;
import com.guzt.starter.bpm2.pojo.query.BpmBackTaskModelQuery;
import com.guzt.starter.bpm2.pojo.query.BpmTaskQuery;

import java.util.List;
import java.util.Set;

/**
 * 流程中的任务管理
 *
 * @author <a href="mailto:xxx@163.com">bbb</a>
 */
public interface BpmUserTaskService {

    /**
     * 查询待处理任务
     *
     * @param query ignore
     * @return ignore
     */
    List<BpmTaskEntity> listTodoTask(BpmTaskQuery query);

    /**
     * 查询待处理的任务对应的实例id集合，用于业务自定义逻辑查询.
     * 例如  select * from  t_my_business_order  a where a.processInstanceId in (xxx,bbb,ccc) order by create_time desc;
     *
     * @param query ignore
     * @return ignore
     */
    Set<String> listTodoInstanceIds(BpmTaskQuery query);

    /**
     * 根据id查询在做的用户任务
     *
     * @param taskId ignore
     * @return BpmTaskEntity
     */
    BpmTaskEntity getTodoTaskById(String taskId);

    /**
     * 多实例-加签
     *
     * @param form ignore
     */
    void addMultiInstanceExecution(BpmMultInstAddForm form);

    /**
     * 多实例-减签
     *
     * @param form ignore
     */
    void deleteMultiInstanceExecution(BpmMultInstDeleteForm form);

    /**
     * 获取当前多实例任务除自己外的其他用户信息列表，
     * 用于 减签 业务场景
     *
     * @param multiInstanceTaskId 当前多实例用户任务id
     * @return List
     */
    List<MultiInstanceUserDTO> getOtherMultiInstanceUsers(String multiInstanceTaskId);

    /**
     * 提交任务
     *
     * @param form ignore
     */
    void commit(BpmCommitForm form);

    /**
     * 申领任务
     *
     * @param taskId   任务id
     * @param assignee 申领人，一般为业务系统中的用户名
     */
    void claim(String taskId, String assignee);

    /**
     * 退领任务
     *
     * @param taskId 任务id
     */
    void unClaim(String taskId);

    /**
     * 转办任务
     *
     * @param taskId   任务id
     * @param assignee 转办给谁，一般为业务系统中的用户名
     */
    void transfer(String taskId, String assignee);

    /**
     * 【本任务节点】的上一步【历史任务节点】列表
     *
     * @param query ignore
     * @return ignore
     */
    List<BpmTaskModelEntity> listBackTaskModel(BpmBackTaskModelQuery query);

    /**
     * 【本任务节点】之前的【历史任务节点】列表
     *
     * @param query ignore
     * @return ignore
     */
    List<BpmTaskModelEntity> listAllBackTaskModel(BpmBackTaskModelQuery query);

    /**
     * 【本任务节点】跳转指定的【任务节点】 例如驳回等操作，前跳后跳等操作
     * <p>
     * A（本任务节点）    B（目标任务节点集合）
     * <p>
     * （1）如果B有多个节点
     * 必须为同一个并行网关内的任务节点（网关开始、合并节点必须一致）
     * 必须不是同一条流程线上的任务节点
     * <p>
     * （2）如果A和B为同一条顺序流程线上，则可以直接跳转
     * <p>
     * （3）如果A非并行分支上的任务节点
     * B是为并行网关上节点，需要创建其B所在并行网关内其他任务节点已完成日志
     * <p>
     * （4）如果A是并行分支上的任务节点
     * <p>
     * 4.1 从外向里面跳转（父并网关 》子并网关）
     * B是为并行网关上节点，需要创建其B所在并行网关内其他任务节点已完成日志
     * <p>
     * <p>
     * 4.2 从里向外面跳转 （子并网关 》父并网关 【或】 非并行网关上的节点 【或】 其他非父子关系的并行网关节点）
     * 需要清除本任务节点并行网关上（包括父网关）所有的其他未完成的用户任务
     * B是为并行网关上节点，需要创建其B所在并行网关内其他任务节点已完成日志
     *
     * @param form ignore
     */
    void jump(BpmJumpForm form);


    /**
     * 查询历史任务节点
     *
     * @param query ignore
     * @return ignore
     */
    List<BpmTaskEntity> listHisTodoTask(BpmTaskQuery query);

}

```






