package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Place;

public class PlaceDao {
	private ConnectionMaker connectionMaker;
	private LinkedList<Place> list;

	public PlaceDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(Place place) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("insert into 강의장소(강의장소) value(?)");
		ps.setString(1, place.getPlace());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public LinkedList<Place> get() throws ClassNotFoundException, SQLException {

		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("select * from 강의장소");
		list = new LinkedList<Place>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Place place = new Place();
			place.setPlace(rs.getString("강의장소"));
			list.add(place);
		}
		rs.close();
		ps.close();
		c.close();
		return list;
	}
}