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
		//��columnNames�������
		columnNames=new Vector();
		columnNames.add("��浥��");
		columnNames.add("������");
		columnNames.add("��Ųֿ�");
		columnNames.add("����");
		
		//��rowData�������
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
	
	//�õ����Ҷ�����,�Զ�����
	public int getColumnCount() {
		// TODO �Զ����ɵķ������
		return this.columnNames.size();
	}

	//�õ����е����У��Զ�����
	public int getRowCount() {
		// TODO �Զ����ɵķ������
		return this.rowData.size();
	}

	//�õ�ĳ��ĳ�е����ݣ��Զ�����
	public Object getValueAt(int row, int column) {
		// TODO �Զ����ɵķ������
		return ((Vector)this.rowData.get(row)).get(column);
	}
    @Override//�÷����Զ�������
	public String getColumnName(int arg0) {
		// TODO �Զ����ɵķ������
		return (String)this.columnNames.get(arg0);
	}
}
