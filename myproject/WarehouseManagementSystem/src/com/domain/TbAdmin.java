package com.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * TbAdmin entity. @author MyEclipse Persistence Tools
 */

public class TbAdmin implements java.io.Serializable {

	// Fields

	private Integer ano;
	private String apass;
	private String aname;
	private String asex;
	private String amethod;
	private String address;
	private Set tbStorages = new HashSet(0);
	
	//提供一个全局admin
	public static TbAdmin admin;

	// Constructors

	/** default constructor */
	public TbAdmin() {
	}

	/** full constructor */
	public TbAdmin(String apass, String aname, String asex, String amethod,
			String address, Set tbStorages) {
		this.apass = apass;
		this.aname = aname;
		this.asex = asex;
		this.amethod = amethod;
		this.address = address;
		this.tbStorages = tbStorages;
	}

	// Property accessors

	public Integer getAno() {
		return this.ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getApass() {
		return this.apass;
	}

	public void setApass(String apass) {
		this.apass = apass;
	}

	public String getAname() {
		return this.aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getAsex() {
		return this.asex;
	}

	public void setAsex(String asex) {
		this.asex = asex;
	}

	public String getAmethod() {
		return this.amethod;
	}

	public void setAmethod(String amethod) {
		this.amethod = amethod;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set getTbStorages() {
		return this.tbStorages;
	}

	public void setTbStorages(Set tbStorages) {
		this.tbStorages = tbStorages;
	}

}