package cn.scau.controller.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.scau.pojo.Student;
import cn.scau.service.IStudentService;
import cn.scau.utils.Export2ExcelUtils;
import cn.scau.utils.JSONSerializer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin")
public class AdminBSController {
	
	@Resource
	IStudentService stuService;
	
	public static final int ROWS_PER_PAGE = 10;
	
	@RequestMapping(value="/login.htm")
	public String adminLogin(){
		return "/admin/login";
	}
	
	/**
	 * 管理员logout请求后台请求处理页面
	 * @param admin
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout", produces="application/json;charset=utf-8")
	@ResponseBody
	public String admindminLogoutRequest(HttpSession session) {
		JSONObject json = new JSONObject();
		if(null != session.getAttribute("_admin"))
			session.removeAttribute("_admin");
		json.put("logout", "y");
		return json.toString();
	}
	
	@RequestMapping(value="/addStu.htm")
	public String addStudentWeb(HttpSession session){
		if(null == session.getAttribute("_admin")){
			return "/admin/login";
		}
		return "/admin/addStudent";
	}
	
	@RequestMapping(value="/add", produces="application/json;charset=utf-8")
	@ResponseBody
	public String addStudent(Student stu, HttpSession session){
		JSONObject json = new JSONObject();
		
		if(null == session.getAttribute("_admin")){
			json.put("ot", "y");
			return json.toString();
		}
		
		if(null != stu){
			int i = stuService.getCountOfStudents(null, null, null);
			if(0 == i){
				stuService.saveStudent(stu);
				json.put("isAdd", "yes");
			} else if(0 < i){
				Student tmp = stuService.studentExistValid(stu);
				
				if(null == tmp){
					stuService.saveStudent(stu);
					json.put("isAdd", "yes");
				} else {
					json.put("isAdd", "exist");
				}
			} 
		} else {
			json.put("isAdd", "no");
		}
		return json.toString();
	}
	
	/**
	 * 
	 * @param stu
	 * @param qc
	 * @param st
	 * @param condition
	 * @param province
	 * @param city
	 * @param area
	 * @param sex
	 * @param btnType
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value="/reqStuDatas", produces="application/json;charset=utf-8")
	@ResponseBody 
	public String allStudentDatas(Student stu, @RequestParam(name="qc")String qc, @RequestParam(name="st", required=false)String st,
			@RequestParam(name="pageNo")Integer pageNo,@RequestParam(name="btnType")String btnType, HttpSession session){
		LinkedHashMap<String, String> orderBy = SortOrderBy(st);
		JSONObject json = new JSONObject();
		
		if(null == session.getAttribute("_admin")){
			json.put("ot", "y");
			return json.toString();
		}
		
		if(null == qc){
			json.put("iserr", "y");
			return json.toString();
		}
		
		switch (Integer.parseInt(qc)) {
		case 11:
			return queryStudentsByAgeService(stu, pageNo, qc, orderBy, btnType);
		case 12:
			return queryStudentsByNameService(stu, pageNo, qc, orderBy, btnType);
		case 13:
			return queryStudentsByStuNoService(stu, pageNo, qc, orderBy, btnType);
		case 14:
			return queryStudentsByYearService(stu, pageNo, qc, orderBy, btnType);
		case 15:
			return queryStudentsBySexService(stu, pageNo, qc, orderBy, btnType);
		case 16:
			return queryStudentsByPCAService(stu, pageNo, qc, orderBy, btnType);
		case 17:
			return queryStudentsByPhoneService(stu, pageNo, qc, orderBy, btnType);
		case 18:
			return queryStudentsByEmailService(stu, pageNo, qc, orderBy, btnType);
		case 19:
			return commonQueryStudentsService(pageNo, qc, orderBy, btnType);
		default:
			break;
		}
		
		json.put("iserr", "y");
		return json.toString();
	}
	
	/**
	 * 通过年龄查询服务接口函数
	 * @param stu
	 * @param pageNo
	 * @param queryCondition
	 * @param orderBy
	 * @param btnType
	 * @return
	 */
	public String queryStudentsByAgeService(Student stu, Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("11"))
			return "no";
		
		String where = " (p.age>=?0) and (p.age<=?1) ";
		Object[] params = {stu.getAge() - 5, stu.getAge() + 5};
		JSONObject jo = new JSONObject();
		
		List<Student> list = null;
		
		if(null != btnType && 0 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(where, params, orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			
			list = stuService.queryStudentsByConditions(where, params, pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			
		}else if(null != btnType && 2 == Integer.parseInt(btnType)){
			
			list = stuService.queryStudentsByConditions(where, params, -1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		
		return jo.toString();
	}
	
	/**
	 * 通过姓名查询服务接口函数
	 * @param stu
	 * @param pageNo
	 * @param queryCondition
	 * @param orderBy
	 * @param btnType
	 * @return
	 */
	public String queryStudentsByNameService(Student stu, Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("12"))
			return "no";
		
		String where = " (p.name like ?0) ";
		Object[] params = {"%" + stu.getName() + "%"};
		JSONObject jo = new JSONObject();
		
		List<Student> list = null;
		
		if(null != btnType && 0 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(where, params, orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			
			list = stuService.queryStudentsByConditions(where, params, pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			
		}else if(null != btnType && 2 == Integer.parseInt(btnType)){
			
			list = stuService.queryStudentsByConditions(where, params, -1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		
		return jo.toString();
	}
	
	/**
	 * 通过学号份查询服务接口函数
	 * @param stu
	 * @param pageNo
	 * @param queryCondition
	 * @param orderBy
	 * @param btnType
	 * @return
	 */
	public String queryStudentsByStuNoService(Student stu, Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("13"))
			return "no";
		
		String where = " (p.studentNo like ?0) ";
		Object[] params = {"%" + stu.getStudentNo() + "%"};
		JSONObject jo = new JSONObject();
		
		List<Student> list = null;
		
		if(null != btnType && 0 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(where, params, orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			
			list = stuService.queryStudentsByConditions(where, params, pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			
		}else if(null != btnType && 2 == Integer.parseInt(btnType)){
			
			list = stuService.queryStudentsByConditions(where, params, -1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		
		return jo.toString();
	}
	
	/**
	 * 通过出生年份查询服务接口函数
	 * @param stu
	 * @param pageNo
	 * @param queryCondition
	 * @param orderBy
	 * @param btnType
	 * @return
	 */
	public String queryStudentsByYearService(Student stu, Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("14"))
			return "no";
		
		String where = " (p.birthday like ?0) ";
		Object[] params = {"%" + stu.getBirthday() + "%"};
		JSONObject jo = new JSONObject();
		
		List<Student> list = null;
		
		if(null != btnType && 0 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(where, params, orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			
			list = stuService.queryStudentsByConditions(where, params, pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			
		}else if(null != btnType && 2 == Integer.parseInt(btnType)){
			
			list = stuService.queryStudentsByConditions(where, params, -1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		
		return jo.toString();
	}
	
	/**
	 * 通过性别查询服务接口函数
	 * @param stu
	 * @param pageNo
	 * @param queryCondition
	 * @param orderBy
	 * @param btnType
	 * @return
	 */
	public String queryStudentsBySexService(Student stu, Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("15"))
			return "no";
		
		String where = " p.sex=?0 ";
		Object[] params = {stu.getSex()};
		JSONObject jo = new JSONObject();
		
		List<Student> list = null;
		
		if(null != btnType && 0 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(where, params, orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			
			list = stuService.queryStudentsByConditions(where, params, pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			
		}else if(null != btnType && 2 == Integer.parseInt(btnType)){
			
			list = stuService.queryStudentsByConditions(where, params, -1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		
		return jo.toString();
	}
	
	/**
	 * 通过籍贯信息查询服务接口函数
	 * @param stu
	 * @param pageNo
	 * @param queryCondition
	 * @param orderBy
	 * @param btnType
	 * @return
	 */
	public String queryStudentsByPCAService(Student stu, Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("16"))
			return "no";
		
		JSONObject jo = new JSONObject();
		
		StringBuilder where = new StringBuilder(" (1=1) and");
		int i = 0;
		List<Object> p = new ArrayList<Object>();
		
		if(null != stu.getProvince() && !"".equals(stu.getProvince())){
			where.append(" (p.province like ?").append(i++).append(")").append(" and");
			p.add("%" + stu.getProvince() + "%");
		}
		
		if(null != stu.getCity() && !"".equals(stu.getCity())){ 
			where.append(" (p.city like ?").append(i++).append(")").append(" and");
			p.add("%" + stu.getCity() + "%");
		}
		
		if(null != stu.getArea() && !"".equals(stu.getArea())){
			where.append(" (p.area like ?").append(i++).append(")").append(" and");
			p.add("%" + stu.getArea() + "%");
		}
		
		String str = where.delete(where.lastIndexOf("and"), where.length()).toString();
		
		List<Student> list = null;
		
		if(null != btnType && 0 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(str, p.toArray(), orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			
			list = stuService.queryStudentsByConditions(str, p.toArray(), pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			
		}else if(null != btnType && 2 == Integer.parseInt(btnType)){
			
			list = stuService.queryStudentsByConditions(str, p.toArray(), -1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		
		return jo.toString();
	}
	
	/**
	 * 通过手机号查询服务接口函数
	 * @param stu
	 * @param pageNo
	 * @param queryCondition
	 * @param orderBy
	 * @param btnType
	 * @return
	 */
	public String queryStudentsByPhoneService(Student stu, Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("17"))
			return "no";
		
		String where = " (p.phone like ?0) ";
		Object[] params = {"%" + stu.getPhone() +"%"};
		JSONObject jo = new JSONObject();
		
		List<Student> list = null;
		
		if(null != btnType && 0 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(where, params, orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			
			list = stuService.queryStudentsByConditions(where, params, pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			
		}else if(null != btnType && 2 == Integer.parseInt(btnType)){
			
			list = stuService.queryStudentsByConditions(where, params, -1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		
		return jo.toString();
	}
	
	/**
	 * 通过邮箱查询的服务接口函数
	 * @param stu
	 * @param pageNo
	 * @param queryCondition
	 * @param orderBy
	 * @param btnType
	 * @return
	 */
	public String queryStudentsByEmailService(Student stu, Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("18"))
			return "no";
		String where = " (p.email like ?0) ";
		Object[] params = {"%" + stu.getEmail() + "%"};
		JSONObject jo = new JSONObject();
		List<Student> list = null;
		
		if(null != btnType && 0 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(where, params, orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			
			list = stuService.queryStudentsByConditions(where, params, pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			
		}else if(null != btnType && 2 == Integer.parseInt(btnType)){
			
			list = stuService.queryStudentsByConditions(where, params, -1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		
		return jo.toString();
	}
	
	/**
	 * 非特定条件下查询所有学生
	 * @param pageNo 查询记录的起始页数
	 * @param queryCondition 查询条件,这里是非特定条件查询,即查询所有按钮触发的记录查询事件
	 * @param sortType	排序类型,一共有6种排序方式
	 * @param btnType	两种按钮:查询所有 1;导出excel 2触发该查询
	 * @return
	 */
	public String commonQueryStudentsService(Integer pageNo, String queryCondition, LinkedHashMap<String, String> orderBy, String btnType){
		if(null == queryCondition || !queryCondition.equals("19"))
			return "no";
		String where = null;
		Object[] params = null;
		JSONObject jo = new JSONObject();
		List<Student> list = null;
		if(null != btnType && 1 == Integer.parseInt(btnType)){
			int count = stuService.getCountOfStudents(where, params, orderBy);
			int totalPages = count % ROWS_PER_PAGE == 0 ? count / ROWS_PER_PAGE : (count / ROWS_PER_PAGE) + 1;
			list = stuService.queryStudents(pageNo, ROWS_PER_PAGE, totalPages, orderBy, count);
			jo.put("totalPages", totalPages);
			StringBuilder sb = new StringBuilder(JSONSerializer.serializer(list));
			jo.put("sDatas", sb.toString());
			System.out.println(jo.toString());
		}
		else if(null != btnType && 2 == Integer.parseInt(btnType)){
			list = stuService.queryStudents(-1, -1, null, orderBy, null);
			
			JSONArray json = JSONArray.fromObject(JSONSerializer.serializer(list));
			String[] heads={"age","area", "birthday", "city", "email", "id", "name", "phone", "province", "sex", "studentNo"};
			Export2ExcelUtils.Export2Excel(heads, heads, json);
			jo.clear();
			jo.put("isExport", "ok");
		}
		return jo.toString();
	}
	
	/**
	 * 排序类型选择
	 * @param sortType
	 * @return
	 */
	public LinkedHashMap<String, String> SortOrderBy(String sortType){
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		
		if(null == sortType)
			return null;
		
		switch (Integer.parseInt(sortType)) {
		case 101:
			orderBy.put("age", "asc");
			break;
		case 102:
			orderBy.put("age", "desc");
			break;
		case 103:
			orderBy.put("studentNo", "asc");		
			break;
		case 104:
			orderBy.put("studentNo", "desc");
			break;
		case 105:
			orderBy.put("phone", "asc");
			break;
		case 106:
			orderBy.put("phone", "desc");
			break;
		default:
			orderBy = null;
			break;
		}
		
		return orderBy;
	}
	
	
	@RequestMapping(value="/stuTable.htm")
	public String studentTable(HttpSession session){
		if(null == session.getAttribute("_admin")){
			return "/admin/login";
		}
		return "/admin/stuTable";
	}
	
	@RequestMapping(value="/update", produces="application/json;charset=utf-8")
	@ResponseBody
	public String updateStudentsDatas(Student stu, HttpSession session){
		JSONObject json = new JSONObject();
		
		if(null == session.getAttribute("_admin")){
			json.put("ot", "y");
			return json.toString();
		}
		
		Boolean flag = stuService.updateStudent(stu);
		if(flag){
			json.put("isUpdate", "y");
			return json.toString();
		}
		
		json.put("isUpdate", "n");
		return json.toString();
	}
	
	@RequestMapping(value="/delete", produces="application/json;charset=utf-8")
	@ResponseBody
	public String deleteStudentsDatas(Student stu, HttpSession session){
		JSONObject json = new JSONObject();
		
		if(null == session.getAttribute("_admin")){
			json.put("ot", "y");
			return json.toString();
		}
		
		stu = stuService.deleteStudent(stu);
		if(null != stu){
			json.put("isDelete", "y");
			return json.toString();
		}
		
		json.put("isDelete", "n");
		return json.toString();
	}
	
}
