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
import com.domain.TbStorage;
import com.domain.TbGood;
import com.domain.TbStorage;
import com.manage_view.GoodManageView;
import com.manage_view.StorageManageView;
import com.service.InStorageService.Item;
import com.util.*;

public class StorageService extends AbstractTableModel{
	//rowData用来存放行数据
	//columnNames存放列名
	Vector rowData,columnNames;
	AdminService adminService;
	public static void main(String[] args) {

	}
	//添加
	public boolean addStorage(TbStorage s){
		boolean b = true;
		try {
			HibernateUtil.save(s);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//删除 
	public boolean delStorage(String sno){
		boolean b = true;
		String hql = "delete TbStorage where sno=?";
		String paramters[] = {sno};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改 主键不能修改
	public boolean updStorage(TbStorage s){
		boolean b=true;
		try {
			HibernateUtil.update(s);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得客户
	public TbStorage getStorageByNo(String sno){
		TbStorage Storage = (TbStorage)HibernateUtil.findById(TbStorage.class, new Integer(sno));
		return Storage;
	}
	
	//更新StorageManageView
	public static void updateStorageView(String data){
		String hql="from TbStorage where sname like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbStorage";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
		StorageManageView.storageService = new StorageService();
		StorageManageView.storageService.queryStorage(hql, paramer);
		StorageManageView.jt.setModel(StorageManageView.storageService);
	}
	//提供一个返回HashMap<int,TbStorage>
	public Map<Integer, TbStorage> getAllHashStorage(){
		HashMap<Integer, TbStorage> map = new HashMap<Integer, TbStorage>();
		List<TbStorage> list = HibernateUtil.executeQuery("from TbStorage", null);
		for(TbStorage s:list){
			map.put(s.getSno(), s);
		}
		return map;
	}
	//提供一个返回所有的名
	public Vector<Item> getAllName(String type){
		if("admin".equals(type)){
			adminService=new AdminService();
			Map<Integer, TbAdmin> map=adminService.getAllHashAdmin();
			Vector<Item> items=new Vector<Item>();
			for(int key:map.keySet()){
				items.addElement(new Item(map.get(key).getAname(),map.get(key)));
			}
			return items;
		}
		return null;
	}
	
	//执行查询，把数据封装到rowData，columnNames
	public void queryStorage(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("仓库编号");
		columnNames.add("仓库名称");
		columnNames.add("仓库地址");
		columnNames.add("仓库大小");
		columnNames.add("管理员");
		
		//向rowData添加数据
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbStorage s=(TbStorage)list.get(i);
			hang.add(s.getSno()+"");
			hang.add(s.getSname());
			hang.add(s.getSadress());
			hang.add(s.getSbig());
			hang.add(s.getTbAdmin().getAname());
			//Session已关闭报错 需要在TbPicker.hbm.xml加上lazy=false 取消懒加载
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
    public class Item{
    	private String description;
    	private Object object;
		public Item(String description, Object object) {
			super();
			this.description = description;
			this.object = object;
		}
		public String getDescription() {
			return description;
		}
		public Object getObject() {
			return object;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public void setObject(Object object) {
			this.object = object;
		}
    	public String toString(){
    		return this.description;
    	}
    }
}
