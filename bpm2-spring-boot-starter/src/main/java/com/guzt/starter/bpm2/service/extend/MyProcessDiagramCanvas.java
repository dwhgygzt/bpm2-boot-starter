package com.guzt.starter.bpm2.service.extend;

import com.guzt.starter.bpm2.pojo.context.HighlightColorContext;
import org.flowable.image.impl.DefaultProcessDiagramCanvas;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * 扩展 DefaultProcessDiagramGenerator
 *
 * @author guzt
 */
public class MyProcessDiagramCanvas extends DefaultProcessDiagramCanvas {

    public MyProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType,
                                  String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
        super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
    }

    public MyProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType) {
        super(width, height, minX, minY, imageType);
    }


    /**
     * 复制 DefaultProcessDiagramCanvas中drawHighLight方法
     *
     * @param x      ignore
     * @param y      ignore
     * @param width  ignore
     * @param height ignore
     * @see DefaultProcessDiagramCanvas#drawHighLight(int, int, int, int)
     */
    @Override
    public void drawHighLight(int x, int y, int width, int height) {
        Paint originalPaint = g.getPaint();
        Stroke originalStroke = g.getStroke();

        g.setPaint(HighlightColorContext.getHighlightColorThreadLocal());
        g.setStroke(THICK_TASK_BORDER_STROKE);

        RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
        g.draw(rect);

        g.setPaint(originalPaint);
        g.setStroke(originalStroke);
    }

}
