package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.table.DefaultTableModel;

import controller.Handler;
import domain.Account;
import domain.Price;
import domain.Recipe;
import network.Protocol;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class DepositListRegisterForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;

	private Protocol protocol;
	private Handler hd;
	private OutputStream os;
	private InputStream is;
	private JTextField price;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepositListRegisterForm frame = new DepositListRegisterForm(null, null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DepositListRegisterForm(JFrame back, Socket socket, Handler hd) throws ClassNotFoundException, IOException {

		this.hd = hd;
		os = socket.getOutputStream();
		is = socket.getInputStream();

		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String now = format.format(d);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 535, 388);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back.setVisible(true);
				setVisible(false);
			}
		});
		button.setBounds(447, 10, 82, 29);
		contentPane.add(button);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(116, 30, 297, 42);
		contentPane.add(label);

		// 리스트 로드

		// ************식단별 소요량 로드************

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 104, 495, 119);
		contentPane.add(scrollPane);

		protocol = new Protocol(Protocol.PT_ACCOUNTLIST);
		os.write(protocol.getPacket());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(new String("신청자번호"));
		model.addColumn(new String("식재료가격"));
		model.addColumn(new String("입금액"));
		model.addColumn(new String("환불금액"));

		LinkedList<Account> list = hd.getAccountList();
		String[] numCombo = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			String[] s = new String[4];
			s[0] = list.get(i).getNumber();
			s[1] = list.get(i).getIngredientPrice();
			s[2] = list.get(i).getPrice();
			s[3] = list.get(i).getRefund();
			numCombo[i] = list.get(i).getNumber();
			model.addRow(s);

		}

		table = new JTable(model);
		scrollPane.setViewportView(table);

		// ************식단별 소요량 로드 End************

//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(12, 104, 495, 119);
//		contentPane.add(scrollPane);
//
//		
//		
//		
//		table = new JTable();
//		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "신청자번호", "식재료가격", "입금액", "환불금액" }) {
//			boolean[] columnEditables = new boolean[] { false, false, false, false };
//
//			public boolean isCellEditable(int row, int column) {
//				return columnEditables[column];
//			}
//		});
//		table.getColumnModel().getColumn(0).setResizable(false);
//		table.getColumnModel().getColumn(0).setPreferredWidth(103);
//		table.getColumnModel().getColumn(1).setResizable(false);
//		table.getColumnModel().getColumn(1).setPreferredWidth(103);
//		table.getColumnModel().getColumn(2).setResizable(false);
//		table.getColumnModel().getColumn(2).setPreferredWidth(103);
//		table.getColumnModel().getColumn(3).setResizable(false);
//		table.getColumnModel().getColumn(3).setPreferredWidth(103);
//		scrollPane.setViewportView(table);

		// 리스트
		JLabel lblNewLabel = new JLabel("계좌이체내역");
		lblNewLabel.setBounds(12, 82, 167, 15);
		contentPane.add(lblNewLabel);

		JButton button_1 = new JButton("새로고침");

		button_1.setBounds(6, 10, 88, 29);
		contentPane.add(button_1);

		JComboBox number = new JComboBox(numCombo);
		number.setBounds(125, 249, 131, 27);
		contentPane.add(number);

		price = new JTextField();
		price.setBounds(314, 248, 130, 26);
		contentPane.add(price);
		price.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("금액 ");
		lblNewLabel_1.setBounds(281, 250, 29, 23);
		contentPane.add(lblNewLabel_1);

		JLabel label_1 = new JLabel("신청번호");
		label_1.setBounds(70, 253, 54, 16);
		contentPane.add(label_1);

		JButton button_2 = new JButton("입금등록");

		button_2.setBounds(116, 303, 117, 29);
		contentPane.add(button_2);

		JButton button_3 = new JButton("환불등록");
	
		button_3.setBounds(305, 303, 117, 29);
		contentPane.add(button_3);

		// 입금 내역 등재
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Price pr = new Price();
				pr.setNumber(number.getSelectedItem().toString());
				pr.setDate(now);
				pr.setPrice(price.getText().trim());

				protocol = new Protocol(Protocol.PT_DEPOSIT);
				try {
					os.write(protocol.getPacket());

					hd.sendPrice(pr);
				} catch (IOException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				price.setText("");
				setVisible(false);
				DepositListRegisterForm drf;
				try {
					drf = new DepositListRegisterForm(back, socket, hd);
					drf.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Price pr = new Price();
				pr.setNumber(number.getSelectedItem().toString());
				pr.setDate(now);
				pr.setPrice(price.getText().trim());

				protocol = new Protocol(Protocol.PT_REFUND);
				try {
					os.write(protocol.getPacket());

					hd.sendPrice(pr);
				} catch (IOException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				price.setText("");
				setVisible(false);
				DepositListRegisterForm drf;
				try {
					drf = new DepositListRegisterForm(back, socket, hd);
					drf.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				DepositListRegisterForm drf;
				try {
					drf = new DepositListRegisterForm(back, socket, hd);
					drf.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}
}
