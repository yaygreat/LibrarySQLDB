package library.bean;


public class Borrower {
	private int borower_id = 0;
	private String ssn = null;
	private String first_name = null;
	private String last_name = null;
	private String email = null;
	private String address = null;
	private String city = null;
	private String state = null;
	private String phone = null;
	private int flag = 0;

	
	public Borrower() {
		
	}
	
	public Borrower(int borower_id) {
		this.borower_id = borower_id;
		//this.ssn = ssn;
		//this.first_name = first_name;
		//this.last_name = last_name;
		//this.email = email;
		//this.address = address;
		//this.city = city;
		//this.state = state;
		//this.phone = phone;
		//this.flag = flag;
	}
	

	//Setters
	public void setBorrowerID(int borower_id) {
		this.borower_id = borower_id;
	}

	public void setSSN(String ssn) {
		this.ssn = ssn;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	//Getters
	public int getBorrowerID() {
		return borower_id;
	}

	public String getSSN() {
		return ssn;
	}

	public String getFirstName() {
		return first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}

	public String getPhone() {
		return phone;
	}
	
	public int getFlag() {
		return flag;
	}
}
