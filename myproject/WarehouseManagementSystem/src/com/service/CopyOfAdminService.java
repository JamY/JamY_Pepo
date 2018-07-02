package com.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.table.*;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.domain.TbAdmin;
import com.domain.TbBuyer;
import com.domain.TbGood;
import com.domain.TbPicker;
import com.domain.TbStorage;
import com.domain.User;
import com.service.InStorageService.Item;
import com.util.*;

public class CopyOfAdminService extends AbstractTableModel{
	//rowData�������������
	//columnNames�������
	Vector rowData,columnNames;
	public static void main(String[] args) {

	}
	public boolean checkAdmin(TbAdmin a){
		boolean b = false;
		String hql = "from TbAdmin where ano=? and apass=?";
		String paramters[] = {a.getAno()+"",a.getApass()};
		Object obj = HibernateUtil.uniqueQuery(hql, paramters);
		return b = obj!=null ? true : false;
	}
//	public boolean checkUser(User u){
//		boolean b=false;
//		String sql="select * from users where id=? and passwd=?";
//		String parms[]={u.getId()+"",u.getPasswd()};
//		ArrayList al=SqlHelper.executeQuery3(sql, parms);
//		if(al.size()==1){
//			b=true;
//		}	
//		return b;
//	}
//	//���ѧ��
//	public boolean addUser(User u){
//		boolean b=true;
//		String sql="insert into users(id,username,sex,passwd) values(?,?,?,?)";
//		String parms[]={u.getId()+"",u.getUsername(),u.getSex(),u.getPasswd()};
//		//�����������ͷ��ش���
//		try{
//			SqlHelper.executeUpdate(sql, parms);
//		}catch(Exception e){
//			e.printStackTrace();
//			b=false;
//		}
//		return b;
//	}
//	//ɾ��ѧ��            
//	public boolean delUser(String id)
//	{
//		String sql="delete from users where id=?";
//		String paras[]={id};
//		boolean b=true;
//		try {
//			SqlHelper.executeUpdate(sql, paras);
//		} catch (Exception e) {
//			e.printStackTrace();
//			b=false;
//		}
//		return b;
//		
//	}
//	//�޸�ѧ��
//	public boolean updUser(User u,String checkId){
//		boolean b=true;
//		//�ж�id�Ƿ���ͬ�������ͬ�����sql
//		if(!checkId.equals(u.getId())){
//			//��ʾ���û�д���id����ͬ
//			String sql="update users set id=?,username=?,sex=?,passwd=?,grade=?,Bno=?,Bnum=? where id=?";
//			String params[]={u.getId()+"",u.getUsername(),u.getSex(),u.getPasswd(),u.getGrade(),u.getBno(),u.getBnum(),checkId};
//			try {
//				SqlHelper.executeUpdate(sql, params);
//			} catch (Exception e) {
//				e.printStackTrace();
//				b=false;
//			}
//		}else{
//			String sql="update users set username=?,sex=?,passwd=?,grade=?,Bno=?,Bnum=? where id=?";
//			String params[]={u.getUsername(),u.getSex(),u.getPasswd(),u.getGrade(),u.getBno(),u.getBnum(),u.getId()+""};
//			try {
//				SqlHelper.executeUpdate(sql, params);
//			} catch (Exception e) {
//				e.printStackTrace();
//				b=false;
//			}
//		}
//		return b;
//	}
	//ͨ��noȡ���û�
	public TbAdmin getAdminByNo(String ano){
//      session�ѹرմ������������� ʹ��initialize()���
		TbAdmin admin = (TbAdmin)HibernateUtil.findById(TbAdmin.class, new Integer(ano));
		return admin;
	}
	
	//��ȡ���еĹ���Ա������
	public static List getAllAdminName(){
		String hql="select aname from TbAdmin";
		String paramters[]={};
		return HibernateUtil.executeQuery(hql, paramters);
	}
	
//   //ͨ���������ȡ����Ա
//	public TbAdmin getAdminByName(String aname){
//		String hql = "from TbAdmin where aname=?";
//		String paramters[]={aname};
//		return (TbAdmin)HibernateUtil.uniqueQuery(hql, paramters);
//	}
	//�ṩһ������HashMap<int,TbAdmin>
	public Map<Integer, TbAdmin> getAllHashAdmin(){
		HashMap<Integer, TbAdmin> map = new HashMap<Integer, TbAdmin>();
		List<TbAdmin> list = HibernateUtil.executeQuery("from TbAdmin", null);
		for(TbAdmin a:list){
			map.put(a.getAno(), a);
		}
		return map;
	}
	
	//ִ�в�ѯ�������ݷ�װ��rowData��columnNames
	public void queryUser(String hql,String []paramters){
		//��columnNames�������
		columnNames=new Vector();
		columnNames.add("����֤��");
		columnNames.add("�û���");
		columnNames.add("�Ա�");
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("�ѽ����ͼ��");
		columnNames.add("���ͼ������");
		
		//��rowData�������
		List al=HibernateUtil.executeQuery(hql, paramters);
		rowData=new Vector();
		for(int i=0;i<al.size();i++){
			Vector hang=new Vector();
			Object ob[]=(Object[])al.get(i);
			hang.add(ob[0]);
			hang.add(ob[1]);
			hang.add(ob[2]);
			hang.add(ob[3]);
			hang.add(ob[4]);
			hang.add(ob[5]);
			hang.add(ob[6]);
			rowData.add(hang);
		}
	}
	
	//�õ����Ҷ�����,�Զ�����
	public int getColumnCount() {
		// TODO �Զ����ɵķ������
		return this.columnNames.size();
	}

	//�õ����е����У��Զ�����
	public int getRowCount() {
		// TODO �Զ����ɵķ������
		return this.rowData.size();
	}

	//�õ�ĳ��ĳ�е����ݣ��Զ�����
	public Object getValueAt(int row, int column) {
		// TODO �Զ����ɵķ������
		return ((Vector)this.rowData.get(row)).get(column);
	}
    public String getColumnName(int arg0) {
		// TODO �Զ����ɵķ������
		return (String)this.columnNames.get(arg0);
	}
}
