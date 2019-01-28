package db;

import java.sql.*;
import java.util.LinkedList;

import domain.Food;

public class FoodDao {
	private ConnectionMaker connectionMaker;
	private LinkedList<Food> list;

	public FoodDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(Food food) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("insert into 식단(식단id,식단명,실습재료비) value(?,?,?)");
		ps.setString(1, food.getNumber());
		ps.setString(2, food.getName());
		ps.setInt(3, food.getPrice());
		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public LinkedList<Food> get() throws ClassNotFoundException, SQLException {

		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("select * from 식단");
		list = new LinkedList<Food>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Food food = new Food();
			food.setNumber(rs.getString("식단id"));
			food.setName(rs.getString("식단명"));
			food.setPrice(rs.getInt("실습재료비"));
			list.add(food);
		}
		rs.close();
		ps.close();
		c.close();
		return list;
	}

}
