package com.service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.*;

import com.domain.TbAdmin;
import com.domain.TbBuyer;
import com.domain.TbKc;
import com.domain.TbPicker;
import com.domain.TbStorage;
import com.domain.TbGood;
import com.domain.TbStorage;
import com.manage_view.GoodManageView;
import com.manage_view.KcManageView;
import com.manage_view.StorageManageView;
import com.service.InStorageService.Item;
import com.util.*;

public class KcService extends AbstractTableModel{
	//rowData用来存放行数据
	//columnNames存放列名
	Vector rowData,columnNames;
	
	GoodService goodService;
	StorageService storageService;
	
	//仓库预警
	public static boolean alertState=false;
	public static int alertNum=0;//警报数量
	public static int normalNum=0;//正常数量
	
//	private int a;
	private static OnAUpdateListener onAUpdateListener;
	
	public void setOnAUpdateListener(OnAUpdateListener onAUpdateListener){
	    this.onAUpdateListener = onAUpdateListener;
	}

	public void setA(int alertNum){
	    this.alertNum = alertNum;
	    this.onAUpdateListener.updateListener(alertNum);
	}
	
	public interface OnAUpdateListener{
	    void updateListener(int alertNum);
	}
	
	public static void main(String[] args) {

	}
	//添加
	public boolean addKc(TbKc k){
		boolean b = true;
		//判断库存是否存在
		String hql="from TbKc where tbGood.gname=? and tbStorage.sname=?";
		String paramters[]={k.getTbGood().getGname(),k.getTbStorage().getSname()};
		try {
			List<TbKc> list=HibernateUtil.executeQuery(hql, paramters);
			if(list.size()==1){
				//存在 则更新
				TbKc kc = list.get(0);
				kc.setKnum(k.getKnum()+kc.getKnum());
				Date date = new Date();
		        //设置要获取到什么样的时间
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        //获取String类型的时间
		        String createdate = sdf.format(date);
				kc.setKdate(createdate);
				updKc(kc);
			}else if(list==null || list.size()==0){
				//创建库存
				HibernateUtil.save(k);
			}else{
				b = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//减少
	public boolean cutKc(TbKc k){
		boolean b = true;
		//判断库存是否存在
		String hql="from TbKc where tbGood.gname=? and tbStorage.sname=?";
		String paramters[]={k.getTbGood().getGname(),k.getTbStorage().getSname()};
		try {
			List<TbKc> list=HibernateUtil.executeQuery(hql, paramters);
			if(list.size()==1){
				//存在 则更新
				TbKc kc = list.get(0);
				kc.setKnum(kc.getKnum()-k.getKnum());
				Date date = new Date();
		        //设置要获取到什么样的时间
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        //获取String类型的时间
		        String createdate = sdf.format(date);
				kc.setKdate(createdate);
				updKc(kc);
			}else if(list==null || list.size()==0){
				b = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	
	//删除 
	public boolean delKc(String kno){
		boolean b = true;
		String hql = "delete TbKc where kno=?";
		String paramters[] = {kno};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改 主键不能修改
	public boolean updKc(TbKc k){
		boolean b=true;
		try {
			HibernateUtil.update(k);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得
	public TbKc getKcByNo(String kno){
		TbKc k = (TbKc)HibernateUtil.findById(TbKc.class, new Integer(kno));
		return k;
	}
	
	//更新KcManageView
	public static void updateKcView(String data){
		String hql="from TbKc where tbStorage.sname like '%"+data+"%' or kno like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbKc";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
		KcManageView.kcService = new KcService();
		KcManageView.kcService.queryKc(hql, paramer);
		KcManageView.jt.setModel(KcManageView.kcService);
	}
	//提供一个返回HashMap<int,TbStorage>
//	public Map<Integer, TbKc> getAllHashKc(){
//		HashMap<Integer, TbKc> map = new HashMap<Integer, TbKc>();
//		List<TbKc> list = HibernateUtil.executeQuery("from TbKc", null);
//		for(TbKc k:list){
//			map.put(k.getKno(), k);
//		}
//		return map;
//	}
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
	
	//执行查询，把数据封装到rowData，columnNames
	public void queryKc(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("库存单号");
		columnNames.add("货物名称");
		columnNames.add("存放仓库");
		columnNames.add("数量");
		columnNames.add("清点时间");
		
		//向rowData添加数据
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		//清空警告list
		AlertService.kcList.clear();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbKc k=(TbKc)list.get(i);
			hang.add(k.getKno()+"");
			hang.add(k.getTbGood().getGname());
			hang.add(k.getTbStorage().getSname());
			//更改仓库预警状态
			int num = k.getKnum();
			if(num<50||num==50){
				KcService.alertNum=++KcService.alertNum;
				AlertService.kcList.add(k);
			}
			hang.add(num);
			hang.add(k.getKdate());
			//Session已关闭报错 需要在TbPicker.hbm.xml加上lazy=false 取消懒加载
			rowData.add(hang);
		}
		this.setA(KcService.alertNum);//触发监听KcService.alertNum变化的事件
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
