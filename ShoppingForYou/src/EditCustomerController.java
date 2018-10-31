import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
	private List<Order> customerOrders;
	private boolean addNewCustomerFailure;
	private boolean addNewCustomerSuccess;
	private ShoppingDbUtil shoppingDbUtil;	
	private boolean duplicateID;
	private boolean duplicateEmail;
	private boolean duplicateAddressID;
	private boolean duplicateCreditCard;	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public EditCustomerController() throws Exception {
		customers = new ArrayList<>();
		currentCustomerOrders = new ArrayList<>();
		currentOrderItems = new ArrayList<>();
		addNewCustomerFailure = false;
		addNewCustomerSuccess = false;
		duplicateID = false;
		duplicateEmail = false;
		duplicateAddressID = false;
		duplicateCreditCard = false;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}

	/**
	 * @return the duplicateID
	 */
	public boolean isDuplicateID() {
		return duplicateID;
	}

	/**
	 * @param duplicateID the duplicateID to set
	 */
	public void setDuplicateID(boolean duplicateID) {
		this.duplicateID = duplicateID;
	}

	/**
	 * @return the duplicateEmail
	 */
	public boolean isDuplicateEmail() {
		return duplicateEmail;
	}

	/**
	 * @param duplicateEmail the duplicateEmail to set
	 */
	public void setDuplicateEmail(boolean duplicateEmail) {
		this.duplicateEmail = duplicateEmail;
	}

	/**
	 * @return the duplicateAddressID
	 */
	public boolean isDuplicateAddressID() {
		return duplicateAddressID;
	}

	/**
	 * @param duplicateAddressID the duplicateAddressID to set
	 */
	public void setDuplicateAddressID(boolean duplicateAddressID) {
		this.duplicateAddressID = duplicateAddressID;
	}

	/**
	 * @return the duplicateCreditCard
	 */
	public boolean isDuplicateCreditCard() {
		return duplicateCreditCard;
	}

	/**
	 * @param duplicateCreditCard the duplicateCreditCard to set
	 */
	public void setDuplicateCreditCard(boolean duplicateCreditCard) {
		this.duplicateCreditCard = duplicateCreditCard;
	}

	/**
	 * @return the addNewCustomerFailure
	 */
	public boolean isAddNewCustomerFailure() {
		return addNewCustomerFailure;
	}

	/**
	 * @param addNewCustomerFailure the addNewCustomerFailure to set
	 */
	public void setAddNewCustomerFailure(boolean addNewCustomerFailure) {
		this.addNewCustomerFailure = addNewCustomerFailure;
	}

	/**
	 * @return the addNewCustomerSuccess
	 */
	public boolean isAddNewCustomerSuccess() {
		return addNewCustomerSuccess;
	}

	/**
	 * @param addNewCustomerSuccess the addNewCustomerSuccess to set
	 */
	public void setAddNewCustomerSuccess(boolean addNewCustomerSuccess) {
		this.addNewCustomerSuccess = addNewCustomerSuccess;
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
		
	public synchronized String addCustomer(Customer theCustomer, int page) {

		int validationChecksResult = validationChecks(theCustomer);
		
		if (validationChecksResult == 0) {
			this.addNewCustomerFailure = true;
			this.addNewCustomerSuccess = false;
			if (page == 0) {
				return "add-customer-result?faces-redirect=true";
			}
			else {
				return "new-customer-result?faces-redirect=true";
			}
		}
		
		logger.info("Adding customer: " + theCustomer);

		try {
			
			// add customer to the database
			shoppingDbUtil.addCustomer(theCustomer);
			
		} catch (Exception exc) {
			this.addNewCustomerFailure = true;
			this.addNewCustomerSuccess = false;
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding customer", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		this.addNewCustomerFailure = false;
		this.addNewCustomerSuccess = true;
		if (page == 0) {
			return "add-customer-result?faces-redirect=true";
		}
		return "new-customer-result?faces-redirect=true";
	}

	private synchronized int validationChecks(Customer theCustomer) {
		int tempID;
		String tempEmail;
		int tempAddressID;
		String tempCreditCard;
		List<Customer> currentCustomers = null;
		
		int id = theCustomer.getId();
		String email = theCustomer.getEmail();
		int addressID = theCustomer.getAddressID();
		String creditCard = theCustomer.getCardNumber();
		
		duplicateID = false;
		duplicateEmail = false;
		duplicateAddressID = false;
		duplicateCreditCard = false;
		
		try {
			
			logger.info("Loading current customers");
			currentCustomers = shoppingDbUtil.getCustomers();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error getting customers", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return 0;
		}
		
		for (int i = 0; i < currentCustomers.size(); i++) {

			tempID = currentCustomers.get(i).getId();
			tempEmail = currentCustomers.get(i).getEmail();
			tempAddressID = currentCustomers.get(i).getAddressID();
			tempCreditCard = currentCustomers.get(i).getCardNumber();
			
			if (id == tempID)
			{
				duplicateID = true;
			}
			if (email.equals(tempEmail)) {
				duplicateEmail = true;
			}
			if (addressID == tempAddressID) {
				duplicateAddressID = true;
			}
			if (creditCard.equals(tempCreditCard)) {
				duplicateCreditCard = true;
			}
			
			if (duplicateID == true | duplicateEmail == true | duplicateAddressID == true | duplicateCreditCard == true) {
				return 0;
			}
		}	
		return 1;
	}

	public String loadCustomer(int customerId) {
		
		logger.info("loading customer: " + customerId);
		
		try {
			// get customer from database
			Customer theCustomer = shoppingDbUtil.getCustomer(customerId);
			
			// put in the request attribute, so we can use it on the form page
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
			Customer loggedInCustomer = shoppingDbUtil.getCustomer(loggedInCustomerID);
			
			// put in the request attribute, so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("loggedInCustomer", loggedInCustomer);	
			
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
		
		return "list-customer-orders?faces-redirect=true";
	}
	
	public String loadOrderItems(int orderNumber) {
		
		logger.info("loading items of order: " + orderNumber);
		currentOrderItems.clear();
		
		try {
			
			// get all order items from database
			currentOrderItems = shoppingDbUtil.getOrderItems(orderNumber);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading order items", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
		
		return "list-order-items?faces-redirect=true";
	}
	
	public String updateCustomer(Customer theCustomer) {

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
		return "edit-customers?faces-redirect=true";
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
		
		return "edit-customers?faces-redirect=true";	
	}
		
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}