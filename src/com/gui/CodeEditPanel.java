package com.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import com.tool.FuncGuide;
import com.tool.GrammarSearcher;

public class CodeEditPanel extends JPanel
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
	public CodeEditPanel(String path)
	{
		setLayout(new BorderLayout(0, 0));
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(610);
		splitPane.setDividerSize(5);
		add(splitPane, BorderLayout.CENTER);

		
		MainWindow.textArea = new RSyntaxTextArea();
//		MainWindow.textArea.setCodeFoldingEnabled(true);
		MainWindow.textArea.setSyntaxEditingStyle("text/" + path.split("\\.")[path.split("\\.").length - 1]);
		MainWindow.textArea.setFont(MainWindow.textArea.getFont().deriveFont((float) 15));

		JPopupMenu popup = MainWindow.textArea.getPopupMenu();
		popup.addSeparator();
		popup.add(new JMenuItem(new GrammarSearcher()));
		popup.add(new JMenuItem(new FuncGuide()));
		FileInputStream f = null;
		try
		{
			f = new FileInputStream(path); // 无奈，想设置编码格式必须用InputStreamReader
		} catch (FileNotFoundException e2)
		{
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} // 实例化为file类
		Reader is = null; // 创建个Reader类
		try
		{
			is = new BufferedReader(new InputStreamReader(f, "utf-8")); // 从f中实例化Reader类
		} catch (UnsupportedEncodingException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try
		{
			MainWindow.textArea.read(is, "d"); // textArea直接读取Reader类
			is.close();
			f.close();

		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		RTextScrollPane sp = new RTextScrollPane(MainWindow.textArea);

		splitPane.setLeftComponent(sp);
		
		MainWindow.tabbedPane.add(new File(path).getName(), this);
		MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);

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

				context.setSearchFor(CodeEditPanel.textField.getText());
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
