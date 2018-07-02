package com.test;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Vector;

public class JComboBoxTest{
    public static void main(String[] args){
        JFrame f=new JFrame("JComboBox1");
        Container contentPane=f.getContentPane();
        contentPane.setLayout(new GridLayout(1,2));
        String[] s = {"美国","日本","大陆","英国","法国","意大利","澳洲","韩国"};
        Vector v=new Vector();
        v.addElement("Nokia 8850");
        v.addElement("Nokia 8250");
        v.addElement("Motorola v8088");
        v.addElement("Motorola v3850");
        v.addElement("Panasonic 8850");
        v.addElement("其它");
        
        Vector v1=new Vector();
        Item item =new Item(0, "aa");
        Item item1 =new Item(1, "bb");
        v1.add(item);
        v1.add(item1);
        JComboBox combo1=new JComboBox(s);
        combo1.addItem("中国");//利用JComboBox类所提供的addItem()方法，加入一个项目到此JComboBox中。
        combo1.setBorder(BorderFactory.createTitledBorder("你最喜欢到哪个国家玩呢?"));
        JComboBox combo2=new JComboBox(v);
        combo2.setBorder(BorderFactory.createTitledBorder("你最喜欢哪一种手机呢？")); 
        JComboBox combo3=new JComboBox(v1); 	
        combo3.setBorder(BorderFactory.createTitledBorder("你最喜欢哪一种手机呢？"));   
        contentPane.add(combo1);
        contentPane.add(combo2);
        contentPane.add(combo3);
        f.pack();
        f.show();
        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0); 
                }
        });
    }
    
} 
class Item{
	int key;
	String name;
	public Item(int key,String name){
		this.key=key;
		this.name=name;
	}
}
