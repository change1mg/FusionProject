package domain;

import java.io.Serializable;

public class Menu implements Serializable {
	private String place;
	private String date;
	private String foodID;

	public String getFoodID() {
		return foodID;
	}

	public void setFoodID(String foodID) {
		this.foodID = foodID;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDate() {
		return date.toString();
	}

	public void setDate(String date) {
		this.date = date;
	}

}
