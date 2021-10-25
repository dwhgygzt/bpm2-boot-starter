package com.guzt.starter.bpm2.util;

import cn.hutool.core.collection.CollectionUtil;
import com.guzt.starter.bpm2.pojo.entity.BpmTaskMinModelEntity;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.UserTask;

import java.util.List;
import java.util.Map;

/**
 * 扩展属性赋值
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class UserTaskAttrUtil {
    public static final String NODE_TYPE_KEY = "nodetype";
    public static final String REVOKE_FLAG_KEY = "revokeflag";
    public static final String END_FLAG_KEY = "endflag";
    public static final String DUEDATE_FLAG_KEY = "duedatedefinition";

    public static String getAttr(UserTask task, String attrKey) {
        Map<String, List<ExtensionAttribute>> attributes = task.getAttributes();
        if (CollectionUtil.isNotEmpty(attributes)) {
            List<ExtensionAttribute> att1 = attributes.get(attrKey);
            return att1.get(0).getValue();
        } else {
            return null;
        }
    }

    public static void setAttr(BpmTaskMinModelEntity entity, UserTask task) {
        Map<String, List<ExtensionAttribute>> attributes = task.getAttributes();

        List<ExtensionAttribute> att1 = attributes.get(NODE_TYPE_KEY);
        if (CollectionUtil.isNotEmpty(att1)) {
            entity.setAttrNodetype(att1.get(0).getValue());
        }

        List<ExtensionAttribute> att2 = attributes.get(REVOKE_FLAG_KEY);
        if (CollectionUtil.isNotEmpty(att2)) {
            entity.setAttrRevokeflag(Boolean.valueOf(att2.get(0).getValue()));
        }

        List<ExtensionAttribute> att3 = attributes.get(END_FLAG_KEY);
        if (CollectionUtil.isNotEmpty(att3)) {
            entity.setAttrEndflag(Boolean.valueOf(att3.get(0).getValue()));
        }

        List<ExtensionAttribute> att4 = attributes.get(DUEDATE_FLAG_KEY);
        if (CollectionUtil.isNotEmpty(att4)) {
            entity.setDuedatedefinition(att4.get(0).getValue());
        }
    }
}
