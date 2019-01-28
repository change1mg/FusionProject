package domain;

import java.io.Serializable;

public class ApplyCheck implements Serializable{
	private String number;
	private String name;
	private String phoneNumber;
	private String place;
	private String foodName;
	private int quantity;
	private String depositDate;// 입금일
	private int depositPrice;
	private String cancelDate;
	private String refundDate;
	private int refundPrice;
	
	public int getRefundPrice() {
		return refundPrice;
	}
	public void setRefundPrice(int refundPrice) {
		this.refundPrice = refundPrice;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
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
	
//	"신청번호", "신청자명", "전화번호", "강의장소", "식단명", "신청수량", "입금일", "입금액", "취소일", "환불일", "환불금액" 
}
