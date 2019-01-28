package gui;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.table.DefaultTableModel;

import controller.Handler;
import domain.Food;
import domain.Place;
import domain.Student;
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
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class ApplicantLoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField;
	private JTextField textField_1;
	private JFrame owner;
	private ApplicantForm applicant;
	private Socket socket;
	private Protocol protocol;
	private Handler hd;
	private OutputStream os;
	private InputStream is;
	private Student student;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicantLoginForm frame = new ApplicantLoginForm(null, null, null);
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
	public ApplicantLoginForm(JFrame back, Socket socket, Handler hd) throws ClassNotFoundException, IOException {
		this.hd = hd;
		os = socket.getOutputStream();
		is = socket.getInputStream();
		this.socket = socket;
		owner = this;
		Student student = new Student();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 415, 333);
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
		button.setBounds(317, 10, 82, 29);
		contentPane.add(button);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(48, 31, 297, 42);
		contentPane.add(label);

		JLabel lblNewLabel = new JLabel("신청자 정보입력");
		lblNewLabel.setBounds(23, 85, 153, 15);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 107, 371, 180);
		contentPane.add(panel);
		panel.setLayout(null);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(158, 59, 153, 21);
		panel.add(textField_1);

		JButton btnNewButton = new JButton("입력");

		btnNewButton.setBounds(236, 134, 75, 23);
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setBounds(81, 31, 57, 15);
		panel.add(lblNewLabel_1);

		JLabel label_1 = new JLabel("전화번호");
		label_1.setBounds(81, 62, 57, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel("수강장소");
		label_2.setBounds(81, 96, 57, 15);
		panel.add(label_2);

		textField = new JTextField();
		textField.setBounds(158, 28, 153, 21);
		panel.add(textField);
		textField.setColumns(10);

		protocol = new Protocol(Protocol.PT_PLACELIST);// 프로토콜 바꿔야
		os.write(protocol.getPacket());

		LinkedList<Place> list1 = hd.getPlaceList();// 컬렉션 바꿔야함
		String[] placeList = new String[list1.size()];
		for (int i = 0; i < list1.size(); i++) {
			placeList[i] = list1.get(i).getPlace();
		}

		JComboBox place = new JComboBox(placeList);
		place.setBounds(157, 93, 154, 18);
		panel.add(place);

		JButton button_1 = new JButton("새로고침");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					ApplicantLoginForm al = new ApplicantLoginForm(back, socket, hd);
					al.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setBounds(0, 10, 82, 29);
		contentPane.add(button_1);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				student.setName(textField.getText());
				student.setNumber(textField_1.getText());
				student.setPlace((String) place.getSelectedItem());
				System.out.println(student.getPlace());
				try {
					applicant = new ApplicantForm(owner, socket, hd, student);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				applicant.setVisible(true);
				setVisible(false);

			}

		});

	}

}