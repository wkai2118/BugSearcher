package com.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class GrammarPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JPanel webBrowserPanel;

	private JWebBrowser webBrowser;

	public GrammarPanel(String url)
	{
		super(new BorderLayout());
		webBrowserPanel = new JPanel(new BorderLayout());
		webBrowser = new JWebBrowser();
		webBrowser.navigate(url);
		webBrowser.setButtonBarVisible(false);
		webBrowser.setMenuBarVisible(false);
		webBrowser.setBarsVisible(false);
		webBrowser.setStatusBarVisible(false);
		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
		add(webBrowserPanel, BorderLayout.CENTER);
	}

	public static void openForm(String url, String title)
	{
		NativeInterface.open();
		JFrame frame = new JFrame();
		frame.setTitle(title);
		// ���ô���رյ�ʱ�򲻹ر�Ӧ�ó���
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new GrammarPanel(url), BorderLayout.CENTER);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setLocationByPlatform(true);
		// �ô���ɼ�
		frame.setVisible(true);
		// ���ô����С
		frame.setResizable(true);
		// ���ô���Ŀ�ȡ��߶�
		frame.setSize(600, 600);
		// ���ô��������ʾ
		frame.setLocationRelativeTo(frame.getOwner());
	}

}