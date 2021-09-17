package com.guzt.starter.bpm2.pojo.form;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

/**
 * 部署流程
 *
 * @author <a href="mailto:guzhongtaoocp@126.com">guzhongtao</a>
 */
public class BpmDeployBytesForm extends BpmBaseParam {

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
    byte[] deployBytes;

    public String getDeployResourceName() {
        return deployResourceName;
    }

    public void setDeployResourceName(String deployResourceName) {
        this.deployResourceName = deployResourceName;
    }

    public byte[] getDeployBytes() {
        return deployBytes;
    }

    public void setDeployBytes(byte[] deployBytes) {
        this.deployBytes = deployBytes;
    }
}
