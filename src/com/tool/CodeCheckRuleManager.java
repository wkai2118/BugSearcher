package com.tool;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.gui.MainWindow;
import com.gui.RegexRuleTab;

public class CodeCheckRuleManager
{
	private static String path;
	public static Pattern[] CompileRules;

	public static void setRulePtah(String temp)
	{
		path = temp;
	}

	public static String getRulePtah(String temp)
	{
		return path;
	}

	public static void openRuleFromFile()
	{
		String[] columnNames = { "规则", "描述" };

		String[][] tableValues = readRuleFromFile();
		MainWindow.RuleModel = new DefaultTableModel(tableValues, columnNames);
		MainWindow.RuleTable = new JTable(MainWindow.RuleModel);
		MainWindow.RuleTable.setRowHeight(30);
		MainWindow.RuleTable.setFont(new Font("Menu.font", Font.PLAIN, 15));
		RegexRuleTab rulepanel = new RegexRuleTab();
		MainWindow.tabbedPane.add("自动审计规则配置", rulepanel);
		MainWindow.RuleTable.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				// TODO 自动生成的方法存根
				int selectRow = MainWindow.RuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					rulepanel.getRuleEdit().setText((String) MainWindow.RuleTable.getValueAt(selectRow, 0));
					rulepanel.getDetailsEdit().setText((String) MainWindow.RuleTable.getValueAt(selectRow, 1));
				}
			}
		});

		MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
	}

	static String[][] readRuleFromFile()
	{
		ArrayList<String[]> rowDate = new ArrayList<String[]>(); // 首先创建个列表
		try
		{
			FileInputStream fis = new FileInputStream(path); // 读取文件流
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8")); // 从文件流中获取数据流
			String line = null;
			try
			{
				while ((line = br.readLine()) != null) // 一行一行读取
				{
					rowDate.add(line.split("￥")); // 将字符串按照指定字符分割
				}
				br.close();
				fis.close();
			} catch (IOException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		} catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		int size = rowDate.size(); // 获取列表的长度

		String[][] rowDates = (String[][]) rowDate.toArray(new String[size][]); // 将列表转换为二维数组

		return rowDates;
	}

	public static void writeRuleForFile()
	{
		FileOutputStream fis = null;
		BufferedWriter bw = null;
		try
		{

			fis = new FileOutputStream(path);
			bw = new BufferedWriter(new OutputStreamWriter(fis, "UTF-8"));
			for (int k = 0; k < MainWindow.RuleTable.getRowCount(); k++) // 循环必须在try/catch之内
			{
				bw.write(
						(String) (MainWindow.RuleModel.getValueAt(k, 0) + "￥" + MainWindow.RuleModel.getValueAt(k, 1)));
				bw.newLine();
			}
			bw.close();
			fis.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}

	}

	public static void initCompileRule()
	{
		ArrayList<Pattern> CompileRule = new ArrayList<Pattern>();
		String[][] RuleDate = CodeCheckRuleManager.readRuleFromFile();
		for (String[] RuleRow : RuleDate)
		{
			CompileRule.add(Pattern.compile(RuleRow[0], Pattern.CASE_INSENSITIVE));
		}
		CompileRules = (Pattern[]) CompileRule.toArray(new Pattern[CompileRule.size()]);
	}
}
