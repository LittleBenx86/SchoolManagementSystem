package cn.scau.webCommon;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取网页url的相对路径和本地工程的绝对路径
 * @author Unicorn
 *
 */
@Component
public class Common {
	@Autowired
	ServletContext context;//组件自动扫描后自动装配
	
	//网络路径
	public static String webPath;
	//应用本身的工程路径
	public static String localPath;
	
	@PostConstruct
	public void init() {
		System.out.println("开始初始化路径参数");
		//获取网页的上下文路径-->相对路径
		Common.webPath = context.getContextPath();
		context.setAttribute("_webPath", webPath);
		//获取本地工程路径-->绝对路径
		Common.localPath = context.getRealPath("/");
		context.setAttribute("_localPath", localPath);
		System.out.println(localPath);
	}
}
