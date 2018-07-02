package com.view;

import com.controller.LoginCL;
import com.domain.TbAdmin;
import com.domain.TbUser;
import com.domain.User;
import com.service.AdminService;
import com.service.UserService;
import com.tools.*;
import com.util.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Index extends JFrame implements ActionListener,KeyListener,MouseListener{
	JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7,jl8,jl9,jl10,jl11;
	JTextField jtf;
	JPasswordField jpf;
	JButton jb1,jb2,jb3;
	JComboBox jcb1;
	Container ct;
	public static void main(String args[]) {
		Index index2 = new Index();
	}
	public Index(){
		//统一字体
		MyTools.InitGlobalFont(MyTools.f2);
		//设置空布局
		this.setLayout(null);
		ct = this.getContentPane();
		//上部分
		jl1=new JLabel("欢迎进入物流仓库管理软件!");
		jl1.setBounds(10, 10, 190, 15);
		jl1.setFont(MyTools.f3);
		jl1.setForeground(Color.green);
		ct.add(jl1);
		//初始化控件
		//中部分
		ImageIcon img=new ImageIcon("image/timg3.png");
		img.setImage(img.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
		jl6=new JLabel(img);
		jl6.setBounds(10, 25, 150, 150);
		ct.add(jl6);
		ImageIcon img2=new ImageIcon("image/timg6.png");
		img2.setImage(img2.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
		jl7=new JLabel(img2);
		jl7.setBounds(130, 25, 150, 150);
		ct.add(jl7);
		ImageIcon img3=new ImageIcon("image/timg4.png");
		img3.setImage(img3.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
		jl8=new JLabel(img3);
		jl8.setBounds(250, 25, 150, 150);
		ct.add(jl8);
		
		ImageIcon img4=new ImageIcon("image/timg1.png");
		img4.setImage(img4.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
		jl9=new JLabel(img4);
		jl9.setBounds(10, 25, 150, 150);
//		ct.add(jl9);
		ImageIcon img5=new ImageIcon("image/timg5.png");
		img5.setImage(img5.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
		jl10=new JLabel(img5);
		jl10.setBounds(130, 25, 150, 150);
//		ct.add(jl10);
		ImageIcon img6=new ImageIcon("image/timg2.png");
		img6.setImage(img6.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
		jl11=new JLabel(img6);
		jl11.setBounds(250, 25, 150, 150);
//		ct.add(jl11);
		
		//下部分
		jl2=new JLabel("用户登录");
		jl2.setBounds(220, 145, 70, 13);
		jl2.setForeground(Color.RED);
		ct.add(jl2);
		jl3=new JLabel("用户类型:");
		jl3.setBounds(20, 145, 70, 13);
		ct.add(jl3);
		String s[]={"普通用户","管理员"};
		jcb1=new JComboBox(s);
		jcb1.setBounds(85, 140, 102, 20);
		ct.add(jcb1);
		jl4=new JLabel("用户ID:");
		jl4.setBounds(20,170,50,13);
		ct.add(jl4);
		jtf=new JTextField();
		jtf.setBounds(70,165,118,20);
		jtf.addMouseListener(this);
		ct.add(jtf);
		jl5=new JLabel("密码:");
		jl5.setBounds(20,195,50,13);
		ct.add(jl5);
		jpf=new JPasswordField();
		jpf.setBounds(70,190,118,20);
		jpf.addKeyListener(this);
		jpf.addMouseListener(this);
		ct.add(jpf);
		jb1=new JButton("登陆");
		jb1.setBounds(220, 163, 94, 21);
		jb1.addActionListener(this);
		ct.add(jb1);
		jb2=new JButton("退出");
		jb2.setBounds(220, 188, 94, 21);
		jb2.addActionListener(this);
		ct.add(jb2);
		jb3=new JButton("帮助");
		jb3.setBounds(320, 163, 87, 47);
		jb3.addActionListener(this);
		ct.add(jb3);
				
		this.setVisible(true);
		this.setSize(430,250);
		this.setResizable(false);
		this.setTitle("物流仓库管理系统");
		//居中显示
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-225, height/2-150);
		//设置头像
		try {
			Image im=ImageIO.read(new File("image/物流.png"));
			this.setIconImage(im);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){
			AdminService adminService;
			UserService userService;
			if(jcb1.getSelectedItem().toString().equals("管理员")){
				TbAdmin a = new TbAdmin();
				adminService = new AdminService();
				String ano = jtf.getText();
				String apass = new String(jpf.getPassword());
				if(ano.equals("")||apass.equals("")){
					JOptionPane.showMessageDialog(this, "请输入ID或密码");
					return;
				}
				a.setAno(new Integer(ano.trim()));
				a.setApass(apass);
				if(adminService.checkAdmin(a)){
					JOptionPane.showMessageDialog(this, "登录成功");
					//定义全局user,已备后面界面需用
					TbAdmin.admin=adminService.getAdminByNo(jtf.getText());
					new MainFrame();
				}else{
					JOptionPane.showMessageDialog(this, "登录失败");
					return;
				}
			}else if(jcb1.getSelectedItem().toString().equals("普通用户")){
				TbUser u = new TbUser();
				userService=new UserService();
				String uno = jtf.getText();
				String upass = new String(jpf.getPassword());
				if(uno.equals("")||upass.equals("")){
					JOptionPane.showMessageDialog(this, "请输入ID或密码");
					return;
				}
				u.setUno(new Integer(uno.trim()));
				u.setUpass(upass);
				if(userService.checkUser(u)){
					JOptionPane.showMessageDialog(this, "登录成功");
					//定义全局user,已备后面界面需用
					TbUser.user=userService.getUserByNo(jtf.getText());
					new MainFrame();
				}else{
					JOptionPane.showMessageDialog(this, "登录失败");
					return;
				}
			}		
			this.dispose();
		}else if(e.getSource()==jb2){
			this.dispose();
		}else if(e.getSource()==jb3){
			JOptionPane.showMessageDialog(this, "暂时不提供帮助");
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自动生成的方法存根
		if(e.getKeyChar()==KeyEvent.VK_ENTER){
			jb1.doClick();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==jtf){
			ct.add(jl6);
			ct.add(jl7);
			ct.add(jl8);
			ct.remove(jl9);
			ct.remove(jl10);
			ct.remove(jl11);
			SwingUtilities.updateComponentTreeUI(this);//更新
		}else if(e.getSource()==jpf){
			ct.add(jl9);
			ct.add(jl10);
			ct.add(jl11);
			ct.remove(jl6);
			ct.remove(jl7);
			ct.remove(jl8);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
}

