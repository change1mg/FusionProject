package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import controller.Handler;
import domain.*;
import network.Protocol;

import java.awt.*;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
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
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecipeForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField foodID;
	private JTextField foodName;
	private JTextField price;
	private JTextField igID;
	private JTextField igName;
	private JTextField igUnit;
	private JTextField recAC;
	private Socket socket;
	private Protocol protocol;
	private Handler hd;
	private OutputStream os;
	private InputStream is;
	private JTable table_1;
	private JTable table_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeForm frame = new RecipeForm(null, null, null);
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
	public RecipeForm(JFrame back, Socket socket, Handler hd) throws IOException, ClassNotFoundException {
		this.hd = hd;
		os = socket.getOutputStream();
		is = socket.getInputStream();
		this.socket = socket;
		menu();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 340);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(6, 69, 460, 238);
		contentPane.add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("식단 관리", null, panel, null);
		panel.setLayout(null);

//************식단 로드************

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 428, 105);
		panel.add(scrollPane);

		protocol = new Protocol(Protocol.PT_FOODLIST);
		os.write(protocol.getPacket());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(new String("식단ID"));
		model.addColumn(new String("식단명"));
		model.addColumn(new String("실습비"));

		LinkedList<Food> list = hd.getFoodList();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				String[] s = new String[3];
				s[0] = list.get(i).getNumber();
				s[1] = list.get(i).getName();
				String n = Integer.toString(list.get(i).getPrice());
				s[2] = n;
				model.addRow(s);
			}
		}

		table = new JTable(model);
		scrollPane.setViewportView(table);

//************식단 로드 End************

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(6, 123, 428, 63);
		panel.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblid = new JLabel("식단ID");
		panel_3.add(lblid);

		foodID = new JTextField();
		panel_3.add(foodID);
		foodID.setColumns(5);

		JLabel label_1 = new JLabel("식단명");
		panel_3.add(label_1);

		foodName = new JTextField();
		panel_3.add(foodName);
		foodName.setColumns(7);

		JLabel label_2 = new JLabel("실습비");
		panel_3.add(label_2);

		price = new JTextField();
		panel_3.add(price);
		price.setColumns(7);

		// 식단 등록
		JButton button_1 = new JButton("등록");

		panel_3.add(button_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("식재료", null, panel_1, null);
		panel_1.setLayout(null);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(6, 123, 428, 63);
		panel_1.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblid_1 = new JLabel("식재료ID");
		panel_4.add(lblid_1);

		igID = new JTextField();
		igID.setColumns(5);
		panel_4.add(igID);

		JLabel label_4 = new JLabel("식재료명");
		panel_4.add(label_4);

		igName = new JTextField();
		igName.setColumns(7);
		panel_4.add(igName);

		JLabel label_5 = new JLabel("단위");
		panel_4.add(label_5);

		igUnit = new JTextField();
		igUnit.setColumns(7);
		panel_4.add(igUnit);

		JButton button_3 = new JButton("등록");

		panel_4.add(button_3);

		// ************식재료 로드************

		JScrollPane scrollPane_1 = new JScrollPane();

		scrollPane_1.setBounds(6, 6, 428, 105);
		panel_1.add(scrollPane_1);

		protocol = new Protocol(Protocol.PT_INGERDIENTLIST);
		os.write(protocol.getPacket());
		DefaultTableModel model1 = new DefaultTableModel();
		model1.addColumn(new String("식재료ID"));
		model1.addColumn(new String("식재료명"));
		model1.addColumn(new String("단위"));

		LinkedList<Ingredients> list1 = hd.getIngredientList();
		if (list1.size() != 0) {
			for (int i = 0; i < list1.size(); i++) {
				String[] s = new String[3];
				s[0] = list1.get(i).getNumber();
				s[1] = list1.get(i).getName();
				s[2] = list1.get(i).getUnit();
				model1.addRow(s);
			}
		}

		table_1 = new JTable(model1);
		scrollPane_1.setViewportView(table_1);

		// ************식재료 로드 End************

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("식단별 식재료", null, panel_2, null);
		panel_2.setLayout(null);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setBounds(6, 123, 428, 63);
		panel_2.add(panel_5);
		panel_5.setLayout(null);

		JLabel label_6 = new JLabel("식단ID");
		label_6.setBounds(14, 10, 36, 16);
		panel_5.add(label_6);

		JLabel lblid_2 = new JLabel("식재료ID");
		lblid_2.setBounds(130, 10, 47, 16);
		panel_5.add(lblid_2);

		JLabel label_8 = new JLabel("소요량");
		label_8.setBounds(281, 10, 33, 16);
		panel_5.add(label_8);

		recAC = new JTextField();
		recAC.setBounds(319, 5, 94, 26);
		recAC.setColumns(7);
		panel_5.add(recAC);

		// ************식단별 소요량 로드************

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 6, 428, 105);
		panel_2.add(scrollPane_2);

		protocol = new Protocol(Protocol.PT_RECIPELIST);
		os.write(protocol.getPacket());
		DefaultTableModel model2 = new DefaultTableModel();
		model2.addColumn(new String("식단ID"));
		model2.addColumn(new String("식재료ID"));
		model2.addColumn(new String("소요량"));

		LinkedList<Recipe> list2 = hd.getRecipeList();
		if (list2.size() != 0) {
			for (int i = 0; i < list2.size(); i++) {
				String[] s = new String[3];
				s[0] = list2.get(i).getFoodID();
				s[1] = list2.get(i).getIngredientID();
				String n = Integer.toString(list2.get(i).getRequirements());
				s[2] = n;
				model2.addRow(s);
			}
		}

		table_2 = new JTable(model2);
		scrollPane_2.setViewportView(table_2);

		// ************식단별 소요량 로드 End************

		// ***********콤보박스 채우는 부분

		String[] cfood = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			cfood[i] = list.get(i).getNumber();
		}

		String[] cin = new String[list1.size()];
		for (int i = 0; i < list1.size(); i++) {
			cin[i] = list1.get(i).getNumber();
		}

		JComboBox comFood = new JComboBox();
		comFood.setModel(new DefaultComboBoxModel(cfood));

		comFood.setBounds(52, 6, 75, 27);
		panel_5.add(comFood);

		JComboBox comig = new JComboBox();
		comig.setModel(new DefaultComboBoxModel(cin));

		comig.setBounds(176, 6, 75, 27);
		panel_5.add(comig);

		JButton button_5 = new JButton("등록");
		button_5.setBounds(176, 36, 75, 29);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] s = new String[3];
				s[0] = (String) comFood.getSelectedItem();
				s[1] = (String) comig.getSelectedItem();
				s[2] = recAC.getText();
				model2.addRow(s);
				Recipe rec = new Recipe();
				rec.setFoodID(s[0].trim());
				rec.setIngredientID(s[1].trim());
				rec.setRequirements(Integer.parseInt(s[2]));
				protocol = new Protocol(Protocol.PT_ADDRECIPE);

				try {
					os.write(protocol.getPacket());
					hd.sendRecipe(rec);
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_5.add(button_5);

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
		label.setBounds(100, 27, 266, 42);
		contentPane.add(label);
		
		JButton button_2 = new JButton("새로고침");
		button_2.setBounds(6, 6, 88, 29);
		contentPane.add(button_2);
		//		새로고침 부분 
				button_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						try {
							RecipeForm rf =new RecipeForm(back,socket,hd);
							rf.setVisible(true);
						} catch (ClassNotFoundException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String[] s = new String[3];
				s[0] = foodID.getText();
				s[1] = foodName.getText();
				s[2] = price.getText();
				model.addRow(s);
				Food food = new Food();
				food.setNumber(s[0]);
				food.setName(s[1]);
				food.setPrice(Integer.parseInt(s[2]));
				protocol = new Protocol(Protocol.PT_ADDFOOD);

				try {
					os.write(protocol.getPacket());
					hd.sendFood(food);
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				protocol = new Protocol(Protocol.PT_FOODLIST);
				try {
					os.write(protocol.getPacket());

					LinkedList<Food> listforcombo1 = hd.getFoodList();
					String[] forcom1 = new String[listforcombo1.size()];
					for (int i = 0; i < listforcombo1.size(); i++) {
						forcom1[i] = listforcombo1.get(i).getNumber();

					}
					comFood.setModel(new DefaultComboBoxModel(forcom1));
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// 식재료 등록부분
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] s = new String[3];
				s[0] = igID.getText();
				s[1] = igName.getText();
				s[2] = igUnit.getText();
				model1.addRow(s);
				Ingredients ig = new Ingredients();
				ig.setNumber(s[0]);
				ig.setName(s[1]);
				ig.setUnit(s[2]);
				protocol = new Protocol(Protocol.PT_ADDINGERDIENT);

				try {
					os.write(protocol.getPacket());
					hd.sendIngredient(ig);
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				protocol = new Protocol(Protocol.PT_INGERDIENTLIST);
				try {
					os.write(protocol.getPacket());

					LinkedList<Ingredients> listforcombo = hd.getIngredientList();
					String[] forcom = new String[listforcombo.size()];
					for (int i = 0; i < listforcombo.size(); i++) {
						forcom[i] = listforcombo.get(i).getNumber();
//System.out.println(listforcombo.size());
					}
					comig.setModel(new DefaultComboBoxModel(forcom));
				} catch (IOException | ClassNotFoundException e1) {
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
				LinkedList<Recipe> list = new LinkedList<Recipe>();
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
				try {
					File f1 = jfc.getSelectedFile();
					FileReader fr;

					fr = new FileReader(f1);

					LinkedList<Food> foodList = new LinkedList<Food>();
					LinkedList<Recipe> recipeList = new LinkedList<Recipe>();
					LinkedList<Ingredients> ingredientsList = new LinkedList<Ingredients>();

					String a = null;
					String foodname = null;

					BufferedReader br = new BufferedReader(fr);
					int i = 0;
					int j = 0;

					while ((a = br.readLine()) != null) {
						boolean flag = true;
						String[] array = a.split(",");
						Recipe recipe = new Recipe();
						if (array.length == 2) {
							Food food = new Food();
							food.setName(array[0].trim());
							food.setNumber(String.valueOf(i));
							food.setPrice(Integer.parseInt(array[1].trim()));
							foodList.add(food);

							recipe.setFoodID(String.valueOf(i));

							i++;
						}

						else {
							Ingredients ingredients = new Ingredients();
							for (int k = 0; k < ingredientsList.size(); k++) {
								if (ingredientsList.get(k).getName().equals(array[0])
										&& ingredientsList.get(k).getUnit().equals(array[2])) {
									recipeList.get(k).setRequirements(
											recipeList.get(k).getRequirements() + Integer.parseInt(array[1]));
									flag = false;
									break;
								}
							}

							if (flag == true) {
								ingredients.setName(array[0].trim());
								ingredients.setNumber(String.valueOf(j));
								ingredients.setUnit(array[2].trim());
								ingredientsList.add(ingredients);

								recipe.setIngredientID(String.valueOf(j));
								recipe.setRequirements(Integer.parseInt(array[1]));
								j++;
								recipe.setFoodID(String.valueOf(i - 1));
								recipeList.add(recipe);
							}
						}
					}
					protocol = new Protocol(Protocol.PT_RECIPELOAD);
					os.write(protocol.getPacket());
					hd.sendFoodList(foodList);
					hd.sendIngredientList(ingredientsList);
					hd.sendRecipeList(recipeList);
//					for(int k=0;k<foodList.size();k++) {
//						System.out.println(foodList.get(k).getNumber()+ "  " + foodList.get(k).getName() + " " + foodList.get(k).getPrice());
//						
//					}
				} catch (NumberFormatException | IOException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// 액션
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
	}
}
