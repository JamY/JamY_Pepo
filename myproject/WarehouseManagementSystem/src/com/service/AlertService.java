package com.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.domain.TbAdmin;
import com.domain.TbKc;
import com.util.HibernateUtil;

public class AlertService extends AbstractTableModel{
	Vector rowData,columnNames;
	public static ArrayList<TbKc> kcList = new ArrayList<TbKc>();
	
	public void queryAlert(){
		//向columnNames添加列名
		columnNames=new Vector();
		columnNames.add("库存单号");
		columnNames.add("货物名");
		columnNames.add("存放仓库");
		columnNames.add("数量");
		
		//向rowData添加数据
		rowData=new Vector();
		for(int i=0;i<kcList.size();i++){
			Vector hang=new Vector();
			TbKc k=kcList.get(i);
			hang.add(k.getKno());
			hang.add(k.getTbGood().getGname());
			hang.add(k.getTbStorage().getSname());
			hang.add(k.getKnum());
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
