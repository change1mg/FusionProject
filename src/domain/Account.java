package domain;

import java.io.Serializable;

public class Account implements Serializable {
	private String number;
	private String ingredientPrice;
	private String price;
	private String refund;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIngredientPrice() {
		return ingredientPrice;
	}

	public void setIngredientPrice(String ingredientPrice) {
		this.ingredientPrice = ingredientPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

}
