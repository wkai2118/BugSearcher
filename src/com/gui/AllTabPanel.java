package com.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.tool.TabManager;

public class AllTabPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public AllTabPanel()
	{
		setLayout(new BorderLayout(0, 0));
		MainWindow.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		MainWindow.tabbedPane.setFocusable(false);
		add(MainWindow.tabbedPane, BorderLayout.CENTER);
		MainWindow.tabbedPane.setFont(new Font("Menu.font", Font.PLAIN, 15));
		MainWindow.tabbedPane.addMouseListener(new java.awt.event.MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					for (int i = 0; i < MainWindow.tabbedPane.getTabCount(); i++)
					{
						Rectangle index = MainWindow.tabbedPane.getBoundsAt(i); // 循环拿到每个标签的边界
						if (index.contains(e.getX(), e.getY()))
						{
							TabManager.closeSelectTab(i);
						}
					}
				}
			}
		});
	}
}
