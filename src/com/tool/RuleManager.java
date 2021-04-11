package com.tool;

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

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.gui.MainWindow;
import com.gui.RulePanel;

public class RuleManager
{
	private static String path;

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
		String[] columnNames =
		{ "����", "����" };

		String[][] tableValues = ruleReadFromFile();
		MainWindow.RuleModel = new DefaultTableModel(tableValues, columnNames);
		MainWindow.RuleTable = new JTable(MainWindow.RuleModel);
		MainWindow.RuleTable.setRowHeight(26);
		MainWindow.RuleTable.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				// TODO �Զ����ɵķ������
				int selectRow = MainWindow.RuleTable.getSelectedRow();
				if (selectRow != -1)
				{
					RulePanel.textField.setText((String) MainWindow.RuleTable.getValueAt(selectRow, 0));
					RulePanel.textField_1.setText((String) MainWindow.RuleTable.getValueAt(selectRow, 1));
				}
			}
		});
		MainWindow.tabbedPane.add("��������", new RulePanel());
		MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
	}

	static String[][] ruleReadFromFile()
	{
		ArrayList<String[]> rowDate = new ArrayList<String[]>(); // ���ȴ������б�
		try
		{
			FileInputStream fis = new FileInputStream(path); // ��ȡ�ļ���
			BufferedReader br = new BufferedReader(new InputStreamReader(fis)); // ���ļ����л�ȡ������
			String line = null;
			try
			{
				while ((line = br.readLine()) != null) // һ��һ�ж�ȡ
				{
					rowDate.add(line.split("��")); // ���ַ�������ָ���ַ��ָ�
				}
			} catch (IOException e)
			{
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}

		} catch (FileNotFoundException e)
		{
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		int size = rowDate.size(); // ��ȡ�б��ĳ���

		String[][] rowDates = (String[][]) rowDate.toArray(new String[size][]); // ���б�ת��Ϊ��ά����

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
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		bw = new BufferedWriter(fis);
		try
		{
			for (int k = 0; k < MainWindow.RuleTable.getRowCount(); k++) // ѭ��������try/catch֮��
			{
				bw.write(
						(String) (MainWindow.RuleModel.getValueAt(k, 0) + "��" + MainWindow.RuleModel.getValueAt(k, 1)));
				bw.newLine();
			}

		} catch (IOException e)
		{
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} finally
		{
			if (bw != null)
				try
				{
					bw.close();
				} catch (IOException e)
				{
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			if (fis != null)
			{
				try
				{
					fis.close();
				} catch (IOException e)
				{
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		}
	}
}