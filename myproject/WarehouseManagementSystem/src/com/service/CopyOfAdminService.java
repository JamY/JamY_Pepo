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
	//rowData用来存放行数据
	//columnNames存放列名
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
//	//添加学生
//	public boolean addUser(User u){
//		boolean b=true;
//		String sql="insert into users(id,username,sex,passwd) values(?,?,?,?)";
//		String parms[]={u.getId()+"",u.getUsername(),u.getSex(),u.getPasswd()};
//		//如果发生错误就返回错误
//		try{
//			SqlHelper.executeUpdate(sql, parms);
//		}catch(Exception e){
//			e.printStackTrace();
//			b=false;
//		}
//		return b;
//	}
//	//删除学生            
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
//	//修改学生
//	public boolean updUser(User u,String checkId){
//		boolean b=true;
//		//判断id是否相同，如果相同则更换sql
//		if(!checkId.equals(u.getId())){
//			//表示与用户写入的id不相同
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
	//通过no取得用户
	public TbAdmin getAdminByNo(String ano){
//      session已关闭存在懒加载问题 使用initialize()解决
		TbAdmin admin = (TbAdmin)HibernateUtil.findById(TbAdmin.class, new Integer(ano));
		return admin;
	}
	
	//获取所有的管理员的名称
	public static List getAllAdminName(){
		String hql="select aname from TbAdmin";
		String paramters[]={};
		return HibernateUtil.executeQuery(hql, paramters);
	}
	
//   //通过提货名获取管理员
//	public TbAdmin getAdminByName(String aname){
//		String hql = "from TbAdmin where aname=?";
//		String paramters[]={aname};
//		return (TbAdmin)HibernateUtil.uniqueQuery(hql, paramters);
//	}
	//提供一个返回HashMap<int,TbAdmin>
	public Map<Integer, TbAdmin> getAllHashAdmin(){
		HashMap<Integer, TbAdmin> map = new HashMap<Integer, TbAdmin>();
		List<TbAdmin> list = HibernateUtil.executeQuery("from TbAdmin", null);
		for(TbAdmin a:list){
			map.put(a.getAno(), a);
		}
		return map;
	}
	
	//执行查询，把数据封装到rowData，columnNames
	public void queryUser(String hql,String []paramters){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("借书证号");
		columnNames.add("用户名");
		columnNames.add("性别");
		columnNames.add("密码");
		columnNames.add("级别");
		columnNames.add("已借出的图书");
		columnNames.add("借出图书数本");
		
		//向rowData添加数据
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
	
	//得到共右多少列,自动调用
	public int getColumnCount() {
		// TODO 自动生成的方法存根
		return this.columnNames.size();
	}

	//得到共有到少行，自动调用
	public int getRowCount() {
		// TODO 自动生成的方法存根
		return this.rowData.size();
	}

	//得到某行某列的数据，自动调用
	public Object getValueAt(int row, int column) {
		// TODO 自动生成的方法存根
		return ((Vector)this.rowData.get(row)).get(column);
	}
    public String getColumnName(int arg0) {
		// TODO 自动生成的方法存根
		return (String)this.columnNames.get(arg0);
	}
}
