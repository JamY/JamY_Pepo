package com.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * TbPicker entity. @author MyEclipse Persistence Tools
 */

public class TbPicker implements java.io.Serializable {

	// Fields

	private Integer pno;
	private String pname;
	private String psex;
	private String pmethod;
	private String paddress;
	private Set tbOutStorages = new HashSet(0);
	private Set tbClients = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbPicker() {
	}

	/** full constructor */
	public TbPicker(String pname, String psex, String pmethod, String paddress,
			Set tbOutStorages, Set tbClients) {
		this.pname = pname;
		this.psex = psex;
		this.pmethod = pmethod;
		this.paddress = paddress;
		this.tbOutStorages = tbOutStorages;
		this.tbClients = tbClients;
	}

	// Property accessors

	public Integer getPno() {
		return this.pno;
	}

	public void setPno(Integer pno) {
		this.pno = pno;
	}

	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPsex() {
		return this.psex;
	}

	public void setPsex(String psex) {
		this.psex = psex;
	}

	public String getPmethod() {
		return this.pmethod;
	}

	public void setPmethod(String pmethod) {
		this.pmethod = pmethod;
	}

	public String getPaddress() {
		return this.paddress;
	}

	public void setPaddress(String paddress) {
		this.paddress = paddress;
	}

	public Set getTbOutStorages() {
		return this.tbOutStorages;
	}

	public void setTbOutStorages(Set tbOutStorages) {
		this.tbOutStorages = tbOutStorages;
	}

	public Set getTbClients() {
		return this.tbClients;
	}

	public void setTbClients(Set tbClients) {
		this.tbClients = tbClients;
	}

}