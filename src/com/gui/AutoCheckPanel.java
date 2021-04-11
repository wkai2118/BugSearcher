package com.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import com.tool.AutoCheckManager;

public class AutoCheckPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTable table;
	static JPanel ResultPanel;
	public static String[][] rowData; // ��ƽ���ݴ�
	static JScrollPane scrollPane;
	public static String[] columnNames =
	{ "©������", "�ļ�λ��", "©����ϸ" }; // ������������
	public static JTable ResultTable; // ���������
	public static DefaultTableModel ResultModel; // ������ģ��
	public static JProgressBar progressBar;

	/**
	 * Create the panel.
	 */

	public AutoCheckPanel()
	{
		setLayout(new BorderLayout(0, 0));

		ResultPanel = new JPanel();
		ResultPanel.setBackground(Color.WHITE);
		add(ResultPanel);
		ResultPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		ResultPanel.add(scrollPane, BorderLayout.CENTER);

		/****************************************************************************************************************************************/

		// ����ÿһ�е�ֵ
		AutoCheckPanel.ResultModel = new DefaultTableModel(rowData, columnNames);

		AutoCheckPanel.ResultTable = new JTable(AutoCheckPanel.ResultModel);

		AutoCheckPanel.ResultTable.setRowHeight(30);

//		AutoCheckPanel.ResultTable.setEnabled(false);

		scrollPane.setViewportView(AutoCheckPanel.ResultTable);

		JToolBar toolBar = new JToolBar();
		toolBar.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);

		JButton btnNewButton = new JButton("\u5F00\u59CB\u5BA1\u8BA1");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!AutoCheckManager.myThreadState)
				{
					AutoCheckManager.runAutoCheck();
				} else
				{
//					JDialog dialog = new SystemTip(MainWindow.frame, "��������У�����ֹͣ");
//					dialog.setVisible(true);
					JOptionPane.showMessageDialog(MainWindow.frame, "��������У�����ֹͣ", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("΢���ź�", Font.BOLD, 13));
		toolBar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u505C\u6B62\u5BA1\u8BA1");
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				AutoCheckManager.stopAutoCheck();
			}
		});
		btnNewButton_1.setFont(new Font("΢���ź�", Font.BOLD, 13));
		toolBar.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("\u8F93\u51FA\u8868\u683C");
		btnNewButton_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				AutoCheckManager.reportForHtml();
			}
		});
		btnNewButton_2.setFont(new Font("΢���ź�", Font.BOLD, 13));
		toolBar.add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("    \u8FDB\u5EA6\uFF1A");
		toolBar.add(lblNewLabel);

		progressBar = new JProgressBar();

		toolBar.add(progressBar);

	}
}
