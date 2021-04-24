package com.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.tool.PHPCodeManager;
import com.tool.CodeDebugManager;

public class CodeDebugTab extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RSyntaxTextArea textArea;
	public DefaultListModel<String> mList = new DefaultListModel<String>();

	/**
	 * Create the panel.
	 */
	public CodeDebugTab()
	{
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(350);
		splitPane.setDividerSize(5); // …Ë÷√∑÷∏ÓÃıµƒœÒÀÿ¥Û–°
		splitPane.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				splitPane.setDividerLocation(0.685);
			}
		});

		textArea = new RSyntaxTextArea();

		RTextScrollPane sp = new RTextScrollPane(textArea);

		textArea.setSyntaxEditingStyle(RSyntaxTextArea.SYNTAX_STYLE_PHP);

		textArea.setFont(textArea.getFont().deriveFont((float) CodeEditTab.DefaultCodeEditSize));

		splitPane.setLeftComponent(sp);

		JPanel terminal = new JPanel();

		splitPane.setRightComponent(terminal);
		terminal.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		terminal.add(toolBar, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("\u8F93\u51FA\u7A97\u53E3");
		lblNewLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		toolBar.add(lblNewLabel);

		toolBar.add(Box.createHorizontalGlue());

		JButton btnNewButton = new JButton("\u8FD0\u884C");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CodeDebugManager.savePHPRunFile(textArea, "src/com/config/runphp.php");
				mList.clear();
				CodeDebugManager.runPHP(mList);
			}
		});
		btnNewButton.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		toolBar.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		terminal.add(scrollPane, BorderLayout.CENTER);

		JList<String> list = new JList<String>();
		list.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, CodeEditTab.DefaultCodeEditSize));
		list.setModel(mList);
		scrollPane.setViewportView(list);

		PHPCodeManager.openPHPFile(textArea, "src/com/config/runphp.php");
	}

}
