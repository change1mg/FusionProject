package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Application;

public class ApplicationDao {
   private ConnectionMaker connectionMaker;
   private LinkedList<Application> list;

   public ApplicationDao(ConnectionMaker connectionMaker) {
      this.connectionMaker = connectionMaker;
   }

   public void add(Application application) throws ClassNotFoundException, SQLException {
      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("insert into 식재료_신청내역(식재료_신청_신청번호,식단_식단id,신청수량,식재료비) value(?,?,?,?)");
      ps.setString(1, application.getApplicantNumber());
      ps.setString(2, application.getFoodNumber());
      ps.setInt(3, application.getQuantity());
      ps.setInt(4, application.getPrice());

      ps.executeUpdate();

      ps.close();
      c.close();
   }

   public LinkedList<Application> get() throws ClassNotFoundException, SQLException {

      Connection c = connectionMaker.makeConnection();
      PreparedStatement ps = c.prepareStatement("select * from 식재료_신청내역");
      list = new LinkedList<Application>();
      ResultSet rs = ps.executeQuery();
      
      while(rs.next()) {
      Application application = new Application();
      application.setApplicantNumber(rs.getString("식재료_신청_신청번호"));
      application.setFoodNumber(rs.getString("식단_식단id"));
      application.setQuantity(rs.getInt("신청수량"));
      application.setPrice(rs.getInt("식재료비"));
      list.add(application);
      }
      rs.close();
      ps.close();
      c.close();
      return list;
   }
}