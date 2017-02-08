package org.lt.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.lt.basic.model.Pager;
import org.lt.basic.model.SystemContext;

@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	/**
	 * 创建一个class的对象，来获取泛型的class
	 */
	private Class<Object> clz;
	public Class<Object> getClz() {
		//获取泛型的Class对象
		if (clz==null) {
			clz=((Class<Object>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	private String initSort(String hql) {
		String order=SystemContext.getOrder();
		String sort=SystemContext.getSort();
		if (sort!=null&&!"".equals(sort.trim())) {
			hql+="order by "+sort;
			if (!"desc".equals(order)) {
				hql+=" asc";
			}else {
				hql+="desc";
			}
		}
		return hql;
	}
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query,Map<String, Object> alias) {
		if (alias!=null) {
			Set<String> keys=alias.keySet();
			for (String key : keys) {
				Object val=alias.get(key);
				if (val instanceof Collection) {
					//查询条件是列表
					query.setParameterList(key,(Collection)val);
				}else {
					query.setParameter(key, val);
				}
			}
		}
	}
	private void setParameter(Query query,Object[] args) {
		if (args!=null&&args.length>0) {
			int index=0;
			for (Object arg : args) {
				query.setParameter(index++, arg);
			}
		}
	}
	@SuppressWarnings("rawtypes")
	private void setPagers(Query query,Pager pager) {
		Integer pageSize=SystemContext.getPageSize();
		Integer pageOffset=SystemContext.getPageOffset();
		if (pageOffset==null||pageOffset<0) {
			pageOffset=0;
		}
		if (pageSize==null||pageSize<0) {
			pageSize=15;
		}
		pager.setOffset(pageOffset);
		pager.setSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}
	private String getCountHql(String hql,Boolean isHql) {
		String end=hql.substring(hql.indexOf("from"));
		String count="select count(*) "+end;
		if (isHql) {
			count.replace("fetch", "");
		}
		return count;
	}
	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	@Override
	public void update(T t) {
		getSession().update(t);
	}

	@Override
	public void delete(int id) {
		getSession().delete(this.load(id));
	}

	
	@Override
	public T load(int id) {
		
		return (T)getSession().load(getClz(), id);
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		
		return this.list(hql, args,null);
	}

	@Override
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[]{arg});
	}

	@Override
	public List<T> list(String hql) {
		return this.list(hql,null,null);
	}

	@Override
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql=initSort(hql);
		Query query=getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.list();
	}

	@Override
	public List<T> list(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}

	@Override
	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql,args,null);
	}

	@Override
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[] {arg}, null);
	}

	@Override
	public Pager<T> find(String hql) {
		return this.find(hql,null,null);
	}

	@Override
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql=initSort(hql);
		Query query=getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		
		String countHql=getCountHql(hql,true);
		Query queryCount=getSession().createQuery(countHql);
		setAliasParameter(queryCount, alias);
		setParameter(queryCount, args);
		
		Pager<T> pager=new Pager<>();
		setPagers(query,pager);
		List<T> datas=query.list();
		pager.setDatas(datas);
		pager.setTotal((long)queryCount.uniqueResult());
		return pager;
	}

	@Override
	public Pager<T> find(String hql, Map<String, Object> alias) {
		return this.find(hql, null, alias);
	}

	@Override
	public Object queryObject(String hql, Object[] args) {
		return this.queryObject(hql, args, null);
	}

	@Override
	public Object queryObject(String hql, Object arg) {
		return this.queryObject(hql, new Object[]{arg}, null);
	}

	@Override
	public Object queryObject(String hql) {
		return this.queryObject(hql, null, null);
	}

	@Override
	public Object queryObject(String hql, Object[] args, Map<String, Object> alias) {
		Query query=getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}

	@Override
	public Object queryObject(String hql, Map<String, Object> alias) {
		return this.queryObject(hql, null, alias);
	}

	@Override
	public void updateByHql(String hql, Object[] args) {
		Query query=getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}

	@Override
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[]{arg});
	}

	@Override
	public void updateByHql(String hql) {
		this.updateByHql(hql, null);
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz, Boolean hasEntity) {
		return this.listBySql(sql, args, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz, Boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Class<?> clz, Boolean hasEntity) {
		return this.listBySql(sql, null, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, Boolean hasEntity) {
		sql=initSort(sql);
		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
		setAliasParameter(sqlQuery, alias);
		setParameter(sqlQuery, args);
		if (hasEntity) {
			sqlQuery.addEntity(clz);
		}else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		}
		return sqlQuery.list();
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Map<String, Object> alias, Class<?> clz, Boolean hasEntity) {
		return this.listBySql(sql, null, alias, clz, hasEntity);
	}

	@Override
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz, Boolean hasEntity) {
		return this.findBySql(sql, args, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz, Boolean hasEntity) {
		return this.findBySql(sql, new Object[]{arg}, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>Pager<N> findBySql(String sql, Class<?> clz, Boolean hasEntity) {
		return this.findBySql(sql, null, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, Boolean hasEntity) {
		sql=initSort(sql);
		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
		setAliasParameter(sqlQuery, alias);
		setParameter(sqlQuery, args);
		
		String countHql=getCountHql(sql,false);
		SQLQuery queryCount=getSession().createSQLQuery(countHql);
		setAliasParameter(queryCount, alias);
		setParameter(queryCount, args);
		
		Pager<N> pager=new Pager<N>();
		setPagers(sqlQuery,pager);
		if (hasEntity) {
			sqlQuery.addEntity(clz);
		}else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List<N> datas=sqlQuery.list();
		pager.setDatas(datas);
		pager.setTotal(((BigInteger)queryCount.uniqueResult()).longValue());
		return (Pager<N>) pager;
	}

	@Override
	public <N extends Object>Pager<N> findBySql(String sql, Map<String, Object> alias, Class<?> clz, Boolean hasEntity) {
		return this.findBySql(sql, null, alias, clz, hasEntity);
	}

}
