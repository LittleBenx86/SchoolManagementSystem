package cn.scau.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import cn.scau.dao.IBaseDao;

public abstract class BaseDaoImpl<T> implements IBaseDao<T> {

private Class<T> _class;
	
	//无参构造器
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		// TODO Auto-generated constructor stub
		//利用反射获取泛型T的实际类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();//获取当前new的对象泛型的父类类型
		this._class = (Class<T>)pt.getActualTypeArguments()[0];//获取第一个类型参数的真实类型
		System.out.println("class T :" + _class);
	}
	
	public BaseDaoImpl(Class<T> _class) {
		super();
		this._class = _class;
		System.out.println("other way : class T :" + _class);
	}
	
	public abstract SessionFactory getSessionFactory();
	
	/**
	 * 获取当前分数据库会话连接
	 * @return
	 */
	public Session getSession() {
		//getCurrentSession()是绑定到当前的线程中的,若没有则会调用openSession重新打开数据库连接会话
		return getSessionFactory().getCurrentSession();
	}
	
	@Override
	public void save(T pojo) {
		// TODO Auto-generated method stub
		getSession().save(pojo);
	}

	@Override
	public void delete(T pojo) {
		// TODO Auto-generated method stub
		getSession().delete(pojo);
	}

	@Override
	public void update(T pojo) {
		// TODO Auto-generated method stub
		getSession().update(pojo);
	}

	@Override
	public T getByID(Integer id) {
		// TODO Auto-generated method stub
		return (T) getSession().get(_class, id);
	}

	@Override
	public List<T> getByIDs(Integer[] ids) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取指定对象的指定ids数据库数据
	 * @param obj
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getByIDs(String obj, Integer[] ids) {
		// TODO Auto-generated method stub
		return getSession().createQuery("FROM " + obj + " WHERE ID IN (:ids)")
				.setParameterList("ids", ids).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery("FROM " + _class.getSimpleName()).list();
	}
	
	@Override
	public List<T> findByHQL(String hql, Object... params){
		@SuppressWarnings("unchecked")
		Query<T> query = getSession().createQuery(hql);
		for(int i = 0; null != params && i < params.length; i++) {
			query.setParameter(i, params.toString());
		}
		return query.list();
	}
	
	@Override
	public void saveAll(Collection<T> c) {
		// TODO Auto-generated method stub
		for (T t : c) {
			getSession().save(t);
		}
	}

	@Override
	public T saveOrUpdate(T pojo) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(pojo);
		return pojo;
	}

	@Override
	public void deleteByID(Integer id) {
		// TODO Auto-generated method stub
		T t = (T)getSession().get(_class, id);
		if(null != t)
			getSession().delete(t);
	}

	@Override
	public void deleteAll(Collection<T> c) {
		// TODO Auto-generated method stub
		for (T t : c) {
			getSession().delete(t);
		}
	}

	@Override
	public Integer getTotalCount() {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(_class);
		cq.from(_class);
		//Root<T> r = cq.from(_class);
		TypedQuery<T> tq = getSession().createQuery(cq);
		return tq.getResultList().size();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Integer getCount(String where, Object[] params,
			LinkedHashMap<String, String> orderBy) {
		String pojoName = _class.getSimpleName();
		String whereSql = where != null && !"".equals(where.trim()) ? " where " + where : ""; 
		System.out.println("select count(*) from " + pojoName + " p " 
				+ whereSql + buildOrderBy(orderBy));
		Query query = getSession().createQuery("select count(*) from " + pojoName + " p " 
				+ whereSql + buildOrderBy(orderBy));
		setQueryParameter(query, params);
		Number number = (Number)query.uniqueResult();
		return number.intValue();
	}

	@Override
	public List<T> load(Integer pageNo, Integer rowsPerPage) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(_class);
		cq.from(_class);
		TypedQuery<T> tq = getSession().createQuery(cq);
		tq.setFirstResult((pageNo -1) * rowsPerPage);
		tq.setMaxResults(rowsPerPage);
		return tq.getResultList();
	}

	@Override
	public List<T> getScrollData() {
		// TODO Auto-generated method stub
		return getScrollData(-1, -1, null, null, null);
	}

	@Override
	public List<T> getScrollData(Integer pageNo, Integer rowsPerPage) {
		// TODO Auto-generated method stub
		return getScrollData(pageNo, rowsPerPage, null, null, null);
	}

	@Override
	public List<T> getScrollData(Integer pageNo, Integer rowsPerPage, LinkedHashMap<String, String> orderBy) {
		// TODO Auto-generated method stub
		return getScrollData(pageNo, rowsPerPage, null, null, orderBy);
	}

	@Override
	public List<T> getScrollData(Integer pageNo, Integer rowsPerPage, String where, Object[] params) {
		// TODO Auto-generated method stub
		return getScrollData(pageNo, rowsPerPage, where, params, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> getScrollData(Integer pageNo, Integer rowsPerPage, String where, Object[] params,
			LinkedHashMap<String, String> orderBy) {
		// TODO Auto-generated method stub
		String pojoName = _class.getSimpleName();
		String whereSql = where != null && !"".equals(where.trim()) ? " where " + where : ""; 
		Query query = getSession().createQuery("select p from " + pojoName + " p" 
				+ whereSql + buildOrderBy(orderBy));
		if(-1 != pageNo && -1 != rowsPerPage)
			query.setFirstResult(pageNo).setMaxResults(rowsPerPage);
		setQueryParameter(query, params);
		return query.list();
	}
	
	/**
	 * 设置查询参数,老式占位符会产生警告
	 * @param query		查询对象
	 * @param params	参数值
	 */
	@SuppressWarnings("rawtypes")
	public void setQueryParameter(Query query, Object[] params) {
		if(null != params)
			for(int i = 0; i < params.length; i++)
				query.setParameter("" + i + "", params[i]);
		
	}
	
	/**
	 * 构建hql排序语句
	 * @param orderBy	排序属性与asc/desc, Key为属性,Value为asc/desc 
	 * @return
	 */
	public String buildOrderBy(LinkedHashMap<String, String> orderBy) {
		StringBuilder str = new StringBuilder();
		if(null != orderBy && !orderBy.isEmpty()) {
			str.append(" order by ");
			for (Map.Entry<String, String> entry : orderBy.entrySet()) {
				str.append("p.").append(entry.getKey()).append(" ").append(entry.getValue()).append(',');
			}
			str.deleteCharAt(str.length() -1);
		}
		return str.toString();
	}
	
	/*@Override
	public Page<T> queryByPage(Integer pageNo, Integer rowsPerPage, String where, Object[] params,
			LinkedHashMap<String, String> orderBy) {
		// TODO Auto-generated method stub
		long count = getCount(where, params, orderBy);
		List<T> list = getScrollData(pageNo - 1, rowsPerPage, where, params, orderBy);
		return new Page<T>(count, pageNo, rowsPerPage, list);
	}*/

}
