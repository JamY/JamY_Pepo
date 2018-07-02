package com.domain;

/**
 * TbOutStorageId entity. @author MyEclipse Persistence Tools
 */

public class TbOutStorageId implements java.io.Serializable {

	// Fields

	private TbGood tbGood;
	private TbPicker tbPicker;
	private TbStorage tbStorage;
	private String odate;
	private Integer onum;

	// Constructors

	/** default constructor */
	public TbOutStorageId() {
	}

	/** full constructor */
	public TbOutStorageId(TbGood tbGood, TbPicker tbPicker,
			TbStorage tbStorage, String odate, Integer onum) {
		this.tbGood = tbGood;
		this.tbPicker = tbPicker;
		this.tbStorage = tbStorage;
		this.odate = odate;
		this.onum = onum;
	}

	// Property accessors

	public TbGood getTbGood() {
		return this.tbGood;
	}

	public void setTbGood(TbGood tbGood) {
		this.tbGood = tbGood;
	}

	public TbPicker getTbPicker() {
		return this.tbPicker;
	}

	public void setTbPicker(TbPicker tbPicker) {
		this.tbPicker = tbPicker;
	}

	public TbStorage getTbStorage() {
		return this.tbStorage;
	}

	public void setTbStorage(TbStorage tbStorage) {
		this.tbStorage = tbStorage;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbOutStorageId))
			return false;
		TbOutStorageId castOther = (TbOutStorageId) other;

		return ((this.getTbGood() == castOther.getTbGood()) || (this
				.getTbGood() != null && castOther.getTbGood() != null && this
				.getTbGood().equals(castOther.getTbGood())))
				&& ((this.getTbPicker() == castOther.getTbPicker()) || (this
						.getTbPicker() != null
						&& castOther.getTbPicker() != null && this
						.getTbPicker().equals(castOther.getTbPicker())))
				&& ((this.getTbStorage() == castOther.getTbStorage()) || (this
						.getTbStorage() != null
						&& castOther.getTbStorage() != null && this
						.getTbStorage().equals(castOther.getTbStorage())))
				&& ((this.getOdate() == castOther.getOdate()) || (this
						.getOdate() != null && castOther.getOdate() != null && this
						.getOdate().equals(castOther.getOdate())))
				&& ((this.getOnum() == castOther.getOnum()) || (this.getOnum() != null
						&& castOther.getOnum() != null && this.getOnum()
						.equals(castOther.getOnum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTbGood() == null ? 0 : this.getTbGood().hashCode());
		result = 37 * result
				+ (getTbPicker() == null ? 0 : this.getTbPicker().hashCode());
		result = 37 * result
				+ (getTbStorage() == null ? 0 : this.getTbStorage().hashCode());
		result = 37 * result
				+ (getOdate() == null ? 0 : this.getOdate().hashCode());
		result = 37 * result
				+ (getOnum() == null ? 0 : this.getOnum().hashCode());
		return result;
	}

}