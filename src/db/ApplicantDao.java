package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import domain.Applicant;
public class ApplicantDao {
   private ConnectionMaker connectionMaker;
   private LinkedList<Applicant> list;

   public ApplicantDao(ConnectionMaker connectionMaker) {
      this.connectionMaker = connectionMaker;
   }

   public void add(Applicant applicant) throws ClassNotFoundException, SQLException {
      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("INSERT INTO 식재료_신청 (신청번호,강의장소,수업일,신청자명,신청자전화번호) value(?,?,?,?,?)");
//      VALUES (9,'본점','2018-12-11','김땡땡','010-3214-6542')
      
      ps.setString(1, applicant.getNumber());
      ps.setString(2, applicant.getPlace());
      ps.setString(3, applicant.getDate());
      ps.setString(4, applicant.getName());
      ps.setString(5, applicant.getPhoneNumber());

      ps.executeUpdate();
      
      PreparedStatement ps1 = c.prepareStatement("INSERT INTO 식재료_신청내역(식재료_신청_신청번호,식단_식단ID,신청수량,식재료비) SELECT "+applicant.getNumber()+",식단ID,"+applicant.getQuantity()+",실습재료비*"+applicant.getQuantity()+" FROM 식단 WHERE 식단명 = '"+applicant.getFoodName()+"'");
      ps1.executeUpdate();
      ps1.close();
      ps.close();
      c.close();
   }

   public LinkedList<Applicant> get() throws ClassNotFoundException, SQLException {
      Connection c = connectionMaker.makeConnection();
      PreparedStatement ps = c.prepareStatement("select * from 식재료_신청");
      list = new LinkedList<Applicant>();
      ResultSet rs = ps.executeQuery();
      
      while(rs.next()) {
         Applicant applicant = new Applicant();
         applicant.setNumber(rs.getString("신청번호"));
         applicant.setPlace(rs.getString("강의장소"));
         applicant.setDate(rs.getString("수업일"));
         applicant.setName(rs.getString("신청자명"));
         applicant.setPhoneNumber(rs.getString("신청자전화번호"));
         applicant.setDepositDate(rs.getString("입금일"));
         applicant.setDepositPrice(rs.getInt("입금액"));
         applicant.setCancelDate(rs.getString("취소일"));
         applicant.setRefundDate(rs.getString("환불일"));
         applicant.setRefundPrice(rs.getInt("환불금액"));
         list.add(applicant);
      }
      
      rs.close();
      ps.close();
      c.close();
      return list;
   }
}