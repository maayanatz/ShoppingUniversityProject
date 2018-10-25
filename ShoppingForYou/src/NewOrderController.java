import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class NewOrderController implements Serializable {

	private static final long serialVersionUID = 1L;
	private Order theOrder;
	private boolean addItemFailure;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public NewOrderController() throws Exception {
		theOrder = createOrder();
		addOrder();
		addItemFailure = false;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}

	/**
	 * @return the theOrder
	 */
	public Order getTheOrder() {
		return theOrder;
	}

	/**
	 * @param theOrder the theOrder to set
	 */
	public void setTheOrder(Order theOrder) {
		this.theOrder = theOrder;
	}

	/**
	 * @return the addItemFailure
	 */
	public boolean isAddItemFailure() {
		return addItemFailure;
	}

	/**
	 * @param addItemFailure the addItemFailure to set
	 */
	public void setAddItemFailure(boolean addItemFailure) {
		this.addItemFailure = addItemFailure;
	}
	
	public static int randomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
	
	public int getLoggedInCustomerID()
	{
		String loggedInCustomerEmail = AdminLoginController.getAdminCurrentEmail();
		int orderCustomerID;
		
		logger.info("Getting order customer ID for email: " + loggedInCustomerEmail);

		try {
			
			// add item to the database
			orderCustomerID = shoppingDbUtil.getLoggedInCustomerID(loggedInCustomerEmail);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error getting order customer ID", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return 0;
		}
		
		return orderCustomerID;
	}
	
	public Order createOrder() {
		int orderNumber = randomNumberInRange(1, 200);
		int orderCustomerID = getLoggedInCustomerID();
		float totalPrice = 0;
		List<ItemInOrder> orderItems = new ArrayList<>();
		
		Order newOrder = new Order(orderNumber, orderCustomerID, totalPrice, orderItems);
		
		return newOrder;
	}
	
	public void addOrder() {
		
		logger.info("Adding order: " + this.getTheOrder().getOrderNumber());
		
		try {
			
			// add order to the database
			shoppingDbUtil.addOrder(this.getTheOrder());
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding order", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public String addOrderItem(int catalogNumber, int amount, int price, int currentAmount) {

		logger.info("Adding item: " + catalogNumber);
		
		if (currentAmount < amount)
		{
			setAddItemFailure(true);
			return "product-details?faces-redirect=true";
		}
		
		setAddItemFailure(false);
		int itemOrderID = randomNumberInRange(201, 400);
		int itemCatalogNumber = catalogNumber;
		int itemOrderNumber = this.getTheOrder().getOrderNumber();
		int itemAmount = amount;
		int itemPrice = price;
		
		ItemInOrder theItem = new ItemInOrder(itemOrderID, itemCatalogNumber, itemOrderNumber, itemAmount, itemPrice);

		try {
			
			// add item to the database
			shoppingDbUtil.addOrderItem(theItem);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding order item", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		updateOrder(theItem);
		
		return "product-details?faces-redirect=true";
	}
	
	public void updateOrder(ItemInOrder newItem) {
		
		logger.info("Updating order: " + this.getTheOrder().getOrderNumber());
		
		this.getTheOrder().getOrderItems().add(newItem);
		this.getTheOrder().setTotalPrice(this.getTheOrder().getTotalPrice() + (newItem.getItemAmount() * newItem.getItemPrice()));
		
		try {
			
			// add order to the database
			shoppingDbUtil.updateOrder(this.getTheOrder());
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating order", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public String cancelOrder() {

		logger.info("Canceling order number: " + this.getTheOrder().getOrderNumber());
		
		try {

			// delete the order and orderItems from the database
			shoppingDbUtil.cancelOrder(this.getTheOrder().getOrderNumber());
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error canceling order number: " + this.getTheOrder().getOrderNumber(), exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "edit-shopping-cart";	
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}