package biz.entity.main;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import biz.entity.Dept;

@Entity
@Table(name = "t_simple_user")//这里是表格名称,每一个实体都是一样
public class SimpleUser implements java.io.Serializable {

	// Fields

	private Long id;
	private User user;
	private String userType;// 公司员工、配送点管理员、配送点员工、车辆管理员、司机、用户
	private Dept dept;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dept_id")
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	/** default constructor */
	public SimpleUser() {
	}

	// Property accessors
	@Id//将id设置为数据库主键
	public Long getId() {
		return this.id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userID")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
	}

}