package com.tool;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.gui.MainWindow;
import com.gui.PHPiniRulePanel;

public class PHPiniItemManager
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
		String[] columnNames = { "项目配置", "项目描述描述" };

		String[][] tableValues = ruleReadFromFile();
		MainWindow.INIRuleModel = new DefaultTableModel(tableValues, columnNames);
		MainWindow.INIRuleTable = new JTable(MainWindow.INIRuleModel);
		MainWindow.INIRuleTable.setRowHeight(30);
		MainWindow.INIRuleTable.setFont(new Font("Menu.font", Font.PLAIN, 15));
		PHPiniRulePanel rulepanel = new PHPiniRulePanel();
		MainWindow.tabbedPane.add("PHPINI扫描项目配置", rulepanel);
		MainWindow.INIRuleTable.addMouseListener(new MouseListener()
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
				int selectRow = MainWindow.INIRuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					rulepanel.getRuleEdit().setText((String) MainWindow.INIRuleTable.getValueAt(selectRow, 0));
					rulepanel.getDetailsEdit().setText((String) MainWindow.INIRuleTable.getValueAt(selectRow, 1));
				}
			}
		});

		MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
	}

	public static String[][] ruleReadFromFile()
	{
		ArrayList<String[]> rowDate = new ArrayList<String[]>(); // 首先创建个列表
		try
		{
			FileInputStream fis = new FileInputStream(path); // 读取文件流
			BufferedReader br = new BufferedReader(new InputStreamReader(fis)); // 从文件流中获取数据流
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

		} catch (FileNotFoundException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		int size = rowDate.size(); // 获取列表的长度

		String[][] rowDates = (String[][]) rowDate.toArray(new String[size][]); // 将列表转换为二维数组

		return rowDates;
	}

	public static void ruleWriteForFile()
	{
		File f = new File(path);
		FileWriter fis = null;
		BufferedWriter bw = null;
		try
		{
			fis = new FileWriter(f);
		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		bw = new BufferedWriter(fis);
		try
		{
			for (int k = 0; k < MainWindow.INIRuleTable.getRowCount(); k++) // 循环必须在try/catch之内
			{
				bw.write((String) (MainWindow.INIRuleModel.getValueAt(k, 0) + "￥"
						+ MainWindow.INIRuleModel.getValueAt(k, 1)));
				bw.newLine();
			}

		} catch (IOException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally
		{
			if (bw != null)
				try
				{
					bw.close();
				} catch (IOException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			if (fis != null)
			{
				try
				{
					fis.close();
				} catch (IOException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
}
