package cn.scau.service;

import java.util.LinkedHashMap;
import java.util.List;

import cn.scau.pojo.Student;

/**
 * 学生service接口
 * @author Unicorn
 *
 */
public interface IStudentService{
	//接口函数声明
	/**
	 * 保存一个学生对象的数据
	 * @param stu
	 */
	void saveStudent(Student stu);
	
	/**
	 * 修改/更新一个学生对象的数据
	 * @param stu
	 * @return
	 */
	Boolean updateStudent(Student stu);
	
	/**
	 * 逻辑上删除一个学生对象的数据
	 * @param stu
	 * @return
	 */
	Student deleteStudent(Student stu);
	
	/**
	 * 校验一个学生对象是否存在
	 * @param stu
	 * @return
	 */
	Student studentExistValid(Student stu);
	
	/**
	 * 获取所有学生的记录总数
	 * @param where
	 * @param params
	 * @param orderBy
	 * @return
	 */
	Integer getCountOfStudents(String where, Object[] params,
			LinkedHashMap<String, String> orderBy);
	
	
	/**
	 * 通用的查询接口
	 * @param where where子句
	 * @param params 查询输入的条件
	 * @param pageNo 起始页面
	 * @param rowsPerPage 每页的记录数量
	 * @param totalPage	总页数
	 * @param orderBy	排序方式
	 * @param count 记录总数
	 * @return
	 */
	List<Student> queryStudentsByConditions(String where, Object[] params, Integer pageNo, Integer rowsPerPage, 
			Integer totalPage, LinkedHashMap<String, String> orderBy, Integer count);
	
	/**
	 * 查询所有按钮请求时的学生数据
	 * @param pageNo 起始页面
	 * @param rowsPerPage 每页的记录数量
	 * @param totalPage 总页数
	 * @param orderBy 排序方式
	 * @param count 记录总数
	 * @return
	 */
	List<Student> queryStudents(Integer pageNo, Integer rowsPerPage, 
			Integer totalPage, LinkedHashMap<String, String> orderBy, Integer count);
	
}
