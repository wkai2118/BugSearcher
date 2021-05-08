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
import com.gui.PHPiniRuleTab;

public class PHPiniItemManager
{
	private static String path;
	public static Pattern[] CompileRules;

	public static void setItemPtah(String temp)
	{
		path = temp;
	}

	public static String getItemPtah(String temp)
	{
		return path;
	}

	public static void openItemFromFile()
	{
		String[] columnNames = { "��Ŀ����", "��Ŀ����" };

		String[][] tableValues = readItemFromFile();
		MainWindow.INIRuleModel = new DefaultTableModel(tableValues, columnNames);
		MainWindow.INIRuleTable = new JTable(MainWindow.INIRuleModel);
		MainWindow.INIRuleTable.setRowHeight(30);
		MainWindow.INIRuleTable.setFont(new Font("Menu.font", Font.PLAIN, 15));
		PHPiniRuleTab rulepanel = new PHPiniRuleTab();
		MainWindow.tabbedPane.add("PHPINIɨ����Ŀ����", rulepanel);
		MainWindow.INIRuleTable.addMouseListener(new MouseListener()
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

	public static String[][] readItemFromFile()
	{
		ArrayList<String[]> rowDate = new ArrayList<String[]>(); // ���ȴ������б�
		try
		{
			FileInputStream fis = new FileInputStream(path); // ��ȡ�ļ���
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8")); // ���ļ����л�ȡ������
			String line = null;
			try
			{
				while ((line = br.readLine()) != null) // һ��һ�ж�ȡ
				{
					rowDate.add(line.split("��")); // ���ַ�������ָ���ַ��ָ�
				}
				br.close();
				fis.close();
			} catch (IOException e)
			{
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}

		} catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		int size = rowDate.size(); // ��ȡ�б�ĳ���

		String[][] rowDates = (String[][]) rowDate.toArray(new String[size][]); // ���б�ת��Ϊ��ά����

		return rowDates;
	}

	public static void writeItemForFile()	//д���ļ�
	{
		FileOutputStream fis = null;
		BufferedWriter bw = null;
		try
		{

			fis = new FileOutputStream(path);
			bw = new BufferedWriter(new OutputStreamWriter(fis, "UTF-8"));
			for (int k = 0; k < MainWindow.INIRuleTable.getRowCount(); k++) // ѭ��������try/catch֮��
			{
				bw.write((String) (MainWindow.INIRuleModel.getValueAt(k, 0) + "��"
						+ MainWindow.INIRuleModel.getValueAt(k, 1)));
				bw.newLine();
			}
			bw.close();
			fis.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}
