package domain;

import java.io.Serializable;

public class Application implements Serializable{
	private String ApplicantNumber;
	private String foodNumber;
	private int quantity;
	private int price;

	public String getApplicantNumber() {
		return ApplicantNumber;
	}

	public void setApplicantNumber(String applicantNumber) {
		ApplicantNumber = applicantNumber;
	}

	public String getFoodNumber() {
		return foodNumber;
	}

	public void setFoodNumber(String foodNumber) {
		this.foodNumber = foodNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
