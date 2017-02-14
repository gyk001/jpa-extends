package cn.guoyukun.spring.jpa.repository.support.annotation;

import java.lang.annotation.*;

/**
 * 开启查询缓存
 *
 * @author guoyukun
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableQueryCache {

    boolean value() default true;

}
