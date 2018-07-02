package com.other_view;

import javax.swing.*;

import com.domain.TbAdmin;
import com.domain.TbBuyer;
import com.domain.TbGood;
import com.domain.TbUser;
import com.domain.User;
import com.service.AdminService;
import com.service.BuyerService;
import com.service.GoodService;
import com.service.UserService;
import com.tools.MyTools;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModPassDialog extends JDialog implements ActionListener,FocusListener{
	
	//��������Ҫ��swing���
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JButton jb1,jb2,jb3;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	JPasswordField jpf1,jpf2,jpf3;
	
	AdminService adminService;
	UserService userService;
	TbAdmin admin;
	TbUser user;
	
	boolean submitState = false;
    
//	JLabel error;
	
	//owner���ĸ�����
	//title������
	//modalָ����ģʽ���ڣ����Ƿ�ģʽ����
	public ModPassDialog(JFrame owner,String title,boolean modal)
	{
		super(owner,title,modal);//���ø��๹�췽�����ﵽģʽ�Ի���Ч��
		admin=TbAdmin.admin;
		user=TbUser.user;
		
		if(admin!=null){
			jl1=new JLabel("����Ա���");
			jl2=new JLabel("����Ա����");		
		}else{
			jl1=new JLabel("�� �� �� ��");
			jl2=new JLabel("�� �� �� ��");
		}
		jl3=new JLabel("������");
		jl4=new JLabel("������");	
		jl5=new JLabel("ȷ������");
		
		jtf1=new JTextField();
		if(admin!=null){
			jtf1.setText(admin.getAno()+"");
		}else{
			jtf1.setText(user.getUno()+"");
		}
		jtf1.setEditable(false);
		jtf2=new JTextField();
		jtf2.addFocusListener(this);
		if(admin!=null){
			jtf2.setText(admin.getAname());
		}else{
			jtf2.setText(user.getUname());
		}
//		jtf3=new JTextField();
//		jtf3.addFocusListener(this);
//		jtf4=new JTextField();
//		jtf4.addFocusListener(this);
//		jtf5=new JTextField();
//		jtf5.addFocusListener(this);
		
		jpf1=new JPasswordField(); 
		jpf1.addFocusListener(this);
		jpf2=new JPasswordField(); 
		jpf2.addFocusListener(this);
		jpf3=new JPasswordField(); 
		jpf3.addFocusListener(this);
		
		jb1=new JButton("�޸�");
		jb1.addActionListener(this);
		jb2=new JButton("ȡ��");
		jb2.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//���ò���
		jp1.setLayout(new GridLayout(5,1));
		jp2.setLayout(new GridLayout(5,1));

		
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		
		jp2.add(jtf1);
		jp2.add(jtf2);
//		jp2.add(jtf3);
//		jp2.add(jtf4);
//		jp2.add(jtf5);
		jp2.add(jpf1);
		jp2.add(jpf2);
		jp2.add(jpf3);
		
		jp3.add(jb1);	
		jp3.add(jb2);
		
		this.add(jp1,BorderLayout.WEST);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(250,200);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setResizable(false);
		// ��ʾΪģ̬�Ի���
		this.setModal(modal);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//ʵ����goodService
		adminService = new AdminService();
		userService = new UserService();
		// TODO �Զ����ɵķ������
		if(arg0.getSource()==jb1){
			String name=jtf2.getText();
			String s4=new String(jpf2.getPassword());
			String s5=new String(jpf3.getPassword());
				//ϣ���޸�
			if(!"".equals(name) && !"".equals(s4) && !"".equals(s5) && submitState && s4.equals(s5)){
				System.out.println(name+" "+s4);
				if(TbAdmin.admin!=null){
					TbAdmin a=TbAdmin.admin;
					a.setAname(name);
					a.setApass(s4);
					if(adminService.updAdmin(a)){
						//this=��������������
						JOptionPane.showMessageDialog(this, "�޸ĳɹ� ");
					}else{
						JOptionPane.showMessageDialog(this, "�޸�ʧ��");
						return;
					}
					//�رնԻ�
					this.dispose(); 
				}else{
					TbUser u=TbUser.user;
					u.setUname(name);
					u.setUpass(s4);
					if(userService.updUser(u)){
						//this=��������������
						JOptionPane.showMessageDialog(this, "�޸ĳɹ� ");
					}else{
						JOptionPane.showMessageDialog(this, "�޸�ʧ��");
						return;
					}
					//�رնԻ�
					this.dispose(); 
				}
				
			}else{
				JOptionPane.showMessageDialog(this, "�޸�ʧ��");
				return;
			}
		}else if(arg0.getSource()==jb2){
			this.dispose();
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==jtf2){
			jl2.setText("����Ա����");
			jl2.setForeground(Color.black);
			submitState=true;
		}else if(e.getSource()==jpf1){
			jl3.setText("������");
			jl3.setForeground(Color.black);
			submitState=true;
		}else if(e.getSource()==jpf2){
			jl5.setText("ȷ������");
			jl5.setForeground(Color.black);
			submitState=true;
		}else if(e.getSource()==jpf3){
			jl5.setText("ȷ������");
			jl5.setForeground(Color.black);
			submitState=true;
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==jtf2){
			if("".equals(jtf2.getText())){
				jl2.setText(jl2.getText());
				jl2.setForeground(Color.red);	
				submitState=false;
			}
		}else if(e.getSource()==jpf1){
			if(TbAdmin.admin!=null){
				if(!TbAdmin.admin.getApass().equals(new String(jpf1.getPassword()))){
					jl3.setText(jl3.getText()+"��");
					jl3.setForeground(Color.red);
					submitState=false;
				}
			}else{
				if(!TbUser.user.getUpass().equals(new String(jpf1.getPassword()))){
					jl3.setText(jl3.getText()+"��");
					jl3.setForeground(Color.red);
					submitState=false;
				}
			}	
		}else if(e.getSource()==jpf2){
			String s4=new String(jpf2.getPassword());
			String s5=new String(jpf3.getPassword());
			if(s4==null||s5==null||!s4.equals(s5)){
				jl5.setText(jl5.getText()+"��");
				jl5.setForeground(Color.red);
				submitState=false;
			}
		}else if(e.getSource()==jpf3){
			String s4=new String(jpf2.getPassword());
			String s5=new String(jpf3.getPassword());
			if(s4==null||s5==null||!s4.equals(s5)){
				jl5.setText(jl5.getText()+"��");
				jl5.setForeground(Color.red);
				submitState=false;
			}
		}
	}

}