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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.tool.FileTreeManager;
import com.tool.GlobalGrammarSearcher;
import com.tool.MinimizeIcon;
import com.tool.RuleManager;
import com.tool.TabManager;

public class MainWindow extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JPanel contentPane; // 主窗口底层面板
	public static JTabbedPane tabbedPane; // 标签面板

	public static JPanel FileTreepanel; // 文件树面板
	public static JScrollPane TreeScrollPane; // 文件树滚轮面板
	public static JTree FileTree; // 文件树
	public static DefaultMutableTreeNode ParentNode;

	public static RSyntaxTextArea textArea; // 代码编辑器

	public static JTable RuleTable; // 规则表格
	public static DefaultTableModel RuleModel; // 规则表格模型

	public static JPanel TabPane; // 占位子用的，防在分割面板的右侧了

	public static MainWindow frame; // 主窗口

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
//					String laf = UIManager.getSystemLookAndFeelClassName();// 获取系统图形界面外观
//					UIManager.setLookAndFeel(laf);// 设置图形界面外观
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

		JButton btnNewButton = new JButton("\u65B0\u5EFA\u9879\u76EE");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setFont(new Font("微软雅黑", Font.BOLD, 13));
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				FileTreeManager.getFileTree();
			}
		});
		toolBar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u81EA\u52A8\u5BA1\u8BA1");
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setFont(new Font("微软雅黑", Font.BOLD, 13));
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				tabbedPane.add("自动审计", new AutoCheckPanel());
				tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
			}
		});
		toolBar.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("\u89C4\u5219\u914D\u7F6E");
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.setFont(new Font("微软雅黑", Font.BOLD, 13));
		btnNewButton_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RuleManager.openRuleFromFile();
			}
		});
		toolBar.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("\u7F16\u7801\u8F6C\u6362");
		btnNewButton_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

			}
		});
		btnNewButton_3.setFocusPainted(false);
		btnNewButton_3.setFont(new Font("微软雅黑", Font.BOLD, 13));
		toolBar.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("\u5173\u95ED\u9009\u9879\u5361");
		btnNewButton_4.setFocusPainted(false);
		btnNewButton_4.setFont(new Font("微软雅黑", Font.BOLD, 13));
		btnNewButton_4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TabManager.closeCurrentTab();
			}
		});
		toolBar.add(btnNewButton_4);

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
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowIconified(WindowEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowClosing(WindowEvent arg0)
			{
				MinimizeIcon.ItemShow.setEnabled(true);
			}

			@Override
			public void windowClosed(WindowEvent arg0)
			{
				// TODO 自动生成的方法存根

			}

			@Override
			public void windowActivated(WindowEvent arg0)
			{
				// TODO 自动生成的方法存根

			}
		});
	}

	// 初始化部分数据
	public static void InitAll()
	{
		FileTreepanel = new FilePanel(); // 实例化左侧面板
		TabPane = new TabPanel(); // 实例化右侧面板
		RuleManager.setRulePtah("src/com/config/Rule.txt");
		GlobalGrammarSearcher.GramSearchInit();
		MinimizeIcon.minisize();
	}

}
