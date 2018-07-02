package com.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.*;

import com.domain.TbBuyer;
import com.manage_view.BuyerManageView;
import com.util.*;

public class BuyerService extends AbstractTableModel{
	//rowData用来存放行数据
	//columnNames存放列名
	Vector rowData,columnNames;
	public static void main(String[] args) {

	}
	//添加
	public boolean addBuyer(TbBuyer buy){
		boolean b = true;
		try {
			HibernateUtil.save(buy);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//删除 
	public boolean delBuyer(String bno){
		boolean b = true;
		String hql = "delete TbBuyer where bno=?";
		String paramters[] = {bno};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改 主键不能修改
	public boolean updBuyer(TbBuyer buy){
		boolean b=true;
		try {
			HibernateUtil.update(buy);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得客户
	public TbBuyer getBuyerByNo(String bno){
		TbBuyer buyer = (TbBuyer)HibernateUtil.findById(TbBuyer.class, new Integer(bno));
		return buyer;
	}
	
	//更新BuyerManageView
	public static void updateBuyerView(String data){
		String hql="from TbBuyer where bname like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbBuyer";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
		BuyerManageView.buyerService = new BuyerService();
		BuyerManageView.buyerService.queryBuyer(hql, paramer);
		BuyerManageView.jt.setModel(BuyerManageView.buyerService);
	}
	//提供一个返回HashMap<int,TbStorage>
	public Map<Integer, TbBuyer> getAllHashBuyer(){
		HashMap<Integer, TbBuyer> map = new HashMap<Integer, TbBuyer>();
		List<TbBuyer> list = HibernateUtil.executeQuery("from TbBuyer", null);
		for(TbBuyer s:list){
			map.put(s.getBno(), s);
		}
		return map;
	}
	
	//执行查询，把数据封装到rowData，columnNames
	public void queryBuyer(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("采购员编号");
		columnNames.add("采购员名称");
		columnNames.add("性别");
		columnNames.add("联系方式");
		columnNames.add("联系地址");
		
		//向rowData添加数据
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbBuyer b=(TbBuyer)list.get(i);
			hang.add(b.getBno());
			hang.add(b.getBname());
			hang.add(b.getBsex());
			hang.add(b.getBmethod());
			hang.add(b.getBaddress());
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
