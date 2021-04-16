package com.tool;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import com.gui.CodeEditPanel;
import com.gui.MainWindow;

public class FuncGuide extends TextAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Matcher f;

	public FuncGuide()
	{
		super("函数定义");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO 自动生成的方法存根
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
		if (MainWindow.FunctionList.size() != 0)
		{
			for (String[] Func : MainWindow.FunctionList.toArray(new String[MainWindow.FunctionList.size()][]))
			{

				if (Func[0].equals(SelectContent))
				{
					CodeEditPanel codeEditPane = new CodeEditPanel(Func[1]);
					SearchContext context = new SearchContext();
					context.setSearchFor("Function " + Func[0]);
					context.setMatchCase(false);
					@SuppressWarnings("unused")
					boolean found = SearchEngine.find(codeEditPane.getTextArea(), context).wasFound();
//					System.out.println(Func[1]);
				}
			}
		} else
		{
			JOptionPane.showMessageDialog(MainWindow.frame, "请先开启自动审计", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void getAllFunc(String path)
	{
		if ((path.split("\\.")[path.split("\\.").length - 1]).equals("php"))
		{
			FileInputStream fis = null;
			try
			{
				fis = new FileInputStream(path);
				String line = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
				while ((line = br.readLine()) != null)
				{
					f = CodeEditPanel.FuncRegx.matcher(line);
					if (f.find())
					{
						String temp = (f.group(0) + "￥" + path).split(" ")[1];
						if (!MainWindow.FunctionList.contains(temp.split("￥")))
						{
							MainWindow.FunctionList.add(temp.split("￥"));
						}
					}
				}
				br.close();
				fis.close();

			} catch (IOException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		}
	}

}
