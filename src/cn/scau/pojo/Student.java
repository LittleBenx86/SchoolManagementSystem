package cn.scau.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学生POJO
 * 
 * @author Unicorn
 *
 */
@Entity
@Table(name="t_student")
public class Student implements Serializable {

	/**
	 * 对象序列化uid
	 */
	private static final long serialVersionUID = 3680084312725106862L;

	// Student attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;// hibernate搜索优化
	@Column(name = "NAME")
	private String name;// 姓名
	@Column(name = "AGE")
	private Integer age;// 年龄
	@Column(name = "SEX")
	private Integer sex;// 性别
	@Column(name = "PROVINCE")
	private String province;// 省
	@Column(name = "CITY")
	private String city;// 市
	@Column(name = "AREA")
	private String area;// 区/县
	@Column(name = "STUDENT_NO")
	private String studentNo;// 学号
	@Column(name = "PHONE")
	private String phone;// 手机号
	@Column(name = "E_MAIL")
	private String email;// 邮箱地址
	@Column(name = "BIRTHDAY")
	private String birthday;// 出生年月日

	//setter and getter methods
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	//tostring
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", province=" + province
				+ ", city=" + city + ", area=" + area + ", studentNo=" + studentNo + ", phone=" + phone + ", email="
				+ email + ", birthday=" + birthday + "]";
	}

	
}
