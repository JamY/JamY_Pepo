package com.other_view;

import javax.swing.*;

import com.domain.TbGood;
import com.domain.TbUser;
import com.domain.User;
import com.service.GoodService;
import com.service.UserService;
import com.util.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDialog extends JDialog implements ActionListener{
	
	//��������Ҫ��swing���
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JButton jb1,jb2,jb3;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	
	UserService userService;
	String uno;//��¼��һ����¼
	
	//owner���ĸ�����
	//title������
	//modalָ����ģʽ���ڣ����Ƿ�ģʽ����
	public UserDialog(JFrame owner,String title,boolean modal,UserService userService,int rowNums,String type)
	{
		super(owner,title,modal);//���ø��๹�췽�����ﵽģʽ�Ի���Ч��
		jl1=new JLabel("�û�����");
		jl2=new JLabel("�û�����");
		jl3=new JLabel("�û�����");	
		
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();	
		
		jb1=new JButton("���");
		if(type.equals("update")){
			jb3=new JButton("�޸�");
			jb3.addActionListener(this);
			uno=userService.getValueAt(rowNums, 0).toString();
			jtf1.setText(userService.getValueAt(rowNums, 1).toString());
			jtf2.setText(userService.getValueAt(rowNums, 2).toString());
			jtf3.setText(userService.getValueAt(rowNums, 3).toString());
		}
		jb1.addActionListener(this);
		jb2=new JButton("ȡ��");
		jb2.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//���ò���
		
		jp1.setLayout(new GridLayout(3,1));
		jp2.setLayout(new GridLayout(3,1));
		
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		
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
		this.setSize(200,150);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setResizable(false);
		// ��ʾΪģ̬�Ի���
		this.setModal(modal);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//ʵ����Service
		userService = new UserService();
		// TODO �Զ����ɵķ������
		if(arg0.getSource()==jb1){
			//ϣ�����
			TbUser u=new TbUser();
			u.setUname(jtf1.getText());
			u.setUpass(jtf2.getText());
			u.setUgrade(new Integer(jtf3.getText()));
			if(userService.addUser(u)){
				//this=��������������
				JOptionPane.showMessageDialog(this, "��ӳɹ� ");
			}else{
				JOptionPane.showMessageDialog(this, "���ʧ��");
				return;
			}
			//�رնԻ�
			this.dispose();
		}else if(arg0.getSource()==jb2){
			this.dispose();
		}else if(arg0.getSource()==jb3){
			//ϣ���޸�
			TbUser u = new TbUser();
			u.setUno(Integer.parseInt(this.uno));
			u.setUname(jtf1.getText());
			u.setUpass(jtf2.getText());
			u.setUgrade(new Integer(jtf3.getText()));
			if(userService.updUser(u)){
				//this=��������������
				JOptionPane.showMessageDialog(this, "�޸ĳɹ� ");
			}else{
				JOptionPane.showMessageDialog(this, "���ʧ��");
				return;
			}
			//�رնԻ�
			this.dispose();
		}
	}
}