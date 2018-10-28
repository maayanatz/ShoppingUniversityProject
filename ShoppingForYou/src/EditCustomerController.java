import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean(name = "editCustomerController", eager = true)
public class EditCustomerController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Customer> customers;
	private List<Order> currentCustomerOrders;
	private List<ItemInOrder> currentOrderItems;
	
	@ManagedProperty(value="#{customer}")
	private Customer loggedInCustomer;
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int phoneNumber;
	private int addressID;
	private int customerID;
	private String streetName;
	private int houseNumber;
	private int apartmentNumber;
	private String city;
	private String country;
	private int postalCode;
	private String cardNumber;
	private int cardCustomer;
	private int cardOwner;
	private List<Order> customerOrders;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public EditCustomerController() throws Exception {
		customers = new ArrayList<>();
		currentCustomerOrders = new ArrayList<>();
		currentOrderItems = new ArrayList<>();
		loggedInCustomer = null;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the loggedInCustomer
	 */
	public Customer getLoggedInCustomer() {
		return loggedInCustomer;
	}

	/**
	 * @param loggedInCustomer the loggedInCustomer to set
	 */
	public void setLoggedInCustomer(Customer loggedInCustomer) {
		this.loggedInCustomer = loggedInCustomer;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		if(this.loggedInCustomer != null) {
			this.id = this.loggedInCustomer.getId();
			}
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.loggedInCustomer.setId(id);
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		if(this.loggedInCustomer != null) {
			this.firstName = this.loggedInCustomer.getFirstName();
			}
		return this.firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.loggedInCustomer.setFirstName(firstName);
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		if(this.loggedInCustomer != null) {
			this.lastName = this.loggedInCustomer.getLastName();
			}
		return this.lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.loggedInCustomer.setLastName(lastName);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		if(this.loggedInCustomer != null) {
			this.email = this.loggedInCustomer.getEmail();
			}
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.loggedInCustomer.setEmail(email);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		if(this.loggedInCustomer != null) {
			this.password = this.loggedInCustomer.getPassword();
			}
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.loggedInCustomer.setPassword(password);
	}

	/**
	 * @return the phoneNumber
	 */
	public int getPhoneNumber() {
		if(this.loggedInCustomer != null) {
			this.phoneNumber = this.loggedInCustomer.getPhoneNumber();
			}
		return this.phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(int phoneNumber) {
		this.loggedInCustomer.setPhoneNumber(phoneNumber);
	}

	/**
	 * @return the addressID
	 */
	public int getAddressID() {
		if(this.loggedInCustomer != null) {
			this.addressID = this.loggedInCustomer.getAddressID();
			}
		return this.addressID;
	}

	/**
	 * @param addressID the addressID to set
	 */
	public void setAddressID(int addressID) {
		this.loggedInCustomer.setAddressID(addressID);
	}

	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		if(this.loggedInCustomer != null) {
			this.customerID = this.loggedInCustomer.getCustomerID();
			}
		return this.customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.loggedInCustomer.setCustomerID(customerID);
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		if(this.loggedInCustomer != null) {
			this.streetName= this.loggedInCustomer.getStreetName();
			}
		return this.streetName;
	}

	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.loggedInCustomer.setStreetName(streetName);
	}

	/**
	 * @return the houseNumber
	 */
	public int getHouseNumber() {
		if(this.loggedInCustomer != null) {
			this.houseNumber = this.loggedInCustomer.getHouseNumber();
			}
		return this.houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(int houseNumber) {
		this.loggedInCustomer.setHouseNumber(houseNumber);
	}

	/**
	 * @return the apartmentNumber
	 */
	public int getApartmentNumber() {
		if(this.loggedInCustomer != null) {
			this.apartmentNumber = this.loggedInCustomer.getApartmentNumber();
			}
		return this.apartmentNumber;
	}

	/**
	 * @param apartmentNumber the apartmentNumber to set
	 */
	public void setApartmentNumber(int apartmentNumber) {
		this.loggedInCustomer.setApartmentNumber(apartmentNumber);
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		if(this.loggedInCustomer != null) {
			this.city = this.loggedInCustomer.getCity();
			}
		return this.city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.loggedInCustomer.setCity(city);
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		if(this.loggedInCustomer != null) {
			this.country = this.loggedInCustomer.getCountry();
			}
		return this.country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.loggedInCustomer.setCountry(country);
	}

	/**
	 * @return the postalCode
	 */
	public int getPostalCode() {
		if(this.loggedInCustomer != null) {
			this.postalCode = this.loggedInCustomer.getPostalCode();
			}
		return this.postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(int postalCode) {
		this.loggedInCustomer.setPostalCode(postalCode);
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		if(this.loggedInCustomer != null) {
			this.cardNumber = this.loggedInCustomer.getCardNumber();
			}
		return this.cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.loggedInCustomer.setCardNumber(cardNumber);
	}

	/**
	 * @return the cardCustomer
	 */
	public int getCardCustomer() {
		if(this.loggedInCustomer != null) {
			this.cardCustomer = this.loggedInCustomer.getCardCustomer();
			}
		return this.cardCustomer;
	}

	/**
	 * @param cardCustomer the cardCustomer to set
	 */
	public void setCardCustomer(int cardCustomer) {
		this.loggedInCustomer.setCardCustomer(cardCustomer);
	}

	/**
	 * @return the cardOwner
	 */
	public int getCardOwner() {
		if(this.loggedInCustomer != null) {
			this.cardOwner = this.loggedInCustomer.getCardOwner();
			}
		return this.cardOwner;
	}

	/**
	 * @param cardOwner the cardOwner to set
	 */
	public void setCardOwner(int cardOwner) {
		this.loggedInCustomer.setCardOwner(cardOwner);
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
	 * @return the shoppingDbUtil
	 */
	public ShoppingDbUtil getShoppingDbUtil() {
		return shoppingDbUtil;
	}

	/**
	 * @param shoppingDbUtil the shoppingDbUtil to set
	 */
	public void setShoppingDbUtil(ShoppingDbUtil shoppingDbUtil) {
		this.shoppingDbUtil = shoppingDbUtil;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/**
	 * @param currentCustomerOrders the currentCustomerOrders to set
	 */
	public void setCurrentCustomerOrders(List<Order> currentCustomerOrders) {
		this.currentCustomerOrders = currentCustomerOrders;
	}

	/**
	 * @param currentOrderItems the currentOrderItems to set
	 */
	public void setCurrentOrderItems(List<ItemInOrder> currentOrderItems) {
		this.currentOrderItems = currentOrderItems;
	}

	/**
	 * @return the currentOrderItems
	 */
	public List<ItemInOrder> getCurrentOrderItems() {
		return currentOrderItems;
	}

	/**
	 * @return the currentCustomerOrders
	 */
	public List<Order> getCurrentCustomerOrders() {
		return currentCustomerOrders;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void loadCustomers() {

		logger.info("Loading customers");
		
		customers.clear();

		try {
			
			// get all customers from database
			customers = shoppingDbUtil.getCustomers();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading customers", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addCustomer(Customer theCustomer) {

		logger.info("Adding customer: " + theCustomer);

		try {
			
			// add customer to the database
			shoppingDbUtil.addCustomer(theCustomer);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding customers", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "edit-customers?faces-redirect=true";
	}

	public String loadCustomer(int customerId) {
		
		logger.info("loading customer: " + customerId);
		
		try {
			// get customer from database
			Customer theCustomer = shoppingDbUtil.getCustomer(customerId);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("customer", theCustomer);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading customer id:" + customerId, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "update-customer-form.xhtml";
	}
	
	public int getLoggedInCustomerID()
	{
		String loggedInCustomerEmail = null;
		
		Object currentEmailFound = FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("currentEmail");
		
		if (currentEmailFound == null) {
			return 1;
		}
		else if (currentEmailFound instanceof String) {
			loggedInCustomerEmail = (String) currentEmailFound;
		}
		else {
			return 0;
		}

		int customerID;
		
		logger.info("Getting logged in customer ID with email: " + loggedInCustomerEmail);

		try {
			
			customerID = shoppingDbUtil.getLoggedInCustomerID(loggedInCustomerEmail);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error getting logged in customer ID", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return 0;
		}
		
		return customerID;
	}
	
	public void loadLoggedInCustomer() {
		
		int loggedInCustomerID = getLoggedInCustomerID();
		
		logger.info("loading customer: " + loggedInCustomerID);
		
		try {
			// get customer from database
			this.loggedInCustomer = shoppingDbUtil.getCustomer(loggedInCustomerID);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading customer id:" + loggedInCustomerID, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public String loadCustomerOrders(int customerID) {
		
		logger.info("loading orders of customer: " + customerID);
		currentCustomerOrders.clear();
		
		try {
			
			// get all customer orders from database
			currentCustomerOrders = shoppingDbUtil.getCustomerOrders(customerID);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading customer orders", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
		
		return "list-customer-orders.xhtml";
	}
	
	public String loadOrderItems(Order theOrder) {
		
		logger.info("loading items of order: " + theOrder.getOrderNumber());
		currentOrderItems.clear();
		
		if (theOrder.getOrderItems() != null) {
			this.currentOrderItems = theOrder.getOrderItems();
		}
		else {
			return null;
		}
		
		return "list-order-items.xhtml";
	}
	
	public String updateCustomer(Customer theCustomer, int page) {

		logger.info("updating customer: " + theCustomer);
		
		try {
			
			// update customer in the database
			shoppingDbUtil.updateCustomer(theCustomer);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating customer: " + theCustomer, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		if (page == 0) {
			return "edit-customers?faces-redirect=true";		
		}
		return "review-my-account?faces-redirect=true";
	}
	
	public String deleteCustomer(Customer theCustomer) {

		logger.info("Deleting customer id: " + theCustomer.getId());
		
		try {
			
			theCustomer.setCustomerOrders(shoppingDbUtil.getCustomerOrders(theCustomer.getId()));

			// delete the customer from the database
			shoppingDbUtil.deleteCustomer(theCustomer.getId(), theCustomer.getCustomerOrders());
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting customer id: " + theCustomer.getId(), exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "edit-customers";	
	}
		
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}