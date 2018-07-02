package com.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * TbStorage entity. @author MyEclipse Persistence Tools
 */

public class TbStorage implements java.io.Serializable {

	// Fields

	private Integer sno;
	private TbAdmin tbAdmin;
	private String sname;
	private String sadress;
	private Long sbig;
	private Set tbKcs = new HashSet(0);
	private Set tbOutStorages = new HashSet(0);
	private Set tbInStorages = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbStorage() {
	}

	/** full constructor */
	public TbStorage(TbAdmin tbAdmin, String sname, String sadress, Long sbig,
			Set tbKcs, Set tbOutStorages, Set tbInStorages) {
		this.tbAdmin = tbAdmin;
		this.sname = sname;
		this.sadress = sadress;
		this.sbig = sbig;
		this.tbKcs = tbKcs;
		this.tbOutStorages = tbOutStorages;
		this.tbInStorages = tbInStorages;
	}

	// Property accessors

	public Integer getSno() {
		return this.sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public TbAdmin getTbAdmin() {
		return this.tbAdmin;
	}

	public void setTbAdmin(TbAdmin tbAdmin) {
		this.tbAdmin = tbAdmin;
	}

	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSadress() {
		return this.sadress;
	}

	public void setSadress(String sadress) {
		this.sadress = sadress;
	}

	public Long getSbig() {
		return this.sbig;
	}

	public void setSbig(Long sbig) {
		this.sbig = sbig;
	}

	public Set getTbKcs() {
		return this.tbKcs;
	}

	public void setTbKcs(Set tbKcs) {
		this.tbKcs = tbKcs;
	}

	public Set getTbOutStorages() {
		return this.tbOutStorages;
	}

	public void setTbOutStorages(Set tbOutStorages) {
		this.tbOutStorages = tbOutStorages;
	}

	public Set getTbInStorages() {
		return this.tbInStorages;
	}

	public void setTbInStorages(Set tbInStorages) {
		this.tbInStorages = tbInStorages;
	}

}