package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import controller.Handler;
import domain.Food;
import domain.Ingredients;
import domain.Instructor;
import domain.Place;
import domain.Recipe;
import domain.Schedule;
import network.Protocol;

import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;

public class ScheduleForm extends JFrame {

	private JPanel contentPane;
	private JTextField insID;
	private JTextField insName;
	private JTextField insAccout;
	private JTextField placeF;
	private JTextField from;
	private JTextField to;
	private JTable table;
	private JTable table_1;
	private JList jlist;

	private Protocol protocol;
	private Handler hd;
	private OutputStream os;
	private InputStream is;
	private Socket socket;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScheduleForm frame = new ScheduleForm(null, null, null);
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
	public ScheduleForm(JFrame back, Socket socket, Handler hd) throws IOException, ClassNotFoundException {
		menu();
		this.hd = hd;
		this.socket = socket;
		os = socket.getOutputStream();
		is = socket.getInputStream();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 394);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(6, 65, 496, 279);
		contentPane.add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("강사 관리", null, panel, null);
		panel.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(6, 164, 463, 63);
		panel.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblid = new JLabel("강사ID");
		lblid.setBounds(34, 11, 36, 16);
		panel_3.add(lblid);

		insID = new JTextField();
		insID.setBounds(75, 6, 70, 26);
		insID.setColumns(5);
		panel_3.add(insID);

		JLabel label_2 = new JLabel("강사명");
		label_2.setBounds(150, 11, 33, 16);
		panel_3.add(label_2);

		insName = new JTextField();
		insName.setBounds(188, 6, 94, 26);
		insName.setColumns(7);
		panel_3.add(insName);

		JLabel label_3 = new JLabel("계좌번호");
		label_3.setBounds(287, 11, 44, 16);
		panel_3.add(label_3);

		insAccout = new JTextField();
		insAccout.setBounds(336, 6, 94, 26);
		insAccout.setColumns(7);
		panel_3.add(insAccout);

		JButton button_1 = new JButton("등록");

		button_1.setBounds(194, 37, 75, 29);
		panel_3.add(button_1);

		// ************강사 로드************

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 463, 146);
		panel.add(scrollPane);

		protocol = new Protocol(Protocol.PT_INSTRUCTORLIST);// 프로토콜 바꿔야
		os.write(protocol.getPacket());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(new String("강사ID"));
		model.addColumn(new String("강사명"));
		model.addColumn(new String("계좌"));

		LinkedList<Instructor> list = hd.getInstructorList();// 컬렉션 바꿔야함
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				String[] s = new String[3];
				s[0] = list.get(i).getNumber();
				s[1] = list.get(i).getName();
				s[2] = list.get(i).getAccount();
				model.addRow(s);
			}
		}

		table = new JTable(model);
		scrollPane.setViewportView(table);

		// ************강사 로드 End************

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("강의 장소", null, panel_1, null);
		panel_1.setLayout(null);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(6, 164, 463, 63);
		panel_1.add(panel_4);
		panel_4.setLayout(null);

		JLabel label_4 = new JLabel("강의장소");
		label_4.setBounds(10, 10, 50, 16);
		panel_4.add(label_4);

		placeF = new JTextField();
		placeF.setBounds(58, 5, 75, 26);
		placeF.setColumns(5);
		panel_4.add(placeF);

		JButton button_2 = new JButton("등록");

		button_2.setBounds(165, 36, 75, 29);
		panel_4.add(button_2);

		// ********************지점 리스트 가져오는부분

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 6, 463, 149);
		panel_1.add(scrollPane_1);

		protocol = new Protocol(Protocol.PT_PLACELIST);// 프로토콜 바꿔야
		os.write(protocol.getPacket());
		DefaultListModel model1 = new DefaultListModel();

		LinkedList<Place> list1 = hd.getPlaceList();// 컬렉션 바꿔야함
		if (list1.size() != 0) {
			for (int i = 0; i < list1.size(); i++) {
				String br = list1.get(i).getPlace();
				model1.addElement(br);
			}
		}

		jlist = new JList(model1);
		scrollPane_1.setViewportView(jlist);

		JLabel label_5 = new JLabel("강의장소");
		scrollPane_1.setColumnHeaderView(label_5);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("강의일정", null, panel_2, null);
		panel_2.setLayout(null);
//@@@@@@@@@@@@@@@@@@@강의일정리스트 가져오는 부분 

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 6, 463, 149);
		panel_2.add(scrollPane_2);

		protocol = new Protocol(Protocol.PT_SCHEDULELIST);// 프로토콜 바꿔야
		os.write(protocol.getPacket());
		DefaultTableModel model2 = new DefaultTableModel();
		model2.addColumn(new String("강사ID"));
		model2.addColumn(new String("강의장소"));
		model2.addColumn(new String("년도"));
		model2.addColumn(new String("요일"));
		model2.addColumn(new String("From"));
		model2.addColumn(new String("To"));

		LinkedList<Schedule> scheList = hd.getScheduleList();// 컬렉션 바꿔야함
		if (scheList.size() != 0) {
			for (int i = 0; i < scheList.size(); i++) {
				String[] s = new String[6];
				s[0] = scheList.get(i).getInstructorID();
				s[1] = scheList.get(i).getPlace();
				s[2] = scheList.get(i).getYear();
				s[3] = scheList.get(i).getDay();
				s[4] = scheList.get(i).getFrom();
				s[5] = scheList.get(i).getTo();
				model2.addRow(s);
			}
		}

		table_1 = new JTable(model2);
		scrollPane_2.setViewportView(table_1);

//@@@@@@@@@
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setBounds(6, 164, 463, 63);
		panel_2.add(panel_5);
		panel_5.setLayout(null);

		JLabel label_8 = new JLabel("강의장소");
		label_8.setBounds(6, 10, 44, 16);
		panel_5.add(label_8);

		from = new JTextField();
		from.setBounds(48, 31, 94, 26);
		from.setColumns(7);
		panel_5.add(from);

		JLabel label_9 = new JLabel("년도");
		label_9.setBounds(130, 10, 33, 16);
		panel_5.add(label_9);

		to = new JTextField();
		to.setBounds(175, 31, 94, 26);
		to.setColumns(7);
		panel_5.add(to);

		JButton button_3 = new JButton("등록");

		button_3.setBounds(387, 31, 75, 29);
		panel_5.add(button_3);
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
		// 콤보 박스 채우는 부분 @@@@@@@
		String[] plc = new String[list1.size()];
		for (int i = 0; i < list1.size(); i++) {
			plc[i] = list1.get(i).getPlace();
		}

		String[] ins = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ins[i] = list.get(i).getNumber();
		}

		JComboBox placeCombo = new JComboBox();
		placeCombo.setModel(new DefaultComboBoxModel(plc));
		placeCombo.setBounds(48, 9, 80, 21);
		panel_5.add(placeCombo);

		JComboBox year = new JComboBox();
		year.setModel(new DefaultComboBoxModel(new String[] { "2018", "2019", "2020", "2021" }));
		year.setBounds(154, 9, 85, 21);
		panel_5.add(year);

		JComboBox instructorID = new JComboBox();
		instructorID.setModel(new DefaultComboBoxModel(ins));
		instructorID.setBounds(283, 9, 67, 21);
		panel_5.add(instructorID);

		JComboBox day = new JComboBox();
		day.setModel(new DefaultComboBoxModel(new String[] { "일", "월", "화", "수", "목", "금", "토" }));
		day.setBounds(377, 9, 80, 21);
		panel_5.add(day);

		JLabel lblid_1 = new JLabel("강사ID");
		lblid_1.setBounds(246, 10, 44, 16);
		panel_5.add(lblid_1);

		JLabel label_1 = new JLabel("요일");
		label_1.setBounds(351, 10, 26, 16);
		panel_5.add(label_1);

		JLabel lblFrom = new JLabel("from");
		lblFrom.setBounds(16, 36, 30, 16);
		panel_5.add(lblFrom);

		JLabel lblTo = new JLabel("to");
		lblTo.setBounds(161, 36, 23, 16);
		panel_5.add(lblTo);

		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back.setVisible(true);
				setVisible(false);
			}
		});
		button.setBounds(420, 6, 82, 29);
		contentPane.add(button);

		JLabel label = new JLabel("식자재 소요량 산출 시스템");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		label.setBounds(124, 18, 266, 42);
		contentPane.add(label);
		
		JButton button_4 = new JButton("새로고침");
		
		button_4.setBounds(6, 6, 82, 29);
		contentPane.add(button_4);

		// 강사 등록 부분
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] s = new String[3];
				s[0] = insID.getText();
				s[1] = insName.getText();
				s[2] = insAccout.getText();
				model.addRow(s);
				Instructor in = new Instructor();// 바꿔줘야
				in.setNumber(s[0]);
				in.setName(s[1]);
				in.setAccount(s[2]);
				protocol = new Protocol(Protocol.PT_ADDINSTRUCTOR);// 프로토콜 바꿔야

				try {
					os.write(protocol.getPacket());
					hd.sendInstructor(in);// 바꿔줘야함
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				protocol = new Protocol(Protocol.PT_INSTRUCTORLIST);
				try {
					os.write(protocol.getPacket());

					LinkedList<Instructor> listforcombo = hd.getInstructorList();
					String[] forcom = new String[listforcombo.size()];
					for (int i = 0; i < listforcombo.size(); i++) {
						forcom[i] = listforcombo.get(i).getNumber();

					}
					instructorID.setModel(new DefaultComboBoxModel(forcom));
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// 지점 등록부분
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String place = placeF.getText().trim();
				model1.addElement(place);
				Place pl = new Place();
				pl.setPlace(place);
				protocol = new Protocol(Protocol.PT_ADDPLACE);// 프로토콜 바꿔야

				try {
					os.write(protocol.getPacket());
					hd.sendPlace(pl);
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				protocol = new Protocol(Protocol.PT_PLACELIST);
				try {
					os.write(protocol.getPacket());

					LinkedList<Place> listforcombo = hd.getPlaceList();
					String[] forcom = new String[listforcombo.size()];
					for (int i = 0; i < listforcombo.size(); i++) {
						forcom[i] = listforcombo.get(i).getPlace();

					}
					placeCombo.setModel(new DefaultComboBoxModel(forcom));
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] s = new String[6];
				s[0] = instructorID.getSelectedItem().toString().trim();
				s[1] = placeCombo.getSelectedItem().toString().trim();
				s[2] = year.getSelectedItem().toString().trim();
				s[3] = day.getSelectedItem().toString().trim();
				s[4] = from.getText().trim();
				s[5] = to.getText().trim();
				model2.addRow(s);
				Schedule sc = new Schedule();// 바꿔줘야
				sc.setInstructorID(s[0]);
				sc.setPlace(s[1]);
				sc.setYear(s[2]);
				sc.setDay(s[3]);
				sc.setFrom(s[4]);
				sc.setTo(s[5]);
				protocol = new Protocol(Protocol.PT_ADDSCHEDULE);// 프로토콜 바꿔야

				try {
					os.write(protocol.getPacket());
					hd.sendSchedule(sc);// 바꿔줘야함
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					ScheduleForm sf = new ScheduleForm(back,socket,hd);
					sf.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void menu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("MENU");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("LOAD");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter defaultFilter;
				jfc.addChoosableFileFilter(new FileNameExtensionFilter("텍스트 문서 (*.txt)", "txt"));
				jfc.addChoosableFileFilter(new FileNameExtensionFilter("아래한글 문서 (*.hwp)", "hwp"));
				jfc.addChoosableFileFilter(defaultFilter = new FileNameExtensionFilter("한셀 문서 (*.csv)", "csv"));
				jfc.setFileFilter(defaultFilter);
				int response = jfc.showOpenDialog(null);
				if (response != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.");
					return;
				}
				File f1 = jfc.getSelectedFile();
				FileReader fr;
				try {
					fr = new FileReader(f1);

					LinkedList<Place> placeList = new LinkedList<Place>();
					LinkedList<Schedule> scheduleList = new LinkedList<Schedule>();
					LinkedList<Instructor> instructorList = new LinkedList<Instructor>();

					String a = null;

					BufferedReader br = new BufferedReader(fr);

					int i = 0;
					int j = 0;

					while ((a = br.readLine()) != null) {
						boolean flag = true;
						String[] array = a.split(",");
						Schedule schedule = new Schedule();
						if (array.length == 1) {
							Place place = new Place();
							place.setPlace(array[0].trim());

							placeList.add(place);

							schedule.setPlace(array[0].trim());

							i++;
						}

						else {
							Instructor instructor = new Instructor();

							for (int k = 0; k < instructorList.size(); k++) {
								if (array[0].equals(instructorList.get(k).getName())) {
									flag = false;
									break;
								}

							}

							 if (flag == true) {
			                     instructor.setNumber(String.valueOf(j));
			                     instructor.setName(array[0]);
			                     instructor.setAccount(array[1]);

			                     instructorList.add(instructor);
			                     j++;
			                  }
//							instructor.setNumber(String.valueOf(j));
//							instructor.setName(array[0].trim());
//							instructor.setAccount(array[1].trim());
//							instructorList.add(instructor);
//							j++;
//							
							
							for (int k = 0; k < scheduleList.size(); k++) {
								if (array[0].trim().equals(instructorList.get(k).getName())
										&& array[1].trim().equals(instructorList.get(k).getAccount())) {
									instructor.setNumber(String.valueOf(k));
									schedule.setInstructorID(String.valueOf(k));
									flag = false;
									j--;
								}

							}
							if (flag == true) {
								schedule.setInstructorID(String.valueOf(j - 1));
							}
							schedule.setPlace(placeList.get(i - 1).getPlace());
							schedule.setYear(array[2].trim());
							schedule.setDay(array[3].trim());
							schedule.setFrom(array[4].trim());
							schedule.setTo(array[5].trim());

							scheduleList.add(schedule);
						}
					}
//					for (int k = 0; k < placeList.size(); k++) {
//						System.out.println(placeList.get(k).getPlace());
//					}
//
//					for (int l = 0; l < instructorList.size(); l++) {
//						System.out.println(instructorList.get(l).getName() + " " + instructorList.get(l).getAccount()
//								+ " " + instructorList.get(l).getNumber());
//
//					}
//
//					for (int m = 0; m < scheduleList.size(); m++) {
//						System.out.println(scheduleList.get(m).getInstructorID() + " " + scheduleList.get(m).getPlace()
//								+ " " + scheduleList.get(m).getYear() + " " + scheduleList.get(m).getDay() + " "
//								+ scheduleList.get(m).getFrom() + " " + scheduleList.get(m).getTo());
//					}

					protocol = new Protocol(Protocol.PT_SCHEDULELOAD);
					os.write(protocol.getPacket());
					hd.sendInstructorList(instructorList);
					hd.sendPlaceList(placeList);
					hd.sendScheduleList(scheduleList);

				} catch (IOException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		mnNewMenu.add(mntmNewMenuItem);
	}
}
