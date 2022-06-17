package com.atme.utils.result.exception;


import com.atme.utils.result.EnumMsg;

/**
 * 基础异常.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 3565011388045342796L;

    private int status;

    private Object[] args;

    public BaseException() {
        this(-1);
    }

    public BaseException(int status) {
        this(status, null);
    }

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public BaseException(EnumMsg<Integer> resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getMsg() {
        return getMessage();
    }
}
