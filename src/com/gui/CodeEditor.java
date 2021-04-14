package com.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

public class CodeEditor extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JSplitPane splitPane;
	public static JTextField textField;

	/**
	 * Create the panel.
	 */
	public CodeEditor()
	{
		setLayout(new BorderLayout(0, 0));
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(580);
		splitPane.setDividerSize(5);
		add(splitPane, BorderLayout.CENTER);

		RTextScrollPane sp = new RTextScrollPane(MainWindow.textArea);

		splitPane.setLeftComponent(sp);

		JPanel SearchPanel = new JPanel();

		SearchPanel.setLayout(null);

		splitPane.setRightComponent(SearchPanel);

		JLabel SearchLabel = new JLabel("关键字查找：");

		SearchLabel.setBounds(30, 10, 100, 30);

		SearchLabel.setFont(new Font("Menu.font", Font.PLAIN, 15));

		SearchPanel.add(SearchLabel);

		textField = new JTextField();

		SearchPanel.add(textField);
		textField.setColumns(10);
		textField.setBounds(120, 10, 700, 30);
		textField.setFont(new Font("Menu.font", Font.PLAIN, 15));

		JButton SearchBtn = new JButton("查找");

		SearchBtn.setBounds(830, 10, 100, 30);

		SearchBtn.setFont(new Font("Menu.font", Font.PLAIN, 15));

		SearchContext context = new SearchContext();

		SearchBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				context.setSearchFor(CodeEditor.textField.getText());
				context.setSearchForward(true);
				boolean found = SearchEngine.find(MainWindow.textArea, context).wasFound();
				if (!found)
				{
					JOptionPane.showMessageDialog(MainWindow.frame, "已搜索完毕", "提示", JOptionPane.DEFAULT_OPTION);
				}
			}
		});

		SearchPanel.add(SearchBtn);

	}
}
