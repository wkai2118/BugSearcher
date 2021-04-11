package com.tool;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.gui.MainWindow;

public class Minimize
{
	public static void minisize()
	{
		SystemTray systemTray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("src/com/icon/16.png");
		String BackTips = "Code";
		PopupMenu menu = new PopupMenu();
		MenuItem ItemGrammar = new MenuItem("全局划词审计");
		MenuItem ItemShow = new MenuItem("显示");
		MenuItem ItemClose = new MenuItem("退出");
		menu.add(ItemShow);
		menu.add(ItemGrammar);
		menu.add(ItemClose);
		TrayIcon BackIcon = new TrayIcon(image, BackTips, menu);
		try
		{
			systemTray.add(BackIcon);
		} catch (AWTException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		ItemShow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				MainWindow.frame.setVisible(true);
				systemTray.remove(BackIcon);
			}
		});
		ItemClose.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				MainWindow.frame.dispose();
				systemTray.remove(BackIcon);
			}
		});
	}
}
