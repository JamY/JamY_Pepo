package com.manage_view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.domain.TbKc;
import com.other_view.BuyerDialog;
import com.other_view.ClientDialog;
import com.other_view.GoodDialog;
import com.other_view.InStorageDialog;
import com.other_view.OutStorageDialog;
import com.service.BuyerService;
import com.service.ClientService;
import com.service.GoodService;
import com.service.InStorageService;
import com.service.KcService;
import com.service.OutStorageService;
import com.service.PickerService;
import com.view.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OutStorageManageView extends JPanel implements ActionListener,KeyListener{
	//定义所需要的控件
	JPanel jp1,jp1_1,jp1_2,jp2;
	JLabel jl1;
	JTextField jtf;
	JButton jb1,jb2,jb3,jb4;
	
	public static JTable jt;
	JScrollPane jsp;
	//数据模型 保持共用一个Service jt
	public static OutStorageService outStorageService;
	public static PickerService pickerService;
	//采购员数据模型
	PickerManageView pickerManageView;
	
	public static void main(String[] args) {
		
	}	
	public OutStorageManageView(){
		//上部分
		jl1=new JLabel("请输入入库单号");
		jtf=new JTextField(20);
		jtf.addKeyListener(this);
		jb1=new JButton("查询");
		jb1.addActionListener(this);
		jb2=new JButton("添加");
		jb2.addActionListener(this);
		jb3=new JButton("修改");
		jb3.addActionListener(this);
		jb4=new JButton("删除");
		jb4.addActionListener(this);
		
		jp1=new JPanel(new BorderLayout());
		jp1_1=new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp1_1.add(jl1);
		jp1_1.add(jtf);
		jp1_1.add(jb1);
		
		outStorageService=new OutStorageService();
		String hql="from TbOutStorage";
		String paramer[]={};
		outStorageService.queryOutStorage(hql, paramer);
		jt=new JTable(outStorageService);
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
//	    tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
	    jt.setDefaultRenderer(Object.class, tcr);
		jsp=new JScrollPane(jt);
		
		jp1_2=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp1_2.add(jb2);
		jp1_2.add(jb3);
		jp1_2.add(jb4);
		
		jp1.add(jp1_1, "North");
		jp1.add(jsp, "Center");
		jp1.add(jp1_2, "South");
		jp1.setPreferredSize(new Dimension(800, 300));//设置JPane的大小
		
		//下部分
		jp2=new JPanel(new BorderLayout());
		pickerManageView = new PickerManageView();
		jp2.add(pickerManageView);
		jp2.setPreferredSize(new Dimension(800, 300));
		
		//设置BorderLayout布局
		this.setLayout(new BorderLayout());
		this.add(jp1,"North");
		this.add(jp2,"Center");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){
			//表示用户想查询
			String name=this.jtf.getText().trim();
			outStorageService.updateOutStorageView(name);
		}else if(e.getSource()==jb2){
			//表示添加用户  true: 创建Dialog完成操作才能往下执行 false: 跳过 往下执行
			OutStorageDialog outStorageDialog=new OutStorageDialog(null, "添加出库信息", true, null, 0, "add");
			outStorageDialog.setVisible(true);
			//更新
			outStorageService.updateOutStorageView(null);
			//更新库存
			KcManageView.kcService.updateKcView(null);
		}else if(e.getSource()==jb4){
			//表示删除客户
			//getSelectedRow会返回用户点中的行
			//如果该用户一行都没选择，就返回-1
			int rowNum=this.jt.getSelectedRow();
			if(rowNum==-1)
			{
				//提示
				JOptionPane.showMessageDialog(this, "请选择一行");
				return ;//返回到主函数
			}
			//得到用户编号 得到第rowNum行第0列（Object->String）
			String ono=outStorageService.getValueAt(rowNum, 0).toString();
			int i=JOptionPane.showConfirmDialog(this, "确定删除吗?");
			if(i==0){
				//表示确定删除
				if(outStorageService.delOutStorage(ono)){
					JOptionPane.showMessageDialog(this, "删除成功!");
				}else{
					JOptionPane.showMessageDialog(this, "失败成功!");
				}
			}
			//更新
			outStorageService.updateOutStorageView(null);
		}else if(e.getSource()==jb3){
			//用户想修改
			//选择一行
			int rowNums=this.jt.getSelectedRow();
			if(rowNums==-1){
				JOptionPane.showMessageDialog(this, "请选择一行");
				return;
			}
			OutStorageDialog outStorageDialog=new OutStorageDialog(null, "修改出库信息", true, outStorageService, rowNums,"update");
			outStorageDialog.setVisible(true);
			//更新
			outStorageService.updateOutStorageView(null);
		}	
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER){ 
			String data=jtf.getText().trim();
			outStorageService.updateOutStorageView(data);
        } 
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}

}
