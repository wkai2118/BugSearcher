package com.tool;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.gui.CodeEditor;
import com.gui.MainWindow;

public class FileTreeManager
{
	public static int FileCount;

	public static void getFileTree() // 获取文件树
	{
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int i = filechooser.showOpenDialog(MainWindow.getStaticContentPane());
		if (i == JFileChooser.APPROVE_OPTION) // 如果i为允许的选项
		{
			String path = filechooser.getSelectedFile().getAbsolutePath(); // 将选择项的绝对路径给path
//			System.out.println(path);
			if (MainWindow.TreeScrollPane != null)
			{
				MainWindow.FileTreepanel.remove(MainWindow.TreeScrollPane); // 这边只能移除滚动面板，直接移除树面板没用
			}
			FileTreeManager.FileCount = 0; // 将文件数量置为0
			MainWindow.ParentNode = traverseFolder(path);
			MainWindow.FileTree = new JTree(MainWindow.ParentNode);
			MainWindow.TreeScrollPane = new JScrollPane(MainWindow.FileTree);
			MainWindow.FileTreepanel.add(MainWindow.TreeScrollPane, BorderLayout.CENTER);
			MainWindow.FileTree.addTreeSelectionListener(new TreeSelectionListener()
			{

				@Override
				public void valueChanged(TreeSelectionEvent e)
				{
					getCodeFromTreeNode(e);
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

	public static void getCodeFromTreeNode(TreeSelectionEvent e)
	{
		JTree TreeSelection = (JTree) e.getSource(); // 从e中获取实例本身
		if ((((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value) != null) // 如果点击的是文件夹，因为文件夹不会存储path，所以node_value为空
		{
			MainWindow.textArea = new RSyntaxTextArea();
			MainWindow.textArea.setCodeFoldingEnabled(true);
			String path = ((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value; // 从节点中获取存储的path
			MainWindow.textArea.setSyntaxEditingStyle("text/" + path.split("\\.")[path.split("\\.").length - 1]);
			MainWindow.textArea.setFont(MainWindow.textArea.getFont().deriveFont((float) 15));

			JPopupMenu popup = MainWindow.textArea.getPopupMenu();
			popup.addSeparator();
			popup.add(new JMenuItem(new GrammarSearcher()));
			FileInputStream f = null;
			try
			{
				f = new FileInputStream(path); // 无奈，想设置编码格式必须用InputStreamReader
			} catch (FileNotFoundException e2)
			{
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			} // 实例化为file类
			Reader is = null; // 创建个Reader类
			try
			{
				is = new BufferedReader(new InputStreamReader(f, "utf-8")); // 从f中实例化Reader类
			} catch (UnsupportedEncodingException e1)
			{
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			try
			{
				MainWindow.textArea.read(is, "d"); // textArea直接读取Reader类
			} catch (IOException e1)
			{
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			JPanel codeEditPane = new CodeEditor();

			MainWindow.tabbedPane.add(new File(path).getName(), codeEditPane);
			MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
		}
	}

}
