package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Instructor;
public class InstructorDao {
   private ConnectionMaker connectionMaker;
   private LinkedList<Instructor> list;

   public InstructorDao(ConnectionMaker connectionMaker) {
      this.connectionMaker = connectionMaker;
   }

   public void add(Instructor instructor) throws ClassNotFoundException, SQLException {
      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("insert into 강사(강사id,강사명,계좌번호) value(?,?,?)");
      ps.setString(1, instructor.getNumber());
      ps.setString(2, instructor.getName());
      ps.setString(3, instructor.getAccount());


      ps.executeUpdate();

      ps.close();
      c.close();
   }

   public LinkedList<Instructor> get() throws ClassNotFoundException, SQLException {

      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("select * from 강사");
      list = new LinkedList<Instructor>();
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
         Instructor instructor = new Instructor();
         instructor.setNumber(rs.getString("강사id"));
         instructor.setName(rs.getString("강사명"));
         instructor.setAccount(rs.getString("계좌번호"));
         list.add(instructor);
      }
      rs.close();
      ps.close();
      c.close();
      return list;
   }
}