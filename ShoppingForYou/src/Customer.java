import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "customer", eager = true)
public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int phoneNumber;
	@ManagedProperty(value="#{address}")
	private Address customerAddress;
	private int addressID;
	private int customerID;
	private String streetName;
	private int houseNumber;
	private int apartmentNumber;
	private String city;
	private String country;
	private int postalCode;
	@ManagedProperty(value="#{creditCard}")
	private CreditCard customerCard;
	private String cardNumber;
	private int cardCustomer;
	private int cardOwner;
	private List<Order> customerOrders;
	
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
		this.customerOrders = new ArrayList<>();
	}

	/**
	 * @return the customerOrders
	 */
	public List<Order> getCustomerOrders() {
		return customerOrders;
	}

	/**
	 * @param customerOrders the customerOrders to set
	 */
	public void setCustomerOrders(List<Order> customerOrders) {
		this.customerOrders = customerOrders;
	}

	/**
	 * @return the addressID
	 */
	public int getAddressID() {
		if(this.customerAddress != null) {
			this.addressID = this.customerAddress.getAddressID();
			}
		return this.addressID;
	}

	/**
	 * @param addressID the addressID to set
	 */
	public void setAddressID(int addressID) {
		this.customerAddress.setAddressID(addressID);
	}

	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		if(this.customerAddress != null) {
			this.customerID = this.customerAddress.getCustomerID();
			}
		return this.customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerAddress.setCustomerID(customerID);
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		if(this.customerAddress != null) {
			this.streetName = this.customerAddress.getStreetName();
			}
		return this.streetName;
	}

	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.customerAddress.setStreetName(streetName);
	}

	/**
	 * @return the houseNumber
	 */
	public int getHouseNumber() {
		if(this.customerAddress != null) {
			this.houseNumber = this.customerAddress.getHouseNumber();
			}
		return this.houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(int houseNumber) {
		this.customerAddress.setHouseNumber(houseNumber);
	}

	/**
	 * @return the apartmentNumber
	 */
	public int getApartmentNumber() {
		if(this.customerAddress != null) {
			this.apartmentNumber = this.customerAddress.getApartmentNumber();
			}
		return this.apartmentNumber;
	}

	/**
	 * @param apartmentNumber the apartmentNumber to set
	 */
	public void setApartmentNumber(int apartmentNumber) {
		this.customerAddress.setApartmentNumber(apartmentNumber);
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		if(this.customerAddress != null) {
			this.city = this.customerAddress.getCity();
			}
		return this.city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.customerAddress.setCity(city);
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		if(this.customerAddress != null) {
			this.country = this.customerAddress.getCountry();
			}
		return this.country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.customerAddress.setCountry(country);
	}

	/**
	 * @return the postalCode
	 */
	public int getPostalCode() {
		if(this.customerAddress != null) {
			this.postalCode = this.customerAddress.getPostalCode();
			}
		return this.postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(int postalCode) {
		this.customerAddress.setPostalCode(postalCode);
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		if(this.customerCard != null) {
			this.cardNumber = this.customerCard.getCardNumber();
			}
		return this.cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.customerCard.setCardNumber(cardNumber);
	}

	/**
	 * @return the cardCustomer
	 */
	public int getCardCustomer() {
		if(this.customerCard != null) {
			this.cardCustomer = this.customerCard.getCardCustomer();
			}
		return this.cardCustomer;
	}

	/**
	 * @param cardCustomer the cardCustomer to set
	 */
	public void setCardCustomer(int cardCustomer) {
		this.customerCard.setCardCustomer(cardCustomer);
	}

	/**
	 * @return the cardOwner
	 */
	public int getCardOwner() {
		if(this.customerCard != null) {
			this.cardOwner = this.customerCard.getCardOwner();
			}
		return this.cardOwner;
	}

	/**
	 * @param cardOwner the cardOwner to set
	 */
	public void setCardOwner(int cardOwner) {
		this.customerCard.setCardOwner(cardOwner);
	}

	/**
	 * @return the customerAddress
	 */
	public Address getCustomerAddress() {
		return customerAddress;
	}

	/**
	 * @param customerAddress the customerAddress to set
	 */
	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}

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
}