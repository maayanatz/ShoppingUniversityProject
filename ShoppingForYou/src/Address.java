import javax.faces.bean.ManagedBean;

@ManagedBean(name = "address", eager = true)
public class Address {
	
	private int addressID;
	private int customerID;
	private String streetName;
	private int houseNumber;
	private int apartmentNumber;
	private String city;
	private String country;
	private int postalCode;
	
	public Address() {
	}
	
	public Address(int addressID, int customerID, String streetName, int houseNumber,
			int apartmentNumber, String city, String country, int postalCode) {
		this.addressID = addressID;
		this.customerID = customerID;
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.apartmentNumber = apartmentNumber;
		this.city = city;
		this.country = country;
		this.postalCode = postalCode;
	}

	/**
	 * @return the addressID
	 */
	public int getAddressID() {
		return addressID;
	}

	/**
	 * @param addressID the addressID to set
	 */
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	/**
	 * @return the owner
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	/**
	 * @return the houseNumber
	 */
	public int getHouseNumber() {
		return houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * @return the apartmentNumber
	 */
	public int getApartmentNumber() {
		return apartmentNumber;
	}

	/**
	 * @param apartmentNumber the apartmentNumber to set
	 */
	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the postalCode
	 */
	public int getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
}
