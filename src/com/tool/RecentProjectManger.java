package com.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RecentProjectManger
{
	public static String path = "src/com/config/RecentItemRecord.txt";
	public static ArrayList<String> rowDate;

	public static void InitRecentItemHistory()
	{
		rowDate = new ArrayList<String>(); // ���ȴ������б�
		try
		{
			FileInputStream fis = new FileInputStream(path); // ��ȡ�ļ���
			BufferedReader br = new BufferedReader(new InputStreamReader(fis)); // ���ļ����л�ȡ������
			String line = null;
			try
			{
				while ((line = br.readLine()) != null) // һ��һ�ж�ȡ
				{
					rowDate.add(line); // ���ַ�������ָ���ַ��ָ�
				}
				br.close();
				fis.close();
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

	}

	public static void addRecentItemHistory(String NewHistory)
	{
		if (!rowDate.contains(NewHistory))
		{
			rowDate.add(NewHistory);
		}
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
			for (String temp : rowDate) // ѭ��������try/catch֮��
			{
				bw.write(temp);
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

	public static void clearAllItemHistory()
	{
		rowDate = new ArrayList<>();
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
			for (String temp : rowDate) // ѭ��������try/catch֮��
			{
				bw.write(temp);
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
