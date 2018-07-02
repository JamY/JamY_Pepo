package com.test;

import java.awt.Frame;

import javax.swing.JOptionPane;

public class MyDialog extends Frame {
	public static void main(String args[]){
		MyDialog dialog = new MyDialog();
//		dialog.showInputDialog("abc");
//		dialog.showMessageDialog("abc", "年后");
//		dialog.showOptionDialog();
		String[] possibleValues = { "First", "Second", "Third" };
		dialog.possibleValues(possibleValues);
	}
	public String showInputDialog(String value) {
		// 显示一个要求用户键入 String 的对话框：
		return JOptionPane.showInputDialog(value);
	}

	public void showMessageDialog(String title, String message) {
		// 显示一个错误对话框，该对话框显示的 message 为 'alert'：
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public boolean showOptionDialog() {
		// 示一个警告对话框，其 options 为 OK、CANCEL，title 为 'Warning'，message 为 'Click OK
		// to continue'：
		String[] options = { "OK", "CANCEL" };
		int result = JOptionPane.showOptionDialog(null, "Click OK to continue",
				"Warning", JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (result == 0)
			return true;
		else
			return false;
	}

	public String possibleValues(String[] a) {
		// 显示一个要求用户选择 String 的对话框：
		// String[] possibleValues = { "First", "Second", "Third" };
		String selectedValue = (String) JOptionPane.showInputDialog(null,
				"Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null,
				a, a[0]);
		return selectedValue;
	}
}
