package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Schedule;
public class ScheduleDao {
   private ConnectionMaker connectionMaker;
   private LinkedList<Schedule> list;

   public ScheduleDao(ConnectionMaker connectionMaker) {
      this.connectionMaker = connectionMaker;
   }

   public void add(Schedule schedule) throws ClassNotFoundException, SQLException {
      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("insert into 강의일정(강의장소,년도,강사id,요일,시작시간,종료시간) value(?,?,?,?,?,?)");
      //"INSERT INTO `mydb`.`강의일정` (`강의장소`, `년도`, `강사id`, `요일`, `시작시간`, `종료시간`) VALUES ('?','?''?','?','?','?')"
      ps.setString(1, schedule.getPlace());
      ps.setString(2, schedule.getYear());
      ps.setString(3, schedule.getInstructorID());
      ps.setString(4, schedule.getDay());
      ps.setString(5, schedule.getFrom());
      ps.setString(6, schedule.getTo());

      ps.executeUpdate();

      ps.close();
      c.close();
   }

   public LinkedList<Schedule> get() throws ClassNotFoundException, SQLException {

      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("select * from 강의일정");
      list = new LinkedList<Schedule>();
      ResultSet rs = ps.executeQuery();
      
      while(rs.next()) {
      Schedule schedule = new Schedule();
      schedule.setPlace(rs.getString("강의장소"));
      schedule.setYear(rs.getString("년도"));
      schedule.setInstructorID(rs.getString("강사id"));
      schedule.setDay(rs.getString("요일"));
      schedule.setFrom(rs.getString("시작시간"));
      schedule.setTo(rs.getString("종료시간"));
      list.add(schedule);
      }
      rs.close();
      ps.close();
      c.close();
      return list;
   }
}