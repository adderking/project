package com.scisdata.web.basedao.interfaces;

import java.lang.annotation.*;

/**
 * Created by fangshilei on 17/4/18.
 * 自定义注解
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**执行的操作类型,如:add操作**/
    public String operationType() default "";
    /** 执行的具体操作,如中文名字  添加用户**/
    public String operationName() default "";
}
