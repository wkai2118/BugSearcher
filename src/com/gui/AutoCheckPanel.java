package com.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
	public static String[] columnNames = { "©������", "�ļ�λ��", "©����ϸ" }; // ������������
	public static JTable ResultTable; // ���������
	public static DefaultTableModel ResultModel; // ������ģ��
	public static JProgressBar progressBar;
	public static JButton btnNewButton;

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

		AutoCheckPanel.ResultTable.setFont(new Font("Menu.font", Font.PLAIN, 14));

//		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(AutoCheckPanel.ResultModel);

		AutoCheckPanel.ResultTable.setRowSorter(null); // ��������

//		AutoCheckPanel.ResultTable.setEnabled(false);

		scrollPane.setViewportView(AutoCheckPanel.ResultTable);

		JToolBar CtrlToolBar = new JToolBar();
		CtrlToolBar.setLayout(new FlowLayout(FlowLayout.CENTER));

		CtrlToolBar.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		CtrlToolBar.setFloatable(false);
		add(CtrlToolBar, BorderLayout.SOUTH);

		btnNewButton = new JButton("��ʼ���");
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!AutoCheckManager.myThreadState)
				{
					AutoCheckManager.runAutoCheck();

				} else
				{

					JOptionPane.showMessageDialog(MainWindow.frame, "��������У�����ֹͣ", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("΢���ź�", Font.BOLD, 13));
		CtrlToolBar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u505C\u6B62\u5BA1\u8BA1");
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				AutoCheckManager.stopAutoCheck();
			}
		});
		btnNewButton_1.setFont(new Font("΢���ź�", Font.BOLD, 13));
		CtrlToolBar.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("\u8F93\u51FA\u8868\u683C");
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				AutoCheckManager.reportForHtml();
			}
		});
		btnNewButton_2.setFont(new Font("΢���ź�", Font.BOLD, 13));
		CtrlToolBar.add(btnNewButton_2);

		CtrlToolBar.addSeparator(new Dimension(5, 0));

		JLabel lblNewLabel = new JLabel("\u8FDB\u5EA6\uFF1A");
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		CtrlToolBar.add(lblNewLabel);

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(680, 13));

		CtrlToolBar.add(progressBar);

		JToolBar FilterToolBar = new JToolBar();
		FilterToolBar.setLayout(new FlowLayout(FlowLayout.CENTER));

		FilterToolBar.setFloatable(false);

		JLabel FilterTypeLabel = new JLabel("����ɸѡ��");
		FilterTypeLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		FilterToolBar.add(FilterTypeLabel);

		JComboBox<String> TypeComboBox = new JComboBox<String>();
		TypeComboBox.setPreferredSize(new Dimension(380, 20));
		FilterToolBar.add(TypeComboBox);

		FilterToolBar.addSeparator(new Dimension(10, 0));

		JLabel FilterFlieLabel = new JLabel("�ļ�ɸѡ��");
		FilterFlieLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		FilterToolBar.add(FilterFlieLabel);

		JComboBox<String> FileComBox = new JComboBox<String>();
		FileComBox.setPreferredSize(new Dimension(380, 20));
		FilterToolBar.add(FileComBox);

		add(FilterToolBar, BorderLayout.NORTH);

	}
}
