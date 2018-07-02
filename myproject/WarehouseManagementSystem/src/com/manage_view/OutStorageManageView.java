package com.manage_view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.domain.TbKc;
import com.other_view.BuyerDialog;
import com.other_view.ClientDialog;
import com.other_view.GoodDialog;
import com.other_view.InStorageDialog;
import com.other_view.OutStorageDialog;
import com.service.BuyerService;
import com.service.ClientService;
import com.service.GoodService;
import com.service.InStorageService;
import com.service.KcService;
import com.service.OutStorageService;
import com.service.PickerService;
import com.view.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OutStorageManageView extends JPanel implements ActionListener,KeyListener{
	//��������Ҫ�Ŀؼ�
	JPanel jp1,jp1_1,jp1_2,jp2;
	JLabel jl1;
	JTextField jtf;
	JButton jb1,jb2,jb3,jb4;
	
	public static JTable jt;
	JScrollPane jsp;
	//����ģ�� ���ֹ���һ��Service jt
	public static OutStorageService outStorageService;
	public static PickerService pickerService;
	//�ɹ�Ա����ģ��
	PickerManageView pickerManageView;
	
	public static void main(String[] args) {
		
	}	
	public OutStorageManageView(){
		//�ϲ���
		jl1=new JLabel("��������ⵥ��");
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
		
		jp1=new JPanel(new BorderLayout());
		jp1_1=new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp1_1.add(jl1);
		jp1_1.add(jtf);
		jp1_1.add(jb1);
		
		outStorageService=new OutStorageService();
		String hql="from TbOutStorage";
		String paramer[]={};
		outStorageService.queryOutStorage(hql, paramer);
		jt=new JTable(outStorageService);
		// ����table���ݾ���
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
//	    tcr.setHorizontalAlignment(SwingConstants.CENTER);// �����Ͼ�����һ��
	    jt.setDefaultRenderer(Object.class, tcr);
		jsp=new JScrollPane(jt);
		
		jp1_2=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp1_2.add(jb2);
		jp1_2.add(jb3);
		jp1_2.add(jb4);
		
		jp1.add(jp1_1, "North");
		jp1.add(jsp, "Center");
		jp1.add(jp1_2, "South");
		jp1.setPreferredSize(new Dimension(800, 300));//����JPane�Ĵ�С
		
		//�²���
		jp2=new JPanel(new BorderLayout());
		pickerManageView = new PickerManageView();
		jp2.add(pickerManageView);
		jp2.setPreferredSize(new Dimension(800, 300));
		
		//����BorderLayout����
		this.setLayout(new BorderLayout());
		this.add(jp1,"North");
		this.add(jp2,"Center");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){
			//��ʾ�û����ѯ
			String name=this.jtf.getText().trim();
			outStorageService.updateOutStorageView(name);
		}else if(e.getSource()==jb2){
			//��ʾ����û�  true: ����Dialog��ɲ�����������ִ�� false: ���� ����ִ��
			OutStorageDialog outStorageDialog=new OutStorageDialog(null, "��ӳ�����Ϣ", true, null, 0, "add");
			outStorageDialog.setVisible(true);
			//����
			outStorageService.updateOutStorageView(null);
			//���¿��
			KcManageView.kcService.updateKcView(null);
		}else if(e.getSource()==jb4){
			//��ʾɾ���ͻ�
			//getSelectedRow�᷵���û����е���
			//������û�һ�ж�ûѡ�񣬾ͷ���-1
			int rowNum=this.jt.getSelectedRow();
			if(rowNum==-1)
			{
				//��ʾ
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return ;//���ص�������
			}
			//�õ��û���� �õ���rowNum�е�0�У�Object->String��
			String ono=outStorageService.getValueAt(rowNum, 0).toString();
			int i=JOptionPane.showConfirmDialog(this, "ȷ��ɾ����?");
			if(i==0){
				//��ʾȷ��ɾ��
				if(outStorageService.delOutStorage(ono)){
					JOptionPane.showMessageDialog(this, "ɾ���ɹ�!");
				}else{
					JOptionPane.showMessageDialog(this, "ʧ�ܳɹ�!");
				}
			}
			//����
			outStorageService.updateOutStorageView(null);
		}else if(e.getSource()==jb3){
			//�û����޸�
			//ѡ��һ��
			int rowNums=this.jt.getSelectedRow();
			if(rowNums==-1){
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return;
			}
			OutStorageDialog outStorageDialog=new OutStorageDialog(null, "�޸ĳ�����Ϣ", true, outStorageService, rowNums,"update");
			outStorageDialog.setVisible(true);
			//����
			outStorageService.updateOutStorageView(null);
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
			outStorageService.updateOutStorageView(data);
        } 
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}

}
