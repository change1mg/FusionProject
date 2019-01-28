package db;

import java.sql.*;
import db.ConnectionMaker;

public class MyConnectionMaker implements ConnectionMaker {

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost/mydb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false", "root",
				"12345678");
		return c;
	}

}