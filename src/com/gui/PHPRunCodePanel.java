package com.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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

import com.tool.FileTreeManager;

public class PHPRunCodePanel extends JPanel
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
	public PHPRunCodePanel()
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

		textArea.setFont(textArea.getFont().deriveFont((float) CodeEditPanel.DefaultCodeEditSize));

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
				FileTreeManager.savePHPFile(textArea, "src/com/config/runphp.php");
				mList.clear();
				runPHP();
			}
		});
		btnNewButton.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		toolBar.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		terminal.add(scrollPane, BorderLayout.CENTER);

		JList<String> list = new JList<String>();
		list.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, CodeEditPanel.DefaultCodeEditSize));
		list.setModel(mList);
		scrollPane.setViewportView(list);

		FileTreeManager.openPHPFile(textArea, "src/com/config/runphp.php");
	}

	public void runPHP()
	{
		Process proc;
		try
		{
			proc = Runtime.getRuntime()
					.exec(MainWindow.PhpExe + " " + new File("src/com/config/runphp.php").getAbsolutePath());
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)
			{
				mList.addElement(line);
			}
			in.close();
			proc.waitFor();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
