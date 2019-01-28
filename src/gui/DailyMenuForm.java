package gui;

// 1-3. 강의일자별 실습메뉴 등록
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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
import domain.DateFormat;
import domain.Food;
import domain.Menu;
import domain.Place;
import network.Protocol;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JFormattedTextField;

public class DailyMenuForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;
	private JFrame owner;

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
					DailyMenuForm frame = new DailyMenuForm(null, null, null);
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
	public DailyMenuForm(JFrame back, Socket socket, Handler hd) throws ClassNotFoundException, IOException {
		os = socket.getOutputStream();
		is = socket.getInputStream();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 377);
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

		// ************식단 로드************

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 104, 495, 119);
		contentPane.add(scrollPane);

		protocol = new Protocol(Protocol.PT_DAYMENULIST);
		os.write(protocol.getPacket());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(new String("강의장소"));
		model.addColumn(new String("수업일"));
		model.addColumn(new String("식단ID"));

		LinkedList<Menu> list = hd.getMenusList();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				String[] s = new String[3];
				s[0] = list.get(i).getPlace();
				s[1] = list.get(i).getDate();
				s[2] = list.get(i).getFoodID();
				model.addRow(s);
			}
		}

		table = new JTable(model);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(83);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(103);
		table.getColumnModel().getColumn(2).setResizable(false);
		scrollPane.setViewportView(table);

		// ************식단 로드 End************
		JLabel lblNewLabel = new JLabel("강의일자별 실습메뉴 등록");
		lblNewLabel.setBounds(12, 82, 158, 15);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 233, 495, 74);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label_1 = new JLabel("강의장소");
		label_1.setBounds(12, 9, 57, 15);
		panel.add(label_1);
//콤보박스채우는부
//		String[] cfood = new String[list.size()];
//		for (int i = 0; i < list.size(); i++) {		
//			cfood[i]=list.get(i).getNumber();
//		}
//		
//		String[] cin = new String[list.size()];
//		for (int i = 0; i < list.size(); i++) {		
//			cin[i]=list.get(i).getNumber();
//		}
//		
//		JComboBox comFood = new JComboBox();
//		comFood.setModel(new DefaultComboBoxModel(cfood));
//		
//		comFood.setBounds(52, 6, 75, 27);
//		panel_5.add(comFood);
//		
//		JComboBox comig = new JComboBox();
//		comig.setModel(new DefaultComboBoxModel(cin));
//		
//		comig.setBounds(176, 6, 75, 27);
//		panel_5.add(comig);

		protocol = new Protocol(Protocol.PT_PLACELIST);// 프로토콜 바꿔야
		os.write(protocol.getPacket());

		LinkedList<Place> list1 = hd.getPlaceList();// 컬렉션 바꿔야함
		String[] br = new String[list1.size()];
		for (int i = 0; i < list1.size(); i++) {
			br[i] = list1.get(i).getPlace();
		}

		protocol = new Protocol(Protocol.PT_FOODLIST);
		os.write(protocol.getPacket());

		LinkedList<Food> list2 = hd.getFoodList();
		String[] food = new String[list2.size()];
		for (int i = 0; i < list2.size(); i++) {
			food[i] = list2.get(i).getNumber();
		}

		JComboBox foodID = new JComboBox(new DefaultComboBoxModel(food));
		foodID.setBounds(418, 7, 77, 21);
		panel.add(foodID);

		JComboBox place = new JComboBox(new DefaultComboBoxModel(br));
		place.setBounds(54, 7, 77, 21);
		panel.add(place);

		JLabel label_2 = new JLabel("수업일");
		label_2.setBounds(131, 9, 42, 15);
		panel.add(label_2);

		JComboBox year = new JComboBox();
		year.setModel(new DefaultComboBoxModel(new String[] {"2018", "2019", "2020", "2021", "2022"}));
		year.setBounds(164, 4, 99, 27);
		panel.add(year);
		
		JComboBox month = new JComboBox();
		month.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		month.setBounds(257, 4, 67, 27);
		panel.add(month);
		
		JComboBox day = new JComboBox();
		day.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		day.setBounds(320, 4, 67, 27);
		panel.add(day);

		JLabel lblid = new JLabel("식단ID");
		lblid.setBounds(386, 9, 42, 15);
		panel.add(lblid);

		JButton button_1 = new JButton("등록");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat df = new DateFormat();
				df.setYear(year.getSelectedItem().toString());
				df.setMonth(month.getSelectedItem().toString());
				df.setDay(day.getSelectedItem().toString());
				String[] s = new String[3];
				s[0] = place.getSelectedItem().toString().trim();
				s[1] = df.toString();
				s[2] = foodID.getSelectedItem().toString().trim();
				model.addRow(s);
				Menu menu = new Menu();
				menu.setPlace(s[0]);
				menu.setDate(s[1]);
				menu.setFoodID(s[2]);
				protocol = new Protocol(Protocol.PT_ADDDAYMENU);

				try {
					os.write(protocol.getPacket());
					hd.sendMenu(menu);
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		button_1.setBounds(179, 36, 67, 23);
		panel.add(button_1);
		
	
		
		JButton button_2 = new JButton("새로고침");
		
		button_2.setBounds(6, 6, 88, 29);
		contentPane.add(button_2);
		
		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					DailyMenuForm dm = new DailyMenuForm(back,socket,hd);
					dm.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}