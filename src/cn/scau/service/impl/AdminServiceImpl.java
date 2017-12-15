package cn.scau.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.scau.dao.IAdminDao;
import cn.scau.pojo.Admin;
import cn.scau.service.IAdminService;
import cn.scau.utils.MD5Util;
import cn.scau.utils.SHAUtil;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements IAdminService{

	@Resource
	private IAdminDao adminDao;//admin dao层接口
	
	@Override
	public Boolean queryAdminValid(Admin admin) {
		// TODO Auto-generated method stub
		String where = " (p.adminName=?0) and (p.password=?1) ";
		Object[] params = {admin.getAdminName(), admin.getPassword()};
		List<Admin> list = adminDao.getScrollData(-1, -1, where, params);
		if(null != list && 1 == list.size() )
			return true;
		return false;
	}

	@Override
	public Boolean validCodeCheck(String code, String validCode) {
		// TODO Auto-generated method stub
		return code.equals(validCode) ? true : false ;
	}
	
}
