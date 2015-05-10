/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.guoyukun.spring.jpa.repository.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.StringUtils;

import cn.guoyukun.spring.jpa.repository.BaseRepository;
import cn.guoyukun.spring.jpa.repository.callback.SearchCallback;
import cn.guoyukun.spring.jpa.repository.support.annotation.SearchableQuery;

/**
 * 基础Repostory简单实现 factory bean
 * 请参考 spring-data-jpa-reference [1.4.2. Adding custom behaviour to all repositories]
 * <p>User: 郭玉昆
 * <p>Date: 13-5-5 上午11:57
 * <p>Version: 1.0
 */
public class SimpleBaseRepositoryFactoryBean<R extends JpaRepository<M, ID>, M, ID extends Serializable>
        extends JpaRepositoryFactoryBean<R, M, ID> {

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(
			EntityManager entityManager) {
		 return new SimpleBaseRepositoryFactory<M, ID>(entityManager);
	}
	
	

//    public SimpleBaseRepositoryFactoryBean() {
//    }
//
//    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
//        return new SimpleBaseRepositoryFactory<M, ID>(entityManager);
//    }
	
}

class SimpleBaseRepositoryFactory<M, ID extends Serializable> extends JpaRepositoryFactory {
	//日志对象
	private static final Logger LOG = LoggerFactory.getLogger(SimpleBaseRepositoryFactory.class);
	
	private EntityManager entityManager;
	
	public SimpleBaseRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	@Override
	protected Object getTargetRepository(RepositoryMetadata metadata) {
		Object object = null;
		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		LOG.debug("DAO接口 {}",repositoryInterface);
        if (isBaseRepository(repositoryInterface)) {
        	object = doGetTargetRepository(metadata);
        }else{
        	object = super.getTargetRepository(metadata);
        }
        return object;
	}
	
	private Object doGetTargetRepository(RepositoryMetadata metadata){
		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		 JpaEntityInformation<M, ID> entityInformation = getEntityInformation((Class<M>) metadata.getDomainType());
         SimpleBaseRepository<M, ID> repository = new SimpleBaseRepository<M, ID>(entityInformation, entityManager);

         SearchableQuery searchableQuery = AnnotationUtils.findAnnotation(repositoryInterface, SearchableQuery.class);
         if (searchableQuery != null) {
             String countAllQL = searchableQuery.countAllQuery();
             if (!StringUtils.isEmpty(countAllQL)) {
                 repository.setCountAllQL(countAllQL);
             }
             String findAllQL = searchableQuery.findAllQuery();
             if (!StringUtils.isEmpty(findAllQL)) {
                 repository.setFindAllQL(findAllQL);
             }
             Class<? extends SearchCallback> callbackClass = searchableQuery.callbackClass();
             if (callbackClass != null && callbackClass != SearchCallback.class) {
                 repository.setSearchCallback(BeanUtils.instantiate(callbackClass));
             }

             repository.setJoins(searchableQuery.joins());

         }
         LOG.debug("生成DAO {}", repository);
         return repository;
	}

	@Override
	protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
			RepositoryMetadata metadata, EntityManager entityManager) {
		SimpleJpaRepository<?, ?> sjr =  super.getTargetRepository(metadata, entityManager);
		LOG.debug(" {}",sjr);
		return sjr;
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
      if (isBaseRepository(metadata.getRepositoryInterface())) {
    	  return SimpleBaseRepository.class;
      }
      return super.getRepositoryBaseClass(metadata);
	}
	
	

//    private EntityManager entityManager;
//
//    public SimpleBaseRepositoryFactory(EntityManager entityManager) {
//        super(entityManager);
//        this.entityManager = entityManager;
//    }
//
//    protected Object getTargetRepository(RepositoryMetadata metadata) {
//        Class<?> repositoryInterface = metadata.getRepositoryInterface();
//
//        if (isBaseRepository(repositoryInterface)) {
//
//            JpaEntityInformation<M, ID> entityInformation = getEntityInformation((Class<M>) metadata.getDomainType());
//            SimpleBaseRepository<M, ID> repository = new SimpleBaseRepository<M, ID>(entityInformation, entityManager);
//
//            SearchableQuery searchableQuery = AnnotationUtils.findAnnotation(repositoryInterface, SearchableQuery.class);
//            if (searchableQuery != null) {
//                String countAllQL = searchableQuery.countAllQuery();
//                if (!StringUtils.isEmpty(countAllQL)) {
//                    repository.setCountAllQL(countAllQL);
//                }
//                String findAllQL = searchableQuery.findAllQuery();
//                if (!StringUtils.isEmpty(findAllQL)) {
//                    repository.setFindAllQL(findAllQL);
//                }
//                Class<? extends SearchCallback> callbackClass = searchableQuery.callbackClass();
//                if (callbackClass != null && callbackClass != SearchCallback.class) {
//                    repository.setSearchCallback(BeanUtils.instantiate(callbackClass));
//                }
//
//                repository.setJoins(searchableQuery.joins());
//
//            }
//
//            return repository;
//        }
//        return super.getTargetRepository(metadata);
//    }
//
//    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
//        if (isBaseRepository(metadata.getRepositoryInterface())) {
//            return SimpleBaseRepository.class;
//        }
//        return super.getRepositoryBaseClass(metadata);
//    }
//
    private boolean isBaseRepository(Class<?> repositoryInterface) {
        return BaseRepository.class.isAssignableFrom(repositoryInterface);
    }
//
//    @Override
//    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key) {
//        return super.getQueryLookupStrategy(key);
//    }
}
