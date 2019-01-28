package domain;

import java.io.Serializable;

public class Place implements Serializable{
	private String place;

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	public String toString() {
		return place;
	}

}
