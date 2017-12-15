package cn.scau.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 格式化请求链接中的数据
 * @author Unicorn
 *
 */
@Controller
@RequestMapping("/")
public class BaseController {
	protected void writeJson(HttpServletResponse response, String json) throws IOException {
		response.setContentType("text/json;charset=utf-8;");
		response.getWriter().print(json);
	}
}
