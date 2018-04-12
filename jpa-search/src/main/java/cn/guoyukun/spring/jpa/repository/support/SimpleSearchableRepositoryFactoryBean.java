package cn.guoyukun.spring.jpa.repository.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import cn.guoyukun.spring.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * 基础Repostory简单实现 factory bean
 * 请参考 spring-data-jpa-reference [1.4.2. Adding custom behaviour to all repositories]
 * <p>User: 郭玉昆
 * <p>Date: 13-5-5 上午11:57
 * <p>Version: 1.0
 */
public class SimpleSearchableRepositoryFactoryBean<R extends JpaRepository<M, ID>, M, ID extends Serializable>
    extends JpaRepositoryFactoryBean<R, M, ID> {

    static final boolean SUPPORT_QUERY_DSL;

    static {
        boolean supoort;
        try {
            Class.forName("com.querydsl.core.types.Predicate");
            supoort = true;
        } catch (ClassNotFoundException e) {
            supoort = false;
        }
        SUPPORT_QUERY_DSL = supoort;
    }

    public SimpleSearchableRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(
        EntityManager entityManager) {
        if (SUPPORT_QUERY_DSL) {
            return new SimpleSearchableWithQueryDslRepositoryFactory<M, ID>(entityManager);
        } else {
            return new SimpleSearchableRepositoryFactory<M, ID>(entityManager);
        }
    }
}

class SimpleSearchableRepositoryFactory<M, ID extends Serializable> extends JpaRepositoryFactory {

    private EntityManager entityManager;

    public SimpleSearchableRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isBaseRepository(metadata.getRepositoryInterface())) {
            return SimpleSearchableRepository.class;
        }
        return super.getRepositoryBaseClass(metadata);
    }

    private boolean isBaseRepository(Class<?> repositoryInterface) {
        return BaseRepository.class.isAssignableFrom(repositoryInterface);
    }

}

class SimpleSearchableWithQueryDslRepositoryFactory<M, ID extends Serializable> extends JpaRepositoryFactory {
    private EntityManager entityManager;

    public SimpleSearchableWithQueryDslRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isBaseRepository(metadata.getRepositoryInterface())) {
            return SimpleSearchableQueryDslRepository.class;
        }
        return super.getRepositoryBaseClass(metadata);
    }

    private boolean isBaseRepository(Class<?> repositoryInterface) {
        return BaseRepository.class.isAssignableFrom(repositoryInterface);
    }

}
