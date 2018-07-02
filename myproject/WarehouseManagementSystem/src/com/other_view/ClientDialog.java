package com.other_view;

import javax.swing.*;

import com.domain.TbAdmin;
import com.domain.TbClient;
import com.domain.TbGood;
import com.domain.TbPicker;
import com.domain.User;
import com.service.ClientService;
import com.service.ClientService.Item;
import com.service.GoodService;
import com.service.PickerService;
import com.service.StorageService;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientDialog extends JDialog implements ActionListener{
	
	//��������Ҫ��swing���
	JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7;
	JButton jb1,jb2,jb3;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	JComboBox jcb1;
	
	ClientService clientService;
	PickerService pickerService;
	String cno;//��¼��һ����¼
	
	//owner���ĸ�����
	//title������
	//modalָ����ģʽ���ڣ����Ƿ�ģʽ����
	public ClientDialog(JFrame owner,String title,boolean modal,ClientService clientService,int rowNums,String type)
	{
		super(owner,title,modal);//���ø��๹�췽�����ﵽģʽ�Ի���Ч��
		jl1=new JLabel("�ͻ�����");
		jl2=new JLabel("�ͻ����");
		jl3=new JLabel("��ϵ��ʽ");
		jl4=new JLabel("��������");
		jl5=new JLabel("�绰");	
		jl6=new JLabel("��ע");
		jl7=new JLabel("���Ա");
		
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();
		jtf4=new JTextField();
		jtf5=new JTextField();
		jtf6=new JTextField();

		jcb1=new JComboBox(new ClientService().getAllName("picker"));
		//����ComboBox�߿�
//		jcb1.setBorder(BorderFactory.createTitledBorder("����ϲ�����ĸ���������?"));
		
		
		jb1=new JButton("���");
		if(type.equals("update")){
			jb3=new JButton("�޸�");
			jb3.addActionListener(this);
			cno=clientService.getValueAt(rowNums, 0).toString();
			jtf1.setText(clientService.getValueAt(rowNums, 1).toString());
			jtf2.setText(clientService.getValueAt(rowNums, 2).toString());
			jtf3.setText(clientService.getValueAt(rowNums, 3).toString());
			jtf4.setText(clientService.getValueAt(rowNums, 4).toString());
			jtf5.setText(clientService.getValueAt(rowNums, 5).toString());
			jtf6.setText(clientService.getValueAt(rowNums, 6).toString());
			
			jcb1.setSelectedIndex(0);//string�������ֵ�ҵ��±�

			
		}
		jb1.addActionListener(this);
		jb2=new JButton("ȡ��");
		jb2.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//���ò���
		jp1.setLayout(new GridLayout(7,1));
		jp2.setLayout(new GridLayout(7,1));
		
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);
		jp1.add(jl7);
		
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		jp2.add(jtf5);
		jp2.add(jtf6);
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
		this.setSize(300,250);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setResizable(false);
		// ��ʾΪģ̬�Ի���
		this.setModal(modal);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//ʵ����
		clientService = new ClientService();
		pickerService = new PickerService();
		// TODO �Զ����ɵķ������
		if(arg0.getSource()==jb1){
			//ϣ�����
			TbClient c=new TbClient();
			c.setCname(jtf1.getText());
			c.setCtype(jtf2.getText());
			c.setCmethod(jtf3.getText());
			c.setCcode(jtf4.getText());
			c.setCphone(Long.parseLong(jtf5.getText()));
			c.setCbz(jtf6.getText());
			c.setTbPicker((TbPicker)(((Item)(jcb1.getSelectedItem())).getObject()));
			if(clientService.addClient(c)){
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
			TbClient c = new TbClient();
			c.setCno(Integer.parseInt(this.cno));
			c.setCname(jtf1.getText());
			c.setCtype(jtf2.getText());
			c.setCmethod(jtf3.getText());
			c.setCcode(jtf4.getText());
			c.setCphone(Long.parseLong(jtf5.getText()));
			c.setCbz(jtf6.getText());
			c.setTbPicker((TbPicker)(((Item)(jcb1.getSelectedItem())).getObject()));
			if(clientService.updClient(c)){
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