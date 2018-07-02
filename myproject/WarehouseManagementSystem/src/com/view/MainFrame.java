package com.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.domain.TbAdmin;
import com.domain.TbUser;
import com.manage_view.BuyerManageView;
import com.manage_view.ClientManageView;
import com.manage_view.GoodManageView;
import com.manage_view.InStorageManageView;
import com.manage_view.KcManageView;
import com.manage_view.OutStorageManageView;
import com.manage_view.PickerManageView;
import com.manage_view.StorageManageView;
import com.manage_view.UserManageView;
import com.other_view.ModPassDialog;
import com.other_view.AlertDialog;
import com.other_view.UserDialog;
import com.service.KcService;
import com.service.KcService.OnAUpdateListener;
import com.tools.MyTools;
import com.test.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MainFrame extends JFrame implements ActionListener,MouseListener,OnAUpdateListener{
	JMenuBar jmb;
	//一级菜单
	JMenu jm1,jm2,jm3,jm4;
	//二级菜单
	JMenuItem jm1_item,jm1_item2,jm1_item3,jm1_item4,jm1_item5;
	JMenuItem jm2_item,jm2_item2,jm2_item3;
	JMenuItem jm3_item,jm3_item2,jm3_item3,jm3_item4;
	JMenuItem jm4_item,jm4_item2;
	//三级菜单
	JMenuItem jm3_item4_1,jm3_item4_2,jm3_item4_3,jm3_item4_4,jm3_item4_5,jm3_item4_6;
	
	JLabel tubiao;
	
	CardLayout cl;	
	JPanel jp1,jp2,jp3,jp4;
	
	JLabel alert;
	JPanel jp_alert;
	javax.swing.Timer t;
	JLabel timeNow;
	JLabel operator;
	KcService k;
	
	GoodManageView goodManageView; 
	ClientManageView clientManageView;
	StorageManageView storageManageView;
	BuyerManageView buyerManageView;
	PickerManageView pickerManageView;
	InStorageManageView inStorageManageView;
	OutStorageManageView outStorageManageView;
	KcManageView kcManageView;
	UserManageView userManageView;
	

	public static void main(String args[]){
		MainFrame mf=new MainFrame();	
	}
	public MainFrame(){
		//统一字体
		MyTools.InitGlobalFont(MyTools.f2);
		//监听KcService中的alertNum变化
		k=new KcService();
		k.setOnAUpdateListener(this);
		
		//上方
		jmb=new JMenuBar();
		//图标
		ImageIcon img = new ImageIcon("image/功能.png");
		img.setImage(img.getImage().getScaledInstance(15,15,Image.SCALE_DEFAULT));
		tubiao=new JLabel(img);
		
		jm1 = new JMenu("基本信息");
		jm2 = new JMenu("库存管理");
		jm3 = new JMenu("系统维护");
		jm4 = new JMenu("退出系统");
		
		jm1_item = new JMenuItem("货物资料");
		jm1_item.addActionListener(this);
		jm1_item2 = new JMenuItem("客户资料");
		jm1_item2.addActionListener(this);
		jm1_item3 = new JMenuItem("仓库资料");
		jm1_item3.addActionListener(this);
		jm1_item4 = new JMenuItem("采购员资料");
		jm1_item4.addActionListener(this);
		jm1_item5 = new JMenuItem("提货员资料");
		jm1_item5.addActionListener(this);
		
		jm2_item = new JMenuItem("货物入库");
		jm2_item.addActionListener(this);
		jm2_item2 = new JMenuItem("货物出库");
		jm2_item2.addActionListener(this);
		jm2_item3 = new JMenuItem("库存盘点");
		jm2_item3.addActionListener(this);
		
		jm3_item = new JMenuItem("用户管理");
		jm3_item.addActionListener(this);
		jm3_item2 = new JMenuItem("管理员管理");
		jm3_item2.addActionListener(this);
		jm3_item3 = new JMenuItem("密码修改");
		jm3_item3.addActionListener(this);
		jm3_item4 = new JMenu("系统主题");
		
		jm3_item4_1 = new JMenuItem("Metal风格");
		jm3_item4_1.addActionListener(this);
		jm3_item4_2 = new JMenuItem("Windows风格");
		jm3_item4_2.addActionListener(this);
		jm3_item4_3 = new JMenuItem("Windows Classic风格");
		jm3_item4_3.addActionListener(this);
		jm3_item4_4 = new JMenuItem("Motif风格");
		jm3_item4_4.addActionListener(this);
		jm3_item4_5 = new JMenuItem("可跨平台的默认风格");
		jm3_item4_5.addActionListener(this);
		jm3_item4_6 = new JMenuItem("当前系统的风格");
		jm3_item4_6.addActionListener(this);
		jm3_item4.add(jm3_item4_1);
		jm3_item4.add(jm3_item4_2);
		jm3_item4.add(jm3_item4_3);
		jm3_item4.add(jm3_item4_4);
		jm3_item4.add(jm3_item4_5);
		jm3_item4.add(jm3_item4_6);
		
		jm4_item = new JMenuItem("退出");
		jm4_item.addActionListener(this);
		jm4_item2 = new JMenuItem("重新登录");
		jm4_item2.addActionListener(this);
		
		jp_alert = new JPanel();
		jp_alert.addMouseListener(this);
		String alertStr="以下货物不足，请及时补充▼";
		alert = new JLabel("                                       "+alertStr);
		alert.setFont(MyTools.f3);
		alert.setForeground(Color.red);
		jp_alert.add(alert);
		
		//下方 (操作员+时间)
		jp4=new JPanel(new BorderLayout());
		//创建Timer
		t=new Timer(1000, this);//每隔1秒触发ActionEvent
		//启动定时器
		t.start();//toString()或toLocaleSting按中国人年历
		timeNow=new JLabel(Calendar.getInstance().getTime().toLocaleString()+"   ");
		timeNow.setFont(MyTools.f1);
		if(TbAdmin.admin!=null){
			operator=new JLabel("操作员:"+TbAdmin.admin.getAname()+"-管理员");
		}else if(TbUser.user!=null){
			operator=new JLabel("操作员:"+TbUser.user.getUname()+"-普通用户");
		}else{
			operator=new JLabel("操作员:未登陆");
		}
		operator.setFont(MyTools.f1);
		jp4.add(operator, "West");
	    jp4.add(timeNow, "East");
		
	    //中间
		jm1.add(jm1_item);
		jm1.add(jm1_item2);
		jm1.add(jm1_item3);
//		jm1.add(jm1_item4);//未实现
//		jm1.add(jm1_item5);
		jm2.add(jm2_item);
		jm2.add(jm2_item2);
		jm2.add(jm2_item3);
		jm3.add(jm3_item);
//		jm3.add(jm3_item2);管理员管理未实现
		jm3.add(jm3_item3);
		jm3.add(jm3_item4);
		jm4.add(jm4_item);
		jm4.add(jm4_item2);
		jmb.add(tubiao);
		jmb.add(jm1);
		jmb.add(jm2);
		jmb.add(jm3);
		jmb.add(jm4);
						
		cl=new CardLayout();
		jp1=new JPanel(cl);

		goodManageView = new GoodManageView();
		clientManageView = new ClientManageView();
		storageManageView = new StorageManageView();
//		buyerManageView = new BuyerManageView();
//		pickerManageView = new PickerManageView();
		inStorageManageView = new InStorageManageView();
		outStorageManageView = new OutStorageManageView();
		kcManageView = new KcManageView();	
		userManageView =new UserManageView();

		jp1.add(goodManageView, "1");
		jp1.add(clientManageView, "2");
		jp1.add(storageManageView, "3");
//		jp1.add(buyerManageView, "4");
//		jp1.add(pickerManageView, "5");
		jp1.add(inStorageManageView, "6");
		jp1.add(outStorageManageView, "7");
		jp1.add(kcManageView, "8");
		jp1.add(userManageView, "9");
		
		this.add(jp1,"Center");
		this.add(jp4, "South");
		
		//添加控件
		this.setJMenuBar(jmb);
		
		this.setSize(800,600);
		//居中显示
		this.setLocationRelativeTo(null);
		this.setTitle("物流仓库系统");
		this.setVisible(true);
		//设置头像
		try {
			Image im=ImageIO.read(new File("image/物流.png"));
			this.setIconImage(im);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jm1_item){		
			this.cl.show(jp1, "1");
		}else if(e.getSource()==jm1_item2){		
			this.cl.show(jp1,"2");
		}else if(e.getSource()==jm1_item3){
			this.cl.show(jp1, "3");
		}else if(e.getSource()==jm1_item4){
			this.cl.show(jp1, "4");
		}else if(e.getSource()==jm1_item5){
			this.cl.show(jp1, "5");
		}else if(e.getSource()==jm2_item){
			this.cl.show(jp1, "6");
		}else if(e.getSource()==jm2_item2){
			this.cl.show(jp1, "7");
		}else if(e.getSource()==jm2_item3){
			this.cl.show(jp1, "8");
		}else if(e.getSource()==jm3_item){
			this.cl.show(jp1, "9");
		}else if(e.getSource()==jm4_item){
			System.exit(0);
		}else if(e.getSource()==jm4_item2){
			TbAdmin.admin=null;
			TbUser.user=null;
			new Index();
			this.dispose();
		}else if(e.getSource()==t){
			//每隔一秒获得当前时间
			this.timeNow.setText("当前时间："+Calendar.getInstance().getTime().toLocaleString()+"   ");
		}else if(e.getSource()==jm3_item3){
			if(TbAdmin.admin!=null||TbUser.user!=null){
				ModPassDialog dialog = new ModPassDialog(null, "密码修改", true);
				dialog.setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "未登陆");
			}
		}else if(e.getSource()==jm3_item4_1){
			try {
				//设置应用程序界面外观
				String lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
				UIManager.setLookAndFeel(lookAndFeel);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else if(e.getSource()==jm3_item4_2){
			try {
				String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
				UIManager.setLookAndFeel(lookAndFeel);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else if(e.getSource()==jm3_item4_3){
			try {
				//设置应用程序界面外观
				String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
				UIManager.setLookAndFeel(lookAndFeel);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else if(e.getSource()==jm3_item4_4){
			try {
				//设置应用程序界面外观
				String lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
				UIManager.setLookAndFeel(lookAndFeel);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else if(e.getSource()==jm3_item4_5){
			try {
				String lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
				UIManager.setLookAndFeel(lookAndFeel);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else if(e.getSource()==jm3_item4_6){
			try {
				String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
				UIManager.setLookAndFeel(lookAndFeel);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==jp_alert){
			AlertDialog dialog = new AlertDialog(this, "仓库预警", true);
			dialog.setVisible(true);
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void updateListener(int alertNum) {
		// TODO 自动生成的方法存根
		if(alertNum>0){
			//添加警告
			jmb.add(jp_alert);
			KcService.alertNum=0;
		}else{
			//移除
			jmb.remove(jp_alert);
		}
	}
	
}
