package com.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.tool.RuleManager;

public class RulePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTextField textField;
	public static JTextField textField_1;
	public JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public RulePanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		scrollPane = new JScrollPane(MainWindow.RuleTable);
		add(scrollPane);

		JPanel ruleTextField = new JPanel();
		ruleTextField.setLayout(new BoxLayout(ruleTextField, BoxLayout.X_AXIS));

		JLabel lblNewLabel = new JLabel("\u89C4\u5219\uFF1A");
		ruleTextField.add(lblNewLabel);

		textField = new JTextField();
		textField.setColumns(10);
		ruleTextField.add(textField);

		add(ruleTextField);

		JPanel ruleDetailsField = new JPanel();
		ruleDetailsField.setLayout(new BoxLayout(ruleDetailsField, BoxLayout.X_AXIS));
		JLabel lblNewLabel_1 = new JLabel("\u8BE6\u60C5\uFF1A");
		ruleDetailsField.add(lblNewLabel_1);

		textField_1 = new JTextField();
		ruleDetailsField.add(textField_1);
		textField_1.setColumns(10);
		add(ruleDetailsField);

		JPanel btnPanel = new JPanel();
		JButton btnNewButton = new JButton("\u6DFB\u52A0");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String[] rowDate =
				{ RulePanel.textField.getText(), RulePanel.textField_1.getText() };
				MainWindow.RuleModel.addRow(rowDate);
				RuleManager.ruleWriteForFile();
			}
		});
		btnPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u66F4\u65B0");
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selectRow = MainWindow.RuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					MainWindow.RuleModel.setValueAt(RulePanel.textField.getText(), selectRow, 0);
					MainWindow.RuleModel.setValueAt(RulePanel.textField_1.getText(), selectRow, 1);
				}
				RuleManager.ruleWriteForFile();
			}
		});
		btnPanel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("\u5220\u9664");
		btnNewButton_2.addActionListener(new ActionListener()
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
		btnPanel.add(btnNewButton_2);
		add(btnPanel);

	}
}
