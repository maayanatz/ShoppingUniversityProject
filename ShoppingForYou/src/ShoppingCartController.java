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
	private boolean addOrderSuccess;
	private boolean duplicateItem;
	private float totalOrderPrice;
	private int productItemNumber;
	private int itemOrderAmount;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	private int orderNumber;
	private int orderCustomerID;
	private boolean addOrderFailure;
	
	public ShoppingCartController() throws Exception {
		items = new ArrayList<>();
		totalOrderPrice = 0;
		addItemFailure = false;
		addItemSuccess = false;
		addOrderSuccess = false;
		addOrderFailure = false;
		duplicateItem = false;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the duplicateItem
	 */
	public boolean isDuplicateItem() {
		return duplicateItem;
	}

	/**
	 * @param duplicateItem the duplicateItem to set
	 */
	public void setDuplicateItem(boolean duplicateItem) {
		this.duplicateItem = duplicateItem;
	}

	/**
	 * @return the addOrderFailure
	 */
	public boolean isAddOrderFailure() {
		return addOrderFailure;
	}

	/**
	 * @param addOrderFailure the addOrderFailure to set
	 */
	public void setAddOrderFailure(boolean addOrderFailure) {
		this.addOrderFailure = addOrderFailure;
	}

	/**
	 * @return the addOrderSuccess
	 */
	public boolean isAddOrderSuccess() {
		return addOrderSuccess;
	}

	/**
	 * @param addOrderSuccess the addOrderSuccess to set
	 */
	public void setAddOrderSuccess(boolean addOrderSuccess) {
		this.addOrderSuccess = addOrderSuccess;
	}

	/**
	 * @return the orderNumber
	 */
	public int getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the orderCustomerID
	 */
	public int getOrderCustomerID() {
		return orderCustomerID;
	}

	/**
	 * @param orderCustomerID the orderCustomerID to set
	 */
	public void setOrderCustomerID(int orderCustomerID) {
		this.orderCustomerID = orderCustomerID;
	}

	/**
	 * @return the itemOrderAmount
	 */
	public int getItemOrderAmount() {
		return itemOrderAmount;
	}
	
	/**
	 * @param itemOrderAmount the itemOrderAmount to set
	 */
	public void setItemOrderAmount(int itemOrderAmount) {
		this.itemOrderAmount = itemOrderAmount;
	}

	/**
	 * @return the productItemNumber
	 */
	public int getProductItemNumber() {
		return productItemNumber;
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
			return 1;
		}
		else if (currentEmailFound instanceof String) {
			loggedInCustomerEmail = (String) currentEmailFound;
		}
		else {
			return 0;
		}

		int customerID;
		
		logger.info("Getting order customer ID for email: " + loggedInCustomerEmail);

		try {
			
			// add item to the database
			customerID = shoppingDbUtil.getLoggedInCustomerID(loggedInCustomerEmail);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error getting order customer ID", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return 0;
		}
		
		return customerID;
	}
	
	public Product loadProduct(int catalogNumber) {
		
		Product theProduct = null;
		logger.info("loading product: " + catalogNumber);
		
		try {
			// get administrator from database
			theProduct = shoppingDbUtil.getProduct(catalogNumber);
			
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

		return theProduct;
	}
	
	public void addItem() {
		
		Product theItemProduct = null;
		theItemProduct = loadProduct(this.productItemNumber);
		
		if (theItemProduct.getAmount() < this.itemOrderAmount)
		{
			setAddItemFailure(true);
			setAddItemSuccess(false);
			return;
		}
		
		int itemOrderID = randomNumberInRange(201, 400);
		int itemOrderNumber = 0;
		
		ItemInOrder newItem = new ItemInOrder(itemOrderID, itemOrderNumber, theItemProduct, this.itemOrderAmount);
		
		items.add(newItem);
		
		setTotalOrderPrice(newItem.getTotalPrice());
		
		setAddItemFailure(false);
		setAddItemSuccess(true);
	}
	
	public int addOrder(Order theOrder) {

		logger.info("Adding order: " + theOrder.getOrderNumber());

		try {
			
			// add order to the database
			shoppingDbUtil.addOrder(theOrder);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding order", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			return 0;
		}
		return 1;
	}
	
	public int updateProductAmount(List<ItemInOrder> items) {

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
			return 0;
		}
		return 1;
	}
	
	public void checkDuplicate() {
		for (int i = 0; i < this.items.size(); i++) {
			if (this.items.get(i).getCatalogNumber() == this.productItemNumber) {
				this.duplicateItem = true;
				this.addItemFailure = false;
				this.addItemSuccess = false;
				return;
			}
		}
		this.duplicateItem = false;
	}
	
	public String addToCart() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        
        try {  
        	this.productItemNumber = Integer.parseInt(params.get("productItemNumber"));
        }  
        catch(Exception exc) {  
			// send this to server logs
			logger.log(Level.SEVERE, "Error getting item product number", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
        }
        checkDuplicate();
        if (this.duplicateItem == false)
        {
        	addItem();
        }
        return "/customerRestricted/add-item.xhtml?faces-redirect=true";
	}
	
	public String submitOrder() {
		this.orderNumber = randomNumberInRange(1, 200);
		this.orderCustomerID = getLoggedInCustomerID();
		
		Order newOrder = new Order(this.orderNumber, this.orderCustomerID, this.totalOrderPrice, this.items);
		
		int addOrderResult = addOrder(newOrder);
		int updateAmountResult = updateProductAmount(newOrder.getOrderItems());
		if (addOrderResult == 1 && updateAmountResult == 1) {
			this.addOrderSuccess = true;
			this.addOrderFailure = false;
		}
		else {
			this.addOrderFailure = true;
			this.addOrderSuccess = false;
		}
		return "/customerRestricted/add-order.xhtml?faces-redirect=true";
	}
	
	public void cancelOrder() {
		items.clear();
		totalOrderPrice = 0;
		addItemFailure = false;
		addItemSuccess = false;
		duplicateItem = false;
	}
		
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
    public void parametersAction(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        productItemNumber = Integer.parseInt(params.get("productItemNumber"));
    }
	
}