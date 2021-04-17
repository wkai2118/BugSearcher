package com.tool;

import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

import javax.swing.text.TextAction;

import org.apache.commons.lang3.StringEscapeUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

@SuppressWarnings("deprecation")
public class DecodingManger extends TextAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String OptionName;
	@SuppressWarnings("unused")
	private RSyntaxTextArea textArea;

	public DecodingManger(String category, RSyntaxTextArea textArea)
	{
		super(category);
		OptionName = category;
		this.textArea = textArea;
	}

	public static String DecodingURL(String content)
	{
		if (content != null)
		{
			try
			{

				return URLDecoder.decode(content, "UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();

			}
		}
		return null;
	}

	public static String DecodingHTML(String content)
	{

		return StringEscapeUtils.unescapeHtml4(content);
	}

	public static String DecodingBase64(String content)
	{
		final Base64.Decoder decoder = Base64.getDecoder();
		String decodeContent = null;
		if (content != null)
		{
			try
			{
				decodeContent = new String(decoder.decode(content), "UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return decodeContent;

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO 自动生成的方法存根
		String SelectContent = textArea.getSelectedText();
		switch (OptionName)
			{
			case "URL-decode":
			{
				textArea.replaceSelection(DecodingURL(SelectContent));
				break;
			}
			case "HTML-decode":
			{
				textArea.replaceSelection(DecodingHTML(SelectContent));
				break;
			}
			case "Base64-decode":
			{
				textArea.replaceSelection(DecodingBase64(SelectContent));
				break;
			}
			}
	}
}
