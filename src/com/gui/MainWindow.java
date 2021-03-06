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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.tool.AutoCheckManager;
import com.tool.CodeCheckRuleManager;
import com.tool.GlobalGrammarSearcher;
import com.tool.ItemManager;
import com.tool.MinimizeManager;
import com.tool.PHPiniItemManager;
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

	public static JTable RuleTable; // 规则表格
	public static DefaultTableModel RuleModel; // 规则表格模型

	public static JTable INIRuleTable; // php.ini规则表格
	public static DefaultTableModel INIRuleModel; // php.ini规则表格模型

	public static AllTabPanel TabPane; // 占位子用的，放在分割面板的右侧了

	public static MainWindow frame; // 主窗口

	public static ArrayList<String[]> FunctionList;

	public static JMenu RecentItemBtn;

	public static JMenuBar menuBar;

	public static AutoCheckTab autoCheckPanel;

	public static Properties InitConfig = new Properties();

	public static String Theme; // 主题

	public static String PhpExe;

	public static String PhpIni;

	JMenuItem clearHistoryBtn;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					InitAll();
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
//		setResizable(false);

		menuBar = new JMenuBar();
		menuBar.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("");
		menuBar.add(mnNewMenu);

		JMenu mnNewMenu_1 = new JMenu("\u6587\u4EF6(F)");
		mnNewMenu_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_1.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem = new JMenuItem("新建项目");
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
				if (i == JFileChooser.APPROVE_OPTION) // 如果i为允许的选项
				{
					path = filechooser.getSelectedFile().getAbsolutePath(); // 将选择项的绝对路径给path
					ItemManager.newItem(path);
					ItemManager.addRecentItemHistory(path); // 添加记录至文件
					TabManager.closeAllTab();
					RecentItemBtn.removeAll(); // 移除所有记录的按钮
					RecentItemBtn.add(clearHistoryBtn); // 添加清空按钮
					InitHistoryBtn(); // 重新初始化按钮
				}
			}
		});

		mnNewMenu_1.add(mntmNewMenuItem);

		RecentItemBtn = new JMenu("最近的项目");
		RecentItemBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_1.add(RecentItemBtn);

		InitHistoryBtn();

		clearHistoryBtn = new JMenuItem("清空历史记录");
		clearHistoryBtn.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		clearHistoryBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ItemManager.clearAllItemHistory();
				RecentItemBtn.removeAll();
				RecentItemBtn.add(clearHistoryBtn);
			}
		});
		RecentItemBtn.add(clearHistoryBtn);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u5173\u95ED\u9879\u76EE(C)");

		mntmNewMenuItem_1.setMnemonic(KeyEvent.VK_C);

		mntmNewMenuItem_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ItemManager.closeItem();
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
				MinimizeManager.exitSystem();
			}
		});
		mntmNewMenuItem_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_3 = new JMenu("\u9644\u52A0\u529F\u80FD(A)");

		mnNewMenu_3.setMnemonic(KeyEvent.VK_A);

		mnNewMenu_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		menuBar.add(mnNewMenu_3);

		JMenu mntmNewMenuItem_7 = new JMenu("\u7F16\u7801\u8F6C\u6362");
		mntmNewMenuItem_7.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_3.add(mntmNewMenuItem_7);

		JMenuItem mntmNewMenuItem_16 = new JMenuItem("Url Encoding");
		mntmNewMenuItem_16.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_16.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DecodingTab urlencoding = new DecodingTab("Url Encoding");
				MainWindow.tabbedPane.add(urlencoding, "Url Encoding");
				MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
			}
		});
		mntmNewMenuItem_7.add(mntmNewMenuItem_16);

		JMenuItem mntmNewMenuItem_17 = new JMenuItem("Html Entity Encoding");
		mntmNewMenuItem_17.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DecodingTab htmlencoding = new DecodingTab("Html Entity Encoding");
				MainWindow.tabbedPane.add(htmlencoding, "Html Entity Encoding");
				MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
			}
		});
		mntmNewMenuItem_17.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_7.add(mntmNewMenuItem_17);

		JMenuItem mntmNewMenuItem_18 = new JMenuItem("Base64 Encoding");
		mntmNewMenuItem_18.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DecodingTab base64encoding = new DecodingTab("Base64 Encoding");
				MainWindow.tabbedPane.add(base64encoding, "Base64 Encoding");
				MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
			}
		});
		mntmNewMenuItem_18.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_7.add(mntmNewMenuItem_18);

		JMenuItem mntmNewMenuItem_19 = new JMenuItem("PHP\u4EE3\u7801\u8C03\u8BD5");
		mntmNewMenuItem_19.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CodeDebugTab phprunpanel = new CodeDebugTab();
				MainWindow.tabbedPane.add("PHP代码调试", phprunpanel);
				MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
			}
		});
		mntmNewMenuItem_19.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_3.add(mntmNewMenuItem_19);

		JMenuItem mntmNewMenuItem_21 = new JMenuItem("PHPINI\u626B\u63CF");
		mntmNewMenuItem_21.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MainWindow.tabbedPane.add("PHPINI配置检测", new PHPiniSearchTab());
				MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);
			}
		});
		mntmNewMenuItem_21.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_3.add(mntmNewMenuItem_21);

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
				TabManager.closeAllCodeTab();
			}
		});

		JMenuItem mntmNewMenuItem_15 = new JMenuItem("\u5173\u95ED\u5176\u4ED6\u6807\u7B7E\u9875");
		mntmNewMenuItem_15.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TabManager.closeOtherTab();
			}
		});
		mntmNewMenuItem_15.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_6.add(mntmNewMenuItem_15);

		JMenuItem mntmNewMenuItem_13 = new JMenuItem("\u5173\u95ED\u53F3\u8FB9\u6240\u6709\u6807\u7B7E\u9875");
		mntmNewMenuItem_13.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				TabManager.closeAllRightTab();
			}
		});
		mntmNewMenuItem_13.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_6.add(mntmNewMenuItem_13);

		JMenuItem mntmNewMenuItem_14 = new JMenuItem("\u5173\u95ED\u5DE6\u8FB9\u6240\u6709\u6807\u7B7E\u9875");
		mntmNewMenuItem_14.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TabManager.closeAllleftTab();
			}
		});
		mntmNewMenuItem_14.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mnNewMenu_6.add(mntmNewMenuItem_14);
		mnNewMenu_6.add(mntmNewMenuItem_5);

		JMenu settingBtn = new JMenu("\u8BBE\u7F6E(S)");

		settingBtn.setMnemonic(KeyEvent.VK_S);

		settingBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		menuBar.add(settingBtn);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u5BA1\u8BA1\u89C4\u5219\u914D\u7F6E");
		mntmNewMenuItem_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CodeCheckRuleManager.openRuleFromFile();
			}
		});
		settingBtn.add(mntmNewMenuItem_3);

		JMenu encodeselect = new JMenu("\u7F16\u7801\u914D\u7F6E");
		encodeselect.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		settingBtn.add(encodeselect);

		JRadioButtonMenuItem utf8 = new JRadioButtonMenuItem("UTF-8");
		utf8.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		utf8.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CodeEditTab.Encode = "utf-8";
			}
		});
		encodeselect.add(utf8);

		JRadioButtonMenuItem gbk = new JRadioButtonMenuItem("GBK");
		gbk.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		gbk.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CodeEditTab.Encode = "gbk";
			}
		});
		encodeselect.add(gbk);

		ButtonGroup btg = new ButtonGroup();

		btg.add(utf8);
		btg.add(gbk);

		switch (CodeEditTab.Encode)
			{
			case "utf-8":
			{
				utf8.setSelected(true);
				break;
			}
			case "gbk":
			{
				gbk.setSelected(true);
				break;
			}
			}

		JMenuItem mntmNewMenuItem_20 = new JMenuItem("PHP\u8FD0\u884C\u73AF\u5883\u914D\u7F6E");
		mntmNewMenuItem_20.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new PHPRunSettingWindow().setVisible(true);
			}
		});

		JMenuItem mntmNewMenuItem_22 = new JMenuItem("PHPINI\u626B\u63CF\u9879\u914D\u7F6E");
		mntmNewMenuItem_22.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		mntmNewMenuItem_22.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				PHPiniItemManager.openItemFromFile();
			}
		});
		settingBtn.add(mntmNewMenuItem_22);
		mntmNewMenuItem_20.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		settingBtn.add(mntmNewMenuItem_20);

		JMenu selectTheme = new JMenu("\u4E3B\u9898\u5207\u6362");
		settingBtn.add(selectTheme);
		selectTheme.setFont(new Font("微软雅黑", Font.PLAIN, 16));

		JMenuItem windowsTheme = new JMenuItem("Windows\u98CE\u683C");
		windowsTheme.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		windowsTheme.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				MainWindow.Theme = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
				try
				{
					UIManager.setLookAndFeel(Theme);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.menuBar);
				SwingUtilities.updateComponentTreeUI(MainWindow.contentPane);
			}
		});
		selectTheme.add(windowsTheme);

		JMenuItem nimbusTheme = new JMenuItem("Nimbus\u98CE\u683C");
		nimbusTheme.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		nimbusTheme.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Theme = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
				try
				{
					UIManager.setLookAndFeel(Theme);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.menuBar);
				SwingUtilities.updateComponentTreeUI(MainWindow.contentPane);
			}
		});
		selectTheme.add(nimbusTheme);

		JMenuItem metalTheme = new JMenuItem("Metal\u98CE\u683C");
		metalTheme.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		metalTheme.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Theme = "javax.swing.plaf.metal.MetalLookAndFeel";
				try
				{
					UIManager.setLookAndFeel(Theme);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.menuBar);
				SwingUtilities.updateComponentTreeUI(MainWindow.contentPane);
			}
		});
		selectTheme.add(metalTheme);

		JMenuItem windowsClassicTheme = new JMenuItem("Windows Classic\u98CE\u683C");
		windowsClassicTheme.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		windowsClassicTheme.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Theme = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
				try
				{
					UIManager.setLookAndFeel(Theme);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.menuBar);
				SwingUtilities.updateComponentTreeUI(MainWindow.contentPane);
			}
		});
		selectTheme.add(windowsClassicTheme);

		JMenuItem motifTheme = new JMenuItem("Motif\u98CE\u683C");
		motifTheme.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		motifTheme.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Theme = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
				try
				{
					UIManager.setLookAndFeel(Theme);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e)
				{
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(MainWindow.menuBar);
				SwingUtilities.updateComponentTreeUI(MainWindow.contentPane);
			}
		});
		selectTheme.add(motifTheme);

		if (System.getProperty("os.name").equals("Linux"))
		{
			windowsTheme.setEnabled(false);
			nimbusTheme.setEnabled(false);
			windowsClassicTheme.setEnabled(false);
		}

		JMenu helpBtn = new JMenu("\u5E2E\u52A9(H)");

		helpBtn.setMnemonic(KeyEvent.VK_H);

		helpBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		menuBar.add(helpBtn);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("\u5173\u4E8E");
		mntmNewMenuItem_4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new AboutWindow().setVisible(true);
			}
		});
		mntmNewMenuItem_4.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		helpBtn.add(mntmNewMenuItem_4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

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
				MinimizeManager.ItemShow.setEnabled(true);
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
		InputStream fis = null;
		try
		{
			fis = new FileInputStream("src/com/config/Init.properties");
			InitConfig.load(fis);
			fis.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}

		try
		{
			Theme = InitConfig.getProperty("theme");
			if (System.getProperty("os.name").equals("Linux"))
			{
				String laf = UIManager.getSystemLookAndFeelClassName();
				UIManager.setLookAndFeel(laf);
			} else
				UIManager.setLookAndFeel(Theme);

			PhpExe = InitConfig.getProperty("phpexe");
			PhpIni = InitConfig.getProperty("phpini");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		FileTreepanel = new JPanel(new BorderLayout()); // 实例化左侧面板
		TabPane = new AllTabPanel(); // 实例化右侧面板
		CodeCheckRuleManager.setRulePtah("src/com/config/Rule.txt");
		PHPiniItemManager.setItemPtah("src/com/config/PHPiniSearchItem.txt");
		CodeCheckRuleManager.initCompileRule();
		AutoCheckManager.AutoCheckInit();
		GlobalGrammarSearcher.GramSearchInit();
		MinimizeManager.minisize();
		ItemManager.InitRecentItemHistory(); // 初始化最近项目
	}

	public void InitHistoryBtn()
	{
		for (String ItemHistory : ItemManager.rowDate)
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
					ItemManager.newItem(ItemHistory);
					TabManager.closeAllTab();
				}
			});
		}
	}

}
