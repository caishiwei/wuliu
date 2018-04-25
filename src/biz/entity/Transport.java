package biz.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import biz.entity.main.SimpleUser;

@Entity
@Table(name = "t_transport")
public class Transport {
	private Long id;
	private Truck truck;
	private SimpleUser driver;
	private Line line;
	private String addDate;

	private int transIndex;

	private String transStatus;//运输中,完成
	
	public int getTransIndex() {
		return transIndex;
	}

	public void setTransIndex(int transIndex) {
		this.transIndex = transIndex;
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "truck_id")
	public Truck getTruck() {
		return truck;
	}

	public void setTruck(Truck truck) {
		this.truck = truck;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver_id")
	public SimpleUser getDriver() {
		return driver;
	}

	public void setDriver(SimpleUser driver) {
		this.driver = driver;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "line_id")
	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

}
