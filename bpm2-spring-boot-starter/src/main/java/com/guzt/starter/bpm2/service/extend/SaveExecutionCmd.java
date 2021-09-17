package com.guzt.starter.bpm2.service.extend;

import com.guzt.starter.bpm2.exception.BpmBusinessException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.idm.engine.impl.util.CommandContextUtil;

import java.io.Serializable;

/**
 * @author guzt
 */
public class SaveExecutionCmd implements Command<Void>, Serializable {
    private static final long serialVersionUID = 1L;
    protected ExecutionEntity entity;

    public SaveExecutionCmd(ExecutionEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        if (this.entity == null) {
            BpmBusinessException.createByErrorMsg("executionEntity is null");
        } else {
            System.out.println("执行 SaveExecutionCmd---" + entity.getId());
            CommandContextUtil.getDbSqlSession(commandContext).insert(this.entity);
        }
        return null;
    }
}

