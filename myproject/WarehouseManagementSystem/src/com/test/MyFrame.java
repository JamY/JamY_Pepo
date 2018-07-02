package com.test;

import java.awt.*;

import javax.swing.JPanel;

public class MyFrame extends JPanel {
	static int x = 0, y = 120;
	static int i = 0;
	static int horizScroll = 1;
	Font fb = new Font("TimeRoman", Font.BOLD, 36);
	String msg[] = { "Java", "Portable", "Secure", "Easy" };
	Color color[] = { Color.blue, Color.yellow, Color.green, Color.red };

	public void paint(Graphics g) {
		g.setFont(fb);
		g.setColor(color[i]);
		g.drawString(msg[i], x, y);
		
		this.setSize(200, 200);
		int pixelsPerLine = 200, totalLines = 4;
		this.setVisible(true);
		for (int j = 0; j < pixelsPerLine * totalLines; j++) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			this.repaint();
			if (horizScroll == 1) {
				if ((x += 3) < 200) {
					continue;
				}
				i = ++i % 4;
				x = 50;
				y = 0;
				horizScroll = 0;
			} else {
				if ((x += 3) < 200) {
					continue;
				}
				i = ++i % 4;
				x = 0;
				y = 120;
				horizScroll = 1;
			}
		}
		System.exit(0);
	}

//	static public void main(String s[]) throws Exception {
//		
//	}
}