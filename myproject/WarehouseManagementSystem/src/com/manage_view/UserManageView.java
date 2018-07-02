package com.manage_view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.domain.TbUser;
import com.other_view.GoodDialog;
import com.other_view.UserDialog;
import com.service.GoodService;
import com.service.UserService;
import com.view.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserManageView extends JPanel implements ActionListener,KeyListener{
	//定义所需要的控件
	JPanel jp1,jp2;
	JLabel jl1;
	JTextField jtf;
	JButton jb1,jb2,jb3,jb4;
	
	public static JTable jt;
	JScrollPane jsp;
	//数据模型 保持共用一个Service jt
	public static UserService userService;
	public static void main(String[] args) {
		
	}	
	public UserManageView(){
		jl1=new JLabel("请输入用户名称");
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
		
		//上部分
		jp1=new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp1.add(jl1);
		jp1.add(jtf);
		jp1.add(jb1);
		
		//中间部分
		userService=new UserService();
		String hql="from TbUser";
		String paramer[]={};
		userService.queryUser(hql, paramer);
		jt=new JTable(userService);
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
	    jt.setDefaultRenderer(Object.class, tcr);
		jsp=new JScrollPane(jt);
		
		//下部分
		jp2=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		if(TbUser.user==null){
			jp2.add(jb2);
			jp2.add(jb3);
			jp2.add(jb4);
		}
		
		//设置BorderLayout布局
		this.setLayout(new BorderLayout());
		this.add(jp1,"North");
		this.add(jsp,"Center");
		this.add(jp2,"South");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){
			//表示用户想查询
			String name=this.jtf.getText().trim();
			userService.updateUserView(name);
		}else if(e.getSource()==jb2){
			//表示添加用户  true: 创建Dialog完成操作才能往下执行 false: 跳过 往下执行
			UserDialog addUserView=
					new UserDialog(null, "添加用户", true, null, 0, "add");
			addUserView.setVisible(true);
			//更新
			userService.updateUserView(null);
		}else if(e.getSource()==jb4){
			//表示删除用户
			int rowNum=this.jt.getSelectedRow();
			if(rowNum==-1)
			{
				//提示
				JOptionPane.showMessageDialog(this, "请选择一行");
				return ;//返回到主函数
			}
			//该Service为初始的那个数据模型
			String uno=userService.getValueAt(rowNum, 0).toString();
			int i=JOptionPane.showConfirmDialog(this, "确定删除吗?");
			if(i==0){
				//表示确定删除
				if(userService.delUser(uno)){
					JOptionPane.showMessageDialog(this, "删除成功!");
				}else{
					JOptionPane.showMessageDialog(this, "失败成功!");
				}
			}
			//更新
			userService.updateUserView(null);
		}else if(e.getSource()==jb3){
			//用户想修改
			int rowNums=this.jt.getSelectedRow();
			if(rowNums==-1){
				JOptionPane.showMessageDialog(this, "请选择一行");
				return;
			}
			UserDialog updUserView=
					new UserDialog(null, "修改用户", true, userService, 
							rowNums,"update");
			updUserView.setVisible(true);
			//更新
			userService.updateUserView(null);
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
			userService.updateUserView(data);
        }  
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}

}
