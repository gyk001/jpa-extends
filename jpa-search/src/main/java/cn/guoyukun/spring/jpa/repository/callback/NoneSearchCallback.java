package cn.guoyukun.spring.jpa.repository.callback;

import cn.guoyukun.spring.jpa.entity.search.Searchable;

import javax.persistence.Query;

/**
 * <p>User: 郭玉昆
 * <p>Date: 13-1-16 下午8:10
 * <p>Version: 1.0
 */
public final class NoneSearchCallback implements SearchCallback {

    @Override
    public void prepareQL(StringBuilder ql, Searchable search) {
    }

    @Override
    public void prepareOrder(StringBuilder ql, Searchable search) {
    }

    @Override
    public void setValues(Query query, Searchable search) {
    }

    @Override
    public void setPageable(Query query, Searchable search) {
    }
}
