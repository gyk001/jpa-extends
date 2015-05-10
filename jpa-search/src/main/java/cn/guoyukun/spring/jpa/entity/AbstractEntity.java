/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.guoyukun.spring.jpa.entity;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

/**
 * 抽象实体基类，如果主键是数据库端自动生成 请使用{@link BaseEntity}，如果是Oracle 请使用{@link BaseOracleEntity}
 * <p/>
 * <p>User: 郭玉昆
 * <p>Date: 13-3-20 下午8:38
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID> {

	public abstract ID getId();

    /**
     * Sets the id of the entity.
     *
     * @param id the id to set
     */
    public abstract void setId(final ID id);

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.domain.Persistable#isNew()

     */
    public boolean isNew() {

        return null == getId();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        AbstractEntity<?> that = (AbstractEntity<?>) obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [getId()=" + getId() + ", isNew()=" + isNew()
				+ ", hashCode()=" + hashCode() + "]";
	}
    

//    @Override
//    public String toString() {
//        return ReflectionToStringBuilder.toString(this);
//    }
}
