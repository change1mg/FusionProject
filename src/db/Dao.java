package db;

import java.sql.*;
import java.util.LinkedList;

import domain.Account;
import domain.ApplyCheck;
import domain.Cancel;
import domain.DailyMenu;
import domain.NeedCheck;
import domain.Place;
import domain.Price;
import domain.User;;

public class Dao {
	private ConnectionMaker connectionMaker;
	private LinkedList<DailyMenu> list;

	public Dao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	// 2-1 기능
	public LinkedList<DailyMenu> getDailyMenu(String date) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement(
				"select 강의장소,식단명 from 강의일자별_실습메뉴 join 식단 on 식단.식단id = 강의일자별_실습메뉴.식단id where 수업일='" + date + "'");
		ResultSet rs = ps.executeQuery();

		list = new LinkedList<DailyMenu>();
		while (rs.next()) {
			DailyMenu menu = new DailyMenu();
			menu.setName(rs.getString("식단명"));
			menu.setPlace(rs.getString("강의장소"));
			list.add(menu);
		}

		rs.close();
		ps.close();
		c.close();
		return list;
	}

	// 3-1조회기
	public LinkedList<DailyMenu> getDailyPrice(String date, Place place) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c
				.prepareStatement("select 식단명,실습재료비 from 강의일자별_실습메뉴 join 식단 on 식단.식단id = 강의일자별_실습메뉴.식단id where 수업일='"
						+ date + "' and 강의장소='" + place + "'");
		ResultSet rs = ps.executeQuery();

		list = new LinkedList<DailyMenu>();
		while (rs.next()) {
			DailyMenu menu = new DailyMenu();
			menu.setName(rs.getString("식단명"));
			menu.setPrice(rs.getInt("실습재료비"));
			list.add(menu);
		}

		rs.close();
		ps.close();
		c.close();
		return list;
	}
	// select 식단명,실습재료비 from 강의일자별_실습메뉴 join 식단 on 식단.식단id = 강의일자별_실습메뉴.식단id where
	// 수업일='2018-01-01'

	// 2-2기능
	public LinkedList<ApplyCheck> getApplyCheck(String date) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement(
				"select 신청번호,신청자명,신청자전화번호,강의장소,식단명,신청수량,입금일,입금액,취소일,환불일,환불금액 from 식재료_신청 inner join 식재료_신청내역 on 식재료_신청.신청번호 = 식재료_신청내역.식재료_신청_신청번호 inner join 식단 on 식단.식단id = 식재료_신청내역.식단_식단id WHERE 수업일 = '"+ date+"' ");
		ResultSet rs = ps.executeQuery();

		LinkedList<ApplyCheck> lista = new LinkedList<ApplyCheck>();
		while (rs.next()) {
			ApplyCheck ac = new ApplyCheck();
			ac.setNumber(rs.getString("신청번호"));
			ac.setName(rs.getString("신청자명"));
			ac.setPhoneNumber(rs.getString("신청자전화번호"));
			ac.setPlace(rs.getString("강의장소"));
			ac.setFoodName(rs.getString("식단명"));
			ac.setQuantity(rs.getInt("신청수량"));
			ac.setDepositDate(rs.getString("입금일"));
			ac.setDepositPrice(rs.getInt("입금액"));
			ac.setCancelDate(rs.getString("취소일"));
			ac.setRefundDate(rs.getString("환불일"));
			ac.setRefundPrice(rs.getInt("환불금액"));
			lista.add(ac);
		}
//			"신청번호", "신청자명", "전화번호", "강의장소", "식단명", "신청수량", "입금일", "입금액", "취소일", "환불일", "환불금액" 
//	  	private String number;
//		private String name;
//		private String phoneNumber;
//		private String place;
//		private String foodName;
//		private int quantity;
//		private String depositDate;// 입금일
//		private int depositPrice;
//		private String cancelDate;
//		private String refundDate;
//	      private int refundPrice;
		rs.close();
		ps.close();
		c.close();
		return lista;
	}

	//식자제 소요량 2-3
	public LinkedList<NeedCheck> getNeedCheck(String fromDate, String toDate)
			throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("select 수업일,강의장소,식재료명,(소요량*신청수량),단위 from 식재료 inner join recipe on 식재료.식재료id = recipe.식재료id inner join 식재료_신청내역 on recipe.식단id = 식재료_신청내역.식단_식단id INNER JOIN 식재료_신청 ON 신청번호 = 식재료_신청_신청번호 WHERE 수업일 BETWEEN '"+fromDate+"' AND '"+toDate+"' AND 입금액 >= 식재료비");
		ResultSet rs = ps.executeQuery();

		LinkedList<NeedCheck> list = new LinkedList<NeedCheck>();
		while (rs.next()) {
			NeedCheck nc = new NeedCheck();
			nc.setDate(rs.getString("수업일"));
			nc.setPlace(rs.getString("강의장소"));
			nc.setIngredientName(rs.getString("식재료명"));
			nc.setNeed(rs.getInt("(소요량*신청수량)"));
			nc.setUnit(rs.getString("단위"));

			list.add(nc);
		}

		rs.close();
		ps.close();
		c.close();
		return list;
	}
	
	//2-4
	public LinkedList<Account> getAccount()throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("SELECT 신청번호, 식재료비*신청수량 , 입금액, 환불금액 FROM 식재료_신청 INNER JOIN 식재료_신청내역 ON 식재료_신청.신청번호 = 식재료_신청내역.식재료_신청_신청번호");
		ResultSet rs = ps.executeQuery();

		LinkedList<Account> list = new LinkedList<Account>();
		while (rs.next()) {
			Account ac = new Account();
			ac.setNumber(rs.getString("신청번호"));
			ac.setIngredientPrice(rs.getString("식재료비*신청수량"));
			ac.setPrice(rs.getString("입금액"));
			ac.setRefund(rs.getString("환불금액"));
			list.add(ac);
		}

		rs.close();
		ps.close();
		c.close();
		return list;
	}
	
	public void delRecipe() throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		Statement stmt = c.createStatement();
		stmt.executeUpdate("delete from Recipe");
		stmt = c.createStatement();
		stmt.executeUpdate("delete from 강의일자별_실습메뉴");
		stmt = c.createStatement();
		stmt.executeUpdate("delete from 식재료_신청내역");
		stmt = c.createStatement();
		stmt.executeUpdate("delete from 식재료_신청");
		stmt = c.createStatement();
		stmt.executeUpdate("delete from 식재료");
		stmt = c.createStatement();
		stmt.executeUpdate("delete from 식단");

		
		stmt.close();
		c.close();

	}
	
	public void delSchedule() throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		Statement stmt = c.createStatement();
		stmt.executeUpdate("delete from 강의일정");
		stmt = c.createStatement();
		stmt.executeUpdate("delete from 강사");
		stmt = c.createStatement();
		stmt.executeUpdate("delete from 강의장소");
		
		stmt.close();
		c.close();

	}
	
	public void updateDeposit(Price price) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("UPDATE 식재료_신청 SET 입금액 = "+price.getPrice()+" , 입금일 = '"+price.getDate()+"' WHERE 신청번호 = "+price.getNumber()+"");

		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	public void updateRefund(Price price) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("UPDATE 식재료_신청 SET 환불금액 = "+price.getPrice()+", 환불일 = '"+price.getDate()+"',입금액 = 입금액-"+ price.getPrice()+" WHERE (신청번호 = "+price.getNumber() +" AND "+price.getPrice()+" <= 입금액 AND '"+price.getDate()+"' >= 취소일 AND 취소일 IS NOT NULL)");

		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	public LinkedList<String> getDateList(String place) throws ClassNotFoundException, SQLException {
		LinkedList<String> list =  new LinkedList<String>();
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("SELECT 수업일 FROM mydb.강의일자별_실습메뉴 where 강의장소='"+place+"'");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			System.out.println(rs.getString("수업일"));
			list.add(rs.getString("수업일"));
		}

		rs.close();
		ps.close();
		c.close();
		return list;
	}
	//3-3	신청 취소 부
	public LinkedList<Cancel> getApllyList(User user) throws ClassNotFoundException, SQLException {
		LinkedList<Cancel> list =  new LinkedList<Cancel>();
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("SELECT 식단명 ,신청수량, 수업일 FROM 식재료_신청내역 INNER JOIN 식단 ON 식단_식단ID = 식단ID INNER JOIN 식재료_신청 ON 신청번호 = 식재료_신청_신청번호 WHERE 식재료_신청_신청번호 = ANY(SELECT 신청번호 FROM 식재료_신청 WHERE 신청자명 = '"+user.getName()+"' AND 신청자전화번호 = '"+user.getNumber()+"' AND 강의장소 = '"+user.getPlace()+"' AND 취소일 IS NULL)");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Cancel cancel = new Cancel();
			cancel.setName(rs.getString("식단명"));
			cancel.setQuantity(rs.getString("신청수량"));
			cancel.setDate(rs.getString("수업일"));
			list.add(cancel);
		}

		rs.close();
		ps.close();
		c.close();
		return list;
	}
	
	
	public void updateCancel(String fastDate, String nowDate ,User user) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		Statement stmt = c.createStatement();
		stmt.executeUpdate("UPDATE 식재료_신청 SET 취소일 = '"+ nowDate +"' WHERE 신청번호 = ANY(SELECT * FROM (SELECT 신청번호 FROM 식재료_신청 INNER JOIN 식재료_신청내역 ON 신청번호 = 식재료_신청_신청번호 WHERE 강의장소 = '"+user.getPlace()+"' AND 신청자명 = '"+user.getName()+"' AND 신청자전화번호 = '"+user.getNumber()+"' AND 수업일 = '"+fastDate+"' AND 식단_식단ID = (SELECT 식단ID FROM 식단 WHERE 식단명 = '"+user.getFoodName()+"'))A)");

		stmt.close();
		c.close();
	}

	
	

	
	

}