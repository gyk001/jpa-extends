package cn.guoyukun.spring.jpa.repository.support;

import cn.guoyukun.spring.jpa.entity.search.Searchable;
import cn.guoyukun.spring.jpa.repository.BaseRepository;
import cn.guoyukun.spring.jpa.repository.RepositoryHelper;
import cn.guoyukun.spring.jpa.repository.callback.SearchCallback;
import cn.guoyukun.spring.jpa.repository.support.annotation.QueryJoin;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yukun.gyk
 * @date 13-5-5 上午11:57
 */
public class SimpleSearchableRepository<M, ID extends Serializable> extends SimpleJpaRepository<M, ID>
        implements BaseRepository<M, ID> {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleSearchableRepository.class);


    public static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
    public static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1=1 ";

    private final JpaEntityInformation<M, ID> entityInformation;
    private final RepositoryHelper repositoryHelper;
    private Class<M> entityClass;
    private String entityName;
    private String idName;
    private String findAllQL;
    private String countAllQL;
    private QueryJoin[] joins;

    private SearchCallback searchCallback = SearchCallback.DEFAULT;

    public SimpleSearchableRepository(JpaEntityInformation<M, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityInformation = entityInformation;
        this.entityClass = this.entityInformation.getJavaType();
        this.entityName = this.entityInformation.getEntityName();
        this.idName = this.entityInformation.getIdAttributeNames().iterator().next();

        repositoryHelper = new RepositoryHelper(entityClass, entityManager);

        findAllQL = String.format(FIND_QUERY_STRING, entityName);
        countAllQL = String.format(COUNT_QUERY_STRING, entityName);
    }

    @Override
    public void delete(ID[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return;
        }
        List<M> models = new ArrayList<M>();
        for (ID id : ids) {
            M model = null;
            try {
                model = entityClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass + " error", e);
            }
            try {
                BeanUtils.setProperty(model, idName, id);
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass + " error, can not set id", e);
            }
            models.add(model);
        }
        deleteInBatch(models);
    }

    @Override
    public Page<M> findAll(final Searchable searchable) {
        LOG.trace("findAllQL:{} || searchable：{}", findAllQL, searchable);
        List<M> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);
        long total = searchable.hasPageable() ? count(searchable) : list.size();
        return new PageImpl<M>(
                list,
                searchable.getPage(),
                total
        );
    }

    @Override
    public long count(Searchable searchable) {
        LOG.trace("countAllQL:{} || searchable：{}", countAllQL, searchable);
        return repositoryHelper.count(countAllQL, searchable, searchCallback);
    }

    @Override
    public M updateByVo(ID id, Object vo) {
        M model = findOne(id);
        if(model==null){
            return null;
        }
        org.springframework.beans.BeanUtils.copyProperties(vo, model);
        return saveAndFlush(model);
    }

    public String getFindAllQL() {
        return findAllQL;
    }

    public void setFindAllQL(String findAllQL) {
        this.findAllQL = findAllQL;
    }

    public String getCountAllQL() {
        return countAllQL;
    }

    public void setCountAllQL(String countAllQL) {
        this.countAllQL = countAllQL;
    }

    public SearchCallback getSearchCallback() {
        return searchCallback;
    }

    public void setSearchCallback(SearchCallback searchCallback) {
        this.searchCallback = searchCallback;
    }

    public QueryJoin[] getJoins() {
        return joins;
    }

    public void setJoins(QueryJoin[] joins) {
        this.joins = joins;
    }
}
