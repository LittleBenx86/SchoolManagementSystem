package cn.scau.dao;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 数据连接的接口
 * CURD: create update retrieve delete
 * @author Unicorn
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	/**
	 * 保存实体
	 * @param pojo
	 */
	void save(T pojo);
	
	/**
	 * 保存集合中的所有实体
	 * @param c 
	 */
	void saveAll(Collection<T> c);
	

	/**
	 * 更新实体
	 * @param pojo
	 */
	void update(T pojo);
	
	/**
	 * 保存或更新一个实体
	 * @param pojo
	 * @return 更新后的实体
	 */
	T saveOrUpdate(T pojo);
	
	/**
	 * 根据id删除实体
	 * @param id
	 */
	void delete(T pojo);
	
	/**
	 * 根据id删除一条记录
	 * @param id
	 */
	void deleteByID(Integer id);
	
	/**
	 * 删除多个实体对应的多条记录
	 * @param c
	 */
	void deleteAll(Collection<T> c);
	
	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	T getByID(Integer id);
	
	/**
	 * 根据一组id获得对象列表
	 * @param ids
	 * @return
	 */
	List<T> getByIDs(Integer[] ids);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 通过hql语句进行查找
	 * @param hql
	 * @param params
	 * @return
	 */
	List<T> findByHQL(String hql, Object... params);
	
	/**
	 * 获取记录总数
	 * @return
	 */
	Integer getTotalCount();
	
	/**
	 * 按照条件语句获取对应记录总数
	 * @param where
	 * @param params
	 * @param orderBy
	 * @return
	 */
	Integer getCount(String where, Object[] params, LinkedHashMap<String, String> orderBy);
	
	/**************************数据库分页*************************************/
	/**
	 * 分页加载记录
	 * @param pageNo 		当前第几页，从0开始
	 * @param rowsPerPage 	每页的记录行数
	 * @return 数据集合
	 */
	List<T> load(Integer pageNo, Integer rowsPerPage);
	
	/**
	 * 分页获取所有记录
	 * @return
	 */
	List<T> getScrollData();
	
	/**
	 * 分页获取记录
	 * @param pageNo		开始索引,如果输入值为-1,即获取所有记录
	 * @param rowsPerPage	每页获取的记录数,如果输入值为-1,即获取所有记录
	 * @return
	 */
	List<T> getScrollData(Integer pageNo, Integer rowsPerPage);
	
	/**
	 * 分页获取记录
	 * @param pageNo	 	 开始索引,如果输入值为-1,即获取所有记录
	 * @param rowsPerPage	 每页获取的记录数,如果输入值为-1,即获取所有记录
	 * @param orderBy		 排序,key为排序的属性.value为asc/desc
	 * @return
	 */
	List<T> getScrollData(Integer pageNo, Integer rowsPerPage, LinkedHashMap<String, String> orderBy);
	
	/**
	 * 分页获取记录
	 * @param pageNo		开始索引,如果输入值为-1,即获取所有记录
	 * @param rowsPerPage	每页获取的记录数,如果输入值为-1,即获取所有记录
	 * @param where			条件语句,不带where关键字,条件语句只能使用位置参数(?传参),该参数的索引值从1开始
	 * @param params		对应位置参数的值
	 * @return
	 */
	List<T> getScrollData(Integer pageNo, Integer rowsPerPage, String where, Object[] params);
	
	/**
	 * 分页获取记录
	 * @param pageNo	 	 开始索引,如果输入值为-1,即获取所有记录
	 * @param rowsPerPage	 每页获取的记录数,如果输入值为-1,即获取所有记录
	 * @param where			 条件语句,不带where关键字,条件语句只能使用位置参数(记得带别名p.并用?传参),该参数的索引值从1开始
	 * @param params		 对应位置参数的值
	 * @param orderBy		 排序,key为排序的属性.value为asc/desc
	 * LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>(); 
     * orderBy.put("email", "asc"); 
     * orderBy.put("password", "desc"); 
	 * @return
	 */
	List<T> getScrollData(Integer pageNo, Integer rowsPerPage, String where, 
			Object[] params, LinkedHashMap<String, String> orderBy);
	
	/**
	 * 分页获取记录 
	 * @param pageNo		页数从1开始
	 * @param rowsPerPage
	 * @param where
	 * @param params
	 * @param orderBy
	 * @return
	 */
	/*Page<T> queryByPage(Integer pageNo, Integer rowsPerPage, String where, Object[] params,
			LinkedHashMap<String, String> orderBy);*/
}
