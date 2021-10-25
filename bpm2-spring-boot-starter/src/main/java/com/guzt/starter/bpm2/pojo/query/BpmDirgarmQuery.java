package com.guzt.starter.bpm2.pojo.query;

import com.guzt.starter.bpm2.pojo.BpmBaseParam;

import java.awt.*;

/**
 * 查询流程图
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmDirgarmQuery extends BpmBaseParam {

    /**
     * 定义id
     */
    private String defineId;

    /**
     * 实例id
     */
    private String instanceId;

    /**
     * 图片类型 png jpg
     */
    private String diagramFormat = "png";

    /**
     * 流程上的字体
     */
    private String diagramFont = "宋体";

    /**
     * 高亮节点的边框颜色，默认为绿色
     */
    private Color highlightColor = Color.GREEN;

    public Color getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }

    public String getDiagramFont() {
        return diagramFont;
    }

    public void setDiagramFont(String diagramFont) {
        this.diagramFont = diagramFont;
    }

    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    public String getDiagramFormat() {
        return diagramFormat;
    }

    public void setDiagramFormat(String diagramFormat) {
        this.diagramFormat = diagramFormat;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
