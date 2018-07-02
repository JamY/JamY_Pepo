package com.domain;

/**
 * TbInStorage entity. @author MyEclipse Persistence Tools
 */

public class TbInStorage implements java.io.Serializable {

	// Fields

	private Integer ino;
	private TbStorage tbStorage;
	private TbBuyer tbBuyer;
	private TbGood tbGood;
	private String iaddress;
	private String idate;
	private Integer inum;

	// Constructors

	/** default constructor */
	public TbInStorage() {
	}

	/** full constructor */
	public TbInStorage(TbStorage tbStorage, TbBuyer tbBuyer, TbGood tbGood,
			String iaddress, String idate, Integer inum) {
		this.tbStorage = tbStorage;
		this.tbBuyer = tbBuyer;
		this.tbGood = tbGood;
		this.iaddress = iaddress;
		this.idate = idate;
		this.inum = inum;
	}

	// Property accessors

	public Integer getIno() {
		return this.ino;
	}

	public void setIno(Integer ino) {
		this.ino = ino;
	}

	public TbStorage getTbStorage() {
		return this.tbStorage;
	}

	public void setTbStorage(TbStorage tbStorage) {
		this.tbStorage = tbStorage;
	}

	public TbBuyer getTbBuyer() {
		return this.tbBuyer;
	}

	public void setTbBuyer(TbBuyer tbBuyer) {
		this.tbBuyer = tbBuyer;
	}

	public TbGood getTbGood() {
		return this.tbGood;
	}

	public void setTbGood(TbGood tbGood) {
		this.tbGood = tbGood;
	}

	public String getIaddress() {
		return this.iaddress;
	}

	public void setIaddress(String iaddress) {
		this.iaddress = iaddress;
	}

	public String getIdate() {
		return this.idate;
	}

	public void setIdate(String idate) {
		this.idate = idate;
	}

	public Integer getInum() {
		return this.inum;
	}

	public void setInum(Integer inum) {
		this.inum = inum;
	}

}