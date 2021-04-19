package com.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import com.tool.PHPiniItemManager;
import javax.swing.JLabel;

public class PHPiniSearchPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	public static String[] columnNames = { "配置项", "配置状态", "说明" }; // 输出结果的列名
	public static String[][] rowData; // 审计结果暂存
	public static Properties PHPini = new Properties();
	public static String PHPIniPath = MainWindow.InitConfig.getProperty("phpini");

	/**
	 * Create the panel.
	 */
	public PHPiniSearchPanel()
	{
		setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("php.ini\u5371\u9669\u9879\u68C0\u6D4B");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		toolBar.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		model = new DefaultTableModel(rowData, columnNames);
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(model);

		table.setRowHeight(30);
		table.setFont(new Font("Menu.font", Font.PLAIN, 14));

		StartPHPiniCheck();
	}

	public void StartPHPiniCheck()
	{
		String[][] RuleDate = PHPiniItemManager.ruleReadFromFile();
		try
		{
			PHPini.load(new FileInputStream(PHPIniPath));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		for (String[] item : RuleDate)
		{
			if (PHPini.getProperty(item[0]) != null)
			{
				String[] rowDate = { item[0], PHPini.getProperty(item[0]), item[1] };
				model.addRow(rowDate);
			} else
			{
				String[] rowDate = { item[0], "该版本的PHP中可能已经弃用该项目", item[1] };
				model.addRow(rowDate);
			}

		}
	}

}
