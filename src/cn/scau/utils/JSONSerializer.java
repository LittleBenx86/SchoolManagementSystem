package cn.scau.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.alibaba.fastjson.JSON;

/**
 * 阿里巴巴fastjson简单封装
 * 需要引用jar包:fastjson-1.2.38
 * 快速格式化会有(乱码)编码格式的问题,需要在spring.xml配置文件中进行配置或者是在注解中配置produce
 * response的数据返回类型: application/json;charset=utf-8;
 * way1:
 * <mvc:annotation-driven>
 *		<!-- <mvc:message-converters>
 *		<bean class="org.springframework.http.converter.StringHttpMessageConverter">
 *				<property name="supportedMediaTypes">
 *					<list>
 *						<value>application/json;charset=UTF-8</value>
 *					</list>
 *				</property>
 *			</bean>
 *		</mvc:message-converters> -->
 *	</mvc:annotation-driven>
 * way2:
 * produces = "application/json;charset=utf-8"
 * @author Unicorn
 *	2017-10-03
 */
public class JSONSerializer {

	private static final String DEFAULT_CHARSET_NAME = "UTF-8";
	
	//快速序列化对象,形成json数据格式
	public static <T> String serializer(T obj) {
		return JSON.toJSONString(obj);
	}
	
	//解序列化,形成对象
	public static <T> T deserializer(String string, Class<T> clz){
		return JSON.parseObject(string, clz);
	}
	
	public static <T> T load(Path path, Class<T> clz) throws IOException {
		return deserializer(new String(Files.readAllBytes(path), 
				DEFAULT_CHARSET_NAME), clz);
	}
	
	public static <T> void save(Path path, T obj) throws IOException{
		if(Files.notExists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		
		Files.write(path, 
				serializer(obj).getBytes(DEFAULT_CHARSET_NAME),
				StandardOpenOption.WRITE,
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING);
		
	}
	
	
}
