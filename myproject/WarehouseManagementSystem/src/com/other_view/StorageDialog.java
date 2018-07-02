package com.other_view;

import javax.swing.*;

import com.domain.TbAdmin;
import com.domain.TbClient;
import com.domain.TbGood;
import com.domain.TbStorage;
import com.domain.User;
import com.service.AdminService;
import com.service.ClientService;
import com.service.GoodService;
import com.service.InStorageService;
import com.service.PickerService;
import com.service.StorageService;
import com.service.StorageService.Item;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StorageDialog extends JDialog implements ActionListener{
	
	//��������Ҫ��swing���
	JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7;
	JButton jb1,jb2,jb3;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	JComboBox jcb1;
	
	StorageService storageService;
	AdminService adminService;
	String sno;//��¼��һ����¼
	
	//owner���ĸ�����
	//title������
	//modalָ����ģʽ���ڣ����Ƿ�ģʽ����
	public StorageDialog(JFrame owner,String title,boolean modal,StorageService storageService,int rowNums,String type)
	{
		super(owner,title,modal);//���ø��๹�췽�����ﵽģʽ�Ի���Ч��
		jl1=new JLabel("�ֿ�����");
		jl2=new JLabel("�ֿ��ַ");
		jl3=new JLabel("�ֿ��С");
		jl4=new JLabel("����Ա");
		
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();

		jcb1=new JComboBox(new StorageService().getAllName("admin"));
		
		jb1=new JButton("���");
		if(type.equals("update")){
			jb3=new JButton("�޸�");
			jb3.addActionListener(this);
			sno=storageService.getValueAt(rowNums, 0).toString();
			jtf1.setText(storageService.getValueAt(rowNums, 1).toString());
			jtf2.setText(storageService.getValueAt(rowNums, 2).toString());
			jtf3.setText(storageService.getValueAt(rowNums, 3).toString());
			jcb1.setSelectedIndex(0);//string�������ֵ�ҵ��±�	
		}
		jb1.addActionListener(this);
		jb2=new JButton("ȡ��");
		jb2.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//���ò���
		jp1.setLayout(new GridLayout(4,1));
		jp2.setLayout(new GridLayout(4,1));
		
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jcb1);
		
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
		// ��ʾΪģ̬�Ի���
		this.setModal(modal);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//ʵ����
		storageService = new StorageService();
		adminService = new AdminService();
		// TODO �Զ����ɵķ������
		if(arg0.getSource()==jb1){
			//ϣ�����
			TbStorage s=new TbStorage();
			s.setSname(jtf1.getText());
			s.setSadress(jtf2.getText());
			s.setSbig(Long.parseLong(jtf3.getText()));
			s.setTbAdmin((TbAdmin)(((Item)(jcb1.getSelectedItem())).getObject()));
			if(storageService.addStorage(s)){
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
			TbStorage s = new TbStorage();
			s.setSno(Integer.parseInt(this.sno));
			s.setSname(jtf1.getText());
			s.setSadress(jtf2.getText());
			s.setSbig(Long.parseLong(jtf3.getText()));
			s.setTbAdmin((TbAdmin)(((Item)(jcb1.getSelectedItem())).getObject()));
			if(storageService.updStorage(s)){
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