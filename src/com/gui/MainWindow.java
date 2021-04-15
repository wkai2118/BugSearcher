package com.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

import com.tool.AutoCheckManager;
import com.tool.FileTreeManager;
import com.tool.GlobalGrammarSearcher;
import com.tool.MinimizeIcon;
import com.tool.RecentProjectManger;
import com.tool.RuleManager;
import com.tool.TabManager;

@SuppressWarnings("unused")
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
	public static DefaultMutableTreeNode ParentNode; // 文件树父节点

	public static RSyntaxTextArea textArea; // 代码编辑器

	public static JTable RuleTable; // 规则表格
	public static DefaultTableModel RuleModel; // 规则表格模型

	public static JPanel TabPane; // 占位子用的，防在分割面板的右侧了

	public static MainWindow frame; // 主窗口

	public static ArrayList<String[]> FunctionList;

	public static JMenu RecentItemBtn;

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
		setTitle("BugSearcher");
		setIconImage(new ImageIcon("src/com/icon/48.png").getImage());
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("");
		menuBar.add(mnNewMenu);

		JMenu mnNewMenu_1 = new JMenu("\u6587\u4EF6(F)");
		mnNewMenu_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_1.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem = new JMenuItem("\u65B0\u5EFA\u9879\u76EE(N)");
		mntmNewMenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 16));

		mntmNewMenuItem.setMnemonic(KeyEvent.VK_N);

		mntmNewMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String path = null;
				JFileChooser filechooser = new JFileChooser();
				filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				filechooser.setFont(new Font("Menu.font", Font.PLAIN, 15));
				int i = filechooser.showOpenDialog(MainWindow.getStaticContentPane());
				if (filechooser.getSelectedFile() != null)
				{
					path = filechooser.getSelectedFile().getAbsolutePath(); // 将选择项的绝对路径给path
				}
				if (i == JFileChooser.APPROVE_OPTION) // 如果i为允许的选项
				{
					FileTreeManager.getFileTree(path);
					RecentProjectManger.addRecentItemHistory(path);
				}
				TabManager.closeAllTab();
			}
		});

		mnNewMenu_1.add(mntmNewMenuItem);

		RecentItemBtn = new JMenu("最近的项目");
		RecentItemBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_1.add(RecentItemBtn);

		for (String ItemHistory : RecentProjectManger.rowDate)
		{
			JMenuItem Record = new JMenuItem(ItemHistory);
			MainWindow.RecentItemBtn.add(Record);
			Record.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			Record.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					// TODO 自动生成的方法存根
					FileTreeManager.getFileTree(ItemHistory);
					TabManager.closeAllTab();
				}
			});
		}

		JMenuItem mntmNewMenuItem_10 = new JMenuItem("清空历史记录");
		mntmNewMenuItem_10.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mntmNewMenuItem_10.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				RecentProjectManger.clearAllItemHistory();
				RecentItemBtn.removeAll();
				RecentItemBtn.add(mntmNewMenuItem_10);
			}
		});
		RecentItemBtn.add(mntmNewMenuItem_10);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u5173\u95ED\u9879\u76EE(C)");

		mntmNewMenuItem_1.setMnemonic(KeyEvent.VK_C);

		mntmNewMenuItem_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_1.addActionListener(new ActionListener()
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
		mnNewMenu_1.add(mntmNewMenuItem_1);

		mnNewMenu_1.addSeparator();

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u9000\u51FA(Q)");

		mntmNewMenuItem_2.setMnemonic(KeyEvent.VK_Q);

		mntmNewMenuItem_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MinimizeIcon.exitSystem();
			}
		});
		mntmNewMenuItem_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_2 = new JMenu("\u89C4\u5219\u7BA1\u7406(R)");

		mnNewMenu_2.setMnemonic(KeyEvent.VK_R);

		mnNewMenu_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u89C4\u5219\u914D\u7F6E");
		mntmNewMenuItem_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RuleManager.openRuleFromFile();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);

		JMenu mnNewMenu_6 = new JMenu("\u6807\u7B7E\u7BA1\u7406(T)");

		mnNewMenu_6.setMnemonic(KeyEvent.VK_T);

		mnNewMenu_6.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		menuBar.add(mnNewMenu_6);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("\u5173\u95ED\u5F53\u524D\u6807\u7B7E");
		mntmNewMenuItem_6.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_6.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (MainWindow.tabbedPane.getSelectedIndex() != 0)
				{
					TabManager.closeSelectTab(MainWindow.tabbedPane.getSelectedIndex());
				}
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_6);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("\u5173\u95ED\u6240\u6709\u6807\u7B7E");
		mntmNewMenuItem_5.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TabManager.closeOtherTab();
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_5);

		JMenu mnNewMenu_3 = new JMenu("\u9644\u52A0\u529F\u80FD(A)");

		mnNewMenu_3.setMnemonic(KeyEvent.VK_A);

		mnNewMenu_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		menuBar.add(mnNewMenu_3);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("\u7F16\u7801\u8F6C\u6362");
		mntmNewMenuItem_7.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_3.add(mntmNewMenuItem_7);

		JMenu mnNewMenu_7 = new JMenu("\u5168\u5C40\u5212\u8BCD\u5BA1\u8BA1");
		mnNewMenu_7.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_3.add(mnNewMenu_7);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("    \u5F00    ");
		mntmNewMenuItem_8.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

			}
		});
		mntmNewMenuItem_8.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_7.add(mntmNewMenuItem_8);

		JMenuItem mntmNewMenuItem_9 = new JMenuItem("    \u5173    ");
		mntmNewMenuItem_9.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_7.add(mntmNewMenuItem_9);

		JMenu mnNewMenu_5 = new JMenu("\u5E2E\u52A9(H)");

		mnNewMenu_5.setMnemonic(KeyEvent.VK_H);

		mnNewMenu_5.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		menuBar.add(mnNewMenu_5);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("\u5173\u4E8E");
		mntmNewMenuItem_4.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_5.add(mntmNewMenuItem_4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

//		contentPane.add(toolBar, BorderLayout.NORTH);

//		JButton CloseAllTab = new JButton("");
//		CloseAllTab.setBackground(SystemColor.menu);
//		CloseAllTab.setToolTipText("");
//		CloseAllTab.setIcon(new ImageIcon(MainWindow.class.getResource("/com/icon/close.png")));
//		CloseAllTab.setBorder(null);
//		CloseAllTab.setPreferredSize(new Dimension(20, 20));
//		CloseAllTab.setFocusPainted(false);
//		CloseAllTab.setFont(new Font("微软雅黑", Font.BOLD, 13));
//		CloseAllTab.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				TabManager.closeOtherTab();
//			}
//		});
//		toolBar.add(CloseAllTab);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(SystemColor.control);
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
		TabPane = new AllTabPanel(); // 实例化右侧面板
		RuleManager.setRulePtah("src/com/config/Rule.txt");
		RuleManager.CompileRuleInit();
		AutoCheckManager.AutoCheckInit();
		GlobalGrammarSearcher.GramSearchInit();
		MinimizeIcon.minisize();
		RecentProjectManger.InitRecentItemHistory(); // 初始化最近项目
	}

}
