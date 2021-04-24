package com.tool;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.text.TextAction;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.gui.GrammarWindow;

public class GrammarSearcher extends TextAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RSyntaxTextArea textArea;

	public GrammarSearcher(RSyntaxTextArea textArea)
	{
		super("划词审计");
		this.textArea = textArea;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

		String SelectContent = textArea.getSelectedText();
		switch (System.getProperty("os.name"))
			{
			case "Windows 10":
				GrammarWindow.openForm("https://www.php.net/" + SelectContent, SelectContent);
				break;
			case "Windows 7":
				GrammarWindow.openForm("https://www.php.net/" + SelectContent, SelectContent);
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