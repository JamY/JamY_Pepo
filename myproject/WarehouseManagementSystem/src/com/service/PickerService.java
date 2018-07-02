package com.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.*;

import com.domain.TbGood;
import com.domain.TbPicker;
import com.manage_view.GoodManageView;
import com.manage_view.PickerManageView;
import com.util.*;

public class PickerService extends AbstractTableModel{
	//rowData用来存放行数据
	//columnNames存放列名
	Vector rowData,columnNames;
	public static void main(String[] args) {

	}
	//添加提货员
	public boolean addPicker(TbPicker p){
		boolean b = true;
		try {
			HibernateUtil.save(p);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//删除提货员            
	public boolean delPicker(String pno){
		boolean b = true;
		String hql = "delete TbPicker where pno=?";
		String paramters[] = {pno};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改提货员 主键Gno不能修改
	public boolean updPicker(TbPicker p){
		boolean b=true;
		try {
			HibernateUtil.update(p);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得提货员
	public TbPicker getPickerByNo(String pno){
		TbPicker good = (TbPicker)HibernateUtil.findById(TbPicker.class, new Integer(pno));
		return good;
	}
	
	//更新PickerManageView
	public static void updatePickerView(String data){
		String hql="from TbPicker where pname like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbPicker";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
		PickerManageView.pickerService = new PickerService();
		PickerManageView.pickerService.queryPicker(hql, paramer);
		PickerManageView.jt.setModel(PickerManageView.pickerService);
	}
	
	//获取所有的提货员的名称
	public static List getAllPickerName(){
		String hql="select pname from TbPicker";
		String paramters[]={};
		return HibernateUtil.executeQuery(hql, paramters);
	}
	
   //通过提货名获取提货员
	public TbPicker getPickerByName(String pname){
		String hql = "from TbPicker where pname=?";
		String paramters[]={pname};
		return (TbPicker)HibernateUtil.uniqueQuery(hql, paramters);
	}
	
	//提供一个返回HashMap<int,TbPicker>
	public Map<Integer, TbPicker> getAllHashPicker(){
		HashMap<Integer, TbPicker> map = new HashMap<Integer, TbPicker>();
		List<TbPicker> list = HibernateUtil.executeQuery("from TbPicker", null);
		for(TbPicker p:list){
			map.put(p.getPno(), p);
		}
		return map;
	}

	//执行查询，把数据封装到rowData，columnNames
	public void queryPicker(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("提货员编号");
		columnNames.add("提货员名称");
		columnNames.add("性别");
		columnNames.add("联系方式");
		columnNames.add("联系地址");
		
		//向rowData添加数据
//		ArrayList al=SqlHelper.executeQuery3(sql, paramer);
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbPicker p=(TbPicker)list.get(i);
			hang.add(p.getPno());
			hang.add(p.getPname());
			hang.add(p.getPsex());
			hang.add(p.getPmethod());
			hang.add(p.getPaddress());
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
