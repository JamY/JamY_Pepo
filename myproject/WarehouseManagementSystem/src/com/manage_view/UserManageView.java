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
	//��������Ҫ�Ŀؼ�
	JPanel jp1,jp2;
	JLabel jl1;
	JTextField jtf;
	JButton jb1,jb2,jb3,jb4;
	
	public static JTable jt;
	JScrollPane jsp;
	//����ģ�� ���ֹ���һ��Service jt
	public static UserService userService;
	public static void main(String[] args) {
		
	}	
	public UserManageView(){
		jl1=new JLabel("�������û�����");
		jtf=new JTextField(20);
		jtf.addKeyListener(this);
		jb1=new JButton("��ѯ");
		jb1.addActionListener(this);
		jb2=new JButton("���");
		jb2.addActionListener(this);
		jb3=new JButton("�޸�");
		jb3.addActionListener(this);
		jb4=new JButton("ɾ��");
		jb4.addActionListener(this);
		
		//�ϲ���
		jp1=new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp1.add(jl1);
		jp1.add(jtf);
		jp1.add(jb1);
		
		//�м䲿��
		userService=new UserService();
		String hql="from TbUser";
		String paramer[]={};
		userService.queryUser(hql, paramer);
		jt=new JTable(userService);
		// ����table���ݾ���
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
	    jt.setDefaultRenderer(Object.class, tcr);
		jsp=new JScrollPane(jt);
		
		//�²���
		jp2=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		if(TbUser.user==null){
			jp2.add(jb2);
			jp2.add(jb3);
			jp2.add(jb4);
		}
		
		//����BorderLayout����
		this.setLayout(new BorderLayout());
		this.add(jp1,"North");
		this.add(jsp,"Center");
		this.add(jp2,"South");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){
			//��ʾ�û����ѯ
			String name=this.jtf.getText().trim();
			userService.updateUserView(name);
		}else if(e.getSource()==jb2){
			//��ʾ����û�  true: ����Dialog��ɲ�����������ִ�� false: ���� ����ִ��
			UserDialog addUserView=
					new UserDialog(null, "����û�", true, null, 0, "add");
			addUserView.setVisible(true);
			//����
			userService.updateUserView(null);
		}else if(e.getSource()==jb4){
			//��ʾɾ���û�
			int rowNum=this.jt.getSelectedRow();
			if(rowNum==-1)
			{
				//��ʾ
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return ;//���ص�������
			}
			//��ServiceΪ��ʼ���Ǹ�����ģ��
			String uno=userService.getValueAt(rowNum, 0).toString();
			int i=JOptionPane.showConfirmDialog(this, "ȷ��ɾ����?");
			if(i==0){
				//��ʾȷ��ɾ��
				if(userService.delUser(uno)){
					JOptionPane.showMessageDialog(this, "ɾ���ɹ�!");
				}else{
					JOptionPane.showMessageDialog(this, "ʧ�ܳɹ�!");
				}
			}
			//����
			userService.updateUserView(null);
		}else if(e.getSource()==jb3){
			//�û����޸�
			int rowNums=this.jt.getSelectedRow();
			if(rowNums==-1){
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return;
			}
			UserDialog updUserView=
					new UserDialog(null, "�޸��û�", true, userService, 
							rowNums,"update");
			updUserView.setVisible(true);
			//����
			userService.updateUserView(null);
		}	
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
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
		// TODO �Զ����ɵķ������
		
	}

}
