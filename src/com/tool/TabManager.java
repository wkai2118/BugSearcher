package com.tool;

import com.gui.MainWindow;

public class TabManager
{
	public static void closeSelectTab(int index)
	{
		if (MainWindow.tabbedPane.getSelectedIndex() != 0)
		{ // Ĭ���ޱ�ǩʱ����ֵΪ-1
			MainWindow.tabbedPane.remove(index);
		} else
		{
			closeOtherTab();
		}
	}

	public static void closeAllTab()
	{
		MainWindow.tabbedPane.removeAll();
		AutoCheckManager.AutoCheckInit();
	}

	public static void closeOtherTab()
	{
		for (int i = MainWindow.tabbedPane.getTabCount() - 1; i > 0; i--)
		{
			MainWindow.tabbedPane.remove(i);
		}
	}
}
