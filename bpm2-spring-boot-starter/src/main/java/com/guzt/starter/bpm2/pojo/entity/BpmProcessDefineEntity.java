package com.guzt.starter.bpm2.pojo.entity;

import org.flowable.engine.RepositoryService;

/**
 * 流程定义对象
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmProcessDefineEntity extends BpmProcessBaseEty {

    private static final long serialVersionUID = 1L;

    /**
     * 流程分类名.
     * <p>
     * 取值 act_re_deployment 中的category， 即deploy时，传递的category值
     */
    private String category;

    /**
     * name of {@link RepositoryService#getResourceAsStream(String, String) the resource} of this process definition.
     */
    private String resourceName;

    /**
     * The resource name in the deployment of the diagram image (if any).
     */
    private String diagramResourceName;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }
}
