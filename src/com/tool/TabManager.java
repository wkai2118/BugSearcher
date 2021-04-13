package com.tool;

import com.gui.MainWindow;

public class TabManager
{
	public static void closeCurrentTab(int index)
	{
		if (MainWindow.tabbedPane.getSelectedIndex() != 0)
		{ // 默认无标签时，该值为-1
			MainWindow.tabbedPane.remove(index);
		}
	}
}
