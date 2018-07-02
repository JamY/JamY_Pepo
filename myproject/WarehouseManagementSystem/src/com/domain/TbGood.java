package com.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * TbGood entity. @author MyEclipse Persistence Tools
 */

public class TbGood implements java.io.Serializable {

	// Fields

	@Override
	public String toString() {
		return "TbGood [gno=" + gno + ", ghao=" + ghao + ", gname=" + gname
				+ ", gdate=" + gdate + ", gfactory=" + gfactory + ", gprice="
				+ gprice + ", idate=" + idate + ", tbOutStorages="
				+ tbOutStorages + ", tbKcs=" + tbKcs + ", tbInStorages="
				+ tbInStorages + "]";
	}

	private Integer gno;
	private String ghao;
	private String gname;
	private String gdate;
	private String gfactory;
	private Double gprice;
	private String idate;
	private Set tbOutStorages = new HashSet(0);
	private Set tbKcs = new HashSet(0);
	private Set tbInStorages = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbGood() {
	}

	/** full constructor */
	public TbGood(String ghao, String gname, String gdate, String gfactory,
			Double gprice, String idate, Set tbOutStorages, Set tbKcs,
			Set tbInStorages) {
		this.ghao = ghao;
		this.gname = gname;
		this.gdate = gdate;
		this.gfactory = gfactory;
		this.gprice = gprice;
		this.idate = idate;
		this.tbOutStorages = tbOutStorages;
		this.tbKcs = tbKcs;
		this.tbInStorages = tbInStorages;
	}

	// Property accessors

	public Integer getGno() {
		return this.gno;
	}

	public void setGno(Integer gno) {
		this.gno = gno;
	}

	public String getGhao() {
		return this.ghao;
	}

	public void setGhao(String ghao) {
		this.ghao = ghao;
	}

	public String getGname() {
		return this.gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getGdate() {
		return this.gdate;
	}

	public void setGdate(String gdate) {
		this.gdate = gdate;
	}

	public String getGfactory() {
		return this.gfactory;
	}

	public void setGfactory(String gfactory) {
		this.gfactory = gfactory;
	}

	public Double getGprice() {
		return this.gprice;
	}

	public void setGprice(Double gprice) {
		this.gprice = gprice;
	}

	public String getIdate() {
		return this.idate;
	}

	public void setIdate(String idate) {
		this.idate = idate;
	}

	public Set getTbOutStorages() {
		return this.tbOutStorages;
	}

	public void setTbOutStorages(Set tbOutStorages) {
		this.tbOutStorages = tbOutStorages;
	}

	public Set getTbKcs() {
		return this.tbKcs;
	}

	public void setTbKcs(Set tbKcs) {
		this.tbKcs = tbKcs;
	}

	public Set getTbInStorages() {
		return this.tbInStorages;
	}

	public void setTbInStorages(Set tbInStorages) {
		this.tbInStorages = tbInStorages;
	}

}