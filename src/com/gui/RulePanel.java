package com.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.gui.MainWindow;
import com.tool.RuleManager;

public class RulePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */

	public JScrollPane scrollPane;
	public JLabel Rule = new JLabel("规则：");
	public JLabel Details = new JLabel("详情：");
	public static JTextField RuleEdit = new JTextField();
	public static JTextField DetailsEdit = new JTextField();
	private JButton AddButton = new JButton("添加");
	private JButton UpdButton = new JButton("更新");
	private JButton DelButton = new JButton("删除");

	public RulePanel()
	{
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		splitPane.setDividerLocation(520);
		splitPane.setContinuousLayout(true); // 设置是否重绘制分割条
		splitPane.setDividerSize(5); // 设置分割条的像素大小

		add(splitPane, BorderLayout.CENTER);

		JPanel RuleTablePanel = new JPanel();
		splitPane.setLeftComponent(RuleTablePanel);
		RuleTablePanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(MainWindow.RuleTable);
		RuleTablePanel.add(scrollPane);

		JPanel RuleEditPanel = new JPanel();
		RuleEditPanel.setLayout(new BorderLayout(0, 0));

		JPanel RTextField = new JPanel();
		RTextField.setLayout(null);
		Rule.setBounds(50, 30, 50, 20);

		RTextField.add(Rule);
		Details.setBounds(50, 80, 50, 20);
		RTextField.add(Details);
		RuleEdit.setBounds(110, 20, 810, 30);
		RuleEdit.setFont(new Font("Menu.font", Font.PLAIN, 15));

		RTextField.add(RuleEdit);
		DetailsEdit.setBounds(110, 70, 810, 30);
		DetailsEdit.setFont(new Font("Menu.font", Font.PLAIN, 15));

		RTextField.add(DetailsEdit);

		RuleEditPanel.add(RTextField, BorderLayout.CENTER);

		splitPane.setRightComponent(RuleEditPanel);

		JPanel RButton = new JPanel(new FlowLayout());
		AddButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String[] rowDate = { RulePanel.RuleEdit.getText(), RulePanel.DetailsEdit.getText() };
				MainWindow.RuleModel.addRow(rowDate);
				RuleManager.ruleWriteForFile();
			}
		});
		RButton.add(AddButton);
		UpdButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selectRow = MainWindow.RuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					MainWindow.RuleModel.setValueAt(RulePanel.RuleEdit.getText(), selectRow, 0);
					MainWindow.RuleModel.setValueAt(RulePanel.DetailsEdit.getText(), selectRow, 1);
				}
				RuleManager.ruleWriteForFile();
			}
		});
		RButton.add(UpdButton);
		DelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selectRow = MainWindow.RuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					MainWindow.RuleModel.removeRow(selectRow);
				}
				RuleManager.ruleWriteForFile();
			}
		});
		RButton.add(DelButton);

		RuleEditPanel.add(RButton, BorderLayout.SOUTH);
	}

}
