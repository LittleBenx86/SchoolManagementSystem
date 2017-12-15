package cn.scau.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.scau.dao.IStudentDao;
import cn.scau.pojo.Student;
import cn.scau.service.IStudentService;

@Service("studentService")
@Transactional
public class StudentServiceImpl implements IStudentService{

	@Resource
	IStudentDao studentDao;
	
	@Override
	public void saveStudent(Student stu) {
		// TODO Auto-generated method stub
		studentDao.save(stu);
	}

	@Override
	public Boolean updateStudent(Student stu) {
		// TODO Auto-generated method stub
		try {
			studentDao.saveOrUpdate(stu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getStackTrace() + "-----------" + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public Student deleteStudent(Student stu) {
		// TODO Auto-generated method stub
		try {
			studentDao.deleteByID(stu.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getStackTrace() + "-----------" + e.getMessage());
			return null;
		}
		return stu;
	}

	@Override
	public Student studentExistValid(Student stu) {
		// TODO Auto-generated method stub
		String where = " p. studentNo=?0 and p.name=?1 ";
		Object[] params = {stu.getStudentNo(), stu.getName()};
		List<Student> list = studentDao.getScrollData(-1, -1, where, params);
		return list.size() > 0? list.get(0) : null;
	}

	@Override
	public Integer getCountOfStudents(String where, Object[] params,
			LinkedHashMap<String, String> orderBy) {
		// TODO Auto-generated method stub
		return studentDao.getCount(where, params, orderBy);
	}

	@Override
	public List<Student> queryStudents(Integer pageNo, Integer rowsPerPage, Integer totalPage, LinkedHashMap<String, String> orderBy, Integer count) {
		// TODO Auto-generated method stub
		
		if(pageNo == -1 && rowsPerPage == -1 && totalPage == null && count == null){
			return studentDao.getScrollData(-1, -1, null, null, orderBy);
		}
		
		if(pageNo == totalPage){
			int rest = count > (pageNo * rowsPerPage) ? count - (pageNo * rowsPerPage) : count;
			return studentDao.getScrollData((pageNo - 1) * rowsPerPage, rest, null, null, orderBy);
		}
		
		return studentDao.getScrollData((pageNo - 1) * rowsPerPage, rowsPerPage, null, null, orderBy);
	}

	@Override
	public List<Student> queryStudentsByConditions(String where, Object[] params, Integer pageNo, Integer rowsPerPage,
			Integer totalPage, LinkedHashMap<String, String> orderBy, Integer count) {
		// TODO Auto-generated method stub
		
		if(pageNo == -1 && rowsPerPage == -1 && totalPage == null && count == null){
			return studentDao.getScrollData(-1, -1, where, params, orderBy);
		}
		
		if(pageNo == totalPage){
			int rest = count > (pageNo * rowsPerPage) ? count - (pageNo * rowsPerPage) : count;
			return studentDao.getScrollData((pageNo - 1) * rowsPerPage, rest, where, params, orderBy);
		}
		
		return studentDao.getScrollData((pageNo - 1) * rowsPerPage, rowsPerPage, where, params, orderBy);
	}
	
}
