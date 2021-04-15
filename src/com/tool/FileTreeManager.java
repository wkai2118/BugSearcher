package com.tool;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.gui.CodeEditPanel;
import com.gui.MainWindow;

public class FileTreeManager
{
	public static int FileCount;

	public static void getFileTree() // 获取文件树
	{
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		filechooser.setFont(new Font("Menu.font", Font.PLAIN, 15));
		int i = filechooser.showOpenDialog(MainWindow.getStaticContentPane());
		if (i == JFileChooser.APPROVE_OPTION) // 如果i为允许的选项
		{
			String path = filechooser.getSelectedFile().getAbsolutePath(); // 将选择项的绝对路径给path
			if (MainWindow.TreeScrollPane != null)
			{
				MainWindow.FileTreepanel.remove(MainWindow.TreeScrollPane); // 这边只能移除滚动面板，直接移除树面板没用
			}
			FileTreeManager.FileCount = 0; // 将文件数量置为0
			MainWindow.ParentNode = traverseFolder(path);
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
						DefaultMutableTreeNodes temp = new DefaultMutableTreeNodes(file2.getName());
						temp.setValue(file2.getAbsolutePath()); // 将文件的路径存储到value中
						FileCount++;
						ParentNode.add(temp);

					}
				}
			}
		} else // 如果文件一开始就不存在，直接返回null
		{
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
			JPanel codeEditPane = new CodeEditPanel(path);
		}
	}

}
