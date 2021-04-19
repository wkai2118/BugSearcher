package com.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class PHPRunSettingWindow extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	public PHPRunSettingWindow()
	{
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("PHP‘À––ª∑æ≥≈‰÷√");
		setSize(450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		textField.setBounds(14, 63, 298, 34);
		textField.setText(MainWindow.PhpExe);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
//		System.out.println(MainWindow.PhpIni);
		textField_1.setText(MainWindow.PhpIni);
		textField_1.setBounds(14, 128, 298, 34);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton = new JButton("\u6D4F\u89C8");
		btnNewButton.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser filechooser = new JFileChooser();
				filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = filechooser.showOpenDialog(getContentPane());
				if (i == JFileChooser.APPROVE_OPTION)
				{
					String path = filechooser.getSelectedFile().getAbsolutePath();
					textField.setText(path);
				}
			}
		});
		btnNewButton.setBounds(319, 62, 99, 35);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u6D4F\u89C8");
		btnNewButton_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser filechooser = new JFileChooser();
				filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = filechooser.showOpenDialog(getContentPane());
				if (i == JFileChooser.APPROVE_OPTION)
				{
					String path = filechooser.getSelectedFile().getAbsolutePath();
					textField_1.setText(path);
				}
			}
		});
		btnNewButton_1.setBounds(319, 128, 99, 34);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel = new JLabel("PHP\u8FD0\u884C\u76EE\u5F55\uFF1A");
		lblNewLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		lblNewLabel.setBounds(14, 32, 133, 34);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("PHP.INI\u76EE\u5F55\uFF1A");
		lblNewLabel_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(14, 96, 133, 34);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton_2 = new JButton("\u786E\u5B9A");
		btnNewButton_2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnNewButton_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MainWindow.PhpExe = textField.getText();
				MainWindow.PhpIni = textField_1.getText();
				dispose();
			}
		});
		btnNewButton_2.setBounds(79, 191, 99, 34);
		contentPane.add(btnNewButton_2);

		JButton btnNewButton_4 = new JButton("\u53D6\u6D88");
		btnNewButton_4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnNewButton_4.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnNewButton_4.setBounds(240, 191, 99, 34);
		contentPane.add(btnNewButton_4);
		setLocationRelativeTo(null);
	}
}
