package domain;

import java.io.Serializable;

public class Applicant implements Serializable {
	private String number;//신청번호 
	private String place;//강의장소 
	private String date;//수업일 
	private String name;//신청자명 
	private String phoneNumber;//신청자 번
	private String foodName;//신청 음식 
	private int quantity;// 신청 수량 
	
	
	//신청 등록끝 
	private String depositDate;// 입금일
	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private int depositPrice;
	private String cancelDate;
	private String refundDate;
	private int refundPrice;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public int getDepositPrice() {
		return depositPrice;
	}

	public void setDepositPrice(int depositPrice) {
		this.depositPrice = depositPrice;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public int getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(int reundPrice) {
		this.refundPrice = reundPrice;
	}
}
