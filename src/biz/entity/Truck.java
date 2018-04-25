package biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_truck")
public class Truck {
	private Long id;
	private String licensePlate;//车牌号、
	private String vtype;//车型、
	private String vlength;//车长、
	private String vload;//载重、
	private String vinner;//内径、
	private String useStatus;//空闲,运输中,报废

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, unique = true)
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getVlength() {
		return vlength;
	}

	public void setVlength(String vlength) {
		this.vlength = vlength;
	}

	public String getVload() {
		return vload;
	}

	public void setVload(String vload) {
		this.vload = vload;
	}

	public String getVinner() {
		return vinner;
	}

	public void setVinner(String vinner) {
		this.vinner = vinner;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

}
