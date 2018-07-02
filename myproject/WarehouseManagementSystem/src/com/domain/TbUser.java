package com.domain;

/**
 * TbUser entity. @author MyEclipse Persistence Tools
 */

public class TbUser implements java.io.Serializable {

	// Fields

	private Integer uno;
	private String uname;
	private String upass;
	private Integer ugrade;
	public static TbUser user;

	// Constructors

	/** default constructor */
	public TbUser() {
	}

	/** full constructor */
	public TbUser(String uname, String upass, Integer ugrade) {
		this.uname = uname;
		this.upass = upass;
		this.ugrade = ugrade;
	}

	// Property accessors

	public Integer getUno() {
		return this.uno;
	}

	public void setUno(Integer uno) {
		this.uno = uno;
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpass() {
		return this.upass;
	}

	public void setUpass(String upass) {
		this.upass = upass;
	}

	public Integer getUgrade() {
		return this.ugrade;
	}

	public void setUgrade(Integer ugrade) {
		this.ugrade = ugrade;
	}

}