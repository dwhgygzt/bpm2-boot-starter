package com.guzt.starter.bpm2.exception;

/**
 * 业务类异常
 *
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
public class BpmBusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String FAIL = "-1";
    public static final String FAIL_MSG = "FAIL";
    private String errorCode;
    private String errorMsg;
    private Object errorBody;

    public static void createByErrorCode(String errorCode) {
        throw new BpmBusinessException(errorCode, "FAIL");
    }

    public static void createByErrorCode(String errorCode, Object errorBody) {
        throw new BpmBusinessException(errorCode, "FAIL", errorBody);
    }

    public static void createByErrorMsg(String errorMsg) {
        throw new BpmBusinessException("-1", errorMsg);
    }

    public static void createByErrorMsg(String errorMsg, Object errorBody) {
        throw new BpmBusinessException("-1", errorMsg, errorBody);
    }

    public static void create(String errorCode, String errorMsg) {
        throw new BpmBusinessException(errorCode, errorMsg);
    }

    public static void create(String errorCode, String errorMsg, Object errorBody) {
        throw new BpmBusinessException(errorCode, errorMsg, errorBody);
    }

    public BpmBusinessException() {
    }

    public BpmBusinessException(String errorCode, String errorMsg) {
        super("errorCode=" + errorCode + ", errorMsg=" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BpmBusinessException(String errorCode, String errorMsg, Object errorBody) {
        super("errorCode=" + errorCode + ", errorMsg=" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorBody = errorBody;
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getErrorBody() {
        return this.errorBody;
    }

    public void setErrorBody(Object errorBody) {
        this.errorBody = errorBody;
    }
}
