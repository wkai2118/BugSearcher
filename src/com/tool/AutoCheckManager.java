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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang3.StringEscapeUtils;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import com.gui.AutoCheckTab;
import com.gui.CodeEditTab;
import com.gui.MainWindow;
import com.tool.ItemManager.DefaultMutableTreeNodes;

@SuppressWarnings("deprecation")
public class AutoCheckManager
{
	public static startAutoCheck myThread = null;
	public static boolean myThreadState = false;
	public static JProgressBar progressBar;
	public static ArrayList<String> bugResult;
	public static DefaultMutableTreeNodes node;
	static Matcher m;

	public static TableListener tableListener = new TableListener();
	static ArrayList<String> bugType;;

	public static void runAutoCheck()
	{
		AutoCheckTab.ResultModel.setRowCount(0);
		AutoCheckTab.ResultTable.setRowSorter(null); // ���¿�ʼ������Ƴ�ԭ�е������趨����ֹɨ���б�����
		AutoCheckTab.ResultTable.removeMouseListener(tableListener); // ��ֹ�ظ�ע���¼�

		if (MainWindow.ParentNode != null) // ˵���Ѿ��½�����Ŀ
		{
			myThread = new startAutoCheck();
			AutoCheckManager.myThreadState = true;
			AutoCheckTab.progressBar.setMinimum(0);
			AutoCheckTab.progressBar.setMaximum(ItemManager.FileCount);
			myThread.start();
		} else
		{
			JOptionPane.showMessageDialog(MainWindow.frame, "�����½���Ŀ", "Error", JOptionPane.ERROR_MESSAGE);
		}
		AutoCheckTab.ResultTable.addMouseListener(tableListener);
	}

	public static void stopAutoCheck()
	{
		if (myThreadState == true)
		{
			myThread.stop();
			myThreadState = false;
			AutoCheckTab.ResultModel.setRowCount(0);
			AutoCheckTab.progressBar.setValue(0);
		}
	}

	public static void RegexMatch(String path)
	{
		bugResult = new ArrayList<String>(); // ���ȴ������б�
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(path);
			String line = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8")); // ���ļ����л�ȡ������
			String[][] RuleDate = CodeCheckRuleManager.ruleReadFromFile();
			try
			{
				while ((line = br.readLine()) != null) // һ��һ�ж�ȡ
				{
					for (int i = 0; i < RuleDate.length; i++) // ����ȡ���з���ÿһ��������
					{
						m = CodeCheckRuleManager.CompileRules[i].matcher(line);
						if (m.find())
						{
							if (!bugResult.contains(RuleDate[i][1] + path + line)) // Ϊ�˱����ظ�
							{
								String[] rowDate = { RuleDate[i][1], path, line };
								AutoCheckTab.ResultModel.addRow(rowDate);
								if (!bugType.contains(RuleDate[i][1]))
								{
									AutoCheckTab.TypeComboBox.addItem(RuleDate[i][1]);
									bugType.add(RuleDate[i][1]);
								}
								bugResult.add(RuleDate[i][1] + path + line);
							}
						}
					}
				}
				br.close();
				fis.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
	}

	public static void reportForHtml()
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("html�ļ�(*.html)", "html");
		chooser.setFileFilter(filter);
		int option = chooser.showSaveDialog(null);
		if (option == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			String fname = chooser.getName(file); // ���ļ���������л�ȡ�ļ���

			// �����û���д���ļ������������ƶ��ĺ�׺������ô���Ǹ������Ϻ�׺
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
				outputWriter.close();
				outputStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static String returnReport()
	{
		FileInputStream f = null;
		String line = "";
		String lines = "";
		String result = "";

		BufferedReader br = null;
		try
		{
			f = new FileInputStream("src/com/config/report.html");
			br = new BufferedReader(new InputStreamReader(f, "GBK"));
			while ((line = br.readLine()) != null)
			{
				lines = lines + line + "\r\n";
			}
			for (int i = 0; i < AutoCheckTab.ResultTable.getRowCount(); i++)
			{
				result = result + "<tr><td width=\"20%\">" + (String) AutoCheckTab.ResultTable.getValueAt(i, 0)
						+ "</td><td width=\"30%\">" + (String) AutoCheckTab.ResultTable.getValueAt(i, 1)
						+ "</td><td width=\"45%\">"
						+ StringEscapeUtils.escapeHtml4((String) AutoCheckTab.ResultTable.getValueAt(i, 2))
						+ "</td></tr>\r\n";
			}
			lines = lines.replace("replace_content", result);
			br.close();
			f.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		return lines;
	}

	public static void AutoCheckInit()
	{
		bugType = new ArrayList<String>();
		MainWindow.autoCheckPanel = new AutoCheckTab();
		MainWindow.tabbedPane.add("�Զ����", MainWindow.autoCheckPanel);
		MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
	}
}

class startAutoCheck extends Thread
{
	@Override
	public void run()
	{
		Enumeration<?> enumeration = MainWindow.ParentNode.preorderEnumeration();

		int fileCount = 0;

		while (enumeration.hasMoreElements())
		{
			AutoCheckManager.node = (DefaultMutableTreeNodes) enumeration.nextElement();
			String temp = null; // ���������߰�����תֵ�������治���ж�
			if (AutoCheckManager.node.getValue() != null)
			{
				temp = AutoCheckManager.node.getValue()
						.split("\\.")[AutoCheckManager.node.getValue().split("\\.").length - 1];
				fileCount++;
			}

			if (temp != null && temp.equals("php")) // ע��Ҫ�ǿղ����ж�
			{
				AutoCheckManager.RegexMatch(AutoCheckManager.node.getValue());
			}
			AutoCheckTab.progressBar.setValue(fileCount);
		}
		AutoCheckManager.myThreadState = false;
		AutoCheckTab.sorter = new TableRowSorter<TableModel>(AutoCheckTab.ResultModel);
		AutoCheckTab.ResultTable.setRowSorter(AutoCheckTab.sorter);
	}

}

class TableListener implements MouseListener
{

	@Override
	public void mouseClicked(MouseEvent e)
	{
		int selectRow = AutoCheckTab.ResultTable.getSelectedRow(); // ֻ�ܻ�ȡ��ͼ�е�����������ͼ��ģ����һһ��Ӧ��
		int model_row = AutoCheckTab.ResultTable.convertRowIndexToModel(selectRow); // ����ͼ����ת��Ϊģ����Ӱ
		if (model_row != -1)
		{
			String path = (String) AutoCheckTab.ResultModel.getValueAt(model_row, 1);
			@SuppressWarnings("unused")
			CodeEditTab codeEditPane = new CodeEditTab(path);

			SearchContext context = new SearchContext();
			context.setSearchFor((String) AutoCheckTab.ResultModel.getValueAt(model_row, 2));
			context.setSearchForward(true);
			@SuppressWarnings("unused")
			boolean found = SearchEngine.find(codeEditPane.getTextArea(), context).wasFound();
			codeEditPane.textField.setText((String) AutoCheckTab.ResultModel.getValueAt(model_row, 2));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO �Զ����ɵķ������

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO �Զ����ɵķ������

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO �Զ����ɵķ������

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO �Զ����ɵķ������

	}

}
