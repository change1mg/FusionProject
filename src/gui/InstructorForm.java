package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Handler;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import java.net.Socket;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class InstructorForm extends JFrame {

	private JPanel contentPane;
	private JFrame owner;
	private DailyMenuCheckForm dailyMenuCheck;
	private ApplicantListCheckForm applicantListCheck;
	private NeedCheckForm needCheck;
	private DepositListRegisterForm depositListRegister;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstructorForm frame = new InstructorForm(null,null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public InstructorForm(JFrame back, Socket socket,Handler hd) throws ClassNotFoundException, IOException {
		owner = this;
		dailyMenuCheck = new DailyMenuCheckForm(owner,socket,hd);
		applicantListCheck = new ApplicantListCheckForm(owner,socket,hd);
		needCheck = new NeedCheckForm(owner,socket,hd);
		depositListRegister= new DepositListRegisterForm(owner,socket,hd);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 205);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(100, 6, 299, 42);
		contentPane.add(label);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(6, 97, 503, 59);
		contentPane.add(panel);

		JButton button = new JButton("실습메뉴 (일자별) 조회");
		button.setBounds(23, 5, 212, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dailyMenuCheck.setVisible(true);
				setVisible(false);
			}

		});
		panel.setLayout(null);
		panel.add(button);

		JButton button_1 = new JButton(
				"식재료 신청자 명단(일자별) 조회");
		button_1.setBounds(247, 5, 230, 23);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicantListCheck.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button_1);

		JButton button_2 = new JButton(
				"식재료 소요량 목록(일자별, 기간별) 조회");
		button_2.setBounds(22, 26, 255, 23);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				needCheck.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button_2);

		JButton button_3 = new JButton("계좌이체 내역 등재");
		button_3.setBounds(302, 26, 150, 23);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				depositListRegister.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button_3);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back.setVisible(true);
				setVisible(false);
			}
		});
		btnBack.setBounds(434, 19, 75, 29);
		contentPane.add(btnBack);
	}
}