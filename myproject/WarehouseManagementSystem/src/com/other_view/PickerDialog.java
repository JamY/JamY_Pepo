package com.other_view;

import javax.swing.*;

import com.domain.TbBuyer;
import com.domain.TbGood;
import com.domain.TbPicker;
import com.domain.User;
import com.service.BuyerService;
import com.service.GoodService;
import com.service.PickerService;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PickerDialog extends JDialog implements ActionListener{
	
	//定义我需要的swing组件
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JButton jb1,jb2,jb3;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	
	PickerService pickerService;
	String pno;//记录哪一条记录
	
	//owner他的父窗口
	//title窗口名
	//modal指定是模式窗口，还是非模式窗口
	public PickerDialog(JFrame owner,String title,boolean modal,PickerService pickerService,int rowNums,String type)
	{
//		super(owner,title,modal);//调用父类构造方法，达到模式对话框效果
		jl1=new JLabel("采购员名称");
		jl2=new JLabel("性别");
		jl3=new JLabel("联系方式");
		jl4=new JLabel("联系地址");	
		
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();
		jtf4=new JTextField();		
		
		jb1=new JButton("添加");
		if(type.equals("update")){
			jb3=new JButton("修改");
			jb3.addActionListener(this);
			pno=pickerService.getValueAt(rowNums, 0).toString();
			jtf1.setText(pickerService.getValueAt(rowNums, 1).toString());
			jtf2.setText(pickerService.getValueAt(rowNums, 2).toString());
			jtf3.setText(pickerService.getValueAt(rowNums, 3).toString());
			jtf4.setText(pickerService.getValueAt(rowNums, 4).toString());
		}
		jb1.addActionListener(this);
		jb2=new JButton("取消");
		jb2.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//设置布局
		jp1.setLayout(new GridLayout(4,1));
		jp2.setLayout(new GridLayout(4,1));

		
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		
		if(type.equals("add")){
			jp3.add(jb1);
		}else if(type.equals("update")){
			jp3.add(jb3);
		}
		jp3.add(jb2);
		
		this.add(jp1,BorderLayout.WEST);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(250,200);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setResizable(false);
		// 显示为模态对话框
		this.setModal(modal);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//实例化Service
		pickerService = new PickerService();
		// TODO 自动生成的方法存根
		if(arg0.getSource()==jb1){
			//希望添加
			TbPicker p=new TbPicker();
			p.setPname(jtf1.getText());
			p.setPsex(jtf2.getText());
			p.setPmethod(jtf3.getText());
			p.setPaddress(jtf4.getText());
			if(pickerService.addPicker(p)){
				//this=从这个框里面出来
				JOptionPane.showMessageDialog(this, "添加成功 ");
			}else{
				JOptionPane.showMessageDialog(this, "添加失败");
				return;
			}
			//关闭对话
			this.dispose();
		}else if(arg0.getSource()==jb2){
			this.dispose();
		}else if(arg0.getSource()==jb3){
			//希望修改
			TbPicker p = new TbPicker();
			p.setPno(Integer.parseInt(this.pno));
			p.setPname(jtf1.getText());
			p.setPsex(jtf2.getText());
			p.setPmethod(jtf3.getText());
			p.setPaddress(jtf4.getText());;
			if(pickerService.updPicker(p)){
				//this=从这个框里面出来
				JOptionPane.showMessageDialog(this, "修改成功 ");
			}else{
				JOptionPane.showMessageDialog(this, "添加失败");
				return;
			}
			//关闭对话
			this.dispose();
		}
	}

}