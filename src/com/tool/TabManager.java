package com.tool;

import com.gui.MainWindow;

public class TabManager
{
	public static void closeCurrentTab(int index)
	{
		if (MainWindow.tabbedPane.getSelectedIndex() != 0)
		{ // Ĭ���ޱ�ǩʱ����ֵΪ-1
			MainWindow.tabbedPane.remove(index);
		}
	}
}
