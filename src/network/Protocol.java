package network;

import java.io.Serializable;

public class Protocol implements Serializable{
	//프로토콜 타입에 관한 변수
	public static final int PT_UNDEFINED = -1;	// 프로토콜이 지정되어 있지 않은 경우
	public static final int PT_EXIT = 0;		// 프로그램 종료

	public static final int PT_FOODLIST = 1;
	public static final int PT_ADDFOOD = 2;
	public static final int PT_INGERDIENTLIST=3;
	public static final int PT_ADDINGERDIENT=4;
	public static final int PT_RECIPELIST=5;
	public static final int PT_ADDRECIPE=6;
	public static final int PT_INSTRUCTORLIST=7;
	public static final int PT_ADDINSTRUCTOR=8;
	public static final int PT_PLACELIST=9;
	public static final int PT_ADDPLACE=10;
	public static final int PT_SCHEDULELIST=11;
	public static final int PT_ADDSCHEDULE=12;
	public static final int PT_DAYMENULIST=13;
	public static final int PT_ADDDAYMENU=14;
	public static final int PT_DailyMenuLIST=15;
	public static final int PT_ADDDailyPrice=16;
	public static final int PT_DailyPriceLIST=17;
	public static final int PT_ALLPYCATION=18;
	public static final int PT_RECIPELOAD=19;
	public static final int PT_SCHEDULELOAD=20;
	public static final int PT_APPLICANTLIST=21;
	public static final int PT_NEEDCHECKLIST=22;
	public static final int PT_ACCOUNTLIST=23;

	public static final int PT_DEPOSIT=24;
	public static final int PT_REFUND=25;
	public static final int PT_PLACE=26;
	public static final int PT_CANCELLIST=27;
	public static final int PT_CANCEL=28;
	
	
	public static final int LEN_PROTOCOL_TYPE=1;	// 프로토콜 타입 길이
	public static final int LEN_MAX = 1000;		//최대 데이터 길이
	protected int protocolType;
	private byte[] packet;	// 프로토콜과 데이터의 저장공간이 되는 바이트 배열

	public Protocol(){					// 생성자
		this(PT_UNDEFINED);
	}

	public Protocol(int protocolType){	// 생성자
		this.protocolType = protocolType;
		getPacket(protocolType);
	}

	// 프로토콜 타입에 따라 바이트 배열 packet의 length가 다름
	public byte[] getPacket(int protocolType){
		packet = new byte[LEN_PROTOCOL_TYPE];
	    packet[0] = (byte)protocolType;	// packet 바이트 배열의 첫 번째 바이트에 프로토콜 타입 설정
	    return packet;
	}

	// 로그인후 성공/실패의 결과 값을 프로토콜로부터 추출하여 문자열로 리턴
	

	public void setProtocolType(int protocolType){
		this.protocolType = protocolType;
	}

	public int getProtocolType(){
		return protocolType;
	}

	public byte[] getPacket(){
		return packet;
	}

	// Default 생성자로 생성한 후 Protocol 클래스의 packet 데이터를 바꾸기 위한 메서드
	public void setPacket(int pt, byte[] buf){
		packet = null;
		packet = getPacket(pt);
		protocolType = pt;
		System.arraycopy(buf, 0, packet, 0, packet.length);
	}



//	public int getBodyLength() {
//		// String(byte[] bytes, int offset, int length)
//		String string_size = new String(packet, 1, 10).trim();//12부터 데이
//		int size = Integer.parseInt(string_size);
//		return size;
//	}
//
//	public void setBodyLength(int s) {
//		String size = Integer.toString(s);
//		System.arraycopy(size.trim().getBytes(), 0, packet, 1, size.trim().getBytes().length);
//		packet[LEN_PROTOCOL_TYPE + size.trim().getBytes().length] = '\0';
//	}

}
