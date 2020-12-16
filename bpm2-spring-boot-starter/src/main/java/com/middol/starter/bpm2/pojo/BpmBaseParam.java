package com.middol.starter.bpm2.pojo;

import java.io.Serializable;

/**
 * BPM 基础参数
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmBaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户号
     */
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
