package com.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import com.tool.DecodingManger;
import com.tool.FuncGuide;
import com.tool.GrammarSearcher;

public class CodeEditPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JSplitPane splitPane;
	public JTextField textField;
	private RSyntaxTextArea textArea;
	private JList<String> list;
	private JList<String> list_1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	public static Pattern FuncRegx = Pattern.compile("(?!\\\\)(.{0,10})(function\\s{1,5}(\\w{1,20})\\s{0,5})(?=\\()",
			Pattern.CASE_INSENSITIVE);
	private static Pattern VarsRegx = Pattern.compile("\\$\\w{1,20}((\\[[\"']|\\[)\\${0,1}[\\w\\[\\]\"']{0,30}){0,1}",
			Pattern.CASE_INSENSITIVE);
	private String[] ArrayFunc;
	private String[] ArrayVars;
	private boolean SearchForward = true;
	private static int DefaultCodeEditSize = 18;

	/**
	 * Create the panel.
	 */
	public CodeEditPanel(String path)
	{
		setLayout(new BorderLayout(0, 0));
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setContinuousLayout(true);
//		splitPane.setDividerLocation(623);
//		splitPane.setDividerLocation(623);

		splitPane.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				splitPane.setDividerLocation(0.925);
			}
		});

		splitPane.setDividerSize(5);
		add(splitPane, BorderLayout.CENTER);

		textArea = new RSyntaxTextArea();
//		MainWindow.textArea.setCodeFoldingEnabled(true);
		textArea.setSyntaxEditingStyle("text/" + path.split("\\.")[path.split("\\.").length - 1]);
		textArea.setFont(textArea.getFont().deriveFont((float) DefaultCodeEditSize));

		JPopupMenu popup = textArea.getPopupMenu();
		popup.addSeparator();
		popup.add(new JMenuItem(new GrammarSearcher()));
		popup.add(new JMenuItem(new FuncGuide()));

		JMenu decode = new JMenu("编码转换");

		decode.add(new DecodingManger("URL-decode", textArea));
		decode.add(new DecodingManger("HTML-decode", textArea));
		decode.add(new DecodingManger("Base64-decode", textArea));

		popup.add(decode);

		popup.setPreferredSize(new Dimension(150, 230));
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
			textArea.read(is, "d"); // textArea直接读取Reader类
			is.close();
			f.close();

		} catch (IOException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		RTextScrollPane sp = new RTextScrollPane(textArea);

		splitPane.setLeftComponent(sp);

		MainWindow.tabbedPane.add(new File(path).getName(), this);
		MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);

		JPanel SearchPanel = new JPanel();

		SearchPanel.setLayout(null);

		splitPane.setRightComponent(SearchPanel);

		JLabel SearchLabel = new JLabel("关键字查找：");

		SearchLabel.setBounds(30, 10, 100, 30);

		SearchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));

		SearchPanel.add(SearchLabel);

		textField = new JTextField();

		SearchPanel.add(textField);

		textField.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textField.setColumns(10);
		textField.setBounds(120, 10, 700, 30);
		textField.setFont(new Font("Menu.font", Font.PLAIN, 15));

		JButton SearchBtn = new JButton("查找");

		SearchBtn.setBounds(830, 10, 100, 30);

		SearchBtn.setFont(new Font("Menu.font", Font.PLAIN, 15));

		SearchContext context = new SearchContext();

//		context.setSearchForward(false);

		SearchBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				context.setSearchFor(textField.getText());
				boolean found = SearchEngine.find(textArea, context).wasFound();
				if (!found)
				{
					JOptionPane.showMessageDialog(MainWindow.frame, "已搜索完毕", "提示", JOptionPane.DEFAULT_OPTION);
					SearchForward = !SearchForward;
					context.setSearchForward(SearchForward);
				}
			}
		});

		SearchPanel.add(SearchBtn);

		JLabel lblNewLabel = new JLabel("\u51FD\u6570\u6C47\u603B\uFF1A");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setBounds(30, 83, 89, 18);
		SearchPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u53D8\u91CF\u6C47\u603B\uFF1A");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(375, 84, 75, 18);
		SearchPanel.add(lblNewLabel_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(120, 83, 224, 254);
		SearchPanel.add(scrollPane);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(465, 83, 224, 254);
		SearchPanel.add(scrollPane_1);

		getFuncAndVars(path); // 获取该标签的函数和变量

		list = new JList<String>();
		list.setListData(ArrayFunc);
		list.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(list);

		list.addMouseListener(new MouseListener()
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

				context.setSearchFor(list.getSelectedValue()); // 将列表选中内容放置搜索引擎
				boolean found = SearchEngine.find(textArea, context).wasFound();
				if (!found)
				{
					SearchForward = !SearchForward; // 如果找不到，变换搜索方向
					context.setSearchForward(SearchForward);
				}

			}
		});

		list_1 = new JList<String>();
		list_1.setListData(ArrayVars);
		list_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		list_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_1.setViewportView(list_1);

		JSlider slider = new JSlider(SwingConstants.VERTICAL);
		slider.setMaximum(28);
		slider.setMinimum(16);
		slider.setValue(DefaultCodeEditSize);
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg0)
			{
//				System.out.println("当前值: " + slider.getValue());
				textArea.setFont(textArea.getFont().deriveFont((float) slider.getValue()));
				DefaultCodeEditSize = slider.getValue();
			}
		});
		slider.setBounds(863, 83, 47, 254);
		SearchPanel.add(slider);

		JLabel lblNewLabel_2 = new JLabel("\u7F16\u8F91\u5668\u5B57\u4F53\u5927\u5C0F\uFF1A");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(748, 84, 121, 18);
		SearchPanel.add(lblNewLabel_2);

		list_1.addMouseListener(new MouseListener()
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

				context.setSearchFor(list_1.getSelectedValue());
				boolean found = SearchEngine.find(textArea, context).wasFound();
				if (!found)
				{
					SearchForward = !SearchForward;
					context.setSearchForward(SearchForward);
				}
			}

		});

	}

	public RSyntaxTextArea getTextArea()
	{
		return textArea;
	}

	public void getFuncAndVars(String path)
	{
		ArrayList<String> ArrayListFunc = new ArrayList<String>();
		ArrayList<String> ArrayListVars = new ArrayList<String>();

		FileInputStream fis = null;
		@SuppressWarnings("unused")
		String line = null;
		try
		{
			fis = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			while ((line = br.readLine()) != null)
			{
				Matcher f = FuncRegx.matcher(line);
				Matcher v = VarsRegx.matcher(line);
				if (f.find())
				{
					ArrayListFunc.add(f.group(0));
				}
				if (v.find() && (!ArrayListVars.contains(v.group(0))))
				{
					ArrayListVars.add(v.group(0));
				}

			}
			br.close();
			fis.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		ArrayFunc = (String[]) ArrayListFunc.toArray(new String[ArrayListFunc.size()]);
		ArrayVars = (String[]) ArrayListVars.toArray(new String[ArrayListFunc.size()]);
	}

}
