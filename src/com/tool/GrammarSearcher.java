package com.tool;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

import com.gui.GrammarPanel;

public class GrammarSearcher extends TextAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrammarSearcher()
	{
		super("划词审计");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JTextComponent tc = getTextComponent(e);
		String SelectContent = null;
		try
		{
			int selStart = tc.getSelectionStart();
			int selEnd = tc.getSelectionEnd();
			if (selStart != selEnd)
			{
				SelectContent = tc.getText(selStart, selEnd - selStart);
			}
		} catch (BadLocationException ble)
		{
			ble.printStackTrace();
			UIManager.getLookAndFeel().provideErrorFeedback(tc);
			return;
		}
		switch (System.getProperty("os.name"))
			{
			case "Windows 10":
				GrammarPanel.openForm("https://www.php.net/" + SelectContent, SelectContent);
				break;
			case "Windows 7":
				GrammarPanel.openForm("https://www.php.net/" + SelectContent, SelectContent);
				break;
			case "Linux":
				Desktop LinuxBrowser = Desktop.getDesktop();
			try
			{
				LinuxBrowser.browse(new URI("https://www.php.net/" + SelectContent));
			} catch (IOException e1)
			{
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			} catch (URISyntaxException e1)
			{
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
				break;
			}

	}

}