package com.domain;

public class User {
	private int uno;
	private String uname;
	private String upass;
	private int ugrade;
	//����ȫ��user
	//��ʾ�û��������̵�¼����Ϣ
	public static User user;
	public int getUno() {
		return uno;
	}
	public String getUname() {
		return uname;
	}
	public String getUpass() {
		return upass;
	}
	public int getUgrade() {
		return ugrade;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public void setUpass(String upass) {
		this.upass = upass;
	}
	public void setUgrade(int ugrade) {
		this.ugrade = ugrade;
	}
	@Override
	public String toString() {
		return "User [uno=" + uno + ", uname=" + uname + ", upass=" + upass
				+ ", ugrade=" + ugrade + "]";
	}
}
