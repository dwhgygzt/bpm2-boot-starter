package com.middol.starter.bpm2.pojo.query;

import com.middol.starter.bpm2.pojo.BpmBaseParam;

/**
 * 分页参数
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmBasePageQuery extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 当前第几页
     */
    private Integer pageNo;

    /**
     * 每一页的大小
     */
    private Integer pageSize;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
