package com.atme.utils.result;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * 枚举信息.
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
public interface EnumMsg<T> extends EnumCode<T> {

    public String getMsg();

    default String getMsg(Object... formatArgs) {
        String msg = getMsg();
        if (ArrayUtils.isEmpty(formatArgs) || StringUtils.isBlank(msg)) {
            return msg;
        }

        return MessageFormat.format(msg, formatArgs);
    }
}
