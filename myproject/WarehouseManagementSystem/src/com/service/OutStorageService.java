package com.service;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.*;

import com.domain.TbBuyer;
import com.domain.TbGood;
import com.domain.TbInStorage;
import com.domain.TbKc;
import com.domain.TbOutStorage;
import com.domain.TbPicker;
import com.domain.TbStorage;
import com.manage_view.InStorageManageView;
import com.manage_view.OutStorageManageView;
import com.util.*;

public class OutStorageService extends AbstractTableModel{
	//rowData用来存放行数据
	//columnNames存放列名
	Vector rowData,columnNames;
	
	GoodService goodService;
	StorageService storageService;
	PickerService pickerService;
	KcService kcService;
	
	public static void main(String[] args) {

	}
	//添加
	public boolean addOutStorage(TbOutStorage o){
		boolean b = true;
		try {
			HibernateUtil.save(o);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//删除           
	public boolean delOutStorage(String ono){
		boolean b = true;
		String hql = "delete TbOutStorage where ono=?";
		String paramters[] = {ono};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改 主键不能修改
	public boolean updOutStorage(TbOutStorage o){
		boolean b=true;
		try {
			HibernateUtil.update(o);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得
	public TbOutStorage getOutStorageByNo(String ono){
		TbOutStorage outStorage = (TbOutStorage)HibernateUtil.findById(TbOutStorage.class, new Integer(ono));
		return outStorage;
	}
	
	//更新OutStorageManageView 通过入库编号
	public static void updateOutStorageView(String data){
		String hql="from TbOutStorage where ono like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbOutStorage";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
		OutStorageManageView.outStorageService = new OutStorageService();
		OutStorageManageView.outStorageService.queryOutStorage(hql, paramer);
		OutStorageManageView.jt.setModel(OutStorageManageView.outStorageService);
	}
	
	//提供一个返回所有的名
	public Vector<Item> getAllName(String type){
		if("good".equals(type)){
			goodService=new GoodService();
			Map<Integer, TbGood> map=goodService.getAllHashGood();
			Vector<Item> items=new Vector<Item>();
			for(int key:map.keySet()){
				items.addElement(new Item(map.get(key).getGname(),map.get(key)));
			}
			return items;
		}else if("picker".equals(type)){
			pickerService=new PickerService();
			Map<Integer, TbPicker> map=pickerService.getAllHashPicker();
			Vector<Item> items=new Vector<Item>();
			for(int key:map.keySet()){
				items.addElement(new Item(map.get(key).getPname(),map.get(key)));
			}
			return items;
		}else if("storage".equals(type)){
			storageService=new StorageService();
			Map<Integer, TbStorage> map=storageService.getAllHashStorage();
			Vector<Item> items=new Vector<Item>();
			for(int key:map.keySet()){
				items.addElement(new Item(map.get(key).getSname(),map.get(key)));
			}
			return items;
		}
		return null;
	}
	
	//减少库存
	public boolean updateKc(TbKc k) {
		boolean b=true;
		kcService=new KcService();
		try {
			b=kcService.cutKc(k);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	
	//执行查询，把数据封装到rowData，columnNames
	public void queryOutStorage(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("出库单号");
		columnNames.add("货物名称");
		columnNames.add("提货员");
		columnNames.add("发货仓库");
		columnNames.add("发往地");
		columnNames.add("出库日期");
		columnNames.add("出库数量");
		
		//向rowData添加数据
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbOutStorage on=(TbOutStorage)list.get(i);
			hang.add(on.getOno()+"");
			hang.add(on.getTbGood().getGname());
			hang.add(on.getTbPicker().getPname());
			hang.add(on.getTbStorage().getSname());
			hang.add(on.getOaddress());
			hang.add(on.getOdate());
			hang.add(on.getOnum());
			//Session已关闭报错 需要在XXX.hbm.xml加上lazy=false 取消懒加载
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
