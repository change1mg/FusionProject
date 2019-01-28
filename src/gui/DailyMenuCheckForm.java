package gui;

// 2-1. 실습메뉴 (일자별) 조회
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.table.DefaultTableModel;

import controller.Handler;
import domain.DailyMenu;
import domain.DateFormat;
import domain.Food;
import network.Protocol;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;

public class DailyMenuCheckForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;

	private Socket socket;
	private Protocol protocol;
	private Handler hd;
	private OutputStream os;
	private InputStream is;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DailyMenuCheckForm frame = new DailyMenuCheckForm(null, null, null);
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
	public DailyMenuCheckForm(JFrame back, Socket socket, Handler hd) throws ClassNotFoundException, IOException {
		this.hd = hd;
		os = socket.getOutputStream();
		is = socket.getInputStream();
		this.socket = socket;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 377);
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
		button.setBounds(378, 6, 82, 29);
		contentPane.add(button);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(80, 30, 297, 42);
		contentPane.add(label);

		// ******************

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 104, 428, 119);
		contentPane.add(scrollPane);

		table = new JTable();
//		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "강의장소", "식단명" }) {
//			boolean[] columnEditables = new boolean[] { false, false };
//
//			public boolean isCellEditable(int row, int column) {
//				return columnEditables[column];
//			}
//		});
//		table.getColumnModel().getColumn(0).setResizable(false);
//		table.getColumnModel().getColumn(0).setPreferredWidth(83);
//		table.getColumnModel().getColumn(1).setResizable(false);
//		table.getColumnModel().getColumn(1).setPreferredWidth(103);
		scrollPane.setViewportView(table);

		// ****************
		JLabel lblNewLabel = new JLabel("강의 일자별 실습메뉴 조회");
		lblNewLabel.setBounds(12, 82, 167, 15);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 233, 428, 74);
		contentPane.add(panel);
		panel.setLayout(null);

		JComboBox year = new JComboBox();
		year.setModel(new DefaultComboBoxModel(new String[] { "2018", "2019", "2020", "2021" }));
		year.setBounds(12, 6, 83, 21);
		panel.add(year);

		JLabel label_2 = new JLabel("년");
		label_2.setBounds(107, 8, 19, 15);
		panel.add(label_2);

		JLabel lblid = new JLabel("월");
		lblid.setBounds(200, 9, 42, 15);
		panel.add(lblid);

		JComboBox day = new JComboBox();
		day.setModel(new DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31" }));
		day.setBounds(225, 6, 67, 21);
		panel.add(day);

		JButton button_1 = new JButton("조회");

		button_1.setBounds(349, 5, 67, 23);
		panel.add(button_1);

		JComboBox month = new JComboBox();
		month.setModel(new DefaultComboBoxModel(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		month.setBounds(121, 6, 67, 21);
		panel.add(month);

		JLabel lblNewLabel_1 = new JLabel("일");
		lblNewLabel_1.setBounds(304, 9, 57, 15);
		panel.add(lblNewLabel_1);

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
//					protocol = new Protocol(Protocol.PT_FOODLIST);
//					os.write(protocol.getPacket());
//					DefaultTableModel model = new DefaultTableModel();
//					model.addColumn(new String("식단ID"));
//					model.addColumn(new String("식단명"));
//					model.addColumn(new String("실습비"));
//
//					LinkedList<Food> list = hd.getFoodList();
//					if (list.size() != 0) {
//						for (int i = 0; i < list.size(); i++) {
//							String[] s = new String[3];
//							s[0] = list.get(i).getNumber();
//							s[1] = list.get(i).getName();
//							String n = Integer.toString(list.get(i).getPrice());
//							s[2] = n;
//							model.addRow(s);
//						}
//					}
//
//					table = new JTable(model);
//					scrollPane.setViewportView(table);
//					
					
					
					protocol = new Protocol(Protocol.PT_DailyMenuLIST);

					os.write(protocol.getPacket());

					DefaultTableModel model = new DefaultTableModel();
					
					model.addColumn(new String("강의장소"));
					model.addColumn(new String("식단명"));
					DateFormat date = new DateFormat();
					date.setYear((String) year.getSelectedItem());
					date.setMonth((String) month.getSelectedItem());
					date.setDay((String) day.getSelectedItem());

					hd.sendData(date);

					LinkedList<DailyMenu> list = hd.getDailyMenuList();
					for (int i = 0; i < list.size(); i++) {
						
						String[] s = new String[2];
						s[0] = list.get(i).getPlace();
						s[1] = list.get(i).getName();
						model.addRow(s);
					}

					table = new JTable(model);
					scrollPane.setViewportView(table);
					
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}
}