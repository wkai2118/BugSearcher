package com.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class TabPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TabPanel()
	{
		setLayout(new BorderLayout(0, 0));
		MainWindow.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(MainWindow.tabbedPane, BorderLayout.CENTER);
		/* MainWindow.tabbedPane 可以在此处添加初始面板的内容 */
	}
}
