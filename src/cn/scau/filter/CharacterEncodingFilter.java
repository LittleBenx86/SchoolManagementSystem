package cn.scau.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.scau.webCommon.Common;

/**
 * 处理编码的格式问题,以及对于资源直接访问的404处理
 * @author Unicorn
 *
 */
public class CharacterEncodingFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//设置请求的编码格式
		req.setCharacterEncoding("UTF-8");
		//设置响应的编码格式
		resp.setContentType("text/html;charset=utf-8;");
		
		//获取同一的资源标识符(链接的后缀-->资源类型)
		String uri = req.getRequestURI();
		if(uri.endsWith(".html"))//如果有直接访问.jsp资源文件的,重定向到404页面
			resp.sendRedirect(Common.webPath+"/404");
		
		//把转换链接传递到下一层过滤器,或者到服务器
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
