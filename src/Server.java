
import java.io.*;

import java.net.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.Handler;
import db.*;
import domain.Applicant;
import domain.Cancel;
import domain.Date31;
import domain.DateFormat;
import domain.Food;
import domain.Ingredients;
import domain.Instructor;
import domain.NeedCheck;
import domain.Place;
import domain.Recipe;
import domain.Schedule;
import domain.User;
import network.Protocol;

public class Server {
	// 연결할 포트를 지정.
	private static final int PORT = 5000;
	// 스레드 풀의 최대 스레드 개수를 지정.
	private static final int THREAD_CNT = 50;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);
	private static int count = 1;

	public static void main(String[] args) {
		try {
			// 서버소켓 생성
			ServerSocket serverSocket = new ServerSocket(PORT);

			while (true) {

				Socket socket = serverSocket.accept();
				System.out.println(count++ + "번째 접속");
				try {
					threadPool.execute(new Worker(socket));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//
class Worker implements Runnable {

	private Socket socket = null;
	private OutputStream os;
	private InputStream is;
	private Protocol protocol;

	public Worker(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ConnectionMaker c = new MyConnectionMaker();
			Handler hd = new Handler(socket, c);

			os = socket.getOutputStream();
			is = socket.getInputStream();
			DateFormat date31;
			Place place31;
			boolean program_stop = false;
			FoodDao fooddao = new FoodDao(c);
			IngredientDao igdao = new IngredientDao(c);
			RecipeDao recipedao = new RecipeDao(c);
			InstructorDao insDao = new InstructorDao(c);
			MenuDao menuDao = new MenuDao(c);
			PlaceDao placeDao = new PlaceDao(c);
			ScheduleDao scheDao = new ScheduleDao(c);
			ApplicantDao appDao = new ApplicantDao(c);
			ApplicationDao aptDao = new ApplicationDao(c);
			Dao dao = new Dao(c);
			while (true) {
				protocol = new Protocol(); // 새 Protocol 객체 생성 (기본 생성자)
				byte[] buf = protocol.getPacket(); // 기본 생성자로 생성할 때에는 바이트 배열의 길이가 1000바이트로 지정됨
				is.read(buf); // 클라이언트로부터 요청받음
				int packetType = buf[0]; // 수신 데이터에서 패킷 타입 얻음
				protocol.setPacket(packetType, buf); // 패킷 타입을 Protocol 객체의 packet 멤버변수에 buf를 복사

				switch (packetType) {
				case Protocol.PT_EXIT: // 프로그램 종료 수신
					protocol = new Protocol(Protocol.PT_EXIT);
					program_stop = true;
					System.out.println("서버종료");
					break;

				case Protocol.PT_FOODLIST:
					hd.sendFoodList();
					break;
				case Protocol.PT_ADDFOOD:
					fooddao.add(hd.getFood());
					hd.getFood();
					break;
				case Protocol.PT_INGERDIENTLIST:
					hd.sendIngredientList();
					break;
				case Protocol.PT_ADDINGERDIENT:
					igdao.add(hd.getIngredient());
//					System.out.println(hd.getIng);
					break;
				case Protocol.PT_RECIPELIST:
					hd.sendRecipeList();
					break;
				case Protocol.PT_ADDRECIPE:
					recipedao.add(hd.getRecipe());
					break;
				case Protocol.PT_INSTRUCTORLIST:
					hd.sendInstructorList();
					break;
				case Protocol.PT_ADDINSTRUCTOR:
					insDao.add(hd.getInstructor());
					break;
				case Protocol.PT_PLACELIST:
					hd.sendPlaceList();
					break;
				case Protocol.PT_ADDPLACE:
					placeDao.add(hd.getPlace());
					break;
				case Protocol.PT_SCHEDULELIST:
					hd.sendScheduleList();
					break;
				case Protocol.PT_ADDSCHEDULE:
					scheDao.add(hd.getSchedule());
					break;
				case Protocol.PT_DAYMENULIST:
					hd.sendMenuList();
					break;
				case Protocol.PT_ADDDAYMENU:
					menuDao.add(hd.getMenu());
					break;
				case Protocol.PT_DailyMenuLIST:
					DateFormat date = hd.getDailyMenu();
					System.out.println(date);
					hd.sendDailyMenuList(date);
					break;
				case Protocol.PT_DailyPriceLIST:// 3-1 기능부분
					date31 = hd.getDailyMenu();
					place31 = hd.getPlace();
					System.out.println(date31);
					hd.sendDailyPriceList(date31, place31);
					break;
				case Protocol.PT_ALLPYCATION:// 3-2신청기능 수정해야함

					// 신청 내역 받는 부분
					DateFormat date32 = hd.getDailyMenu();
					Place place32 = hd.getPlace();
					User user = hd.getUser();
					Applicant app = new Applicant();
					app.setNumber(user.getID());// 신청번호
					app.setName(user.getName());// 신청자이름
					app.setFoodName(user.getFoodName());
					app.setPhoneNumber(user.getNumber());
					int quantity = Integer.parseInt(user.getQuantity());
					app.setQuantity(quantity);
					app.setPlace(place32.toString());
					app.setDate(date32.toString());
					System.out.println(
							user.getID() + user.getName() + user.getFoodName() + user.getNumber() + user.getQuantity());// 신청부분
																														// 수정해야
					appDao.add(app);

					break;
				case Protocol.PT_RECIPELOAD:// 레시피 파일 로드부분 // 올딜리트 추가해야함
					dao.delRecipe();
					LinkedList<Food> fList = hd.getFoodList();
					LinkedList<Ingredients> iList = hd.getIngredientList();
					LinkedList<Recipe> rList = hd.getRecipeList();
					for (int i = 0; i < fList.size(); i++) {
						fooddao.add(fList.get(i));
						System.out.print(fList.get(i).getNumber());
					}
					for (int i = 0; i < iList.size(); i++) {
						igdao.add(iList.get(i));
						System.out.print(iList.get(i).getNumber());
					}
					for (int i = 0; i < rList.size(); i++) {
						recipedao.add(rList.get(i));
						System.out.print(rList.get(i).getFoodID());
						System.out.print(rList.get(i).getIngredientID());
						System.out.print(rList.get(i).getRequirements());
					}
					break;
				case Protocol.PT_SCHEDULELOAD:// 강의일정표 파일 로드 부분 올딜리트 추가해야함
					dao.delSchedule();
					LinkedList<Instructor> instructorList = hd.getInstructorList();
					for (int i = 0; i < instructorList.size(); i++) {
						insDao.add(instructorList.get(i));
					}
					LinkedList<Place> placeList = hd.getPlaceList();
					for (int i = 0; i < placeList.size(); i++) {
						placeDao.add(placeList.get(i));
					}
					LinkedList<Schedule> scheduleList = hd.getScheduleList();
					for (int i = 0; i < scheduleList.size(); i++) {
						scheDao.add(scheduleList.get(i));
					}

					break;

				case Protocol.PT_APPLICANTLIST:// 2-2 기능부분
					DateFormat date22 = hd.getDailyMenu();

					System.out.println("신청자 정보 조회 날짜 :" + date22);
					hd.sendApplyCheckList(date22);
					break;

				case Protocol.PT_NEEDCHECKLIST:
					DateFormat fromDate = hd.getDailyMenu();
					DateFormat toDate = hd.getDailyMenu();

					hd.sendNeedCheckList(fromDate, toDate);
					break;
					
				case Protocol.PT_ACCOUNTLIST:
					hd.sendAccountList();
					break;
					
				case Protocol.PT_DEPOSIT:
					dao.updateDeposit(hd.getPrice());
					break;
					
				case Protocol.PT_REFUND:
					dao.updateRefund(hd.getPrice());
					break;
				case Protocol.PT_PLACE:
					String place = hd.inDate();
					System.out.println("3-1 기능 "+place);
					
					
					LinkedList<String> list = dao.getDateList(place);
					hd.sendPlace(list);
					break;
					
					
				case Protocol.PT_CANCELLIST:
					User cancel = new User();
					cancel = hd.getUser();
					hd.sendCancelList(dao.getApllyList(cancel));
					break;
					
					
				case Protocol.PT_CANCEL:
					DateFormat date33 = hd.getDailyMenu();//오류날 가능성있
					User cUser = hd.getUser();
					String classDate = date33.toString();
					Date d = new Date();
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					String nowDate = f.format(d);
					dao.updateCancel(classDate,nowDate,cUser);
					break;
				}// end switch
				if (program_stop)
					break;

			} // end while

			is.close();
			os.close();
			socket.close();

		} catch (IOException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	public LinkedList<NeedCheck> changeList(LinkedList<NeedCheck> inList) {
//		 LinkedList<NeedCheck> outList = new LinkedList<NeedCheck>();
//		 
//		 for(int i = 0 ; i<inList.size(); i++) {
//			 if(같으면누) {
//				 
//			 }
//			 else if(다르면 그냥 넣){
//				 
//			 }
//
//		 }
//		 
//		 
//		 return outList;
//	
//	}

}