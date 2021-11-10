package com.guzt.starter.bpm2.pojo.form;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

/**
 * 部署流程
 *
 * @author guzt
 */
public class BpmDeployTextForm extends BpmBaseParam {

    private static final long serialVersionUID = 1L;

    /**
     * 部署资源名称（例如 bpmn2.xml 文件名）
     */
    String deployResourceName;

    /**
     * 资源信息，下面的几种全部转换为Bytes进行部署了
     * DeploymentBuilder addInputStream(String resourceName, InputStream inputStream);
     * <p>
     * DeploymentBuilder addClasspathResource(String resource);
     * <p>
     * DeploymentBuilder addString(String resourceName, String text);
     * <p>
     * DeploymentBuilder addBytes(String resourceName, byte[] bytes);
     */
    String text;

    /**
     * 流程类别
     */
    String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeployResourceName() {
        return deployResourceName;
    }

    public void setDeployResourceName(String deployResourceName) {
        this.deployResourceName = deployResourceName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
