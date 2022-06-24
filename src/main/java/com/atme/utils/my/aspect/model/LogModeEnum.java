package com.atme.utils.my.aspect.model;

import com.atme.utils.my.aspect.model.EnumMsg;

/**
 * 日志模式枚举.
 *
 * @author S
 * @version 1.0 2020/2/21
 * @since 1.0
 */
public enum LogModeEnum implements EnumMsg<Byte> {

    SPRING_SECURITY((byte) 1, "spring security模式"),
    NAME_PARAM((byte) 2, "name param模式"),
    ;

    private Byte code;
    private String msg;

    LogModeEnum(Byte code, String msg) {
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
