package cn.scau.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 后台管理员POJO
 * 
 * @author Unicorn
 *
 */
@Entity
@Table(name = "t_admin")
public class Admin implements Serializable {

	/**
	 * 对象序列化uid
	 */
	private static final long serialVersionUID = -1330984754399382597L;

	// admin attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;// hibernate 搜索索引,同时也是管理员的id编号
	@Column(name = "ADMIN_NAME")
	private String adminName;
	@Column(name = "PASSWORD")
	private String password;

	// setter and getter methods
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//tostring
	@Override
	public String toString() {
		return "Admin [id=" + id + ", adminName=" + adminName + ", password=" + password + "]";
	}
}
