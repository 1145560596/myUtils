package com.atme.utils.use;


import com.atme.utils.result.EnumMsg;
import com.atme.utils.result.Result;
import com.atme.utils.result.exception.BaseException;

/**
 * api结果工具类.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
public class ResultUtils {

    /**
     * 结果是否成功.
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> boolean isSuccess(Result<T> result) {
        return result != null && result.isSuccess();
    }

    /**
     * 将枚举转为结果.
     *
     * @param enumMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> toResult(EnumMsg<Integer> enumMsg) {
        return toResult(enumMsg, null, (Object[]) null);
    }

    /**
     * 将枚举转为结果.
     *
     * @param enumMsg
     * @param formatArgs
     * @param <T>
     * @return
     */
    public static <T> Result<T> toResult(EnumMsg<Integer> enumMsg, Object... formatArgs) {
        return toResult(enumMsg, null, formatArgs);
    }

    /**
     * 将枚举转为结果.
     *
     * @param enumMsg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> toResult(EnumMsg<Integer> enumMsg, T data) {
        return toResult(enumMsg, data, (Object[]) null);
    }

    /**
     * 将枚举转为结果.
     *
     * @param enumMsg
     * @param data
     * @param formatArgs
     * @param <T>
     * @return
     */
    public static <T> Result<T> toResult(EnumMsg<Integer> enumMsg, T data, Object... formatArgs) {
        Result<T> result = new Result<>();
        if (enumMsg != null) {
            result.setStatus(enumMsg.getCode());
            result.setMsg(enumMsg.getMsg(formatArgs));
        }

        result.setData(data);
        return result;
    }

    /**
     * 将基础异常转为结果.
     *
     * @param baseException
     * @param <T>
     * @return
     */
    public static <T> Result<T> toResult(BaseException baseException) {
        return toResult(baseException, null);
    }

    /**
     * 将基础异常转为结果.
     *
     * @param baseException
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> toResult(BaseException baseException, T data) {
        Result<T> result = new Result<>();
        if (baseException != null) {
            result.setStatus(baseException.getStatus());
            result.setMsg(baseException.getMsg());
        }

        result.setData(data);
        return result;
    }

    /**
     * 将结果转为基础异常.
     *
     * @param enumMsg
     * @return
     */
    public static BaseException toException(EnumMsg<Integer> enumMsg) {
        return new BaseException(enumMsg.getCode(), enumMsg.getMsg());
    }
}
