package com.atme.utils.my.aspect.model.annotation;


import com.atme.utils.my.aspect.model.LogModeEnum;
import com.atme.utils.my.aspect.model.LogTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解.
 *
 * @author S
 * @version 1.0 2020/12/10
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 日志类型.
     *
     * @return
     */
    LogTypeEnum type() default LogTypeEnum.OPERATION;

    /**
     * 操作名.
     *
     * @return
     */
    String name();

    /**
     * 操作描述.
     *
     * @return
     */
    String desc() default "";

    /**
     * 日志模式.
     * SPRING_SECURITY模式 已登录从SPRING_SECURITY获取登录人信息.
     * NAME_PARAM模式 未登录 接口首个参数继承{@link com.speed.poseidon.param.NameParam} 根据name查询用户信息获取登录人信息.
     *
     * @return
     */
    LogModeEnum mode() default LogModeEnum.SPRING_SECURITY;
}
