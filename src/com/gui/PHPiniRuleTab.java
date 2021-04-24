package com.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.tool.PHPiniItemManager;
import javax.swing.JTextArea;

public class PHPiniRuleTab extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */

	public JScrollPane scrollPane;
	public JLabel RuleLabel = new JLabel("\u914D\u7F6E\u9879\u76EE\uFF1A");
	public JLabel DetailsLabel = new JLabel("\u9879\u76EE\u63CF\u8FF0\uFF1A");
	public JTextField RuleEdit = new JTextField();
	public JTextArea DetailsEdit = new JTextArea();
	private JButton AddButton = new JButton("ÃÌº”");
	private JButton UpdButton = new JButton("∏¸–¬");
	private JButton DelButton = new JButton("…æ≥˝");

	public PHPiniRuleTab()
	{
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(true); // …Ë÷√ «∑Ò÷ÿªÊ÷∆∑÷∏ÓÃı
		splitPane.setDividerSize(5); // …Ë÷√∑÷∏ÓÃıµƒœÒÀÿ¥Û–°

		splitPane.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				splitPane.setDividerLocation(0.60);
			}
		});

		add(splitPane, BorderLayout.CENTER);

		JPanel INIRuleTablePanel = new JPanel();
		splitPane.setLeftComponent(INIRuleTablePanel);
		INIRuleTablePanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(MainWindow.INIRuleTable);
		INIRuleTablePanel.add(scrollPane);

		JPanel RuleEditPanel = new JPanel();
		RuleEditPanel.setLayout(new BorderLayout(0, 0));

		JPanel RTextField = new JPanel();
		RTextField.setLayout(null);

		RuleLabel.setBounds(50, 30, 94, 20);
		RuleLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		RTextField.add(RuleLabel);

		DetailsLabel.setBounds(50, 80, 94, 20);
		DetailsLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		RTextField.add(DetailsLabel);

		RuleEdit.setBounds(143, 26, 777, 30);
		RuleEdit.setFont(new Font("Dialog", Font.PLAIN, 16));
		RTextField.add(RuleEdit);

		RuleEditPanel.add(RTextField, BorderLayout.CENTER);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(143, 80, 777, 110);
		RTextField.add(scrollPane_1);
		DetailsEdit.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		DetailsEdit.setLineWrap(true);

		scrollPane_1.setViewportView(DetailsEdit);

		splitPane.setRightComponent(RuleEditPanel);

		JPanel RButton = new JPanel(new FlowLayout());
		AddButton.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		AddButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String[] rowDate = { RuleEdit.getText(), DetailsEdit.getText() };
				MainWindow.INIRuleModel.addRow(rowDate);
				PHPiniItemManager.ruleWriteForFile();
				RuleEdit.setText("");
				DetailsEdit.setText("");
			}
		});
		RButton.add(AddButton);
		UpdButton.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		UpdButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selectRow = MainWindow.INIRuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					MainWindow.INIRuleModel.setValueAt(RuleEdit.getText(), selectRow, 0);
					MainWindow.INIRuleModel.setValueAt(DetailsEdit.getText(), selectRow, 1);
				}
				PHPiniItemManager.ruleWriteForFile();
				RuleEdit.setText("");
				DetailsEdit.setText("");
			}
		});
		RButton.add(UpdButton);
		DelButton.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		DelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selectRow = MainWindow.INIRuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					MainWindow.INIRuleModel.removeRow(selectRow);
				}
				PHPiniItemManager.ruleWriteForFile();
				RuleEdit.setText("");
				DetailsEdit.setText("");
			}
		});
		RButton.add(DelButton);

		RuleEditPanel.add(RButton, BorderLayout.SOUTH);
	}

	public JTextField getRuleEdit()
	{
		return RuleEdit;
	}

	public JTextArea getDetailsEdit()
	{
		return DetailsEdit;
	}
}
