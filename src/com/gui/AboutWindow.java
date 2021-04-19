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
		setTitle("关于");
		setSize(300, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		setLocationRelativeTo(null);
		setResizable(false);

		textArea.setText("	关于软件\r\n" + "~首先申明：此软件参考了Seay代码审计工具，审计规则也来自于Seay代码审计工具\r\n"
				+ "~此软件的特色就是对审计内容做了分类，其次添加了划词审计的功能，方便大家学习新的PHP函数\r\n"
				+ "~我做这软件的目的就是为了完成我的毕业设计，如果这个软件流传出去的话，希望大家只是拿去学习使用\r\n" + "~如果有什么做不好的地方，欢迎指正\r\n" + "	\r\n"
				+ "~本软件已经开源：https://e.coding.net/wkai2118/biyesheji/BugSearcher.git\r\n" + "~作者QQ：1633745323\r\n"
				+ "~作者的垃圾博客：https://www.yuque.com/wkai2118/myblog");
	}

}
