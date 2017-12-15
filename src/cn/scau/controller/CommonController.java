package cn.scau.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.scau.pojo.Admin;
import cn.scau.service.IAdminService;
import cn.scau.utils.MD5Util;
import cn.scau.utils.SHAUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class CommonController {

	@Resource
	private IAdminService adminService;
	
	@RequestMapping("/404")
	public void webNoFound() {
		//时间关系没做404处理
	}
	
	/**
	 * 管理员login请求后台请求处理页面
	 * @param admin
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/adminLoginRequest", produces="application/json;charset=utf-8")
	@ResponseBody
	public String adminLoginRequest(Admin admin, HttpSession session) {
		JSONObject json = new JSONObject();
	
		if(null != admin.getPassword())
			admin.setPassword(MD5Util.encoderByMD5(SHAUtil.encoderBySHA(admin.getPassword())));
		Boolean flag = adminService.queryAdminValid(admin);
		
		if(flag) {
			session.setAttribute("_admin", admin);
			json.put("success", "yes");
		} else
			json.put("success", "err");
		return json.toString();
	}
}
