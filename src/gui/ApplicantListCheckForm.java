package gui;

// 2-2. 식재료 신청자 명단 조회
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
import domain.ApplyCheck;
import domain.DailyMenu;
import domain.DateFormat;
import domain.Place;
import network.Protocol;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;

public class ApplicantListCheckForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;
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
					ApplicantListCheckForm frame = new ApplicantListCheckForm(null, null, null);
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
	public ApplicantListCheckForm(JFrame back, Socket socket, Handler hd) throws ClassNotFoundException, IOException {
		this.hd = hd;
		os = socket.getOutputStream();
		is = socket.getInputStream();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 783, 377);
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
		button.setBounds(673, 10, 82, 29);
		contentPane.add(button);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(254, 30, 297, 42);
		contentPane.add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 104, 743, 119);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "신청번호", "신청자명", "전화번호", "강의장소", "식단명", "신청수량", "입금일", "입금액", "취소일", "환불일", "환불금액" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
					false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(87);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(8).setResizable(false);
		table.getColumnModel().getColumn(9).setResizable(false);
		table.getColumnModel().getColumn(9).setPreferredWidth(83);
		table.getColumnModel().getColumn(10).setResizable(false);
		table.getColumnModel().getColumn(10).setPreferredWidth(103);
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("식재료 신청자 명단 조회");
		lblNewLabel.setBounds(12, 82, 184, 15);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 233, 743, 74);
		contentPane.add(panel);
		panel.setLayout(null);

		JComboBox year = new JComboBox();
		year.setModel(new DefaultComboBoxModel(new String[] { "2018", "2019", "2020", "2021" }));
		year.setBounds(314, 11, 88, 21);
		panel.add(year);

		JLabel label_2 = new JLabel("년");
		label_2.setBounds(406, 14, 42, 15);
		panel.add(label_2);

		JLabel lblid = new JLabel("월");
		lblid.setBounds(515, 14, 42, 15);
		panel.add(lblid);

		JComboBox day = new JComboBox();
		day.setModel(new DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31" }));
		day.setBounds(550, 11, 67, 21);
		panel.add(day);

		JButton button_1 = new JButton("조회");

		button_1.setBounds(664, 10, 67, 23);
		panel.add(button_1);

		JComboBox month = new JComboBox();
		month.setModel(new DefaultComboBoxModel(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		month.setBounds(448, 11, 67, 21);
		panel.add(month);

		JLabel lblNewLabel_1 = new JLabel("일");
		lblNewLabel_1.setBounds(619, 14, 57, 15);
		panel.add(lblNewLabel_1);
		// 조회 버튼 기능 부분
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat date = new DateFormat();
				date.setYear((String) year.getSelectedItem());
				date.setMonth((String) month.getSelectedItem());
				date.setDay((String) day.getSelectedItem());
				try {
					protocol = new Protocol(Protocol.PT_APPLICANTLIST);

					os.write(protocol.getPacket());

					hd.sendData(date);
					LinkedList<ApplyCheck> list = hd.getApplyCheckList();
//
					DefaultTableModel model = new DefaultTableModel();
					model.addColumn(new String("신청번호"));
					model.addColumn(new String("신청자명"));
					model.addColumn(new String("전화번호"));
					model.addColumn(new String("강의장소"));
					model.addColumn(new String("식단명"));
					model.addColumn(new String("신청수량"));
					model.addColumn(new String("입금일"));
					model.addColumn(new String("입금액"));
					model.addColumn(new String("취소일"));
					model.addColumn(new String("환불일"));
					model.addColumn(new String("환불금액"));

//					"신청번호", "신청자명", "전화번호", "강의장소", "식단명", "신청수량", "입금일", "입금액", "취소일", "환불일", "환불금액" 

					for (int i = 0; i < list.size(); i++) {
						String[] s = new String[11];
						String qu = Integer.toString(list.get(i).getQuantity());
						String de = Integer.toString(list.get(i).getDepositPrice());
						String re = Integer.toString(list.get(i).getRefundPrice());
						
						s[0] = list.get(i).getNumber();
						s[1] = list.get(i).getName();
						s[2] = list.get(i).getPhoneNumber();
						s[3] = list.get(i).getPlace();
						s[4] = list.get(i).getFoodName();
						s[5] = qu;
						s[6] = list.get(i).getDepositDate();
						s[7] = de;
						s[8] = list.get(i).getCancelDate();
						s[9] = list.get(i).getRefundDate();
						s[10] = re;
						

						model.addRow(s);
					}

					table = new JTable(model);
					scrollPane.setViewportView(table);
				} catch (IOException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}