package cn.scau.dao.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.scau.dao.IAdminDao;
import cn.scau.pojo.Admin;

@Repository("adminDao")
@Transactional
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements IAdminDao {

	@Resource
	private SessionFactory sessionFactory;//获取hibernate的会话连接工厂
	
	public AdminDaoImpl() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return sessionFactory;
	}

}
