package com.atme.utils.my.result.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 基础的返回枚举.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
public interface ResultCodeEnumMsg extends EnumMsg<Integer> {

    public static final CommonResultCodeEnumMsg UNKNOWN_EXCEPTION = new CommonResultCodeEnumMsg(-1, "服务器未知异常");

    public static final CommonResultCodeEnumMsg SUCCESS = new CommonResultCodeEnumMsg(0, "成功");

    public static final CommonResultCodeEnumMsg PARAM_INVALID = new CommonResultCodeEnumMsg(1, "参数不正确");

    public static final CommonResultCodeEnumMsg TOKEN_INVALID = new CommonResultCodeEnumMsg(2, "token无效");

    public static final CommonResultCodeEnumMsg USER_NOT_LOGIN = new CommonResultCodeEnumMsg(3, "用户未登陆");

    public static final CommonResultCodeEnumMsg ROLE_INVALID = new CommonResultCodeEnumMsg(4, "角色无效");

    public static final CommonResultCodeEnumMsg PASSWORD_INVALID = new CommonResultCodeEnumMsg(5, "密码错误");

    public static final CommonResultCodeEnumMsg USER_NOT_EXIST = new CommonResultCodeEnumMsg(6, "用户不存在");

    public static final CommonResultCodeEnumMsg USER_IS_FORBIDDEN = new CommonResultCodeEnumMsg(7, "用户禁止登录");

    public static final CommonResultCodeEnumMsg UN_AUTHORIZED = new CommonResultCodeEnumMsg(8, "未授权");

    public static final CommonResultCodeEnumMsg NO_PERMISSION = new CommonResultCodeEnumMsg(9, "无权限");

    public static final CommonResultCodeEnumMsg UPDATE_FAIL = new CommonResultCodeEnumMsg(10, "修改失败");

    public static final CommonResultCodeEnumMsg NO_DATA = new CommonResultCodeEnumMsg(11, "暂无数据");
    @AllArgsConstructor
    @Data
    class CommonResultCodeEnumMsg implements ResultCodeEnumMsg {
        private Integer code;
        private String msg;

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }
}