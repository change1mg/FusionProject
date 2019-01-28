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
import domain.Cancel;
import domain.DateFormat;
import domain.Food;
import domain.Place;
import domain.Student;
import domain.User;
import network.Protocol;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class CancelForm extends JFrame {

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
					CancelForm frame = new CancelForm(null,null,null,null);
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
	public CancelForm(JFrame back, Socket socket,Handler hd,Student student) throws ClassNotFoundException, IOException{
		this.hd = hd;
		os = socket.getOutputStream();
		is = socket.getInputStream();
		this.socket = socket;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 515, 349);
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
		button.setBounds(409, 10, 82, 29);
		contentPane.add(button);
		
		JLabel label = new JLabel("신청내역");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(193, 31, 97, 42);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("신청내역");
		lblNewLabel.setBounds(12, 82, 75, 15);
		contentPane.add(lblNewLabel);
		
		
		
		
		////
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 102, 485, 165);
		contentPane.add(scrollPane);
		
		protocol = new Protocol(Protocol.PT_CANCELLIST);
		os.write(protocol.getPacket());
		
		// 요청 부분 
		User user = new User();
		user.setName(student.getName());
		user.setNumber(student.getNumber());
		user.setPlace(student.getPlace());
		hd.sendUser(user);
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(new String("식단명"));
		model.addColumn(new String("인분"));
		model.addColumn(new String("실습날짜"));

		LinkedList<Cancel> cList =  hd.getCancelList();
		if (cList.size() != 0) {
			for (int i = 0; i < cList.size(); i++) {
				String[] s = new String[3];
				s[0] = cList.get(i).getName();
				s[1] = cList.get(i).getQuantity();
				s[2] = cList.get(i).getDate();
				model.addRow(s);
			}
		}

		table = new JTable(model);
		scrollPane.setViewportView(table);
	
		
		///
		JButton btnNewButton = new JButton("취소하기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat date33 = new DateFormat();
				String d = table.getValueAt(table.getSelectedRow(), 2).toString();
				date33.setYear(d.substring(0, 4));
				date33.setMonth(d.substring(5, 7));
				date33.setDay(d.substring(8, 10));
				Place place = new Place();

				User user = new User();

				user.setName(student.getName());
				user.setNumber(student.getNumber());
				String name = table.getValueAt(table.getSelectedRow(), 0).toString();
				user.setFoodName(name);
				user.setPlace(student.getPlace());
				protocol = new Protocol(Protocol.PT_CANCEL);

				try {
					os.write(protocol.getPacket());
//신청내역 전송 부분 
					hd.sendData(date33);
					hd.sendUser(user);
				} catch (IOException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(394, 277, 97, 23);
		contentPane.add(btnNewButton);
		
		
		
		
		JButton button_1 = new JButton("새로고침");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
				try {
					CancelForm cf;
					cf = new CancelForm(back,socket,hd,student);
					cf.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		button_1.setBounds(12, 10, 88, 29);
		contentPane.add(button_1);
	}
}
//
//DateFormat date32 = new DateFormat();
//String d = date.getSelectedItem().toString();
//date32.setYear(d.substring(0, 4));
//date32.setMonth(d.substring(5, 7));
//date32.setDay(d.substring(8, 10));
//Place place = new Place();
//place.setPlace(student.getPlace());
//
//User user = new User();
//
//
//user.setName(student.getName());
//user.setNumber(student.getNumber());
//user.setFoodName(name);
//
//protocol = new Protocol(Protocol.PT_ALLPYCATION);
//
//
//	os.write(protocol.getPacket());
////신청내역 전송 부분 
//
//	hd.sendData(date32);//수업
//	hd.sendPlace(place);//강의장
//	hd.sendUser(user);