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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.gui.CodeEditPanel;
import com.gui.MainWindow;

public class FileTreeManager
{
	public static int FileCount;

	public static void getFileTree(String path) // 获取文件树
	{
		MainWindow.FunctionList = new ArrayList<String[]>();
		if (MainWindow.TreeScrollPane != null)
		{
			MainWindow.FileTreepanel.remove(MainWindow.TreeScrollPane); // 这边只能移除滚动面板，直接移除树面板没用
		}
		FileTreeManager.FileCount = 0; // 将文件数量置为0
		if ((MainWindow.ParentNode = traverseFolder(path)) != null)
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
						getCodeFromTreeNode(e);
					}
				}
			});

			MainWindow.FileTreepanel.updateUI();
		}
	}

	static class DefaultMutableTreeNodes extends DefaultMutableTreeNode
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

	public static DefaultMutableTreeNodes traverseFolder(String path)
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
						ParentNode.add(traverseFolder(file2.getAbsolutePath()));
					} else
					{ // 否则直接将文件添加至父节点
						FuncGuide.getAllFunc(file2.getAbsolutePath());
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

	public static void getCodeFromTreeNode(MouseEvent e)
	{
		JTree TreeSelection = (JTree) e.getSource(); // 从e中获取实例本身
		if ((((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value) != null) // 如果点击的是文件夹，因为文件夹不会存储path，所以node_value为空
		{
			String path = ((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value; // 从节点中获取存储的path
			@SuppressWarnings("unused")
			CodeEditPanel codeEditPane = new CodeEditPanel(path);
		}
	}

	public static void openPHPFile(RSyntaxTextArea textArea, String path)
	{
		FileInputStream f = null;
		try
		{
			f = new FileInputStream(path);
		} catch (FileNotFoundException e2)
		{
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} // 实例化为file类
		Reader is = null; // 创建个Reader类
		try
		{
			is = new BufferedReader(new InputStreamReader(f, CodeEditPanel.Encode));
		} catch (UnsupportedEncodingException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try
		{
			textArea.read(is, "d"); // textArea直接读取Reader类
			is.close();
			f.close();

		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	public static void savePHPFile(RSyntaxTextArea textArea, String path)
	{
		FileOutputStream f = null;
		try
		{
			f = new FileOutputStream(path);
		} catch (FileNotFoundException e2)
		{
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} // 实例化为file类
		BufferedWriter is = null; // 创建个Reader类
		try
		{
			is = new BufferedWriter(new OutputStreamWriter(f, CodeEditPanel.Encode));
		} catch (UnsupportedEncodingException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try
		{
			textArea.write(is); // textArea直接读取Reader类
			is.close();
			f.close();

		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

}
