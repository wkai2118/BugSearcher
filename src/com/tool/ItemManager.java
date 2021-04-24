package com.tool;

import java.awt.BorderLayout;
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

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.gui.MainWindow;

public class ItemManager
{
	public static int FileCount;
	public static String path = "src/com/config/RecentItemRecord.txt";
	public static ArrayList<String> rowDate;

	public static void newItem(String path) // 获取文件树，新建项目
	{
		MainWindow.FunctionList = new ArrayList<String[]>();
		if (MainWindow.TreeScrollPane != null)
		{
			MainWindow.FileTreepanel.remove(MainWindow.TreeScrollPane); // 这边只能移除滚动面板，直接移除树面板没用
		}
		ItemManager.FileCount = 0; // 将文件数量置为0
		if ((MainWindow.ParentNode = traverseItemFolder(path)) != null)
		{
			MainWindow.FileTree = new JTree(MainWindow.ParentNode);
			MainWindow.FileTree.setFont(new Font("Menu.font", Font.PLAIN, 15));
			MainWindow.TreeScrollPane = new JScrollPane(MainWindow.FileTree);
			MainWindow.FileTreepanel.add(MainWindow.TreeScrollPane, BorderLayout.CENTER);

			MainWindow.FileTree.addMouseListener(new MouseListener()
			{

				@Override
				public void mouseReleased(MouseEvent e)
				{
					// TODO 自动生成的方法存根

				}

				@Override
				public void mousePressed(MouseEvent e)
				{
					// TODO 自动生成的方法存根

				}

				@Override
				public void mouseExited(MouseEvent e)
				{
					// TODO 自动生成的方法存根

				}

				@Override
				public void mouseEntered(MouseEvent e)
				{
					// TODO 自动生成的方法存根

				}

				@Override
				public void mouseClicked(MouseEvent e)
				{
					// TODO 自动生成的方法存根
					if (e.getClickCount() == 2)
					{
						PHPCodeManager.getCodeFromTreeNode(e);
					}
				}
			});

			MainWindow.FileTreepanel.updateUI();
		}
	}

	public static DefaultMutableTreeNodes traverseItemFolder(String path)
	{
		DefaultMutableTreeNodes ParentNode = new DefaultMutableTreeNodes(new File(path).getName()); // 首先创建一个父节点

		File file = new File(path); // 将父节点的路径实例化为file

		if (file.exists())
		{ // 首先判断文件是否存在
			File[] files = file.listFiles(); // 返回文件夹中所有的文件
			if (files.length == 0)
			{ // 代表空文件夹
				if (file.isDirectory())
				{
					DefaultMutableTreeNodes dn = new DefaultMutableTreeNodes(file.getName()); // 将空文件夹的名称返回
					dn.setValue(file.getAbsolutePath()); // 设置空文件夹的路径
					return dn;
				}
			} else // 能到这，说明文件夹中有东西
			{
				for (File file2 : files)
				{
					if (file2.isDirectory()) // 如果里面还是文件夹，则使用递归继续处理
					{
						ParentNode.add(traverseItemFolder(file2.getAbsolutePath()));
					} else
					{ // 否则直接将文件添加至父节点
						PHPCodeManager.getFuncAndPath(file2.getAbsolutePath());
						DefaultMutableTreeNodes temp = new DefaultMutableTreeNodes(file2.getName());
						temp.setValue(file2.getAbsolutePath()); // 将文件的路径存储到value中
						FileCount++;
						ParentNode.add(temp);
					}
				}
			}
		} else // 如果文件一开始就不存在，直接返回null
		{
			JOptionPane.showMessageDialog(MainWindow.frame, "项目文件夹不存在", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return ParentNode; // 最终返回父节点
	}

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

	public static void closeItem()
	{
		TabManager.closeAllTab();
		if (AutoCheckManager.myThreadState)
		{
			AutoCheckManager.stopAutoCheck();
		}
		if (MainWindow.ParentNode != null)
		{
			MainWindow.FileTreepanel.remove(MainWindow.TreeScrollPane);
			MainWindow.FileTreepanel.updateUI();
			MainWindow.ParentNode = null;
		}
	}

	static class DefaultMutableTreeNodes extends DefaultMutableTreeNode // 继承的树节点，为了能够存取一些值
	{

		/**
		 * 
		 */
		public String node_value; // node_value用于存储信息
		private static final long serialVersionUID = 1L;

		public void setValue(String values) // 用于设置信息
		{
			node_value = values;
		}

		public String getValue() // 用于获取信息
		{
			return node_value;
		}

		public DefaultMutableTreeNodes(String path) // 需要调用父类的构造方法
		{
			super(path);
		}
	}
}
