package com.test;

import java.awt.Frame;

import javax.swing.JOptionPane;

public class MyDialog extends Frame {
	public static void main(String args[]){
		MyDialog dialog = new MyDialog();
//		dialog.showInputDialog("abc");
//		dialog.showMessageDialog("abc", "���");
//		dialog.showOptionDialog();
		String[] possibleValues = { "First", "Second", "Third" };
		dialog.possibleValues(possibleValues);
	}
	public String showInputDialog(String value) {
		// ��ʾһ��Ҫ���û����� String �ĶԻ���
		return JOptionPane.showInputDialog(value);
	}

	public void showMessageDialog(String title, String message) {
		// ��ʾһ������Ի��򣬸öԻ�����ʾ�� message Ϊ 'alert'��
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public boolean showOptionDialog() {
		// ʾһ������Ի����� options Ϊ OK��CANCEL��title Ϊ 'Warning'��message Ϊ 'Click OK
		// to continue'��
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
		// ��ʾһ��Ҫ���û�ѡ�� String �ĶԻ���
		// String[] possibleValues = { "First", "Second", "Third" };
		String selectedValue = (String) JOptionPane.showInputDialog(null,
				"Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null,
				a, a[0]);
		return selectedValue;
	}
}
