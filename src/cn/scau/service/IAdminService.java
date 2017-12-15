package cn.scau.service;

import cn.scau.pojo.Admin;

/**
 * 管理员service接口
 * @author Unicorn
 *
 */
public interface IAdminService {
	//接口函数声明
	/**
	 * 管理员登录时检验管理员的账户名和密码是否正确
	 * @param admin	管理员实体对象
	 * @return
	 */
	Boolean queryAdminValid(Admin admin);
	
	/**
	 * 校验管理员登录的验证码
	 * @param code	用户输入的验证码
	 * @param validCode		服务器绑定的验证码
	 * @return
	 */
	Boolean validCodeCheck(String code, String validCode);
}
