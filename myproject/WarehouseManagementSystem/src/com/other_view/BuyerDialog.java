package com.other_view;

import javax.swing.*;

import com.domain.TbBuyer;
import com.domain.TbGood;
import com.domain.User;
import com.service.BuyerService;
import com.service.GoodService;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyerDialog extends JDialog implements ActionListener{
	
	//��������Ҫ��swing���
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JButton jb1,jb2,jb3;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	
	BuyerService buyerService;
	String bno;//��¼��һ����¼
	
	//owner���ĸ�����
	//title������
	//modalָ����ģʽ���ڣ����Ƿ�ģʽ����
	public BuyerDialog(JFrame owner,String title,boolean modal,BuyerService buyerService,int rowNums,String type)
	{
		super(owner,title,modal);//���ø��๹�췽�����ﵽģʽ�Ի���Ч��
		jl1=new JLabel("�ɹ�Ա����");
		jl2=new JLabel("�Ա�");
		jl3=new JLabel("��ϵ��ʽ");
		jl4=new JLabel("��ϵ��ַ");	
		
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();
		jtf4=new JTextField();		
		
		jb1=new JButton("���");
		if(type.equals("update")){
			jb3=new JButton("�޸�");
			jb3.addActionListener(this);
			bno=buyerService.getValueAt(rowNums, 0).toString();
			jtf1.setText(buyerService.getValueAt(rowNums, 1).toString());
			jtf2.setText(buyerService.getValueAt(rowNums, 2).toString());
			jtf3.setText(buyerService.getValueAt(rowNums, 3).toString());
			jtf4.setText(buyerService.getValueAt(rowNums, 4).toString());
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
		// ��ʾΪģ̬�Ի���
		this.setModal(modal);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//ʵ����goodService
		buyerService = new BuyerService();
		// TODO �Զ����ɵķ������
		if(arg0.getSource()==jb1){
			//ϣ�����
			TbBuyer b=new TbBuyer();
			b.setBname(jtf1.getText());
			b.setBsex(jtf2.getText());
			b.setBmethod(jtf3.getText());
			b.setBaddress(jtf4.getText());
			if(buyerService.addBuyer(b)){
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
			TbBuyer b = new TbBuyer();
			b.setBno(Integer.parseInt(this.bno));
			b.setBname(jtf1.getText());
			b.setBsex(jtf2.getText());
			b.setBmethod(jtf3.getText());
			b.setBaddress(jtf4.getText());;
			if(buyerService.updBuyer(b)){
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