package com.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.*;

import com.domain.TbAdmin;
import com.domain.TbClient;
import com.domain.TbGood;
import com.domain.TbPicker;
import com.manage_view.ClientManageView;
import com.manage_view.GoodManageView;
import com.service.StorageService.Item;
import com.util.*;

public class ClientService extends AbstractTableModel{
	//rowData用来存放行数据
	//columnNames存放列名
	Vector rowData,columnNames;
	PickerService pickerService;
	public static void main(String[] args) {

	}
	//添加客户
	public boolean addClient(TbClient c){
		boolean b = true;
		try {
			HibernateUtil.save(c);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	//删除客户           
	public boolean delClient(String cno){
		boolean b = true;
		String hql = "delete TbClient where cno=?";
		String paramters[] = {cno};
		try {
			HibernateUtil.executeUpdate(hql, paramters);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;	
	}
	//修改客户 主键Cno不能修改
	public boolean updClient(TbClient c){
		boolean b=true;
		try {
			HibernateUtil.update(c);
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//通过id取得客户
	public TbClient getClientByNo(String cno){
		TbClient client = (TbClient)HibernateUtil.findById(TbClient.class, new Integer(cno));
		return client;
	}
	
	//更新ClientManageView
	public static void updateClientView(String data){
		String hql="from TbClient where cname like '%"+data+"%'";
	    if(data == null || data.equals("")){
	    	hql="from TbClient";
	    }
		String paramer[]={};
		//构建新的数据模型类，并更新
		ClientManageView.clientService = new ClientService();
		ClientManageView.clientService.queryClient(hql, paramer);
		ClientManageView.jt.setModel(ClientManageView.clientService);
	}
	public Vector<Item> getAllName(String type){
		if("picker".equals(type)){
			pickerService=new PickerService();
			Map<Integer, TbPicker> map= pickerService.getAllHashPicker();
			Vector<Item> items=new Vector<Item>();
			for(int key:map.keySet()){
				items.addElement(new Item(map.get(key).getPname(),map.get(key)));
			}
			return items;
		}
		return null;
	}
	
	//执行查询，把数据封装到rowData，columnNames
	public void queryClient(String hql,String []paramer){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("客户编号");
		columnNames.add("客户名称");
		columnNames.add("客户类型");
		columnNames.add("联系方式");
		columnNames.add("邮政编码");
		columnNames.add("电话");
		columnNames.add("备注");
		columnNames.add("提货员");
		
		//向rowData添加数据
		List list  = HibernateUtil.executeQuery(hql, paramer);
		rowData=new Vector();
		for(int i=0;i<list.size();i++){
			Vector hang=new Vector();
			TbClient c=(TbClient)list.get(i);
			hang.add(c.getCno()+"");
			hang.add(c.getCname());
			hang.add(c.getCtype());
			hang.add(c.getCmethod());
			hang.add(c.getCcode());
			hang.add(c.getCphone());
			hang.add(c.getCbz());
			hang.add(c.getTbPicker().getPname());
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
