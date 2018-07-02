package com.other_view;

import javax.swing.*;

import com.domain.TbBuyer;
import com.domain.TbClient;
import com.domain.TbGood;
import com.domain.TbInStorage;
import com.domain.TbKc;
import com.domain.TbStorage;
import com.domain.User;
import com.service.ClientService;
import com.service.GoodService;
import com.service.InStorageService;
import com.service.InStorageService.Item;
import com.service.PickerService;
import com.service.StorageService;
import com.util.JDateChooser;

import java.awt.*;

import javax.swing.JComboBox;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class InStorageDialog extends JDialog implements ActionListener,MouseListener{
	
	//��������Ҫ��swing���
	JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7;
	JButton jb1,jb2,jb3;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	JComboBox jcb1,jcb2,jcb3;
	
	InStorageService inStorageService;
//	GoodService goodService = new GoodService();;
	String ino;//��¼��һ����¼
	
	//owner���ĸ�����
	//title������
	//modalָ����ģʽ���ڣ����Ƿ�ģʽ����
	public InStorageDialog(JFrame owner,String title,boolean modal,InStorageService inStorageService,int rowNums,String type)
	{
//		super(owner,title,modal);//���ø��๹�췽�����ﵽģʽ�Ի���Ч�� �ᵼ�¾���ʧ��
		jl1=new JLabel("��������");
		jl2=new JLabel("�ɹ�Ա");
		jl3=new JLabel("��Ųֿ�");
		jl4=new JLabel("�ջ���");
		jl5=new JLabel("�ɹ�����");
		jl6=new JLabel("�ɹ�����");	
		
		jcb1=new JComboBox(new InStorageService().getAllName("good"));
		jcb2=new JComboBox(new InStorageService().getAllName("buyer"));
		jcb3=new JComboBox(new InStorageService().getAllName("storage"));
		jtf1=new JTextField();
		jtf2=new JTextField();	
		jtf2.addMouseListener(this);
		jtf3=new JTextField();
		
		jb1=new JButton("���");
		if(type.equals("update")){
			jb3=new JButton("�޸�");
			jb3.addActionListener(this);
			ino=inStorageService.getValueAt(rowNums, 0).toString();
			jtf1.setText(inStorageService.getValueAt(rowNums, 4).toString());
			jtf2.setText(inStorageService.getValueAt(rowNums, 5).toString());
			jtf3.setText(inStorageService.getValueAt(rowNums, 6).toString());
			
			//������Ҫ�Ľ�
			jcb1.setSelectedIndex(0);//string�������ֵ�ҵ��±�
			jcb2.setSelectedIndex(0);
			jcb3.setSelectedIndex(0);

			
		}
		jb1.addActionListener(this);
		jb2=new JButton("ȡ��");
		jb2.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//���ò���
		jp1.setLayout(new GridLayout(6,1));
		jp2.setLayout(new GridLayout(6,1));
		
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);
		
		jp2.add(jcb1);
		jp2.add(jcb2);
		jp2.add(jcb3);
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
		inStorageService = new InStorageService();
//		goodService = new GoodService();
		// TODO �Զ����ɵķ������
		if(arg0.getSource()==jb1){
			//ϣ�����
			TbInStorage i=new TbInStorage();
			TbGood g=(TbGood)(((Item)(jcb1.getSelectedItem())).getObject());
			TbBuyer b=(TbBuyer)(((Item)(jcb2.getSelectedItem())).getObject());
			TbStorage s=(TbStorage)(((Item)(jcb3.getSelectedItem())).getObject());
			String address=jtf1.getText();
			String date=jtf2.getText();
			int num=Integer.parseInt(jtf3.getText());
			i.setTbGood(g);
			i.setTbBuyer(b);
			i.setTbStorage(s);
			i.setIaddress(address);
			i.setIdate(date);
			i.setInum(num);
			if(inStorageService.addInStorage(i)){
				//this=��������������
				JOptionPane.showMessageDialog(this, "��ӳɹ�");
			}else{
				JOptionPane.showMessageDialog(this, "���ʧ��");
				return;
			}
			//�رնԻ�
			this.dispose();
			
			//���¿��
			TbKc kc=new TbKc();
			kc.setTbGood(g);
			kc.setTbStorage(s);
			kc.setKnum(num);
			kc.setKdate(date);
			
			if(inStorageService.updateKc(kc)){
				//this=��������������
				JOptionPane.showMessageDialog(this, "����Ѹ���");
			}else{
				JOptionPane.showMessageDialog(this, "������ʧ��");
				return;
			}
		}else if(arg0.getSource()==jb2){
			this.dispose();
		}else if(arg0.getSource()==jb3){
			//ϣ���޸�
			TbInStorage i = new TbInStorage();
			i.setIno(Integer.parseInt(this.ino));
			i.setTbGood((TbGood)(((Item)(jcb1.getSelectedItem())).getObject()));
			i.setTbBuyer((TbBuyer)(((Item)(jcb2.getSelectedItem())).getObject()));
			i.setTbStorage((TbStorage)(((Item)(jcb3.getSelectedItem())).getObject()));
			i.setIaddress(jtf1.getText());
			i.setIdate(jtf2.getText());
			i.setInum(Integer.parseInt(jtf3.getText()));
			if(inStorageService.updInStorage(i)){
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
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==jtf2){
			JDateChooser gg = new JDateChooser();
			gg.showDateChooser();
			jtf2.setText(gg.getDateFormat("yyyy-MM-dd"));
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