package com.tool;

import com.gui.MainWindow;

public class TabManager
{
	public static void closeCurrentTab()
	{
		if (MainWindow.tabbedPane.getSelectedIndex() != -1)
		{ // Ĭ���ޱ�ǩʱ����ֵΪ-1
			MainWindow.tabbedPane.remove(MainWindow.tabbedPane.getSelectedIndex());
		}
	}
}
