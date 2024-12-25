package test01.lib;

public class ContactTest1 {

	private String name;
	private int phone;
	private String address;
	private String relationship;

	public ContactTest1(String name, int phone, String address, String relationship) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.relationship = relationship;

	}

	@Override
	public String toString() {

		return ("회원정보 : 이름 = " + name + ",  전화번호 :" + phone + ", 주소 :" + address + ",  종류 : " + relationship);
	}

	public String samename(String name) {   //이름검색 기능 만들었음 (계속 사용돼서)
		if ((this.name).equals(name)) {
			return ("회원정보 : 이름 = " + name + ",  전화번호 :" + this.address + ", 주소 :" + this.address + ",  종류 : "
					+ this.relationship);
		} else {
			return "일치하는 회원정보가 없습니다.";
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

}
