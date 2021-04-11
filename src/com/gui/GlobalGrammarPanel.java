package com.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tool.GlobalGrammarSearcher;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class GlobalGrammarPanel extends JFrame
{

	/**
	 * 
	 */
	private JPanel webBrowserPanel;

	public static GlobalGrammarPanel GrammarSearch = null;

	public static JWebBrowser webBrowser;

	private static final long serialVersionUID = 1L;

	public static boolean SearchisExit = true;

	public GlobalGrammarPanel(String url)
	{
		NativeInterface.open();
		webBrowserPanel = new JPanel(new BorderLayout());
		webBrowser = new JWebBrowser();
		webBrowser.navigate(url);
		webBrowser.setButtonBarVisible(false);
		webBrowser.setMenuBarVisible(false);
		webBrowser.setBarsVisible(false);
		webBrowser.setStatusBarVisible(false);
		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
		add(webBrowserPanel, BorderLayout.CENTER);

		setAlwaysOnTop(true);
		setUndecorated(true);
		setType(JFrame.Type.UTILITY);
		addWindowFocusListener(new WindowFocusListener()
		{

			@Override
			public void windowLostFocus(WindowEvent e)
			{
				// TODO 自动生成的方法存根
				GlobalGrammarSearcher.GramSearchOpen();
				GlobalGrammarPanel.GrammarSearch.dispose();
			}

			@Override
			public void windowGainedFocus(WindowEvent e)
			{
				// TODO 自动生成的方法存根
				GlobalGrammarSearcher.GramSearchClose();
				FloatingIcon.Icon.setVisible(false);
			}
		});

	}
}
