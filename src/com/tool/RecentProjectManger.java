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
		rowDate = new ArrayList<String>(); // 首先创建个列表
		try
		{
			FileInputStream fis = new FileInputStream(path); // 读取文件流
			BufferedReader br = new BufferedReader(new InputStreamReader(fis)); // 从文件流中获取数据流
			String line = null;
			try
			{
				while ((line = br.readLine()) != null) // 一行一行读取
				{
					rowDate.add(line); // 将字符串按照指定字符分割
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
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		bw = new BufferedWriter(fis);
		try
		{
			for (String temp : rowDate) // 循环必须在try/catch之内
			{
				bw.write(temp);
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
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		bw = new BufferedWriter(fis);
		try
		{
			for (String temp : rowDate) // 循环必须在try/catch之内
			{
				bw.write(temp);
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
