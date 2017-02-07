package org.lt.basic.dao;

import java.util.List;
import java.util.Map;

import org.lt.basic.model.Pager;

/**
 * 公共的DAO处理对象，这个对象中包含了Hibernate的所有基本操作和对SQL的操作。
 * @author li949
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public T add(T t);
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);
	/**
	 * 根据id删除对象
	 * @param t
	 */
	public void delete(int id);
	/**
	 * 根据id加载对象
	 * @param id
	 * @return
	 */
	public T load(int id);
	/**
	 * 不分页列表对象
	 * @param hql	查询对象的hql
	 * @param args	查询参数
	 * @return	返回一组不分页的列表对象
	 */
	public List<T> list(String hql,Object[] args);
	public List<T> list(String hql,Object arg);
	public List<T> list(String hql);
	/**
	 * 基于别名和查询参数的混合列表对象
	 * @param hql
	 * @param args
	 * @param alias 别名
	 * @return
	 */
	public List<T> list(String hql,Object[] args,Map<String, Object> alias);
	public List<T> list(String hql,Map<String, Object> alias);
	
	/**
	 * 分页列表对象
	 * @param hql	查询对象的hql
	 * @param args	查询参数
	 * @return	返回一组分页的列表对象
	 */
	public Pager<T> find(String hql,Object[] args);
	public Pager<T> find(String hql,Object arg);
	public Pager<T> find(String hql);
	/**
	 * 基于别名和查询参数的混合列表对象
	 * @param hql
	 * @param args
	 * @param alias 别名
	 * @return
	 */
	public Pager<T> find(String hql,Object[] args,Map<String, Object> alias);
	public Pager<T> find(String hql,Map<String, Object> alias);
	/**
	 * 根据hql查询对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object queryObject(String hql,Object[] args);
	public Object queryObject(String hql,Object arg);
	public Object queryObject(String hql);
	public Object queryObject(String hql,Object[] args,Map<String, Object> alias);
	public Object queryObject(String hql,Map<String, Object> alias);
	/**
	 * 根据hql更新对象
	 * @param hql
	 * @param args
	 */
	public void updateByHql(String hql,Object[] args);
	public void updateByHql(String hql,Object arg);
	public void updateByHql(String hql);
	
	/**
	 * 根据Sql查询对象，不包含关联对象，不分页
	 * @param hql
	 * @param args
	 * @param clz	查询的实体对象
	 * @param hasEntity		该对象是否是一个Hibernate所管理的实体，如果不是需要使用setResultTransform查询
	 * @return
	 */
	public List<Object> listBySql(String sql,Object[] args,Class<Object> clz,Boolean hasEntity);
	public List<Object> listBySql(String sql,Object arg,Class<Object> clz,Boolean hasEntity);
	public List<Object> listBySql(String sql,Class<Object> clz,Boolean hasEntity);
	public List<Object> listBySql(String sql,Object[] args,Map<String, Object> alias,Class<Object> clz,Boolean hasEntity);
	public List<Object> listBySql(String sql,Map<String, Object> alias,Class<Object> clz,Boolean hasEntity);
	
	/**
	 * 根据Sql查询对象，不包含关联对象，分页
	 * @param hql
	 * @param args
	 * @param clz	查询的实体对象
	 * @param hasEntity		该对象是否是一个Hibernate所管理的实体，如果不是需要使用setResultTransform查询
	 * @return
	 */
	public Pager<Object> findBySql(String sql,Object[] args,Class<Object> clz,Boolean hasEntity);
	public Pager<Object> findBySql(String sql,Object arg,Class<Object> clz,Boolean hasEntity);
	public Pager<Object> findBySql(String sql,Class<Object> clz,Boolean hasEntity);
	public Pager<Object> findBySql(String sql,Object[] args,Map<String, Object> alias,Class<Object> clz,Boolean hasEntity);
	public Pager<Object> findBySql(String sql,Map<String, Object> alias,Class<Object> clz,Boolean hasEntity);
	
}
