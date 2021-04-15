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

	public static void getFileTree() // ��ȡ�ļ���
	{
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		filechooser.setFont(new Font("Menu.font", Font.PLAIN, 15));
		int i = filechooser.showOpenDialog(MainWindow.getStaticContentPane());
		if (i == JFileChooser.APPROVE_OPTION) // ���iΪ�����ѡ��
		{
			String path = filechooser.getSelectedFile().getAbsolutePath(); // ��ѡ����ľ���·����path
			if (MainWindow.TreeScrollPane != null)
			{
				MainWindow.FileTreepanel.remove(MainWindow.TreeScrollPane); // ���ֻ���Ƴ�������壬ֱ���Ƴ������û��
			}
			FileTreeManager.FileCount = 0; // ���ļ�������Ϊ0
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

	public static void getCodeFromTreeNode(MouseEvent e)
	{
		JTree TreeSelection = (JTree) e.getSource(); // ��e�л�ȡʵ������
		if ((((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value) != null) // �����������ļ��У���Ϊ�ļ��в���洢path������node_valueΪ��
		{
			String path = ((DefaultMutableTreeNodes) TreeSelection.getLastSelectedPathComponent()).node_value; // �ӽڵ��л�ȡ�洢��path
			@SuppressWarnings("unused")
			JPanel codeEditPane = new CodeEditPanel(path);
		}
	}

}
