package com.tool;

import com.gui.MainWindow;

public class TabManager
{
	public static void closeSelectTab(int index)
	{
		if (MainWindow.tabbedPane.getSelectedIndex() != 0)
		{ // 默认无标签时，该值为-1
			MainWindow.tabbedPane.remove(index);
		} else
		{
			closeAllCodeTab();
		}
	}

	public static void closeAllTab() // 包括自动审计面板
	{
		MainWindow.tabbedPane.removeAll();
		AutoCheckManager.AutoCheckInit();
	}

	public static void closeAllCodeTab()
	{
		for (int i = MainWindow.tabbedPane.getTabCount() - 1; i > 0; i--)
		{
			MainWindow.tabbedPane.remove(i);
		}
	}

	public static void closeOtherTab()
	{
		if (MainWindow.tabbedPane.getSelectedIndex() != 0)
		{
			closeAllRightTab();
			closeAllleftTab();
		}
	}

	public static void closeAllRightTab()
	{
		for (int i = MainWindow.tabbedPane.getTabCount() - 1; i > MainWindow.tabbedPane.getSelectedIndex(); i--)
		{
			MainWindow.tabbedPane.remove(i);
		}
	}

	public static void closeAllleftTab()
	{
		for (int i = MainWindow.tabbedPane.getSelectedIndex() - 1; i > 0; i--)
		{
			MainWindow.tabbedPane.remove(i);
		}
	}

}
