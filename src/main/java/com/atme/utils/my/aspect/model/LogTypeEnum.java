package com.atme.utils.my.aspect.model;


import com.atme.utils.my.aspect.model.EnumMsg;

/**
 * 日志类型枚举.
 *
 * @author S
 * @version 1.0 2020/2/21
 * @since 1.0
 */
public enum LogTypeEnum implements EnumMsg<Byte> {

    OPERATION((byte) 1, "操作日志"),
    SECURITY((byte) 2, "安全日志"),
    ;

    private Byte code;
    private String msg;

    LogTypeEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Byte getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
