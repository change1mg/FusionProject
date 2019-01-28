package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import controller.Handler;
import domain.DailyMenu;
import domain.DateFormat;
import domain.Food;
import domain.Place;
import domain.Student;
import domain.User;
import network.Protocol;

public class MenuCheckAndApplicantForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;

	private Protocol protocol;
	private Handler hd;
	private OutputStream os;
	private InputStream is;
	private String name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuCheckAndApplicantForm frame = new MenuCheckAndApplicantForm(null, null, null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuCheckAndApplicantForm(JFrame back, Socket socket, Handler hd, Student student)
			throws ClassNotFoundException, IOException {

		this.hd = hd;
		os = socket.getOutputStream();
		is = socket.getInputStream();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 473, 419);

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
		button.setBounds(385, 6, 82, 29);
		contentPane.add(button);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(83, 30, 297, 42);
		contentPane.add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 139, 432, 119);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("실습메뉴 일자별 조회 및 신청");
		lblNewLabel.setBounds(12, 82, 184, 15);
		contentPane.add(lblNewLabel);

		JButton button_1 = new JButton("조회");
		button_1.setBounds(328, 109, 117, 29);
		contentPane.add(button_1);

		protocol = new Protocol(Protocol.PT_PLACE);
		os.write(protocol.getPacket());
		System.out.println(student.getPlace());
		hd.getDate(student.getPlace());
		// 이까지 잘됐음
		LinkedList<String> list = hd.getComboDate();
		System.out.println(list.size());
		String[] st = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			st[i] = list.get(i);
			System.out.println(st[i]);
		}
		JComboBox date = new JComboBox();
		date.setModel(new DefaultComboBoxModel(st));
		date.setBounds(32, 109, 284, 29);
		contentPane.add(date);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 309, 432, 65);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel foodName = new JLabel("식단명");
		foodName.setBounds(42, 22, 103, 16);
		panel.add(foodName);

		JComboBox quantity = new JComboBox();
		quantity.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		quantity.setBounds(166, 18, 68, 27);
		panel.add(quantity);

		JButton btnNewButton = new JButton("신청");

		btnNewButton.setBounds(255, 17, 86, 29);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("취소");

		btnNewButton_1.setBounds(340, 17, 86, 29);
		panel.add(btnNewButton_1);

		JButton button_2 = new JButton("▼");

		button_2.setBounds(190, 270, 75, 29);
		contentPane.add(button_2);

		// 콤보박스 채우는 부분 끝

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("조회버튼 클");
				String d = date.getSelectedItem().toString();
				d.substring(0, 4);
				DateFormat date = new DateFormat();
				date.setYear(d.substring(0, 4));
				date.setMonth(d.substring(5, 7));
				date.setDay(d.substring(8, 10));
				Place place = new Place();
				place.setPlace(student.getPlace());
				try {
					protocol = new Protocol(Protocol.PT_DailyPriceLIST);

					os.write(protocol.getPacket());

					hd.sendData(date);
					hd.sendPlace(place);
					LinkedList<DailyMenu> list = hd.getDailyMenuList();

					DefaultTableModel model = new DefaultTableModel();
					model.addColumn(new String("식단명"));
					model.addColumn(new String("실습비"));

					for (int i = 0; i < list.size(); i++) {
						String[] s = new String[2];
						s[0] = list.get(i).getName();
						String n = Integer.toString(list.get(i).getPrice());
						s[1] = n;
						model.addRow(s);
						System.out.println(list.get(i).getName() + n);
					}

					table = new JTable(model);
					scrollPane.setViewportView(table);
				} catch (IOException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// 신청 버튼
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat date32 = new DateFormat();
				String d = date.getSelectedItem().toString();
				date32.setYear(d.substring(0, 4));
				date32.setMonth(d.substring(5, 7));
				date32.setDay(d.substring(8, 10));
				Place place = new Place();
				place.setPlace(student.getPlace());

				User user = new User();

				user.setID(getAPPID());
				user.setName(student.getName());
				user.setNumber(student.getNumber());
				user.setFoodName(name);
				user.setQuantity((String) quantity.getSelectedItem());

				protocol = new Protocol(Protocol.PT_ALLPYCATION);

				try {
					os.write(protocol.getPacket());
//신청내역 전송 부분 

					hd.sendData(date32);
					hd.sendPlace(place);
					hd.sendUser(user);
				} catch (IOException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println(
						name + student.getName() + student.getNumber() + student.getPlace() + quantity.getSelectedItem());

			}
		});

//		button_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				DateFormat date = new DateFormat();
//				date.setYear((String) year.getSelectedItem());
//				date.setMonth((String) month.getSelectedItem());
//				date.setDay((String) day.getSelectedItem());
//				Place place = new Place();
//				place.setPlace(student.getPlace());
//
//				User user = new User();
//
//				user.setID(getAPPID());
//				user.setName(student.getName());
//				user.setNumber(student.getNumber());
//				user.setFoodName(name);
//				user.setQuantity((String) combo.getSelectedItem());
//
//				protocol = new Protocol(Protocol.PT_ALLPYCATION);
//
//				try {
//					os.write(protocol.getPacket());
////신청내역 전송 부분 
//
//					hd.sendData(date);
//					hd.sendPlace(place);
//					hd.sendUser(user);
//				} catch (IOException | ClassNotFoundException | SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				System.out.println(
//						name + student.getName() + student.getNumber() + student.getPlace() + combo.getSelectedItem());
//			}
//		});
//

		// 추가버
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				name = table.getValueAt(table.getSelectedRow(), 0).toString();
				foodName.setText(name);
			}
		});
		//삭제 
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				foodName.setText("");
			}
		});

	}

	String getAPPID() {
		Date d = new Date();
		SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss.SSS");

		String nowTime = now.format(d);
		String nowTime0 = nowTime.substring(0, 4);
		String nowTime1 = nowTime.substring(18, 19);
		String nowTime2 = nowTime.substring(20);
		String result = nowTime0 + nowTime1 + nowTime2;
		return result.trim();
	}
}
