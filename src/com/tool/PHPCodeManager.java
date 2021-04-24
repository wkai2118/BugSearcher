package com.tool;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import com.gui.CodeEditTab;
import com.gui.MainWindow;
import com.tool.ItemManager.DefaultMutableTreeNodes;

public class PHPCodeManager extends TextAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Matcher f;

	public PHPCodeManager()
	{
		super("��������");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO �Զ����ɵķ������
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
					CodeEditTab codeEditPane = new CodeEditTab(Func[1]);
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
			JOptionPane.showMessageDialog(MainWindow.frame, "���ȿ����Զ����", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void openPHPFile(RSyntaxTextArea textArea, String path)
	{
		FileInputStream f = null;
		try
		{
			f = new FileInputStream(path);
		} catch (FileNotFoundException e2)
		{
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		} // ʵ����Ϊfile��
		Reader is = null; // ������Reader��
		try
		{
			is = new BufferedReader(new InputStreamReader(f, CodeEditTab.Encode));
		} catch (UnsupportedEncodingException e1)
		{
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		try
		{
			textArea.read(is, "d"); // textAreaֱ�Ӷ�ȡReader��
			is.close();
			f.close();

		} catch (IOException e1)
		{
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
	}

	public static void getFuncAndPath(String path)	//��ȡ������·��
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
					f = CodeEditTab.FuncRegx.matcher(line);
					if (f.find())
					{
						String temp = (f.group(0) + "��" + path).split(" ")[1];
						if (!MainWindow.FunctionList.contains(temp.split("��")))
						{
							MainWindow.FunctionList.add(temp.split("��"));
						}
					}
				}
				br.close();
				fis.close();

			} catch (IOException e)
			{
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}

		}
	}

	public static void getFuncAndVars(String path)	//��ȡ���к�������ͱ���
	{
		ArrayList<String> ArrayListFunc = new ArrayList<String>();
		ArrayList<String> ArrayListVars = new ArrayList<String>();

		FileInputStream fis = null;
		@SuppressWarnings("unused")
		String line = null;
		try
		{
			fis = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			while ((line = br.readLine()) != null)
			{
				Matcher f = CodeEditTab.FuncRegx.matcher(line);
				Matcher v = CodeEditTab.VarsRegx.matcher(line);
				if (f.find())
				{
					ArrayListFunc.add(f.group(0));
				}
				if (v.find() && (!ArrayListVars.contains(v.group(0))))
				{
					ArrayListVars.add(v.group(0));
				}

			}
			br.close();
			fis.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		CodeEditTab.ArrayFunc = (String[]) ArrayListFunc.toArray(new String[ArrayListFunc.size()]);
		CodeEditTab.ArrayVars = (String[]) ArrayListVars.toArray(new String[ArrayListFunc.size()]);
	}

	public static void getCodeFromTreeNode(MouseEvent e)
	{
		JTree TreeSelection = (JTree) e.getSource(); // ��e�л�ȡʵ������
		if ((((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value) != null) // �����������ļ��У���Ϊ�ļ��в���洢path������node_valueΪ��
		{
			String path = ((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value; // �ӽڵ��л�ȡ�洢��path
			@SuppressWarnings("unused")
			CodeEditTab codeEditPane = new CodeEditTab(path);
		}
	}

}
