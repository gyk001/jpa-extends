package cn.guoyukun.spring.jpa.repository.callback;

import cn.guoyukun.spring.jpa.entity.search.Searchable;

import javax.persistence.Query;

/**
 * <p>User: 郭玉昆
 * <p>Date: 13-1-16 下午4:01
 * <p>Version: 1.0
 */
public interface SearchCallback {

    public static final SearchCallback NONE = new NoneSearchCallback();
    public static final SearchCallback DEFAULT = new DefaultSearchCallback();


    /**
     * 动态拼HQL where、group by having
     *
     * @param ql
     * @param search
     */
    public void prepareQL(StringBuilder ql, Searchable search);

    public void prepareOrder(StringBuilder ql, Searchable search);

    /**
     * 根据search给query赋值及设置分页信息
     *
     * @param query
     * @param search
     */
    public void setValues(Query query, Searchable search);

    public void setPageable(Query query, Searchable search);

}
