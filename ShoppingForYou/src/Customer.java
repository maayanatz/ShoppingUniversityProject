import javax.faces.bean.ManagedBean;

@ManagedBean
public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int phoneNumber;
	private Address customerAddress;
	private CreditCard customerCard;
	
	public Customer() {
	}
	
	public Customer(int id, String firstName, String lastName, String email,
			String password, int phoneNumber, Address customerAddress, CreditCard customerCard) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.customerAddress = customerAddress;
		this.customerCard = customerCard;
	}

	/**
	 * @return the customerAddress
	 */
	public Address getCustomerAddress() {
		return customerAddress;
	}

//	/**
//	 * @param customerAddress the customerAddress to set
//	 */
//	public void setCustomerAddress(Address customerAddress) {
//		this.customerAddress = customerAddress;
//	}

	/**
	 * @return the customerCard
	 */
	public CreditCard getCustomerCard() {
		return customerCard;
	}

	/**
	 * @param customerCard the customerCard to set
	 */
	public void setCustomerCard(CreditCard customerCard) {
		this.customerCard = customerCard;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", password=" + password + ", phoneNumber=" + phoneNumber + "]";
	}

}