package com.test;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
public class Demo {
 public static void main(String[] args) {
  Demo demo = new Demo();
  demo.run();
 }
 
 public void run() {
  JFrame frame = new JFrame("title1");
  frame.setLayout(null);
  frame.setBounds(10, 10, 500, 300);
  
  Button button = new Button("click");
  button.setBounds(15, 15, 200, 100);
  frame.add(button);
  frame.setVisible(true);
button.addActionListener(new ActionListener() {
   
   @Override
   public void actionPerformed(ActionEvent e) {
    JFrame frame = new JFrame("title2");
    frame.setLayout(null);
    frame.setBounds(20, 20, 300, 100);
    frame.setVisible(true);
   }
  });
 }
}