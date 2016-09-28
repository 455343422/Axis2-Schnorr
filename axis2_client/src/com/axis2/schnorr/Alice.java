package com.axis2.schnorr;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.util.Random;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Window.Type;


public class Alice extends JFrame {
	public Bob server;
	public  Schnorr SCHNORR;
	
	public JTextField privateKeyTextField;
	public JTextField random_R_TextField;
	public JTextArea X_TextArea;
	public JTextField random_E_TextField;
	public JTextArea S_TextArea;
//	File file = new File("F:\\publicKey");
		
	public Alice() {
			
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				server.dispose();
			}
		});
		getContentPane().setBackground(SystemColor.control);
		setTitle("Alice (client)");
		setSize(580,638);
		setLocation(70, 50);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("密钥生成:");
	
		label.setFont(new Font("宋体", Font.BOLD, 14));
		label.setBounds(12, 0, 235, 27);
		getContentPane().add(label);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(12, 28, 538, 96);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		privateKeyTextField = new JTextField();
		privateKeyTextField.setForeground(Color.RED);
		privateKeyTextField.setFont(new Font("宋体", Font.PLAIN, 14));
		privateKeyTextField.setBounds(121, 21, 299, 22);
		panel.add(privateKeyTextField);
		privateKeyTextField.setColumns(25);
		
		JButton privateKeyGenerateButton = new JButton("New Key");
		privateKeyGenerateButton.setBounds(429, 20, 99, 25);
		panel.add(privateKeyGenerateButton);
		
		JButton generatePublickKeysButton = new JButton("2. Generate Public Keys");
		generatePublickKeysButton.setBounds(10, 56, 172, 25);
		panel.add(generatePublickKeysButton);
		
		JButton showPubKeysButton = new JButton("Show Keys");
		showPubKeysButton.setBounds(192, 56, 111, 25);
		panel.add(showPubKeysButton);
		
		JButton sendPublilcKeyButton = new JButton("3. Send Publilc Keys to Bob >>");
		sendPublilcKeyButton.setBounds(313, 56, 215, 25);
		panel.add(sendPublilcKeyButton);
		
		JLabel lblPrivateKey = new JLabel("1. Private Key:");
		lblPrivateKey.setBounds(28, 24, 88, 16);
		panel.add(lblPrivateKey);
		
		JLabel label_1 = new JLabel("身份认证协议:");
		label_1.setFont(new Font("宋体", Font.BOLD, 14));
		label_1.setBounds(12, 132, 317, 16);
		getContentPane().add(label_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(12, 156, 538, 323);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("1.预处理:");
		lblNewLabel.setBounds(29, 13, 291, 16);
		panel_1.add(lblNewLabel);
		
		JButton random_R_Button = new JButton("Random R:");
		random_R_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				random_R_TextField.setText(BigInteger.probablePrime(128, new Random()).toString());
				
			}
		});
		random_R_Button.setBounds(12, 35, 151, 25);
		panel_1.add(random_R_Button);
		
		random_R_TextField = new JTextField();
		random_R_TextField.setBounds(182, 36, 344, 22);
		panel_1.add(random_R_TextField);
		random_R_TextField.setColumns(10);
		
		JButton calculate_X_Button = new JButton("Calculate X");
		calculate_X_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BigInteger r = new BigInteger(random_R_TextField.getText());
				X_TextArea.setText(SCHNORR.calculate_X(r).toString());
			}
		});
		calculate_X_Button.setBounds(12, 70, 151, 25);
		panel_1.add(calculate_X_Button);
		
		JLabel label_2 = new JLabel("2.启动:");
		label_2.setBounds(29, 145, 134, 16);
		panel_1.add(label_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(182, 71, 344, 61);
		panel_1.add(scrollPane);
		
		X_TextArea = new JTextArea();
		X_TextArea.setForeground(Color.BLUE);
		X_TextArea.setFont(new Font("宋体", Font.PLAIN, 13));
		X_TextArea.setLineWrap(true);
		scrollPane.setViewportView(X_TextArea);
		
		JButton send_X_Button = new JButton("Send X to Bob >>");
		send_X_Button.setBounds(182, 141, 229, 25);
		panel_1.add(send_X_Button);
		
		random_E_TextField = new JTextField();
		random_E_TextField.setForeground(new Color(0, 128, 128));
		random_E_TextField.setColumns(10);
		random_E_TextField.setBounds(182, 174, 229, 22);
		panel_1.add(random_E_TextField);
		
		JLabel lblE = new JLabel("e =");
		lblE.setHorizontalAlignment(SwingConstants.RIGHT);
		lblE.setBounds(107, 174, 56, 16);
		panel_1.add(lblE);
		
		JLabel lblNewLabel_1 = new JLabel("4.确认:");
		lblNewLabel_1.setBounds(29, 206, 134, 16);
		panel_1.add(lblNewLabel_1);
		
		JButton calculate_S_Button = new JButton("Calculate S");
		calculate_S_Button.setBounds(12, 228, 151, 25);
		panel_1.add(calculate_S_Button);
				
		calculate_S_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BigInteger r = new BigInteger(random_R_TextField.getText());
				BigInteger e = new BigInteger(random_E_TextField.getText());
				BigInteger s = SCHNORR.calculate_S(r, e);
				S_TextArea.setText(s.toString());
				
			}
		});
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(182, 228, 344, 44);
		panel_1.add(scrollPane_1);
		
		S_TextArea = new JTextArea();
		S_TextArea.setForeground(Color.RED);
		S_TextArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		S_TextArea.setLineWrap(true);
		scrollPane_1.setViewportView(S_TextArea);
		
		JButton send_S_Button = new JButton("Send S to Bob >>");
		
		send_S_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		//		server.S_TextArea.setText(S_TextArea.getText());
				FileWriter fw;
				try {
					fw = new FileWriter(new File("F:\\S"));
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(S_TextArea.getText()+"\n");
					bw.close();
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		send_S_Button.setBounds(182, 283, 229, 25);
		panel_1.add(send_S_Button);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(12, 505, 538, 75);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAll();
			}
		});
		resetButton.setBounds(185, 13, 155, 49);
		panel_2.add(resetButton);
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.dispose();
				dispose();
			}
		});
		closeButton.setBounds(371, 13, 155, 49);
		panel_2.add(closeButton);
		
		send_X_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			//	server.X_TextArea.setText(X_TextArea.getText());
				FileWriter fw;
				try {
					fw = new FileWriter(new File("F:\\X"));
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(X_TextArea.getText()+"\n");
					bw.close();
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
							
			}
		});
		
		
		
		sendPublilcKeyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileWriter fw;
				try {
					fw = new FileWriter(new File("F:\\publicKey"));
					BufferedWriter bw = new BufferedWriter(fw);
					BigInteger publickeys[] = SCHNORR.getPublicKeys();
					bw.write("p:"+publickeys[0].toString()+"\n");
					bw.write("q:"+publickeys[1].toString()+"\n");
					bw.write("g:"+publickeys[2].toString()+"\n");
					bw.write("y:"+publickeys[3].toString()+"\n");
					bw.close();
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		showPubKeysButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, SCHNORR,
				"Keys:", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		
		generatePublickKeysButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SCHNORR.generateKeys(privateKeyTextField.getText());
				
			}
		});
		
		privateKeyGenerateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				privateKeyTextField.setText(BigInteger.probablePrime(128, new Random()).toString());
			}
		});
		
	}

	protected void clearAll() {
		SCHNORR.clear();
		
		privateKeyTextField.setText(null);
		random_R_TextField.setText(null);
		X_TextArea.setText(null);
		random_E_TextField.setText(null);
		S_TextArea.setText(null);
		
		server.textArea_p.setText(null);
		server.textArea_q.setText(null);
		server.textArea_g.setText(null);
		server.textArea_y.setText(null);
		server.X_TextArea.setText(null);
		server.random_E_TextField.setText(null);
		server.S_TextArea.setText(null);
	}
}
