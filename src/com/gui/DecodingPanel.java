package com.gui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import com.tool.DecodingManger;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.BoxLayout;

public class DecodingPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public DecodingPanel(String category)
	{
		setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel(category);
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		toolBar.add(lblNewLabel);

		toolBar.add(Box.createHorizontalGlue());

		JButton btnEncode = new JButton("Encode");
		btnEncode.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));

		toolBar.add(btnEncode);

		JButton btnNewButton_1 = new JButton("Decode");

		btnNewButton_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		toolBar.add(btnNewButton_1);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		JTextArea textAreaEncode = new JTextArea();
		textAreaEncode.setLineWrap(true);
		textAreaEncode.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		scrollPane.setViewportView(textAreaEncode);

		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1);

		JTextArea textAreaDecode = new JTextArea();
		textAreaDecode.setLineWrap(true);
		textAreaDecode.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		scrollPane_1.setViewportView(textAreaDecode);

		btnEncode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String encode = textAreaEncode.getText();

				switch (category)
					{
					case "Url Encoding":
					{
						textAreaDecode.setText(DecodingManger.encodingURL(encode));
						break;
					}
					case "Html Entity Encoding":
					{
						textAreaDecode.setText(DecodingManger.encodingHTML(encode));
						break;
					}
					case "Base64 Encoding":
					{
						textAreaDecode.setText(DecodingManger.encodingBase64(encode));
						break;
					}
					}

			}
		});

		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String encode = textAreaEncode.getText();

				switch (category)
					{
					case "Url Encoding":
					{
						textAreaDecode.setText(DecodingManger.decodingURL(encode));
						break;
					}
					case "Html Entity Encoding":
					{
						textAreaDecode.setText(DecodingManger.decodingHTML(encode));
						break;
					}
					case "Base64 Encoding":
					{
						textAreaDecode.setText(DecodingManger.decodingBase64(encode));
						break;
					}
					}
			}
		});

	}
}
