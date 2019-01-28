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
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JFormattedTextField;

public class StudentMenu extends JFrame {

	private JPanel contentPane;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentMenu frame = new StudentMenu();
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
	public StudentMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 415, 333);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("Back");
		button.setBounds(317, 10, 82, 29);
		contentPane.add(button);
		
		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(48, 31, 297, 42);
		contentPane.add(label);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 107, 371, 180);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton button_1 = new JButton("실습메뉴 일자별 조회 및 신청");
		button_1.setBounds(54, 50, 221, 23);
		panel.add(button_1);
		
		JButton button_2 = new JButton("실습메뉴 신청 취소");
		button_2.setBounds(54, 95, 221, 23);
		panel.add(button_2);
	}
}