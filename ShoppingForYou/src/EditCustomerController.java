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

@ManagedBean
@SessionScoped
public class EditCustomerController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Customer> customers;
	private List<Order> currentCustomerOrders;
	private List<ItemInOrder> currentOrderItems;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public EditCustomerController() throws Exception {
		customers = new ArrayList<>();
		currentCustomerOrders = new ArrayList<>();
		currentOrderItems = new ArrayList<>();
		shoppingDbUtil = ShoppingDbUtil.getInstance();
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
		
		return "edit-customers";	
	}
		
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}