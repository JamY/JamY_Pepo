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
import com.manage_view.GoodManageView;
import com.util.*;

public class GoodService extends AbstractTableModel{
	//rowData用来存放行数据
	//columnNames存放列名
	Vector rowData,columnNames;
	public static void main(String[] args) {

	}
	//添加货物
	public boolean addGood(TbGood g){
		boolean b = true;
		try {
			HibernateUtil.save(g);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//删除货物            
	public boolean delGood(String gno){
		boolean b = true;
		String hql = "delete TbGood where gno=?";
		String paramters[] = {gno};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改 主键不能修改
	public boolean updGood(TbGood g){
		boolean b=true;
		try {
			HibernateUtil.update(g);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得货物
	public TbGood getGoodByNo(String gno){
		TbGood good = (TbGood)HibernateUtil.findById(TbGood.class, new Integer(gno));
		return good;
	}
	
	//更新GoodManageView
	public static void updateGoodView(String data){
		String hql="from TbGood where gname like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbGood";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
		GoodManageView.goodService = new GoodService();
		GoodManageView.goodService.queryGood(hql, paramer);
		GoodManageView.jt.setModel(GoodManageView.goodService);
	}
	
	//提供一个返回HashMap<int,TbGood>
	public Map<Integer, TbGood> getAllHashGood(){
		HashMap<Integer, TbGood> map = new HashMap<Integer, TbGood>();
		List<TbGood> list = HibernateUtil.executeQuery("from TbGood", null);
		for(TbGood g:list){
			map.put(g.getGno(), g);
		}
		return map;
	}
	
	//执行查询，把数据封装到rowData，columnNames
	public void queryGood(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("货物编号");
		columnNames.add("货物货号");
		columnNames.add("货物名称");
		columnNames.add("生产日期");
		columnNames.add("生产厂家");
		columnNames.add("货物单价");
		columnNames.add("入库时间");
		
		//向rowData添加数据
//		ArrayList al=SqlHelper.executeQuery3(sql, paramer);
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbGood g=(TbGood)list.get(i);
			hang.add(g.getGno());
			hang.add(g.getGhao());
			hang.add(g.getGname());
			hang.add(g.getGdate());
			hang.add(g.getGfactory());
			hang.add(g.getGprice()+"元");
			hang.add(g.getIdate());
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
