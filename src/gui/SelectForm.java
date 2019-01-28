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
import java.net.Socket;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class SelectForm extends JFrame {

	private JPanel contentPane;
	private JFrame owner;
	private AdminForm admin;
	private InstructorForm instructor;
	private ApplicantLoginForm applicant;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectForm frame = new SelectForm(null);
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
	public SelectForm(Socket socket) throws ClassNotFoundException, IOException {
		owner = this;
		Handler hd = new Handler(socket);
		admin = new AdminForm(owner, socket,hd);
		instructor = new InstructorForm(owner,socket,hd);
		applicant = new ApplicantLoginForm(owner,socket,hd);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 167);
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
		panel.setBounds(6, 97, 438, 42);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton button = new JButton("관리자");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button);

		JButton button_1 = new JButton("강사");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructor.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button_1);

		JButton button_2 = new JButton("수강생");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applicant.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button_2);
	}
}
