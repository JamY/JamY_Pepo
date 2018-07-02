package com.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.*;

import com.domain.TbAdmin;
import com.domain.TbBuyer;
import com.domain.TbGood;
import com.manage_view.GoodManageView;
import com.util.*;

public class AdminService extends AbstractTableModel{
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
	//获取所有的管理员的名称
	public static List getAllAdminName(){
		String hql="select aname from TbAdmin";
		String paramters[]={};
		return HibernateUtil.executeQuery(hql, paramters);
	}
	//添加
	public boolean addAdmin(TbAdmin a){
		boolean b = true;
		try {
			HibernateUtil.save(a);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//删除            
	public boolean delAdmin(String ano){
		boolean b = true;
		String hql = "delete TbAdmin where ano=?";
		String paramters[] = {ano};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改 主键不能修改
	public boolean updAdmin(TbAdmin a){
		boolean b=true;
		try {
			HibernateUtil.update(a);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得
	public TbAdmin getAdminByNo(String ano){
		TbAdmin a = (TbAdmin)HibernateUtil.findById(TbAdmin.class, new Integer(ano));
		return a;
	}
	
	//更新AdminManageView
	public static void updateAdminView(String data){
		String hql="from TbAdmin where aname like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbAdmin";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
//		AdminManageView.adminService = new AdminService();
//		AdminManageView.adminService.queryAdmin(hql, paramer);
//		AdminManageView.jt.setModel(AdminManageView.adminService);
	}
	
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
	public void queryAdmin(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("管理员账号");
		columnNames.add("管理员密码");
		columnNames.add("管理员名称");
		columnNames.add("性别");
		columnNames.add("联系方式");
		columnNames.add("联系地址");
		
		//向rowData添加数据
//		ArrayList al=SqlHelper.executeQuery3(sql, paramer);
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbAdmin a=(TbAdmin)list.get(i);
			hang.add(a.getAno());
			hang.add(a.getApass());
			hang.add(a.getAname());
			hang.add(a.getAsex());
			hang.add(a.getAmethod());
			hang.add(a.getAddress());
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
