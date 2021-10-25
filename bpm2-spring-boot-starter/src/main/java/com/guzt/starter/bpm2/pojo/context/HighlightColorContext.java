package com.guzt.starter.bpm2.pojo.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * 操作日志id 上下文
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class HighlightColorContext implements AutoCloseable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ThreadLocal 对象
     */
    static final ThreadLocal<Color> COLOR_THREAD_LOCAL = new ThreadLocal<>();

    public HighlightColorContext() {
        super();
    }

    public HighlightColorContext(Color color) {
        COLOR_THREAD_LOCAL.set(color);
    }

    public static void setHighlightColorThreadLocal(Color id) {
        COLOR_THREAD_LOCAL.set(id);
    }

    /**
     * 必须是同一个线程里面才有可能能 get到对象，前提是你必须之前set过
     *
     * @return CurrentUserVO
     */
    public static Color getHighlightColorThreadLocal() {
        return COLOR_THREAD_LOCAL.get();
    }

    public static void remove() {
        COLOR_THREAD_LOCAL.remove();
    }

    /**
     * 一般是 借助try (resource) {...}结构.
     * <p>
     * try (var ctx = new UserContext("Bob")) {
     * // 可任意调用 UserContext.currentUser():
     * String currentUser = UserContext.currentUser();
     * }  // 在此自动调用 UserContext.close() 方法释放ThreadLocal关联对象
     *
     */
    @Override
    public void close() {
        logger.debug("HighlightColorContext remove Color...");
        COLOR_THREAD_LOCAL.remove();
    }
}
