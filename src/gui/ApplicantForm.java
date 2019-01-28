package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Handler;
import domain.Student;

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

public class ApplicantForm extends JFrame {

	private JPanel contentPane;
	private JFrame owner;
	private MenuCheckAndApplicantForm menuCheckAndApplicant;
	private CancelForm cancel;
//	private RecipeForm recipe;
//	private ScheduleForm schedule;
//	private DailyMenuForm dailyMenu;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicantForm frame = new ApplicantForm(null,null,null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public ApplicantForm(JFrame back, Socket socket,Handler hd,Student student) throws ClassNotFoundException, IOException {
		owner = this;
		menuCheckAndApplicant = new MenuCheckAndApplicantForm(owner,socket,hd,student);
		cancel = new CancelForm(owner,socket,hd,student);

//		recipe = new RecipeForm(owner, null);
//		schedule = new ScheduleForm(owner,null);
//		dailyMenu = new DailyMenuForm(owner,null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 167);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(100, 6, 266, 42);
		contentPane.add(label);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(6, 97, 453, 42);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		
		JButton button_1 = new JButton("실습메뉴 일자별 조회 및 신청");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCheckAndApplicant.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button_1);

		JButton button_2 = new JButton("식재료 취소");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button_2);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back.setVisible(true);
				setVisible(false);
			}
		});
		btnBack.setBounds(385, 6, 75, 29);
		contentPane.add(btnBack);
	}
}