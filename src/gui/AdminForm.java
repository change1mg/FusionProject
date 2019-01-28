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

public class AdminForm extends JFrame {

	private JPanel contentPane;
	private JFrame owner;
	private RecipeForm recipe;
	private ScheduleForm schedule;
	private DailyMenuForm menu;
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
	public AdminForm(JFrame back, Socket socket,Handler hd) throws ClassNotFoundException, IOException {
		owner = this;
		schedule = new ScheduleForm(owner,socket,hd);
		recipe = new RecipeForm(owner, socket,hd);
		
		menu = new DailyMenuForm(owner,socket,hd);
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

		JButton button = new JButton("Recipe Load");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					recipe.setVisible(true);
					setVisible(false);
			}

		});
		panel.add(button);

		JButton button_1 = new JButton("강의 일정표 Load");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schedule.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(button_1);

		JButton button_2 = new JButton("강의 일자별 실습메뉴 등록");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setVisible(true);
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
