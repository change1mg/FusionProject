package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import domain.Ingredients;
public class IngredientDao {
   private ConnectionMaker connectionMaker;
   private LinkedList<Ingredients> list;

   public IngredientDao(ConnectionMaker connectionMaker) {
      this.connectionMaker = connectionMaker;
   }

   public void add(Ingredients ingredients) throws ClassNotFoundException, SQLException {
      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("insert into 식재료(식재료id,식재료명,단위) value(?,?,?)");
      ps.setString(1, ingredients.getNumber());
      ps.setString(2, ingredients.getName());
      ps.setString(3, ingredients.getUnit());

      ps.executeUpdate();

      ps.close();
      c.close();
   }

   public LinkedList<Ingredients> get() throws ClassNotFoundException, SQLException {

      Connection c = connectionMaker.makeConnection();

      PreparedStatement ps = c.prepareStatement("select * from 식재료");
      list = new LinkedList<Ingredients>();
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
         Ingredients ingredients = new Ingredients();
         ingredients.setNumber(rs.getString("식재료id"));
         ingredients.setName(rs.getString("식재료명"));
         ingredients.setUnit(rs.getString("단위"));
         list.add(ingredients);
      }
      rs.close();
      ps.close();
      c.close();
      return list;
   }
}