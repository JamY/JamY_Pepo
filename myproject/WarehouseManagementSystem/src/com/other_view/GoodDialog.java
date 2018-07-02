package com.other_view;

import javax.swing.*;

import com.domain.TbGood;
import com.domain.User;
import com.service.GoodService;
import com.util.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GoodDialog extends JDialog implements ActionListener,MouseListener{
	
	//��������Ҫ��swing���
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JButton jb1,jb2,jb3;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	
	GoodService goodService;
	String gno;//��¼��һ����¼
	
	//owner���ĸ�����
	//title������
	//modalָ����ģʽ���ڣ����Ƿ�ģʽ����
	public GoodDialog(JFrame owner,String title,boolean modal,GoodService goodService,int rowNums,String type)
	{
		super(owner,title,modal);//���ø��๹�췽�����ﵽģʽ�Ի���Ч��
		jl1=new JLabel("�������");
		jl2=new JLabel("��������");
		jl3=new JLabel("��������");
		jl4=new JLabel("��������");
		jl5=new JLabel("���ﵥ��");	
		
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();
		jtf3.addMouseListener(this);
		jtf4=new JTextField();
		jtf5=new JTextField();
		
		
		jb1=new JButton("���");
		if(type.equals("update")){
			jb3=new JButton("�޸�");
			jb3.addActionListener(this);
			jl6=new JLabel("���ʱ��");
			jtf6=new JTextField();
			jtf6.addMouseListener(this);
			gno=goodService.getValueAt(rowNums, 0).toString();
			jtf1.setText(goodService.getValueAt(rowNums, 1).toString());
			jtf2.setText(goodService.getValueAt(rowNums, 2).toString());
			jtf3.setText(goodService.getValueAt(rowNums, 3).toString());
			jtf4.setText(goodService.getValueAt(rowNums, 4).toString());
			jtf5.setText(goodService.getValueAt(rowNums, 5).toString().replace("Ԫ", ""));
			jtf6.setText(goodService.getValueAt(rowNums, 6).toString());
		}
		jb1.addActionListener(this);
		jb2=new JButton("ȡ��");
		jb2.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//���ò���
		if(type.equals("add")){
			jp1.setLayout(new GridLayout(5,1));
			jp2.setLayout(new GridLayout(5,1));
		}else if(type.equals("update")){
			jp1.setLayout(new GridLayout(6,1));
			jp2.setLayout(new GridLayout(6,1));
		}
		
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		jp2.add(jtf5);
		
		if(type.equals("add")){
			jp3.add(jb1);
		}else if(type.equals("update")){
			jp1.add(jl6);
			jp2.add(jtf6);
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
		goodService = new GoodService();
		// TODO �Զ����ɵķ������
		if(arg0.getSource()==jb1){
			//ϣ�����
			TbGood g=new TbGood();
			g.setGhao(jtf1.getText());
			g.setGname(jtf2.getText());
			g.setGdate(jtf3.getText());
			g.setGfactory(jtf4.getText());
			g.setGprice(Double.parseDouble(jtf5.getText()));
			Date date = new Date();
	        //����Ҫ��ȡ��ʲô����ʱ��
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //��ȡString���͵�ʱ��
	        String createdate = sdf.format(date);
			g.setIdate(createdate);
			if(goodService.addGood(g)){
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
			TbGood g = new TbGood();
			g.setGno(Integer.parseInt(this.gno));
			g.setGhao(jtf1.getText());
			g.setGname(jtf2.getText());
			g.setGdate(jtf3.getText());
			g.setGfactory(jtf4.getText());;
			g.setGprice(Double.parseDouble(jtf5.getText()));
			g.setIdate(jtf6.getText());
			if(goodService.updGood(g)){
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
		if(e.getSource()==jtf3){
			JDateChooser gg = new JDateChooser();
			System.out.println("ok");
			gg.showDateChooser();
			jtf3.setText(gg.getDateFormat("yyyy-MM-dd"));
		}else if(e.getSource()==jtf6){
			JDateChooser gg = new JDateChooser();
			System.out.println("ok");
			gg.showDateChooser();
			jtf6.setText(gg.getDateFormat("yyyy-MM-dd"));
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