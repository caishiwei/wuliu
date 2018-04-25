package biz.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_line")
public class Line {
	private Long id;
	private String name;
	private Dept startDept;
	private Dept endDept;

	@Transient
	public String getDesc() {
		return startDept.getName() + " - " + endDept.getName();
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "start_dept_id")
	public Dept getStartDept() {
		return startDept;
	}

	public void setStartDept(Dept startDept) {
		this.startDept = startDept;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "end_dept_id")
	public Dept getEndDept() {
		return endDept;
	}

	public void setEndDept(Dept endDept) {
		this.endDept = endDept;
	}

}
