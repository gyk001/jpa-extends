/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.guoyukun.spring.jpa.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * <p> 抽象实体基类，提供统一的ID，和相关的基本功能方法
 * 如果是oracle请参考{@link BaseOracleEntity}
 * <p>User: 郭玉昆
 * <p>Date: 13-1-12 下午4:05
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseUUIDEntity<ID extends Serializable> extends AbstractEntity<ID> {

    @Id
    @GeneratedValue(generator = "uuidGenerator")       
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

}
