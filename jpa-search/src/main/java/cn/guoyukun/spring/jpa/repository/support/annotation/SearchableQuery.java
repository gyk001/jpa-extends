package cn.guoyukun.spring.jpa.repository.support.annotation;

import cn.guoyukun.spring.jpa.repository.callback.SearchCallback;

import java.lang.annotation.*;

/**
 * 覆盖默认的根据条件查询数据
 *
 * @author guoyukun
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchableQuery {

    /**
     * 覆盖默认的查询所有ql
     *
     * @return
     */
    String findAllQuery() default "";

    /**
     * 覆盖默认的统计所有ql
     *
     * @return
     */
    String countAllQuery() default "";

    /**
     * 给ql拼条件及赋值的回调类型
     *
     * @return cn.guoyukun.spring.jpa.repository.callback.SearchCallback子类
     */
    Class<? extends SearchCallback> callbackClass() default SearchCallback.class;


    QueryJoin[] joins() default {};


}
