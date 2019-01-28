package domain;

import java.io.Serializable;

public class Food implements Serializable{
	private String number;
	private String name;
	private int price;

	public Food() {
	}
	public Food(String number, String name, int price) {
		this.number = number;
		this.name = name;
		this.price = price;
	}
	public String getNumber() {
		return number;
	}

	public void setNumber(String foodNumber) {
		this.number = foodNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String toString() {
		return number + name + price;
	}
}
