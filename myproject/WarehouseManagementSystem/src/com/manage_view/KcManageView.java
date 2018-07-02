package com.manage_view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.domain.TbUser;
import com.other_view.ClientDialog;
import com.other_view.GoodDialog;
import com.other_view.KcDialog;
import com.other_view.StorageDialog;
import com.service.ClientService;
import com.service.GoodService;
import com.service.KcService;
import com.service.StorageService;
import com.view.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KcManageView extends JPanel implements ActionListener,KeyListener{
	//��������Ҫ�Ŀؼ�
	JPanel jp1,jp2;
	JLabel jl1;
	JTextField jtf;
	JButton jb1,jb2,jb3,jb4;
	
	public static JTable jt;
	JScrollPane jsp;
	//����ģ�� ���ֹ���һ��Service jt
	public static KcService kcService;
	public static void main(String[] args) {
		
	}	
	public KcManageView(){
		jl1=new JLabel("�������浥�� �� �ֿ���");
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
		kcService=new KcService();
		String hql="from TbKc";
		String paramer[]={};
		kcService.queryKc(hql, paramer);
		jt=new JTable(kcService);
		// ����table���ݾ���
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
//	    tcr.setHorizontalAlignment(SwingConstants.CENTER);// �����Ͼ�����һ��
	    jt.setDefaultRenderer(Object.class, tcr);
		jsp=new JScrollPane(jt);
		
		//�²���
		jp2=new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		
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
			kcService.updateKcView(name);
		}else if(e.getSource()==jb2){
//			//��ʾ����û�  true: ����Dialog��ɲ�����������ִ�� false: ���� ����ִ��
//			KcDialog storageDialog=new KcDialog(null, "��Ӳֿ�", true, null, 0, "add");
//			storageDialog.setVisible(true);
//			//����
//			storageService.updateStorageView(null);
		}else if(e.getSource()==jb4){
			if(TbUser.user!=null){
				JOptionPane.showMessageDialog(this, "��ͨ�û�����ɾ��");
				return ;//���ص�������
			}
			
			//��ʾɾ��
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
			String kno=kcService.getValueAt(rowNum, 0).toString();
			int i=JOptionPane.showConfirmDialog(this, "ȷ��ɾ����?");
			if(i==0){
				//��ʾȷ��ɾ��
				if(kcService.delKc(kno)){
					JOptionPane.showMessageDialog(this, "ɾ���ɹ�!");
				}else{
					JOptionPane.showMessageDialog(this, "ʧ�ܳɹ�!");
				}
			}
			//����
			kcService.updateKcView(null);
		}else if(e.getSource()==jb3){
			if(TbUser.user!=null){
				JOptionPane.showMessageDialog(this, "��ͨ�û������޸�");
				return ;//���ص�������
			}
			
			//�û����޸�
			//ѡ��һ��
			int rowNums=this.jt.getSelectedRow();
			if(rowNums==-1){
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return;
			}
			KcDialog kcDialog=new KcDialog(null, "�޸Ĳֿ�", true, kcService, rowNums,"update");
			kcDialog.setVisible(true);
			//����
			kcService.updateKcView(null);
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
			kcService.updateKcView(data);
        }  
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}

}
