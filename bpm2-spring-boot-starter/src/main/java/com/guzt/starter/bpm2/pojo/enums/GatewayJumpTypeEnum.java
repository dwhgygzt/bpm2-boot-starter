package com.guzt.starter.bpm2.pojo.enums;

/**
 * A(并行网关内的任务) -> B
 *
 * @author guzt
 */
public enum GatewayJumpTypeEnum {

    // 1 TO_IN_CHILD 从外向里面跳转（父并网关 》子并网关）
    // 2 TO_OUT_NO_PARALLEL 从里向外面跳转 B为非并行行网关上的节点
    // 3 TO_OUT_PARENT 从里向外面跳转 B为父并行网关上的节点
    // 4 TO_OUT_OTHER_PARALLEL 从里向外面跳转 B为其他独立并行网关上的节点
    // NO_SUPPORT_JUMP 不支持的跳转类型

    TO_IN_CHILD, TO_OUT_NO_PARALLEL, TO_OUT_PARENT, TO_OUT_OTHER_PARALLEL, NO_SUPPORT_JUMP


}
