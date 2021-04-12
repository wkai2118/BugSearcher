package com.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tool.Clipboard;
import com.tool.GlobalGrammarSearcher;

public class FloatingIcon extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static FloatingIcon Icon;

	public FloatingIcon()
	{
		JPanel cp = new JPanel();
		setContentPane(cp);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setType(JFrame.Type.UTILITY); // 隐藏任务栏图标
		setAutoRequestFocus(false);
		setFocusableWindowState(false);
		setBackground(new Color(0, 0, 0, 0)); // 设置背景透明
		ImageIcon img = new ImageIcon("src/com/icon/searchs.png");
		JLabel back = new JLabel(img);
		cp.add(back);
		back.setBounds(0, 0, 38, 38);

		addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
//				System.out.println(System.getProperty("os.name"));
				switch (System.getProperty("os.name"))
					{
					case "Windows 10":
						WindowsEntered();
						break;
					case "Windows 7":
						WindowsEntered();
						break;
					case "Linux":
						LinuxEntered();
						break;
					}
			}

			@Override
			public void mouseClicked(MouseEvent arg0)
			{

			}
		});

	}

	public void WindowsEntered()
	{
		GlobalGrammarSearcher.GramSearchClose();
		GlobalGrammarPanel.SearchisExit = false;
		FloatingIcon.Icon.setVisible(false);
		GlobalGrammarSearcher.robot.keyPress(KeyEvent.VK_CONTROL);
		GlobalGrammarSearcher.robot.keyPress(KeyEvent.VK_C);
		GlobalGrammarSearcher.robot.keyRelease(KeyEvent.VK_CONTROL);
		GlobalGrammarSearcher.robot.keyRelease(KeyEvent.VK_C);
		GlobalGrammarSearcher.robot.delay(220);
		String contents = Clipboard.getClipboardString();
		GlobalGrammarPanel.GrammarSearch = new GlobalGrammarPanel("https://www.php.net/" + contents);
		GlobalGrammarPanel.GrammarSearch.setBounds(GlobalGrammarSearcher.ReleaseX + 38,
				GlobalGrammarSearcher.ReleaseY - 12, 600, 450);
		GlobalGrammarPanel.GrammarSearch.setVisible(true);
	}

	public void LinuxEntered()
	{
		FloatingIcon.Icon.setVisible(false);
		GlobalGrammarSearcher.robot.keyPress(KeyEvent.VK_CONTROL);
		GlobalGrammarSearcher.robot.keyPress(KeyEvent.VK_C);
		GlobalGrammarSearcher.robot.keyRelease(KeyEvent.VK_CONTROL);
		GlobalGrammarSearcher.robot.keyRelease(KeyEvent.VK_C);
		GlobalGrammarSearcher.robot.delay(220);
		String contents = Clipboard.getClipboardString();
		Desktop LinuxBrowser = Desktop.getDesktop();
		try
		{
			LinuxBrowser.browse(new URI("https://www.php.net/" + contents));
		} catch (IOException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (URISyntaxException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
