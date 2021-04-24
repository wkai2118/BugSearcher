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

import com.tool.CodeCheckRuleManager;

public class RegexRuleTab extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */

	public JScrollPane scrollPane;
	public JLabel RuleLabel = new JLabel("πÊ‘Ú£∫");
	public JLabel DetailsLabel = new JLabel("œÍ«È£∫");
	public JTextField RuleEdit = new JTextField();
	public JTextField DetailsEdit = new JTextField();
	private JButton AddButton = new JButton("ÃÌº”");
	private JButton UpdButton = new JButton("∏¸–¬");
	private JButton DelButton = new JButton("…æ≥˝");

	public RegexRuleTab()
	{
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		splitPane.setDividerLocation(520);
		splitPane.setContinuousLayout(true); // …Ë÷√ «∑Ò÷ÿªÊ÷∆∑÷∏ÓÃı
		splitPane.setDividerSize(5); // …Ë÷√∑÷∏ÓÃıµƒœÒÀÿ¥Û–°

		splitPane.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				splitPane.setDividerLocation(0.76);
			}
		});

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

		RuleLabel.setBounds(50, 30, 50, 20);
		RuleLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		RTextField.add(RuleLabel);

		DetailsLabel.setBounds(50, 80, 50, 20);
		DetailsLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		RTextField.add(DetailsLabel);

		RuleEdit.setBounds(110, 20, 810, 30);
		RuleEdit.setFont(new Font("Menu.font", Font.PLAIN, 15));
		RTextField.add(RuleEdit);

		DetailsEdit.setBounds(110, 70, 810, 30);
		DetailsEdit.setFont(new Font("Menu.font", Font.PLAIN, 15));
		RTextField.add(DetailsEdit);

		RuleEditPanel.add(RTextField, BorderLayout.CENTER);

		splitPane.setRightComponent(RuleEditPanel);

		JPanel RButton = new JPanel(new FlowLayout());
		AddButton.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		AddButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String[] rowDate = { RuleEdit.getText(), DetailsEdit.getText() };
				MainWindow.RuleModel.addRow(rowDate);
				CodeCheckRuleManager.ruleWriteForFile();
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
				int selectRow = MainWindow.RuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					MainWindow.RuleModel.setValueAt(RuleEdit.getText(), selectRow, 0);
					MainWindow.RuleModel.setValueAt(DetailsEdit.getText(), selectRow, 1);
				}
				CodeCheckRuleManager.ruleWriteForFile();
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
				int selectRow = MainWindow.RuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					MainWindow.RuleModel.removeRow(selectRow);
				}
				CodeCheckRuleManager.ruleWriteForFile();
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

	public JTextField getDetailsEdit()
	{
		return DetailsEdit;
	}
}
