package com.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
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
import com.tool.GrammarSearcher;
import com.tool.PHPCodeManager;

public class CodeEditTab extends JPanel
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
	public static Pattern VarsRegx = Pattern.compile("\\$\\w{1,20}((\\[[\"']|\\[)\\${0,1}[\\w\\[\\]\"']{0,30}){0,1}",
			Pattern.CASE_INSENSITIVE);
	public static String[] ArrayFunc;
	public static String[] ArrayVars;
	private boolean SearchForward = true;
	public static int DefaultCodeEditSize = Integer.valueOf(MainWindow.InitConfig.getProperty("editsize"));
	public static String Encode = MainWindow.InitConfig.getProperty("encode");

	/**
	 * Create the panel.
	 */
	public CodeEditTab(String path)
	{
		setLayout(new BorderLayout(0, 0));
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setContinuousLayout(true);
//		splitPane.setDividerLocation(623);
		splitPane.setDividerLocation(623);

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
		textArea.setCodeFoldingEnabled(true);
		textArea.setSyntaxEditingStyle("text/" + path.split("\\.")[path.split("\\.").length - 1]);
		textArea.setFont(textArea.getFont().deriveFont((float) DefaultCodeEditSize));
		textArea.setCurrentLineHighlightColor(Color.GREEN);
		textArea.setSelectionColor(Color.GREEN);

		JPopupMenu popup = textArea.getPopupMenu();
		popup.addSeparator();
		popup.add(new JMenuItem(new GrammarSearcher(textArea)));
		popup.add(new JMenuItem(new PHPCodeManager()));

		JMenu decode = new JMenu("????????");

		decode.add(new DecodingManger("URL-decode", textArea));
		decode.add(new DecodingManger("HTML-decode", textArea));
		decode.add(new DecodingManger("Base64-decode", textArea));

		popup.add(decode);

		popup.setPreferredSize(new Dimension(150, 230));

		PHPCodeManager.openPHPFile(textArea, path); // ??????????????????????

		RTextScrollPane sp = new RTextScrollPane(textArea);

		splitPane.setLeftComponent(sp);

		MainWindow.tabbedPane.add(new File(path).getName(), this);
		MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabbedPane.getTabCount() - 1);

		JPanel SearchPanel = new JPanel();

		SearchPanel.setLayout(null);

		splitPane.setRightComponent(SearchPanel);

		JLabel SearchLabel = new JLabel("????????????");

		SearchLabel.setBounds(30, 10, 100, 30);

		SearchLabel.setFont(new Font("????????", Font.PLAIN, 15));

		SearchPanel.add(SearchLabel);

		textField = new JTextField();

		SearchPanel.add(textField);

		textField.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textField.setColumns(10);
		textField.setBounds(120, 10, 700, 30);
		textField.setFont(new Font("Menu.font", Font.PLAIN, 15));

		JButton SearchBtn = new JButton("????");

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
					JOptionPane.showMessageDialog(MainWindow.frame, "??????????", "????", JOptionPane.DEFAULT_OPTION);
					SearchForward = !SearchForward;
					context.setSearchForward(SearchForward);
				}
			}
		});

		SearchPanel.add(SearchBtn);

		JLabel lblNewLabel = new JLabel("\u51FD\u6570\u6C47\u603B\uFF1A");
		lblNewLabel.setFont(new Font("????????", Font.PLAIN, 15));
		lblNewLabel.setBounds(30, 83, 89, 18);
		SearchPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u53D8\u91CF\u6C47\u603B\uFF1A");
		lblNewLabel_1.setFont(new Font("????????", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(375, 84, 75, 18);
		SearchPanel.add(lblNewLabel_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(120, 83, 224, 254);
		SearchPanel.add(scrollPane);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(465, 83, 224, 254);
		SearchPanel.add(scrollPane_1);

		PHPCodeManager.getFuncAndVars(path); // ??????????????????????

		list = new JList<String>();
		list.setListData(ArrayFunc);
		list.setFont(new Font("????????", Font.PLAIN, 15));
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(list);

		list.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO ??????????????????

			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO ??????????????????

			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO ??????????????????

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO ??????????????????

			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				// TODO ??????????????????

				context.setSearchFor(list.getSelectedValue()); // ??????????????????????????
				boolean found = SearchEngine.find(textArea, context).wasFound();
				if (!found)
				{
					SearchForward = !SearchForward; // ????????????????????????
					context.setSearchForward(SearchForward);
				}

			}
		});

		list_1 = new JList<String>();
		list_1.setListData(ArrayVars);
		list_1.setFont(new Font("????????", Font.PLAIN, 15));
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
//				System.out.println("??????: " + slider.getValue());
				textArea.setFont(textArea.getFont().deriveFont((float) slider.getValue()));
				DefaultCodeEditSize = slider.getValue();
			}
		});
		slider.setBounds(863, 83, 47, 254);
		SearchPanel.add(slider);

		JLabel lblNewLabel_2 = new JLabel("\u7F16\u8F91\u5668\u5B57\u4F53\u5927\u5C0F\uFF1A");
		lblNewLabel_2.setFont(new Font("????????", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(748, 84, 121, 18);
		SearchPanel.add(lblNewLabel_2);

		list_1.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO ??????????????????

			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO ??????????????????

			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO ??????????????????

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO ??????????????????

			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				// TODO ??????????????????

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

}
