package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Recipe;

public class RecipeDao {
	private ConnectionMaker connectionMaker;
	private LinkedList<Recipe> list;

	public RecipeDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(Recipe recipe) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("insert into recipe(식단id,식재료id,소요량) value(?,?,?)");
		ps.setString(1, recipe.getFoodID());
		ps.setString(2, recipe.getIngredientID());
		ps.setInt(3, recipe.getRequirements());
		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public LinkedList<Recipe> get() throws ClassNotFoundException, SQLException {

		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("select * from recipe");
		list = new LinkedList<Recipe>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Recipe recipe = new Recipe();
			recipe.setFoodID(rs.getString("식단id"));
			recipe.setIngredientID(rs.getString("식재료id"));
			recipe.setRequirements(rs.getInt("소요량"));
			list.add(recipe);
		}
		rs.close();
		ps.close();
		c.close();
		return list;
	}
}