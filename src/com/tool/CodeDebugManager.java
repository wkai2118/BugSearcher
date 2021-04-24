package com.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.DefaultListModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.gui.CodeEditTab;
import com.gui.MainWindow;

public class CodeDebugManager
{
	public static void savePHPRunFile(RSyntaxTextArea textArea, String path)
	{
		FileOutputStream f = null;
		try
		{
			f = new FileOutputStream(path);
		} catch (FileNotFoundException e2)
		{
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		} // ʵ����Ϊfile��
		BufferedWriter is = null; // ������Reader��
		try
		{
			is = new BufferedWriter(new OutputStreamWriter(f, CodeEditTab.Encode));
		} catch (UnsupportedEncodingException e1)
		{
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		try
		{
			textArea.write(is); // textAreaֱ�Ӷ�ȡReader��
			is.close();
			f.close();

		} catch (IOException e1)
		{
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
	}

	public static void runPHP(DefaultListModel<String> mList)
	{
		Process proc;
		try
		{
			proc = Runtime.getRuntime()
					.exec(MainWindow.PhpExe + " " + new File("src/com/config/runphp.php").getAbsolutePath());
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)
			{
				mList.addElement(line);
			}
			in.close();
			proc.waitFor();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
