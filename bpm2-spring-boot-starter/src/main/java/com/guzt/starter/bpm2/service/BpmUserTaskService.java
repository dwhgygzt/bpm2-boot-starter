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
 * @author <a href="mailto:guzhongtaoocp@126.com">guzhongtao</a>
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
