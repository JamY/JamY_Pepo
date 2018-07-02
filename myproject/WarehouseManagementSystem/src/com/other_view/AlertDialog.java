package com.other_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.service.AlertService;
import com.service.BuyerService;
import com.tools.MyTools;

public class AlertDialog extends JDialog implements ActionListener{
	JPanel jp1;
	JLabel jl1;
    JTable jt;
	JScrollPane jsp;
	AlertService alertService;
	
	
	public AlertDialog(JFrame owner,String title,boolean modal){
		super(owner,title,modal);
		jl1=new JLabel("当前以下货物数量少于50");
		jl1.setFont(MyTools.f3);
		jl1.setForeground(Color.red);
		alertService=new AlertService();
		alertService.queryAlert();
		jt=new JTable(alertService);
		
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		jt.setDefaultRenderer(Object.class, tcr);
		jsp=new JScrollPane(jt);
		
		jp1=new JPanel();
		jp1.add(jsp);
		
		this.setLayout(new BorderLayout());
		this.add(jl1, "North");
		this.add(jp1,"Center");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(450,200);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setResizable(false);
		// 显示为模态对话框
		this.setModal(modal);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		
	}

}
