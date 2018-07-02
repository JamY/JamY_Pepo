package com.domain;

/**
 * TbClient entity. @author MyEclipse Persistence Tools
 */

public class TbClient implements java.io.Serializable {

	// Fields

	private Integer cno;
	private TbPicker tbPicker;
	private String cname;
	private String ctype;
	private String cmethod;
	private String ccode;
	private Long cphone;
	private String cbz;

	// Constructors

	/** default constructor */
	public TbClient() {
	}

	/** full constructor */
	public TbClient(TbPicker tbPicker, String cname, String ctype,
			String cmethod, String ccode, Long cphone, String cbz) {
		this.tbPicker = tbPicker;
		this.cname = cname;
		this.ctype = ctype;
		this.cmethod = cmethod;
		this.ccode = ccode;
		this.cphone = cphone;
		this.cbz = cbz;
	}

	// Property accessors

	public Integer getCno() {
		return this.cno;
	}

	public void setCno(Integer cno) {
		this.cno = cno;
	}

	public TbPicker getTbPicker() {
		return this.tbPicker;
	}

	public void setTbPicker(TbPicker tbPicker) {
		this.tbPicker = tbPicker;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCtype() {
		return this.ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getCmethod() {
		return this.cmethod;
	}

	public void setCmethod(String cmethod) {
		this.cmethod = cmethod;
	}

	public String getCcode() {
		return this.ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public Long getCphone() {
		return this.cphone;
	}

	public void setCphone(Long cphone) {
		this.cphone = cphone;
	}

	public String getCbz() {
		return this.cbz;
	}

	public void setCbz(String cbz) {
		this.cbz = cbz;
	}

}