package com.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.*;

import com.domain.TbBuyer;
import com.domain.TbGood;
import com.domain.TbUser;
import com.manage_view.GoodManageView;
import com.manage_view.UserManageView;
import com.util.*;

public class UserService extends AbstractTableModel{
	//rowData用来存放行数据
	//columnNames存放列名
	Vector rowData,columnNames;
	public static void main(String[] args) {

	}
	public boolean checkUser(TbUser u) {
		boolean b = false;
		String hql = "from TbUser where uno=? and upass=?";
		String paramters[] = {u.getUno()+"",u.getUpass()};
		Object obj = HibernateUtil.uniqueQuery(hql, paramters);
		return b = obj!=null ? true : false;
	}
	//添加
	public boolean addUser(TbUser u){
		boolean b = true;
		try {
			HibernateUtil.save(u);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//删除        
	public boolean delUser(String uno){
		boolean b = true;
		String hql = "delete TbUser where uno=?";
		String paramters[] = {uno};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改 主键不能修改
	public boolean updUser(TbUser u){
		boolean b=true;
		try {
			HibernateUtil.update(u);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得货物
	public TbUser getUserByNo(String uno){
		TbUser u = (TbUser)HibernateUtil.findById(TbUser.class, new Integer(uno));
		return u;
	}
	
	//更新UserManageView
	public static void updateUserView(String data){
		String hql="from TbUser where uname like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbUser";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
		UserManageView.userService = new UserService();
		UserManageView.userService.queryUser(hql, paramer);
		UserManageView.jt.setModel(UserManageView.userService);
	}
	
	//提供一个返回HashMap<int,TbGood>
	public Map<Integer, TbUser> getAllHashUser(){
		HashMap<Integer, TbUser> map = new HashMap<Integer, TbUser>();
		List<TbUser> list = HibernateUtil.executeQuery("from TbUser", null);
		for(TbUser u:list){
			map.put(u.getUno(), u);
		}
		return map;
	}
	
	//执行查询，把数据封装到rowData，columnNames
	public void queryUser(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("用户编号");
		columnNames.add("用户名称");
		if(TbUser.user==null){
			columnNames.add("用户密码");
		}
		columnNames.add("用户权限");

		//向rowData添加数据
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbUser u=(TbUser)list.get(i);
			hang.add(u.getUno());
			hang.add(u.getUname());
			hang.add(u.getUpass());
			hang.add(u.getUgrade());
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
    @Override//该方法自动调几次
	public String getColumnName(int arg0) {
		// TODO 自动生成的方法存根
		return (String)this.columnNames.get(arg0);
	}
}
