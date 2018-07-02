package com.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * TbBuyer entity. @author MyEclipse Persistence Tools
 */

public class TbBuyer implements java.io.Serializable {

	// Fields

	private Integer bno;
	private String bname;
	private String bsex;
	private String bmethod;
	private String baddress;
	private Set tbInStorages = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbBuyer() {
	}

	/** full constructor */
	public TbBuyer(String bname, String bsex, String bmethod, String baddress,
			Set tbInStorages) {
		this.bname = bname;
		this.bsex = bsex;
		this.bmethod = bmethod;
		this.baddress = baddress;
		this.tbInStorages = tbInStorages;
	}

	// Property accessors

	public Integer getBno() {
		return this.bno;
	}

	public void setBno(Integer bno) {
		this.bno = bno;
	}

	public String getBname() {
		return this.bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBsex() {
		return this.bsex;
	}

	public void setBsex(String bsex) {
		this.bsex = bsex;
	}

	public String getBmethod() {
		return this.bmethod;
	}

	public void setBmethod(String bmethod) {
		this.bmethod = bmethod;
	}

	public String getBaddress() {
		return this.baddress;
	}

	public void setBaddress(String baddress) {
		this.baddress = baddress;
	}

	public Set getTbInStorages() {
		return this.tbInStorages;
	}

	public void setTbInStorages(Set tbInStorages) {
		this.tbInStorages = tbInStorages;
	}

}