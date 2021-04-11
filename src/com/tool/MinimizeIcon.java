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

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.gui.FloatingIcon;
import com.gui.GlobalGrammarPanel;
import com.gui.MainWindow;

public class MinimizeIcon
{
	public static MenuItem ItemShow;

	public static void minisize()
	{
		SystemTray systemTray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("src/com/icon/16.png");
		String BackTips = "BugSearcher";
		PopupMenu menu = new PopupMenu();
		PopupMenu ItemGrammar = new PopupMenu("ȫ�ֻ������");
		ItemShow = new MenuItem("��ʾ");
		MenuItem ItemClose = new MenuItem("�˳�");
		MinimizeIcon.ItemShow.setEnabled(false);

		MenuItem GrammarOpen = new MenuItem("��");
		MenuItem GrammarClose = new MenuItem("��");

		menu.add(ItemShow);

		ItemGrammar.add(GrammarOpen);
		GrammarClose.setEnabled(false);
		ItemGrammar.add(GrammarClose);
		menu.add(ItemGrammar);

		menu.add(ItemClose);
		TrayIcon BackIcon = new TrayIcon(image, BackTips, menu);
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
//				FloatingIcon.Icon.setSize(32, 32);
//				FloatingIcon.Icon.setLocationRelativeTo(null);
//				FloatingIcon.Icon.setVisible(true);

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
}
