package cn.scau.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.scau.pojo.Admin;
import cn.scau.webCommon.Common;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	private Set<String> set;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(null != set) for (String str : set) {
			String uri = request.getRequestURI();
			if(uri.endsWith(str)) 
				return true;
		}
		Admin admin = (Admin) request.getSession().getAttribute("_admin");
		if( null == admin ) {
			response.sendRedirect(Common.webPath + "/admin/login.htm");
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	/**不给get set function 会抛异常,这是strust的问题*/
	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}
}
