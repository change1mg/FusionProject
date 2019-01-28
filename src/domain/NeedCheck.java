package domain;

import java.io.Serializable;

public class NeedCheck implements Serializable {
	private String date;
	private String place;
	private String ingredientName;
	private int need;
	private String unit;

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}



	public int getNeed() {
		return need;
	}

	public void setNeed(int need) {
		this.need = need;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
