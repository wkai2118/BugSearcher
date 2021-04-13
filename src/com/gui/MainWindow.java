package com.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.tool.AutoCheckManager;
import com.tool.FileTreeManager;
import com.tool.GlobalGrammarSearcher;
import com.tool.MinimizeIcon;
import com.tool.RuleManager;
import com.tool.TabManager;

@SuppressWarnings("unused")
public class MainWindow extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JPanel contentPane; // �����ڵײ����
	public static JTabbedPane tabbedPane; // ��ǩ���

	public static JPanel FileTreepanel; // �ļ������
	public static JScrollPane TreeScrollPane; // �ļ����������
	public static JTree FileTree; // �ļ���
	public static DefaultMutableTreeNode ParentNode; // �ļ������ڵ�

	public static RSyntaxTextArea textArea; // ����༭��

	public static JTable RuleTable; // ������
	public static DefaultTableModel RuleModel; // ������ģ��

	public static JPanel TabPane; // ռλ���õģ����ڷָ������Ҳ���

	public static MainWindow frame; // ������

	/**
	 * Launch the application.
	 */

	public static void main(String[] args)
	{
		InitAll();

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
//					String laf = UIManager.getSystemLookAndFeelClassName();// ��ȡϵͳͼ�ν������
//					UIManager.setLookAndFeel(laf);// ����ͼ�ν������
					frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public static JPanel getStaticContentPane()
	{
		return contentPane;
	}

	/**
	 * Create the frame.
	 */

	public MainWindow()
	{
		setTitle("Code auditor");
		setIconImage(new ImageIcon("src/com/icon/48.png").getImage());
		setSize(1200, 800);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		JButton NewItemBtn = new JButton("�½���Ŀ");
		NewItemBtn.setFocusPainted(false);
		NewItemBtn.setFont(new Font("΢���ź�", Font.BOLD, 15));
		NewItemBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				FileTreeManager.getFileTree();
			}
		});
		toolBar.add(NewItemBtn);

		JButton CloseItem = new JButton("�ر���Ŀ");
		CloseItem.setFocusPainted(false);
		CloseItem.setFont(new Font("΢���ź�", Font.BOLD, 15));
		CloseItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TabManager.closeAllTab();
				if (AutoCheckManager.myThreadState)
				{
					AutoCheckManager.stopAutoCheck();
				}
				if (ParentNode != null)
				{
					FileTreepanel.remove(TreeScrollPane);
					FileTreepanel.updateUI();
					MainWindow.ParentNode = null;
				}
			}
		});
		toolBar.add(CloseItem);

		JButton ConfigBtn = new JButton("��������");
		ConfigBtn.setFocusPainted(false);
		ConfigBtn.setFont(new Font("΢���ź�", Font.BOLD, 15));
		ConfigBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RuleManager.openRuleFromFile();
			}
		});
		toolBar.add(ConfigBtn);

		JButton CodingBtn = new JButton("����ת��");
		CodingBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});
		CodingBtn.setFocusPainted(false);
		CodingBtn.setFont(new Font("΢���ź�", Font.BOLD, 15));
		toolBar.add(CodingBtn);

		JButton CloseAllTab = new JButton("CloseAllTab");
		CloseAllTab.setFocusPainted(false);
		CloseAllTab.setFont(new Font("΢���ź�", Font.BOLD, 15));
		CloseAllTab.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TabManager.closeOtherTab();
			}
		});
		toolBar.add(CloseAllTab);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(200);
		splitPane.setDividerSize(5);
		splitPane.setLeftComponent(FileTreepanel);
		splitPane.setRightComponent(TabPane);

		contentPane.add(splitPane, BorderLayout.CENTER);

		addWindowListener(new WindowListener()
		{

			@Override
			public void windowOpened(WindowEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowIconified(WindowEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowClosing(WindowEvent arg0)
			{
				MinimizeIcon.ItemShow.setEnabled(true);
			}

			@Override
			public void windowClosed(WindowEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}

			@Override
			public void windowActivated(WindowEvent arg0)
			{
				// TODO �Զ����ɵķ������

			}
		});
	}

	// ��ʼ����������
	public static void InitAll()
	{
		FileTreepanel = new FilePanel(); // ʵ����������
		TabPane = new TabPanel(); // ʵ�����Ҳ����
		RuleManager.setRulePtah("src/com/config/Rule.txt");
		RuleManager.CompileRuleInit();
		AutoCheckManager.AutoCheckInit();
		GlobalGrammarSearcher.GramSearchInit();
		MinimizeIcon.minisize();

	}

}
