package com.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.tool.AutoCheckManager;
import java.awt.SystemColor;

public class AutoCheckPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTable table;
	static JPanel ResultPanel;
	public static String[][] rowData; // 审计结果暂存
	static JScrollPane scrollPane;
	public static String[] columnNames = { "漏洞描述", "文件位置", "漏洞详细" }; // 输出结果的列名
	public static JTable ResultTable; // 输出结果表格
	public static DefaultTableModel ResultModel; // 输出表格模型
	public static JProgressBar progressBar;
	public static JButton btnNewButton;
	public static JComboBox<String> TypeComboBox;
	public static JComboBox<String> FileComboBox;
	public static TableRowSorter<TableModel> sorter;

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

		// 设置每一行的值
		AutoCheckPanel.ResultModel = new DefaultTableModel(rowData, columnNames);

		AutoCheckPanel.ResultTable = new JTable(AutoCheckPanel.ResultModel);

		AutoCheckPanel.ResultTable.setRowHeight(30);

		AutoCheckPanel.ResultTable.setFont(new Font("Menu.font", Font.PLAIN, 14));

//		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(AutoCheckPanel.ResultModel);

		AutoCheckPanel.ResultTable.setRowSorter(null); // 取消排序

//		AutoCheckPanel.ResultTable.setEnabled(false);

		scrollPane.setViewportView(AutoCheckPanel.ResultTable);

		JToolBar CtrlToolBar = new JToolBar();
		CtrlToolBar.setBackground(SystemColor.menu);
		CtrlToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));

		CtrlToolBar.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		CtrlToolBar.setFloatable(false);
		add(CtrlToolBar, BorderLayout.SOUTH);

		btnNewButton = new JButton("开始审计");
		btnNewButton.setBackground(SystemColor.menu);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBorder(null);
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!AutoCheckManager.myThreadState)
				{
					AutoCheckManager.runAutoCheck();
				} else
				{

					JOptionPane.showMessageDialog(MainWindow.frame, "正在审计中，请先停止", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		CtrlToolBar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u505C\u6B62\u5BA1\u8BA1");
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBorder(null);
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				AutoCheckManager.stopAutoCheck();
			}
		});
		btnNewButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		CtrlToolBar.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("\u8F93\u51FA\u8868\u683C");
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.setBorder(null);
		btnNewButton_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				AutoCheckManager.reportForHtml();
			}
		});
		btnNewButton_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		CtrlToolBar.add(btnNewButton_2);

		CtrlToolBar.addSeparator(new Dimension(5, 0));

		JLabel lblNewLabel = new JLabel("\u8FDB\u5EA6\uFF1A");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		CtrlToolBar.add(lblNewLabel);

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(500, 13));

		CtrlToolBar.add(progressBar);

		JToolBar FilterToolBar = new JToolBar();
		FilterToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));

		add(FilterToolBar, BorderLayout.NORTH);

		FilterToolBar.setFloatable(false);

		JLabel FilterTypeLabel = new JLabel("类型筛选：");
		FilterTypeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		FilterToolBar.add(FilterTypeLabel);

		String[] Items = { "全部" };

		TypeComboBox = new JComboBox<String>();
		TypeComboBox.setPreferredSize(new Dimension(580, 23));
		TypeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		FilterToolBar.add(TypeComboBox);

		ComboBoxModel<String> TyprCm = new DefaultComboBoxModel<>(Items);
		TypeComboBox.setModel(TyprCm);
		TyprCm.addListDataListener(new ListDataListener()
		{

			@Override
			public void intervalRemoved(ListDataEvent e)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void intervalAdded(ListDataEvent e)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void contentsChanged(ListDataEvent e)
			{
				if (TypeComboBox.getSelectedItem() == "全部")
				{
					AutoCheckPanel.sorter.setRowFilter(null);
				} else
				{
					AutoCheckPanel.sorter
							.setRowFilter(RowFilter.regexFilter(TypeComboBox.getSelectedItem().toString()));
				}
			}
		});
	}
}
