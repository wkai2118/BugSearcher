package com.tool;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Clipboard
{
	public static String getClipboardString()
	{
		java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		if (contents != null)
		{
			if (contents.isDataFlavorSupported(DataFlavor.stringFlavor))
			{
				String text = null;
				try
				{
					text = (String) contents.getTransferData(DataFlavor.stringFlavor);
				} catch (UnsupportedFlavorException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				return text;
			}
		}
		return null;
	}

	public static void setClipboardString(String text)
	{
		java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// 封装文本内容
		Transferable contents = new StringSelection(text);
		// 把文本内容设置到系统剪贴板
		clipboard.setContents(contents, null);
	}
}
