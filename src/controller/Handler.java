package controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import db.*;
import db.FoodDao;
import db.IngredientDao;
import db.MyConnectionMaker;
import domain.*;

public class Handler {
	Socket socket;
	ObjectInputStream objectInputStream; // Class의 객체를 읽어올때 사용
	ObjectOutputStream objectOutputStream;
	ConnectionMaker c;// db커넥션

	public Handler(Socket socket) throws IOException {
		this.socket = socket;
		c = null;
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectInputStream = new ObjectInputStream(socket.getInputStream());
	}
	

	public Handler(Socket socket, ConnectionMaker c) throws IOException {
		this.socket = socket;
		this.c = c;
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectInputStream = new ObjectInputStream(socket.getInputStream());
	}

	
//식단 부분  
	public void finalize() throws IOException {
		objectOutputStream.close();
	}

	public void sendFood(Food food) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(food); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Food getFood() throws ClassNotFoundException, IOException {

		Food food = (Food) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return food;
	}

	public void sendFoodList() throws ClassNotFoundException, IOException, SQLException {
		FoodDao dao = new FoodDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}
	public void sendFoodList(LinkedList<Food> list) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(list); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Food> getFoodList() throws ClassNotFoundException, IOException {

		@SuppressWarnings("unchecked")
		LinkedList<Food> list = (LinkedList<Food>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return list;
	}

//식재료 부분 
	public void sendIngredient(Ingredients ig) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(ig); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Ingredients getIngredient() throws ClassNotFoundException, IOException {

		Ingredients ig = (Ingredients) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return ig;
	}

	public void sendIngredientList() throws ClassNotFoundException, IOException, SQLException {
		IngredientDao dao = new IngredientDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}
	
	public void sendIngredientList(LinkedList<Ingredients> list) throws ClassNotFoundException, IOException, SQLException {

		objectOutputStream.writeObject(list); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Ingredients> getIngredientList() throws ClassNotFoundException, IOException {

		@SuppressWarnings("unchecked")
		LinkedList<Ingredients> list = (LinkedList<Ingredients>) objectInputStream.readObject(); // readObject는 object
																									// 객체로 불러오기 때문에

		return list;
	}

	// 레시피 부분
	public void sendRecipe(Recipe re) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(re); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Recipe getRecipe() throws ClassNotFoundException, IOException {

		Recipe re = (Recipe) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return re;
	}

	public void sendRecipeList() throws ClassNotFoundException, IOException, SQLException {
		RecipeDao dao = new RecipeDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}
	
	public void sendRecipeList(LinkedList<Recipe> list) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(list); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Recipe> getRecipeList() throws ClassNotFoundException, IOException {

		LinkedList<Recipe> list = (LinkedList<Recipe>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기
																						// 때문에

		return list;
	}

	// 강사 부분
	public void sendInstructor(Instructor ins) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(ins); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Instructor getInstructor() throws ClassNotFoundException, IOException {

		Instructor re = (Instructor) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return re;
	}

	public void sendInstructorList() throws ClassNotFoundException, IOException, SQLException {
		InstructorDao dao = new InstructorDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}
	public void sendInstructorList(LinkedList<Instructor> instructorList) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(instructorList); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Instructor> getInstructorList() throws ClassNotFoundException, IOException {

		LinkedList<Instructor> list = (LinkedList<Instructor>) objectInputStream.readObject(); // readObject는 object 객체로
																								// 불러오기 때문에

		return list;
	}

	public void sendApplicant(Applicant ins) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(ins); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Applicant getApplicant() throws ClassNotFoundException, IOException {

		Applicant re = (Applicant) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return re;
	}

	public void sendApplicant() throws ClassNotFoundException, IOException, SQLException {
		ApplicantDao dao = new ApplicantDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Applicant> getApplicantList() throws ClassNotFoundException, IOException {

		LinkedList<Applicant> list = (LinkedList<Applicant>) objectInputStream.readObject(); // readObject는 object 객체로
																								// 불러오기 때문에

		return list;
	}

	public void sendApplicantDao(ApplicantDao ins) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(ins); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public ApplicantDao getApplicantDao() throws ClassNotFoundException, IOException {

		ApplicantDao re = (ApplicantDao) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return re;
	}

	public void sendApplicantDao() throws ClassNotFoundException, IOException, SQLException {
		ApplicantDao dao = new ApplicantDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<ApplicantDao> getApplicantDaoList() throws ClassNotFoundException, IOException {

		LinkedList<ApplicantDao> list = (LinkedList<ApplicantDao>) objectInputStream.readObject(); // readObject는 object
																									// 객체로 불러오기 때문에

		return list;
	}

	public void sendIngredients(Ingredients ins) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(ins); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Ingredients getIngredients() throws ClassNotFoundException, IOException {

		Ingredients re = (Ingredients) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return re;
	}

	public void sendIngredients() throws ClassNotFoundException, IOException, SQLException {
		IngredientDao dao = new IngredientDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Ingredients> getingredientsList() throws ClassNotFoundException, IOException {

		LinkedList<Ingredients> list = (LinkedList<Ingredients>) objectInputStream.readObject(); // readObject는 object
																									// 객체로 불러오기 때문에

		return list;
	}

	public void sendMenu(Menu ins) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(ins); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Menu getMenu() throws ClassNotFoundException, IOException {

		Menu re = (Menu) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return re;
	}

	public void sendMenuList() throws ClassNotFoundException, IOException, SQLException {
		MenuDao dao = new MenuDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Menu> getMenusList() throws ClassNotFoundException, IOException {

		LinkedList<Menu> list = (LinkedList<Menu>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return list;
	}

	public void sendPlace(Place ins) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(ins); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Place getPlace() throws ClassNotFoundException, IOException {

		Place re = (Place) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return re;
	}

	public void sendPlaceList() throws ClassNotFoundException, IOException, SQLException {
		PlaceDao dao = new PlaceDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}
	public void sendPlaceList(LinkedList<Place> placeList) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(placeList); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Place> getPlaceList() throws ClassNotFoundException, IOException {

		LinkedList<Place> list = (LinkedList<Place>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return list;
	}

	public void sendSchedule(Schedule ins) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(ins); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public Schedule getSchedule() throws ClassNotFoundException, IOException {

		Schedule re = (Schedule) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return re;
	}

	public void sendScheduleList() throws ClassNotFoundException, IOException, SQLException {
		ScheduleDao dao = new ScheduleDao(c);
		objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}
	
	public void sendScheduleList(LinkedList<Schedule> scheduleList) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(scheduleList); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Schedule> getScheduleList() throws ClassNotFoundException, IOException {

		LinkedList<Schedule> list = (LinkedList<Schedule>) objectInputStream.readObject(); // readObject는 object 객체로
																							// 불러오기 때문에

		return list;
	}
//기능부분 

	public void sendData(DateFormat date) throws ClassNotFoundException, IOException, SQLException {
		objectOutputStream.writeObject(date); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public DateFormat getDailyMenu() throws ClassNotFoundException, IOException {

		DateFormat date = (DateFormat) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return date;
	}
//날짜 전송 부분 
	public void sendDailyMenuList(DateFormat date) throws ClassNotFoundException, IOException, SQLException {
		Dao dao = new Dao(c);
		
		objectOutputStream.writeObject(dao.getDailyMenu(date.toString())); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<DailyMenu> getDailyMenuList() throws ClassNotFoundException, IOException {

		@SuppressWarnings("unchecked")
		LinkedList<DailyMenu> list = (LinkedList<DailyMenu>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return list;
	}
	
	
	
	//3-1받은정보로 리스트 보내는 부분 
	public void sendDailyPriceList(DateFormat date,Place place) throws ClassNotFoundException, IOException, SQLException {
		Dao dao = new Dao(c);
		
		objectOutputStream.writeObject(dao.getDailyPrice(date.toString(),place)); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}
	
	public void sendUser(User user) throws IOException
	{	
		objectOutputStream.writeObject(user); // 데이터 직렬화
		objectOutputStream.flush();
	}
	
	public User getUser() throws ClassNotFoundException, IOException {
		User user = (User)objectInputStream.readObject();
		return user;
	}
	
	//2-2기능 부분 
	public void sendApplyCheckList(DateFormat date) throws ClassNotFoundException, IOException, SQLException {
		Dao dao = new Dao(c);
		objectOutputStream.writeObject(dao.getApplyCheck(date.toString())); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<ApplyCheck> getApplyCheckList() throws ClassNotFoundException, IOException {

		LinkedList<ApplyCheck> list = (LinkedList<ApplyCheck>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return list;
	}
	
	//2-3 기능 부분 
	public void sendNeedCheckList(DateFormat fromDate, DateFormat toDate) throws ClassNotFoundException, IOException, SQLException {
		Dao dao = new Dao(c);
		objectOutputStream.writeObject(dao.getNeedCheck(fromDate.toString(),toDate.toString())); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<NeedCheck> getNeedCheckList() throws ClassNotFoundException, IOException {

		LinkedList<NeedCheck> list = (LinkedList<NeedCheck>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		
		//여기에 넣어야함
		
		return changeList(list);
	}
	//2-4기능 부
	public void sendAccountList() throws ClassNotFoundException, IOException, SQLException {
		Dao dao = new Dao(c);
		objectOutputStream.writeObject(dao.getAccount()); // 데이터 직렬화
		objectOutputStream.flush(); // 직렬화된 데이터 전달
	}

	public LinkedList<Account> getAccountList() throws ClassNotFoundException, IOException {

		LinkedList<Account> list = (LinkedList<Account>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

		return list;
	}
	
	 static LinkedList<NeedCheck> changeList(LinkedList<NeedCheck> inList) {
	      LinkedList<NeedCheck> outList = new LinkedList<NeedCheck>();

	      for (int i = 0; i < inList.size(); i++) {
	         NeedCheck ol = new NeedCheck();
	         boolean flag = true;
	         ol.setDate(inList.get(i).getDate());
	         ol.setPlace(inList.get(i).getPlace());
	         ol.setIngredientName(inList.get(i).getIngredientName());
	         ol.setNeed(inList.get(i).getNeed());
	         ol.setUnit(inList.get(i).getUnit());

	         for (int j = 0; j < outList.size(); j++) {
	            if (ol.getDate().equals(outList.get(j).getDate()) && ol.getPlace().equals(outList.get(j).getPlace())
	                  && ol.getIngredientName().equals(outList.get(j).getIngredientName())) {
	               outList.get(j).setNeed(outList.get(j).getNeed() + ol.getNeed());
	               flag = false;
	               break;

	            }
	         }
	         if (flag == true) {
	            outList.add(ol);
	         }

	      }

	      return outList;
	   }
	 
	 
		public void sendPrice(Price price) throws ClassNotFoundException, IOException, SQLException {
			objectOutputStream.writeObject(price); // 데이터 직렬화
			objectOutputStream.flush(); // 직렬화된 데이터 전달
		}

		public Price getPrice() throws ClassNotFoundException, IOException {

			Price re = (Price) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

			return re;
		}
		
		
		
		
		public void sendPlace(LinkedList<String> list) throws ClassNotFoundException, IOException, SQLException {
			objectOutputStream.writeObject(list); // 데이터 직렬화
			objectOutputStream.flush(); // 직렬화된 데이터 전달
		}

		public String inDate() throws ClassNotFoundException, IOException {

			String re = (String) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

			return re;
		}
		public void getDate(String place) throws IOException {
			objectOutputStream.writeObject(place); // 데이터 직렬화
			objectOutputStream.flush(); // 직렬화된 데이터 전달
		}
		public LinkedList<String> getComboDate() throws ClassNotFoundException, IOException{
			LinkedList<String> list = (LinkedList<String>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

			return list;
		}

		
		
//		//식재료취소부분 
//		public Food get() throws ClassNotFoundException, IOException {
//
//			Food food = (Food) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에
//
//			return food;
//		}
//
//		public void sendFoodList() throws ClassNotFoundException, IOException, SQLException {
//			FoodDao dao = new FoodDao(c);
//			objectOutputStream.writeObject(dao.get()); // 데이터 직렬화
//			objectOutputStream.flush(); // 직렬화된 데이터 전달
//		}
		public void sendCancelList(LinkedList<Cancel> list) throws ClassNotFoundException, IOException, SQLException {
			objectOutputStream.writeObject(list); // 데이터 직렬화
			objectOutputStream.flush(); // 직렬화된 데이터 전달
		}

		public LinkedList<Cancel> getCancelList() throws ClassNotFoundException, IOException {

			@SuppressWarnings("unchecked")
			LinkedList<Cancel> list = (LinkedList<Cancel>) objectInputStream.readObject(); // readObject는 object 객체로 불러오기 때문에

			return list;
		}

		

}
