package com.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class AboutWindow extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public AboutWindow()
	{
		setTitle("����");
		setSize(300, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		setLocationRelativeTo(null);
		setResizable(false);

		textArea.setText("	�������\r\n" + "~����������������ο���Seay������ƹ��ߣ���ƹ���Ҳ������Seay������ƹ���\r\n"
				+ "~���������ɫ���Ƕ�����������˷��࣬�������˻�����ƵĹ��ܣ�������ѧϰ�µ�PHP����\r\n"
				+ "~�����������Ŀ�ľ���Ϊ������ҵı�ҵ��ƣ����������������ȥ�Ļ���ϣ�����ֻ����ȥѧϰʹ��\r\n" + "~�����ʲô�����õĵط�����ӭָ��\r\n" + "	\r\n"
				+ "~������Ѿ���Դ��https://e.coding.net/wkai2118/biyesheji/BugSearcher.git\r\n" + "~����QQ��1633745323\r\n"
				+ "~���ߵ��������ͣ�https://www.yuque.com/wkai2118/myblog");
	}

}
