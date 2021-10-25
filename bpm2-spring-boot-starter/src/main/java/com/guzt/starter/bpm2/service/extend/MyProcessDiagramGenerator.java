package com.guzt.starter.bpm2.service.extend;

import cn.hutool.core.util.ReflectUtil;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.image.impl.DefaultProcessDiagramCanvas;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;

import java.util.List;

/**
 * 扩展 DefaultProcessDiagramGenerator
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class MyProcessDiagramGenerator extends DefaultProcessDiagramGenerator {

    @Override
    protected DefaultProcessDiagramCanvas generateProcessDiagram(BpmnModel bpmnModel, String imageType,
                                                                 List<String> highLightedActivities,
                                                                 List<String> highLightedFlows,
                                                                 String activityFontName, String labelFontName, String annotationFontName,
                                                                 ClassLoader customClassLoader, double scaleFactor,
                                                                 boolean drawsequenceflownamewithnolabeldi) {

        prepareBpmnModel(bpmnModel);

        MyProcessDiagramCanvas processDiagramCanvas = this.initMyProcessDiagramCanvas(
                bpmnModel, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);

        // Draw pool shape, if process is participant in collaboration
        for (Pool pool : bpmnModel.getPools()) {
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo, scaleFactor);
        }

        // Draw lanes
        for (Process process : bpmnModel.getProcesses()) {
            for (Lane lane : process.getLanes()) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
                processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo, scaleFactor);
            }
        }

        // Draw activities and their sequence-flows
        for (Process process : bpmnModel.getProcesses()) {
            for (FlowNode flowNode : process.findFlowElementsOfType(FlowNode.class)) {
                if (!isPartOfCollapsedSubProcess(flowNode, bpmnModel)) {
                    drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlows, scaleFactor, drawsequenceflownamewithnolabeldi);
                }
            }
        }

        // Draw artifacts
        for (Process process : bpmnModel.getProcesses()) {

            for (Artifact artifact : process.getArtifacts()) {
                drawArtifact(processDiagramCanvas, bpmnModel, artifact);
            }

            List<SubProcess> subProcesses = process.findFlowElementsOfType(SubProcess.class, true);
            if (subProcesses != null) {
                for (SubProcess subProcess : subProcesses) {

                    GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(subProcess.getId());
                    if (graphicInfo != null && graphicInfo.getExpanded() != null && !graphicInfo.getExpanded()) {
                        continue;
                    }

                    if (!isPartOfCollapsedSubProcess(subProcess, bpmnModel)) {
                        for (Artifact subProcessArtifact : subProcess.getArtifacts()) {
                            drawArtifact(processDiagramCanvas, bpmnModel, subProcessArtifact);
                        }
                    }
                }
            }
        }

        return processDiagramCanvas;
    }

    /**
     * 参考 DefaultProcessDiagramGenerator 中静态方法 initProcessDiagramCanvas 的业务逻辑
     *
     * @param bpmnModel          ignore
     * @param imageType          ignore
     * @param activityFontName   ignore
     * @param labelFontName      ignore
     * @param annotationFontName ignore
     * @param customClassLoader  ignore
     * @return MyProcessDiagramCanvas
     * @see DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String, String, ClassLoader)
     */
    protected MyProcessDiagramCanvas initMyProcessDiagramCanvas(
            BpmnModel bpmnModel, String imageType, String activityFontName,
            String labelFontName, String annotationFontName, ClassLoader customClassLoader) {

        DefaultProcessDiagramCanvas processDiagramCanvas = DefaultProcessDiagramGenerator.initProcessDiagramCanvas(
                bpmnModel, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);

        int width = (int) ReflectUtil.getFieldValue(processDiagramCanvas, "canvasWidth");
        int height = (int) ReflectUtil.getFieldValue(processDiagramCanvas, "canvasHeight");
        int minX = (int) ReflectUtil.getFieldValue(processDiagramCanvas, "minX");
        int maxY = (int) ReflectUtil.getFieldValue(processDiagramCanvas, "minY");

        return new MyProcessDiagramCanvas(width, height, minX, maxY,
                imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
    }
}
