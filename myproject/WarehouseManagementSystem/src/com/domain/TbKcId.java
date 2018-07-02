package com.domain;

/**
 * TbKcId entity. @author MyEclipse Persistence Tools
 */

public class TbKcId implements java.io.Serializable {

	// Fields

	private TbGood tbGood;
	private TbStorage tbStorage;
	private Integer knum;
	private String kdate;

	// Constructors

	/** default constructor */
	public TbKcId() {
	}

	/** full constructor */
	public TbKcId(TbGood tbGood, TbStorage tbStorage, Integer knum, String kdate) {
		this.tbGood = tbGood;
		this.tbStorage = tbStorage;
		this.knum = knum;
		this.kdate = kdate;
	}

	// Property accessors

	public TbGood getTbGood() {
		return this.tbGood;
	}

	public void setTbGood(TbGood tbGood) {
		this.tbGood = tbGood;
	}

	public TbStorage getTbStorage() {
		return this.tbStorage;
	}

	public void setTbStorage(TbStorage tbStorage) {
		this.tbStorage = tbStorage;
	}

	public Integer getKnum() {
		return this.knum;
	}

	public void setKnum(Integer knum) {
		this.knum = knum;
	}

	public String getKdate() {
		return this.kdate;
	}

	public void setKdate(String kdate) {
		this.kdate = kdate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbKcId))
			return false;
		TbKcId castOther = (TbKcId) other;

		return ((this.getTbGood() == castOther.getTbGood()) || (this
				.getTbGood() != null && castOther.getTbGood() != null && this
				.getTbGood().equals(castOther.getTbGood())))
				&& ((this.getTbStorage() == castOther.getTbStorage()) || (this
						.getTbStorage() != null
						&& castOther.getTbStorage() != null && this
						.getTbStorage().equals(castOther.getTbStorage())))
				&& ((this.getKnum() == castOther.getKnum()) || (this.getKnum() != null
						&& castOther.getKnum() != null && this.getKnum()
						.equals(castOther.getKnum())))
				&& ((this.getKdate() == castOther.getKdate()) || (this
						.getKdate() != null && castOther.getKdate() != null && this
						.getKdate().equals(castOther.getKdate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTbGood() == null ? 0 : this.getTbGood().hashCode());
		result = 37 * result
				+ (getTbStorage() == null ? 0 : this.getTbStorage().hashCode());
		result = 37 * result
				+ (getKnum() == null ? 0 : this.getKnum().hashCode());
		result = 37 * result
				+ (getKdate() == null ? 0 : this.getKdate().hashCode());
		return result;
	}

}