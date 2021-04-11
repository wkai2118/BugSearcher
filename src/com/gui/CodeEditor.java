package com.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

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

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);

		RTextScrollPane sp = new RTextScrollPane(MainWindow.textArea);
		splitPane.setLeftComponent(sp);

		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);

		JPanel panel_1 = new JPanel();
		sl_panel.putConstraint(SpringLayout.NORTH, panel_1, 0, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, panel_1, 94, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, panel_1, 912, SpringLayout.WEST, panel);
		panel.add(panel_1);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);

		JLabel lblNewLabel = new JLabel("\u5173\u952E\u5B57\u67E5\u627E\uFF1A");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, panel_1);
		panel_1.add(lblNewLabel);

		textField = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_panel_1.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, lblNewLabel);
		sl_panel_1.putConstraint(SpringLayout.EAST, textField, -10, SpringLayout.EAST, panel_1);
		panel_1.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\u67E5\u627E");
		sl_panel_1.putConstraint(SpringLayout.NORTH, btnNewButton, 9, SpringLayout.SOUTH, textField);
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SearchContext context = new SearchContext();
				context.setSearchFor(CodeEditor.textField.getText());
				context.setSearchForward(true);
				boolean found = SearchEngine.find(MainWindow.textArea, context).wasFound();
				if (!found)
				{
					JOptionPane.showMessageDialog(MainWindow.frame, "已搜索完毕", "提示", JOptionPane.DEFAULT_OPTION);
				}
			}
		});
		sl_panel_1.putConstraint(SpringLayout.WEST, btnNewButton, 10, SpringLayout.WEST, panel_1);
		panel_1.add(btnNewButton);

	}
}
