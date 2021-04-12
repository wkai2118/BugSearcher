package com.tool;

import com.gui.MainWindow;

public class TabManager
{
	public static void closeCurrentTab()
	{
		if (MainWindow.tabbedPane.getSelectedIndex() != -1)
		{ // 默认无标签时，该值为-1
			MainWindow.tabbedPane.remove(MainWindow.tabbedPane.getSelectedIndex());
		}
	}
}
