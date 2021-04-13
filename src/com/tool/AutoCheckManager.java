package com.tool;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.StringEscapeUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import com.gui.AutoCheckPanel;
import com.gui.CodeEditor;
import com.gui.MainWindow;
import com.tool.FileTreeManager.DefaultMutableTreeNodes;

@SuppressWarnings("deprecation")
public class AutoCheckManager
{
	public static startAutoCheck myThread = null;
	public static boolean myThreadState = false;
	public static JProgressBar progressBar;
	public static ArrayList<String> bugResult;
	public static DefaultMutableTreeNodes node;
	static Matcher m;
	static TableListener tableListener = new TableListener();

	public static void runAutoCheck()
	{
		AutoCheckPanel.ResultModel.setRowCount(0);
		AutoCheckPanel.ResultTable.removeMouseListener(tableListener); // 防止重复注册事件
		if (MainWindow.ParentNode != null) // 说明已经新建了项目
		{
			myThread = new startAutoCheck();
			AutoCheckManager.myThreadState = true;
			AutoCheckPanel.progressBar.setMinimum(0);
			AutoCheckPanel.progressBar.setMaximum(FileTreeManager.FileCount);
			myThread.start();
		} else
		{
			JOptionPane.showMessageDialog(MainWindow.frame, "请先新建项目", "Error", JOptionPane.ERROR_MESSAGE);
		}
		AutoCheckPanel.ResultTable.addMouseListener(tableListener);
	}

	public static void stopAutoCheck()
	{
		if (myThreadState == true)
		{
			myThread.stop();
			myThreadState = false;
		}
	}

	public static void RegexMatch(String path)
	{
		bugResult = new ArrayList<String>(); // 首先创建个列表
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(path);
			String line = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8")); // 从文件流中获取数据流
			String[][] RuleDate = RuleManager.ruleReadFromFile();
			try
			{
				while ((line = br.readLine()) != null) // 一行一行读取
				{
					for (int i = 0; i < RuleDate.length; i++)
					{
						m = RuleManager.CompileRules[i].matcher(line);
						if (m.find())
						{
							if (!bugResult.contains(RuleDate[i][1] + path + line)) // 为了避免重复
							{
								String[] rowDate = { RuleDate[i][1], path, line };
								AutoCheckPanel.ResultModel.addRow(rowDate);
								bugResult.add(RuleDate[i][1] + path + line);
							}
						}
					}
				}

			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					br.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		} finally
		{
			try
			{
				fis.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void reportForHtml()
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("html文件(*.html)", "html");
		chooser.setFileFilter(filter);
		int option = chooser.showSaveDialog(null);
		if (option == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			String fname = chooser.getName(file); // 从文件名输入框中获取文件名

			// 假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
			if (fname.indexOf(".html") == -1)
			{
				file = new File(chooser.getCurrentDirectory(), fname + ".html");
			}
			try
			{
				FileOutputStream outputStream = new FileOutputStream(file);
				OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, "GBK");
				BufferedWriter writer = new BufferedWriter(outputWriter);
				writer.write(returnReport());
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static String returnReport()
	{
		FileInputStream f = null;
		try
		{
			f = new FileInputStream("src/com/config/report.html");
		} catch (FileNotFoundException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new InputStreamReader(f, "GBK"));
		} catch (UnsupportedEncodingException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		String line = "";
		String lines = "";
		String result = "";
		try
		{
			while ((line = br.readLine()) != null)
			{
				lines = lines + line + "\r\n";
			}

		} catch (IOException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		for (int i = 0; i < AutoCheckPanel.ResultTable.getRowCount(); i++)
		{
			result = result + "<tr><td width=\"20%\">" + (String) AutoCheckPanel.ResultTable.getValueAt(i, 0)
					+ "</td><td width=\"30%\">" + (String) AutoCheckPanel.ResultTable.getValueAt(i, 1)
					+ "</td><td width=\"45%\">"
					+ StringEscapeUtils.escapeHtml4((String) AutoCheckPanel.ResultTable.getValueAt(i, 2))
					+ "</td></tr>\r\n";
		}
		lines = lines.replace("replace_content", result);
//		System.out.println(lines);
		return lines;
	}

	public static void AutoCheckInit()
	{
		MainWindow.tabbedPane.add("自动审计", new AutoCheckPanel());
		MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
	}

}

class startAutoCheck extends Thread
{
	@Override
	public void run()
	{
		Enumeration<?> enumeration = MainWindow.ParentNode.preorderEnumeration();

		int i = 0;

		while (enumeration.hasMoreElements())
		{
			AutoCheckManager.node = (DefaultMutableTreeNodes) enumeration.nextElement();
			String temp = null; // 这必须在里边啊，中转值不在里面不好判断
			if (AutoCheckManager.node.getValue() != null)
			{
				temp = AutoCheckManager.node.getValue()
						.split("\\.")[AutoCheckManager.node.getValue().split("\\.").length - 1];
				i++;
			}

			if (temp != null && temp.equals("php")) // 注意要非空才能判断
			{
				AutoCheckManager.RegexMatch(AutoCheckManager.node.getValue());
			}
			AutoCheckPanel.progressBar.setValue(i);
		}
		AutoCheckManager.myThreadState = false;
	}

}

class TableListener implements MouseListener
{

	@Override
	public void mouseClicked(MouseEvent e)
	{
		int selectRow = AutoCheckPanel.ResultTable.getSelectedRow();
		if (selectRow != -1)
		{
			MainWindow.textArea = new RSyntaxTextArea();
			MainWindow.textArea.setCodeFoldingEnabled(true);
			String path = (String) AutoCheckPanel.ResultModel.getValueAt(selectRow, 1);
			MainWindow.textArea.setSyntaxEditingStyle("text/" + path.split("\\.")[path.split("\\.").length - 1]);
			MainWindow.textArea.setFont(MainWindow.textArea.getFont().deriveFont((float) 15));
			JPopupMenu popup = MainWindow.textArea.getPopupMenu();
			popup.addSeparator();
			popup.add(new JMenuItem(new GrammarSearcher()));

			FileInputStream f = null;
			try
			{
				f = new FileInputStream(path); // 无奈，想设置编码格式必须用InputStreamReader
			} catch (FileNotFoundException e2)
			{
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			} // 实例化为file类
			Reader is = null; // 创建个Reader类
			try
			{
				is = new BufferedReader(new InputStreamReader(f, "utf-8")); // 从f中实例化Reader类
			} catch (UnsupportedEncodingException e1)
			{
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			try
			{
				MainWindow.textArea.read(is, "d"); // textArea直接读取Reader类
			} catch (IOException e1)
			{
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			JPanel codeEditPane = new CodeEditor();

			MainWindow.tabbedPane.add(new File(path).getName(), codeEditPane);
			MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);

			SearchContext context = new SearchContext();
			context.setSearchFor((String) AutoCheckPanel.ResultModel.getValueAt(selectRow, 2));
			context.setSearchForward(true);
			@SuppressWarnings("unused")
			boolean found = SearchEngine.find(MainWindow.textArea, context).wasFound();
			CodeEditor.textField.setText((String) AutoCheckPanel.ResultModel.getValueAt(selectRow, 2));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO 自动生成的方法存根

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO 自动生成的方法存根

	}

}
