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

	public static void getFileTree() // ��ȡ�ļ���
	{
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int i = filechooser.showOpenDialog(MainWindow.getStaticContentPane());
		if (i == JFileChooser.APPROVE_OPTION) // ���iΪ�����ѡ��
		{
			String path = filechooser.getSelectedFile().getAbsolutePath(); // ��ѡ����ľ���·����path
//			System.out.println(path);
			if (MainWindow.TreeScrollPane != null)
			{
				MainWindow.FileTreepanel.remove(MainWindow.TreeScrollPane); // ���ֻ���Ƴ�������壬ֱ���Ƴ������û��
			}
			FileTreeManager.FileCount = 0; // ���ļ�������Ϊ0
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

	public static DefaultMutableTreeNodes traverseFolder(String path)
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
						ParentNode.add(traverseFolder(file2.getAbsolutePath()));
					} else
					{ // ����ֱ�ӽ��ļ���������ڵ�
						DefaultMutableTreeNodes temp = new DefaultMutableTreeNodes(file2.getName());
						temp.setValue(file2.getAbsolutePath()); // ���ļ���·���洢��value��
						FileCount++;
						ParentNode.add(temp);
					}
				}
			}
		} else // ����ļ�һ��ʼ�Ͳ����ڣ�ֱ�ӷ���null
		{
			return null;
		}
		return ParentNode; // ���շ��ظ��ڵ�
	}

	public static void getCodeFromTreeNode(TreeSelectionEvent e)
	{
		JTree TreeSelection = (JTree) e.getSource(); // ��e�л�ȡʵ������
		if ((((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value) != null) // �����������ļ��У���Ϊ�ļ��в���洢path������node_valueΪ��
		{
			MainWindow.textArea = new RSyntaxTextArea();
			MainWindow.textArea.setCodeFoldingEnabled(true);
			String path = ((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value; // �ӽڵ��л�ȡ�洢��path
			MainWindow.textArea.setSyntaxEditingStyle("text/" + path.split("\\.")[path.split("\\.").length - 1]);
			MainWindow.textArea.setFont(MainWindow.textArea.getFont().deriveFont((float) 15));

			JPopupMenu popup = MainWindow.textArea.getPopupMenu();
			popup.addSeparator();
			popup.add(new JMenuItem(new GrammarSearcher()));
			FileInputStream f = null;
			try
			{
				f = new FileInputStream(path); // ���Σ������ñ����ʽ������InputStreamReader
			} catch (FileNotFoundException e2)
			{
				// TODO �Զ����ɵ� catch ��
				e2.printStackTrace();
			} // ʵ����Ϊfile��
			Reader is = null; // ������Reader��
			try
			{
				is = new BufferedReader(new InputStreamReader(f, "utf-8")); // ��f��ʵ����Reader��
			} catch (UnsupportedEncodingException e1)
			{
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			try
			{
				MainWindow.textArea.read(is, "d"); // textAreaֱ�Ӷ�ȡReader��
			} catch (IOException e1)
			{
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			JPanel codeEditPane = new CodeEditor();

			MainWindow.tabbedPane.add(new File(path).getName(), codeEditPane);
			MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
		}
	}

}
