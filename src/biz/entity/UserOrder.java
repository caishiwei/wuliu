package biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_order")
public class UserOrder {
	private Long id;
	private String userPhone;
	private String userName;
	private String address;
	private String addDate;
	private Dept startDept;
	private Dept endDept;
	private String orderStatus;//新订单(只有地址,没有站点站)
	//待配送(设定终点站)
	//运输中(装车)
	//已运达(到终点站)
	//已签收

	private String goodsName;
	private double goodsWeight;
	private double money;

	private String fromUserName;
	private String fromUserPhone;
	private String fromUserAddress;

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getFromUserPhone() {
		return fromUserPhone;
	}

	public void setFromUserPhone(String fromUserPhone) {
		this.fromUserPhone = fromUserPhone;
	}

	public String getFromUserAddress() {
		return fromUserAddress;
	}

	public void setFromUserAddress(String fromUserAddress) {
		this.fromUserAddress = fromUserAddress;
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserPhone() {
		return userPhone;
	}

	@Column(nullable = false)
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Column(nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(nullable = false)
	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "start_dept_id")
	public Dept getStartDept() {
		return startDept;
	}

	public void setStartDept(Dept startDept) {
		this.startDept = startDept;
	}

	@Column(nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "end_dept_id")
	public Dept getEndDept() {
		return endDept;
	}

	public void setEndDept(Dept endDept) {
		this.endDept = endDept;
	}

	@Column(nullable = false)
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(nullable = false)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(nullable = false)
	public double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	@Column(nullable = false)
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

}
