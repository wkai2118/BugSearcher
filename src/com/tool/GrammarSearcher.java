package com.tool;

import java.awt.event.ActionEvent;

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
		super("ªÆ¥ …Ûº∆");
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
//		System.out.println(SelectContent);
		GrammarPanel.openForm("https://www.php.net/" + SelectContent, SelectContent);
	}

}