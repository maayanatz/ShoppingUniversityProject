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
public class CustomerOrderController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ItemInOrder> thisOrderItems;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public CustomerOrderController() throws Exception {
		thisOrderItems = new ArrayList<>();
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the thisOrderItems
	 */
	public List<ItemInOrder> getThisOrderItems() {
		return thisOrderItems;
	}

	/**
	 * @param thisOrderItems the thisOrderItems to set
	 */
	public void setThisOrderItems(List<ItemInOrder> thisOrderItems) {
		this.thisOrderItems = thisOrderItems;
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

	public void loadOrderItems(int orderNumber) {

		logger.info("Loading order items");
		
		thisOrderItems.clear();

		try {
			
			// get all order items from database
			thisOrderItems = shoppingDbUtil.getThisOrderItems(orderNumber);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading order items", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public String addOrder(Order theorder) {

		logger.info("Adding order: " + theorder);

		try {
			
			// add product to the database
			shoppingDbUtil.addOrder(theorder);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding order", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "customer-add-order?faces-redirect=true";
	}

	public String loadOrder(int orderNumber) {
		
		logger.info("loading order: " + orderNumber);
		
		try {
			// get order from database
			Order theOrder = shoppingDbUtil.getOrder(orderNumber);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("order", theOrder);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading order number:" + orderNumber, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "customer-update-order.xhtml";
	}	
	
	public String updateAdministrator(Administrator theAdministrator) {

		logger.info("updating administrator: " + theAdministrator);
		
		try {
			
			// update administrator in the database
			shoppingDbUtil.updateAdministrator(theAdministrator);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating administrator: " + theAdministrator, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "edit-administrators?faces-redirect=true";		
	}
	
	public String deleteAdministrator(int adminID) {

		logger.info("Deleting administrator ID: " + adminID);
		
		try {

			// delete the administrator from the database
			shoppingDbUtil.deleteAdministrator(adminID);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting administrator ID: " + adminID, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "edit-administrators";	
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}