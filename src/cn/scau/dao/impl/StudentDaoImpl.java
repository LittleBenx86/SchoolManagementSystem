package cn.scau.dao.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.scau.dao.IStudentDao;
import cn.scau.pojo.Student;

@Repository("studentDao")
@Transactional
public class StudentDaoImpl extends BaseDaoImpl<Student> implements IStudentDao{

	@Resource
	private SessionFactory sessionFactory;//获取hibernate会话工厂连接
	
	public StudentDaoImpl() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return sessionFactory;
	}
	
}
