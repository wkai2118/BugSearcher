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

	public static void newItem(String path) // ��ȡ�ļ������½���Ŀ
	{
		MainWindow.FunctionList = new ArrayList<String[]>();
		if (MainWindow.TreeScrollPane != null)
		{
			MainWindow.FileTreepanel.remove(MainWindow.TreeScrollPane); // ���ֻ���Ƴ�������壬ֱ���Ƴ������û��
		}
		ItemManager.FileCount = 0; // ���ļ�������Ϊ0
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
					// TODO �Զ����ɵķ������

				}

				@Override
				public void mousePressed(MouseEvent e)
				{
					// TODO �Զ����ɵķ������

				}

				@Override
				public void mouseExited(MouseEvent e)
				{
					// TODO �Զ����ɵķ������

				}

				@Override
				public void mouseEntered(MouseEvent e)
				{
					// TODO �Զ����ɵķ������

				}

				@Override
				public void mouseClicked(MouseEvent e)
				{
					// TODO �Զ����ɵķ������
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
		DefaultMutableTreeNodes ParentNode = new DefaultMutableTreeNodes(new File(path).getName()); // ���ȴ���һ�����ڵ�

		File file = new File(path); // �����ڵ��·��ʵ����Ϊfile

		if (file.exists())
		{ // �����ж��ļ��Ƿ����
			File[] files = file.listFiles(); // �����ļ��������е��ļ�
			if (files.length == 0)
			{ // ������ļ���
				if (file.isDirectory())
				{
					DefaultMutableTreeNodes dn = new DefaultMutableTreeNodes(file.getName()); // �����ļ��е����Ʒ���
					dn.setValue(file.getAbsolutePath()); // ���ÿ��ļ��е�·��
					return dn;
				}
			} else // �ܵ��⣬˵���ļ������ж���
			{
				for (File file2 : files)
				{
					if (file2.isDirectory()) // ������滹���ļ��У���ʹ�õݹ��������
					{
						ParentNode.add(traverseItemFolder(file2.getAbsolutePath()));
					} else
					{ // ����ֱ�ӽ��ļ���������ڵ�
						PHPCodeManager.getFuncAndPath(file2.getAbsolutePath());
						DefaultMutableTreeNodes temp = new DefaultMutableTreeNodes(file2.getName());
						temp.setValue(file2.getAbsolutePath()); // ���ļ���·���洢��value��
						FileCount++;
						ParentNode.add(temp);
					}
				}
			}
		} else // ����ļ�һ��ʼ�Ͳ����ڣ�ֱ�ӷ���null
		{
			JOptionPane.showMessageDialog(MainWindow.frame, "��Ŀ�ļ��в�����", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return ParentNode; // ���շ��ظ��ڵ�
	}

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

	static class DefaultMutableTreeNodes extends DefaultMutableTreeNode // �̳е����ڵ㣬Ϊ���ܹ���ȡһЩֵ
	{

		/**
		 * 
		 */
		public String node_value; // node_value���ڴ洢��Ϣ
		private static final long serialVersionUID = 1L;

		public void setValue(String values) // ����������Ϣ
		{
			node_value = values;
		}

		public String getValue() // ���ڻ�ȡ��Ϣ
		{
			return node_value;
		}

		public DefaultMutableTreeNodes(String path) // ��Ҫ���ø���Ĺ��췽��
		{
			super(path);
		}
	}
}
