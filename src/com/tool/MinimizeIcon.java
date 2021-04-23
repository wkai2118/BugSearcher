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
import java.io.FileOutputStream;
import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.gui.CodeEditPanel;
import com.gui.FloatingIcon;
import com.gui.GlobalGrammarPanel;
import com.gui.MainWindow;

public class MinimizeIcon
{
	public static MenuItem ItemShow;
	public static SystemTray systemTray;
	public static TrayIcon BackIcon;

	public static void minisize()
	{
		systemTray = SystemTray.getSystemTray();

		Image image = Toolkit.getDefaultToolkit().getImage("src/com/icon/16.png");
		String BackTips = "BugSearcher";
		PopupMenu menu = new PopupMenu();
		PopupMenu ItemGrammar = new PopupMenu("ȫ�ֻ������");
		ItemShow = new MenuItem("��ʾ");
		MenuItem ItemClose = new MenuItem("�˳�");
		MinimizeIcon.ItemShow.setEnabled(false);

		MenuItem GrammarOpen = new MenuItem("     ��     ");
		MenuItem GrammarClose = new MenuItem("     ��     ");

		menu.add(ItemShow);

		ItemGrammar.add(GrammarOpen);
		GrammarClose.setEnabled(false);
		ItemGrammar.add(GrammarClose);
		menu.add(ItemGrammar);

		menu.add(ItemClose);
		BackIcon = new TrayIcon(image, BackTips, menu);
		try
		{
			systemTray.add(BackIcon);
		} catch (AWTException e)
		{
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

		ItemShow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				MainWindow.frame.setVisible(true);
				ItemShow.setEnabled(false);
			}
		});
		ItemClose.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				exitSystem();
			}
		});

		GrammarOpen.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO �Զ����ɵķ������
				GrammarOpen.setEnabled(false);
				GrammarClose.setEnabled(true);
				GlobalGrammarSearcher.GramSearchOpen();
			}
		});

		GrammarClose.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO �Զ����ɵķ������
				GrammarClose.setEnabled(false);
				GrammarOpen.setEnabled(true);
				FloatingIcon.Icon.setVisible(false);
				GlobalGrammarSearcher.GramSearchClose();
			}
		});

	}

	public static void exitSystem()
	{
		MainWindow.InitConfig.setProperty("editsize", "" + CodeEditPanel.DefaultCodeEditSize);
		MainWindow.InitConfig.setProperty("theme", MainWindow.Theme);
		MainWindow.InitConfig.setProperty("encode", CodeEditPanel.Encode);
		MainWindow.InitConfig.setProperty("phpexe", MainWindow.PhpExe);
		MainWindow.InitConfig.setProperty("phpini", MainWindow.PhpIni);
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream("src/com/config/Init.properties");
			MainWindow.InitConfig.store(fos, "Init.properties");
			fos.close();
		} catch (IOException e2)
		{
			e2.printStackTrace();
		}

		MainWindow.frame.dispose(); // ����������
		FloatingIcon.Icon.dispose(); // ����������
		if (GlobalGrammarPanel.GrammarSearch != null)
			GlobalGrammarPanel.GrammarSearch.dispose(); // ����в�ѯ��û�أ��͹ر���
		systemTray.remove(BackIcon);
		GlobalScreen.removeNativeMouseListener(GlobalGrammarSearcher.Searcher);
		GlobalScreen.removeNativeMouseMotionListener(GlobalGrammarSearcher.Searcher);
		try
		{
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e1)
		{
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		GlobalGrammarSearcher.mTimer.cancel();// ֹͣ���м�ʱ������
		System.exit(0);
	}
}
