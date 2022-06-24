package com.atme.utils.my.aspect.model.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ExcelOutLog {

    /**
     * 日志描述信息
     *
     * @return
     */
    String description() default "";

}