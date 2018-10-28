import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ShoppingCartController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ItemInOrder> items;
	private boolean addItemFailure;
	private boolean addItemSuccess;
	private float totalOrderPrice;
	private int itemNumber;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ShoppingCartController() throws Exception {
		items = new ArrayList<>();
		totalOrderPrice = 0;
		addItemFailure = false;
		addItemSuccess = false;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the itemNumber
	 */
	public int getItemNumber() {
		return itemNumber;
	}

	/**
	 * @param itemNumber the itemNumber to set
	 */
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the totalOrderPrice
	 */
	public float getTotalOrderPrice() {
		return totalOrderPrice;
	}

	/**
	 * @param totalOrderPrice the totalOrderPrice to set
	 */
	public void setTotalOrderPrice(float totalItemPrice) {
		this.totalOrderPrice = this.totalOrderPrice + totalItemPrice;
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

	/**
	 * @return the addItemSuccess
	 */
	public boolean isAddItemSuccess() {
		return addItemSuccess;
	}

	/**
	 * @param addItemSuccess the addItemSuccess to set
	 */
	public void setAddItemSuccess(boolean addItemSuccess) {
		this.addItemSuccess = addItemSuccess;
	}


	/**
	 * @return the items
	 */
	public List<ItemInOrder> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<ItemInOrder> items) {
		this.items = items;
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
	
	public static int randomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
	
	public int getLoggedInCustomerID()
	{
		String loggedInCustomerEmail = null;
		
		Object currentEmailFound = FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("currentEmail");
		
		if (currentEmailFound == null) {
			return 0;
		}
		else if (currentEmailFound instanceof String) {
			loggedInCustomerEmail = (String) currentEmailFound;
		}
		else {
			return 0;
		}

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
	
	public String loadProduct(int catalogNumber) {
		
		logger.info("loading product: " + catalogNumber);
		
		try {
			// get administrator from database
			Product theProduct = shoppingDbUtil.getProduct(catalogNumber);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("itemProduct", theProduct);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading product:" + catalogNumber, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}

		return "add-item";
	}
	
	public void addItem() {
		
		Product theItemProduct = null;
		
		Object currentProduct = FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("product");
		
		if (currentProduct == null) {
			return;
		}
		else if (currentProduct instanceof Product) {
			theItemProduct = (Product) currentProduct;
		}
		else {
			return;
		}
		
		if (theItemProduct.getAmount() < theItemProduct.getAmountInOrder())
		{
			setAddItemFailure(true);
			setAddItemSuccess(false);
			return;
		}
		
		int itemOrderID = randomNumberInRange(201, 400);
		int itemOrderNumber = 0;
		
		ItemInOrder newItem = new ItemInOrder(itemOrderID, itemOrderNumber, theItemProduct);
		
		items.add(newItem);
		
		setTotalOrderPrice(newItem.getTotalPrice());
		
		setAddItemFailure(false);
		setAddItemSuccess(true);
	}
	
	public void addOrder(Order theOrder) {

		logger.info("Adding order: " + theOrder.getOrderNumber());

		try {
			
			// add order to the database
			shoppingDbUtil.addOrder(theOrder);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding order", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public void updateProductAmount(List<ItemInOrder> items) {

		logger.info("Updating Products Amount");

		try {
			
			for (int i = 0; i < items.size(); i++) {
				ItemInOrder theItem = items.get(i); 
				shoppingDbUtil.decreaseProductAmount(theItem.getCatalogNumber(), theItem.getAmountInOrder());
			}
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error Updating Products Amount", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public String addToCart() {
		return "/customerRestricted/add-item.xhtml?faces-redirect=true";
	}
	
	public void submitOrder() {
		int orderNumber = randomNumberInRange(1, 200);
		int orderCustomerID = getLoggedInCustomerID();
		
		Order newOrder = new Order(orderNumber, orderCustomerID, totalOrderPrice, items);
		
		addOrder(newOrder);
		updateProductAmount(newOrder.getOrderItems());
	}
	
	public void cancelOrder() {
		items.clear();
		totalOrderPrice = 0;
		addItemFailure = false;
		addItemSuccess = false;
	}
		
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}