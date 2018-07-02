package com.domain;

/**
 * TbOutStorage entity. @author MyEclipse Persistence Tools
 */

public class TbOutStorage implements java.io.Serializable {

	// Fields

	private Integer ono;
	private TbStorage tbStorage;
	private TbPicker tbPicker;
	private TbGood tbGood;
	private String oaddress;
	private String odate;
	private Integer onum;

	// Constructors

	/** default constructor */
	public TbOutStorage() {
	}

	/** full constructor */
	public TbOutStorage(TbStorage tbStorage, TbPicker tbPicker, TbGood tbGood,
			String oaddress, String odate, Integer onum) {
		this.tbStorage = tbStorage;
		this.tbPicker = tbPicker;
		this.tbGood = tbGood;
		this.oaddress = oaddress;
		this.odate = odate;
		this.onum = onum;
	}

	// Property accessors

	public Integer getOno() {
		return this.ono;
	}

	public void setOno(Integer ono) {
		this.ono = ono;
	}

	public TbStorage getTbStorage() {
		return this.tbStorage;
	}

	public void setTbStorage(TbStorage tbStorage) {
		this.tbStorage = tbStorage;
	}

	public TbPicker getTbPicker() {
		return this.tbPicker;
	}

	public void setTbPicker(TbPicker tbPicker) {
		this.tbPicker = tbPicker;
	}

	public TbGood getTbGood() {
		return this.tbGood;
	}

	public void setTbGood(TbGood tbGood) {
		this.tbGood = tbGood;
	}

	public String getOaddress() {
		return this.oaddress;
	}

	public void setOaddress(String oaddress) {
		this.oaddress = oaddress;
	}

	public String getOdate() {
		return this.odate;
	}

	public void setOdate(String odate) {
		this.odate = odate;
	}

	public Integer getOnum() {
		return this.onum;
	}

	public void setOnum(Integer onum) {
		this.onum = onum;
	}

}