package com.tool;

import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
	private RSyntaxTextArea textArea;

	public DecodingManger(String category, RSyntaxTextArea textArea)
	{
		super(category);
		OptionName = category;
		this.textArea = textArea;
	}

	public static String decodingURL(String content)
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

	public static String decodingHTML(String content)
	{

		return StringEscapeUtils.unescapeHtml4(content);
	}

	public static String decodingBase64(String content)
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

	public static String encodingURL(String content)
	{
		if (content != null)
		{
			try
			{

				return URLEncoder.encode(content, "UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();

			}
		}
		return null;

	}

	public static String encodingHTML(String content)
	{
		return StringEscapeUtils.escapeHtml4(content);
	}

	public static String encodingBase64(String content)
	{

		final Base64.Encoder encoder = Base64.getEncoder();

		byte[] textByte = null;
		try
		{
			textByte = content.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}

		String encodeContent = null;

		if (content != null)
		{
			try
			{
				encodeContent = new String(encoder.encode(textByte), "UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return encodeContent;
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
				textArea.replaceSelection(decodingURL(SelectContent));
				break;
			}
			case "HTML-decode":
			{
				textArea.replaceSelection(decodingHTML(SelectContent));
				break;
			}
			case "Base64-decode":
			{
				textArea.replaceSelection(decodingBase64(SelectContent));
				break;
			}
			}
	}
}
