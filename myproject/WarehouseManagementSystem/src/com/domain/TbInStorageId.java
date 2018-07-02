package com.domain;

/**
 * TbInStorageId entity. @author MyEclipse Persistence Tools
 */

public class TbInStorageId implements java.io.Serializable {

	// Fields

	private TbGood tbGood;
	private TbBuyer tbBuyer;
	private TbStorage tbStorage;
	private String idate;
	private Integer inum;

	// Constructors

	/** default constructor */
	public TbInStorageId() {
	}

	/** full constructor */
	public TbInStorageId(TbGood tbGood, TbBuyer tbBuyer, TbStorage tbStorage,
			String idate, Integer inum) {
		this.tbGood = tbGood;
		this.tbBuyer = tbBuyer;
		this.tbStorage = tbStorage;
		this.idate = idate;
		this.inum = inum;
	}

	// Property accessors

	public TbGood getTbGood() {
		return this.tbGood;
	}

	public void setTbGood(TbGood tbGood) {
		this.tbGood = tbGood;
	}

	public TbBuyer getTbBuyer() {
		return this.tbBuyer;
	}

	public void setTbBuyer(TbBuyer tbBuyer) {
		this.tbBuyer = tbBuyer;
	}

	public TbStorage getTbStorage() {
		return this.tbStorage;
	}

	public void setTbStorage(TbStorage tbStorage) {
		this.tbStorage = tbStorage;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbInStorageId))
			return false;
		TbInStorageId castOther = (TbInStorageId) other;

		return ((this.getTbGood() == castOther.getTbGood()) || (this
				.getTbGood() != null && castOther.getTbGood() != null && this
				.getTbGood().equals(castOther.getTbGood())))
				&& ((this.getTbBuyer() == castOther.getTbBuyer()) || (this
						.getTbBuyer() != null && castOther.getTbBuyer() != null && this
						.getTbBuyer().equals(castOther.getTbBuyer())))
				&& ((this.getTbStorage() == castOther.getTbStorage()) || (this
						.getTbStorage() != null
						&& castOther.getTbStorage() != null && this
						.getTbStorage().equals(castOther.getTbStorage())))
				&& ((this.getIdate() == castOther.getIdate()) || (this
						.getIdate() != null && castOther.getIdate() != null && this
						.getIdate().equals(castOther.getIdate())))
				&& ((this.getInum() == castOther.getInum()) || (this.getInum() != null
						&& castOther.getInum() != null && this.getInum()
						.equals(castOther.getInum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTbGood() == null ? 0 : this.getTbGood().hashCode());
		result = 37 * result
				+ (getTbBuyer() == null ? 0 : this.getTbBuyer().hashCode());
		result = 37 * result
				+ (getTbStorage() == null ? 0 : this.getTbStorage().hashCode());
		result = 37 * result
				+ (getIdate() == null ? 0 : this.getIdate().hashCode());
		result = 37 * result
				+ (getInum() == null ? 0 : this.getInum().hashCode());
		return result;
	}

}