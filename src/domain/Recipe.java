package domain;

import java.io.Serializable;
import java.util.*;

public class Recipe implements Serializable {
	private int requirements;
	private String ingredientID;
	private String foodID;

	public int getRequirements() {
		return requirements;
	}

	public void setRequirements(int requirements) {
		this.requirements = requirements;
	}

	public String getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(String ingredientID) {
		this.ingredientID = ingredientID;
	}

	public String getFoodID() {
		return foodID;
	}

	public void setFoodID(String foodID) {
		this.foodID = foodID;
	}



}
