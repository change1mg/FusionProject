package db;

import java.sql.*;
import java.util.LinkedList;

import domain.Menu;
public class MenuDao {
   private ConnectionMaker connectionMaker;
   private LinkedList<Menu> list;

   public MenuDao(ConnectionMaker connectionMaker) {
      this.connectionMaker = connectionMaker;
   }

   public void add(Menu menu) throws ClassNotFoundException, SQLException {
      Connection c = connectionMaker.makeConnection();
      PreparedStatement ps = c.prepareStatement("insert into 강의일자별_실습메뉴(강의장소,수업일,식단id) value(?,?,?)");
      ps.setString(1, menu.getPlace());
      ps.setString(2, menu.getDate());
      ps.setString(3, menu.getFoodID());

      ps.executeUpdate();

      ps.close();
      c.close();
   }

   public LinkedList<Menu> get() throws ClassNotFoundException, SQLException {

      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("select * from 강의일자별_실습메뉴");
      list = new LinkedList<Menu>();
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
         Menu menu = new Menu();
         menu.setPlace(rs.getString("강의장소"));
         menu.setDate(rs.getString("수업일"));
         menu.setFoodID(rs.getString("식단id"));
         list.add(menu);
      }
      rs.close();
      ps.close();
      c.close();
      return list;
   }
}