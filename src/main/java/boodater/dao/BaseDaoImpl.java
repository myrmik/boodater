package boodater.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


@Transactional
public abstract class BaseDaoImpl<K extends Serializable, D> implements BaseDao<K, D> {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    private Class<D> typeOfDomain;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        this.typeOfDomain = (Class<D>)
                ((ParameterizedType)getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[1];
    }

    @SuppressWarnings("unchecked")
    protected void insert(D domainObject) {
        sessionFactory.getCurrentSession().saveOrUpdate(domainObject);
    }

    @SuppressWarnings("unchecked")
    protected D select(K id) {
        return (D)sessionFactory.getCurrentSession().get(typeOfDomain, id);
    }


    @SuppressWarnings("unchecked")
    protected D selectByCriteria(String criteriaName, Object criteriaValue) {
        return (D) sessionFactory.getCurrentSession()
                .createCriteria(typeOfDomain)
                .add(Restrictions.eq(criteriaName, criteriaValue))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<D> selectAll() {
        return sessionFactory.getCurrentSession().createCriteria(typeOfDomain).list();
    }

    protected void delete(K id) {
        D domainObject = select(id);
        if (domainObject != null)
            sessionFactory.getCurrentSession().delete(domainObject);
    }
}
