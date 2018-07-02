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
		//ͳһ����
		MyTools.InitGlobalFont(MyTools.f2);
		//���ÿղ���
		this.setLayout(null);
		ct = this.getContentPane();
		//�ϲ���
		jl1=new JLabel("��ӭ���������ֿ�������!");
		jl1.setBounds(10, 10, 190, 15);
		jl1.setFont(MyTools.f3);
		jl1.setForeground(Color.green);
		ct.add(jl1);
		//��ʼ���ؼ�
		//�в���
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
		
		//�²���
		jl2=new JLabel("�û���¼");
		jl2.setBounds(220, 145, 70, 13);
		jl2.setForeground(Color.RED);
		ct.add(jl2);
		jl3=new JLabel("�û�����:");
		jl3.setBounds(20, 145, 70, 13);
		ct.add(jl3);
		String s[]={"��ͨ�û�","����Ա"};
		jcb1=new JComboBox(s);
		jcb1.setBounds(85, 140, 102, 20);
		ct.add(jcb1);
		jl4=new JLabel("�û�ID:");
		jl4.setBounds(20,170,50,13);
		ct.add(jl4);
		jtf=new JTextField();
		jtf.setBounds(70,165,118,20);
		jtf.addMouseListener(this);
		ct.add(jtf);
		jl5=new JLabel("����:");
		jl5.setBounds(20,195,50,13);
		ct.add(jl5);
		jpf=new JPasswordField();
		jpf.setBounds(70,190,118,20);
		jpf.addKeyListener(this);
		jpf.addMouseListener(this);
		ct.add(jpf);
		jb1=new JButton("��½");
		jb1.setBounds(220, 163, 94, 21);
		jb1.addActionListener(this);
		ct.add(jb1);
		jb2=new JButton("�˳�");
		jb2.setBounds(220, 188, 94, 21);
		jb2.addActionListener(this);
		ct.add(jb2);
		jb3=new JButton("����");
		jb3.setBounds(320, 163, 87, 47);
		jb3.addActionListener(this);
		ct.add(jb3);
				
		this.setVisible(true);
		this.setSize(430,250);
		this.setResizable(false);
		this.setTitle("�����ֿ����ϵͳ");
		//������ʾ
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-225, height/2-150);
		//����ͷ��
		try {
			Image im=ImageIO.read(new File("image/����.png"));
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
			if(jcb1.getSelectedItem().toString().equals("����Ա")){
				TbAdmin a = new TbAdmin();
				adminService = new AdminService();
				String ano = jtf.getText();
				String apass = new String(jpf.getPassword());
				if(ano.equals("")||apass.equals("")){
					JOptionPane.showMessageDialog(this, "������ID������");
					return;
				}
				a.setAno(new Integer(ano.trim()));
				a.setApass(apass);
				if(adminService.checkAdmin(a)){
					JOptionPane.showMessageDialog(this, "��¼�ɹ�");
					//����ȫ��user,�ѱ������������
					TbAdmin.admin=adminService.getAdminByNo(jtf.getText());
					new MainFrame();
				}else{
					JOptionPane.showMessageDialog(this, "��¼ʧ��");
					return;
				}
			}else if(jcb1.getSelectedItem().toString().equals("��ͨ�û�")){
				TbUser u = new TbUser();
				userService=new UserService();
				String uno = jtf.getText();
				String upass = new String(jpf.getPassword());
				if(uno.equals("")||upass.equals("")){
					JOptionPane.showMessageDialog(this, "������ID������");
					return;
				}
				u.setUno(new Integer(uno.trim()));
				u.setUpass(upass);
				if(userService.checkUser(u)){
					JOptionPane.showMessageDialog(this, "��¼�ɹ�");
					//����ȫ��user,�ѱ������������
					TbUser.user=userService.getUserByNo(jtf.getText());
					new MainFrame();
				}else{
					JOptionPane.showMessageDialog(this, "��¼ʧ��");
					return;
				}
			}		
			this.dispose();
		}else if(e.getSource()==jb2){
			this.dispose();
		}else if(e.getSource()==jb3){
			JOptionPane.showMessageDialog(this, "��ʱ���ṩ����");
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getKeyChar()==KeyEvent.VK_ENTER){
			jb1.doClick();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==jtf){
			ct.add(jl6);
			ct.add(jl7);
			ct.add(jl8);
			ct.remove(jl9);
			ct.remove(jl10);
			ct.remove(jl11);
			SwingUtilities.updateComponentTreeUI(this);//����
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
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
}

