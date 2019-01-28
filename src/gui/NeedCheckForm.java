package gui;

// 2-3. 식재료 소요량 목록
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
import domain.NeedCheck;
import network.Protocol;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;

public class NeedCheckForm extends JFrame {

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
					NeedCheckForm frame = new NeedCheckForm(null, null, null);
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
	public NeedCheckForm(JFrame back, Socket socket, Handler hd) throws ClassNotFoundException, IOException {

		this.hd = hd;
		os = socket.getOutputStream();
		is = socket.getInputStream();
		this.socket = socket;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 353);
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
		button.setBounds(431, 10, 82, 29);
		contentPane.add(button);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(113, 31, 297, 42);
		contentPane.add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 104, 501, 119);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "수업일", "강의장소", "식재료명", "소요량", "단위" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

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

		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("식재료 소요량 목록 조회");
		lblNewLabel.setBounds(12, 82, 184, 15);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 233, 501, 74);
		contentPane.add(panel);
		panel.setLayout(null);

		JComboBox toYear = new JComboBox();
		toYear.setModel(new DefaultComboBoxModel(new String[] { "2018", "2019", "2020", "2021" }));
		toYear.setBounds(74, 42, 88, 21);
		panel.add(toYear);

		JLabel label_2 = new JLabel("년");
		label_2.setBounds(166, 45, 42, 15);
		panel.add(label_2);

		JLabel lblid = new JLabel("월");
		lblid.setBounds(275, 45, 42, 15);
		panel.add(lblid);

		JComboBox toDay = new JComboBox();
		toDay.setModel(new DefaultComboBoxModel(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
						"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
		toDay.setBounds(300, 42, 67, 21);
		panel.add(toDay);

		JButton button_1 = new JButton("조회");

		button_1.setBounds(419, 41, 67, 23);
		panel.add(button_1);

		JComboBox toMonth = new JComboBox();
		toMonth.setModel(new DefaultComboBoxModel(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		toMonth.setBounds(206, 42, 67, 21);
		panel.add(toMonth);

		JLabel lblNewLabel_1 = new JLabel("일");
		lblNewLabel_1.setBounds(379, 45, 57, 15);
		panel.add(lblNewLabel_1);

		JComboBox fromYear = new JComboBox();
		fromYear.setModel(new DefaultComboBoxModel(new String[] { "2018", "2019", "2020", "2021" }));
		fromYear.setBounds(12, 11, 88, 21);
		panel.add(fromYear);

		JLabel label_1 = new JLabel("년");
		label_1.setBounds(104, 14, 42, 15);
		panel.add(label_1);

		JComboBox fromMonth = new JComboBox();
		fromMonth.setModel(new DefaultComboBoxModel(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		fromMonth.setBounds(141, 11, 67, 21);
		panel.add(fromMonth);

		JLabel label_3 = new JLabel("월");
		label_3.setBounds(213, 14, 42, 15);
		panel.add(label_3);

		JComboBox fromDay = new JComboBox();
		fromDay.setModel(new DefaultComboBoxModel(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
						"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
		fromDay.setBounds(250, 11, 67, 21);
		panel.add(fromDay);

		JLabel label_4 = new JLabel("일");
		label_4.setBounds(317, 14, 57, 15);
		panel.add(label_4);

		JLabel lblNewLabel_2 = new JLabel("~");
		lblNewLabel_2.setBounds(38, 45, 35, 15);
		panel.add(lblNewLabel_2);

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				protocol = new Protocol(Protocol.PT_NEEDCHECKLIST);

				try {
					os.write(protocol.getPacket());

					DefaultTableModel model = new DefaultTableModel();

					model.addColumn(new String("수업일"));
					model.addColumn(new String("강의장소"));
					model.addColumn(new String("식재료명"));
					model.addColumn(new String("소요량"));
					model.addColumn(new String("단위"));

					// "수업일", "강의장소", "식재료명", "소요량", "단위"
					DateFormat date = new DateFormat();
					date.setYear((String) fromYear.getSelectedItem());
					date.setMonth((String) fromMonth.getSelectedItem());
					date.setDay((String) fromDay.getSelectedItem());

					DateFormat date1 = new DateFormat();
					date1.setYear((String) toYear.getSelectedItem());
					date1.setMonth((String) toMonth.getSelectedItem());
					date1.setDay((String) toDay.getSelectedItem());

					hd.sendData(date);
					hd.sendData(date1);
//
					LinkedList<NeedCheck> list = hd.getNeedCheckList();
				for (int i = 0; i < list.size(); i++) {
					
					String[] s = new String[5];
					s[0] = list.get(i).getDate();
					s[1] = list.get(i).getPlace();
					s[2] = list.get(i).getIngredientName();
					String n = Integer.toString(list.get(i).getNeed());
					s[3] = n;
					s[4] = list.get(i).getUnit();
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