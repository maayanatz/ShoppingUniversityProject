import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "order", eager = true)
public class Order {
	
	private int orderNumber;
	private int orderCustomerID;
	private float totalPrice;
	@ManagedProperty(value="#{itemInOrder}")
	private ItemInOrder orderItems;
	private int itemOrderID;
	private int itemCatalogNumber;
	private int itemOrderNumber;
	private int itemAmount;
	private float itemTotalPrice;
	
	public Order() {
	}

	public Order(int orderNumber, int orderCustomerID, float totalPrice, ItemInOrder orderItems) {
		super();
		this.orderNumber = orderNumber;
		this.orderCustomerID = orderCustomerID;
		this.totalPrice = totalPrice;
		this.orderItems = orderItems;
	}
	
	/**
	 * @return the orderItems
	 */
	public ItemInOrder getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(ItemInOrder orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * @return the itemOrderID
	 */
	public int getItemOrderID() {
		if(this.orderItems != null) {
			this.itemOrderID = this.orderItems.getItemOrderID();
			}
		return this.itemOrderID;
	}

	/**
	 * @param itemOrderID the itemOrderID to set
	 */
	public void setItemOrderID(int itemOrderID) {
		this.orderItems.setItemOrderID(itemOrderID);
	}

	/**
	 * @return the itemCatalogNumber
	 */
	public int getItemCatalogNumber() {
		if(this.orderItems != null) {
			this.itemCatalogNumber = this.orderItems.getItemCatalogNumber();
			}
		return this.itemCatalogNumber;
	}

	/**
	 * @param itemCatalogNumber the itemCatalogNumber to set
	 */
	public void setItemCatalogNumber(int itemCatalogNumber) {
		this.orderItems.setItemCatalogNumber(itemCatalogNumber);
	}

	/**
	 * @return the itemOrderNumber
	 */
	public int getItemOrderNumber() {
		if(this.orderItems != null) {
			this.itemOrderNumber = this.orderItems.getItemOrderNumber();
			}
		return this.itemOrderNumber;
	}

	/**
	 * @param itemOrderNumber the itemOrderNumber to set
	 */
	public void setItemOrderNumber(int itemOrderNumber) {
		this.orderItems.setItemOrderNumber(itemOrderNumber);
	}

	/**
	 * @return the itemAmount
	 */
	public int getItemAmount() {
		if(this.orderItems != null) {
			this.itemAmount = this.orderItems.getItemAmount();
			}
		return this.itemAmount;
	}

	/**
	 * @param itemAmount the itemAmount to set
	 */
	public void setItemAmount(int itemAmount) {
		this.orderItems.setItemAmount(itemAmount);
	}

	/**
	 * @return the itemTotalPrice
	 */
	public float getItemTotalPrice() {
		if(this.orderItems != null) {
			this.itemTotalPrice = this.orderItems.getItemTotalPrice();
			}
		return this.itemTotalPrice;
	}

	/**
	 * @param itemTotalPrice the itemTotalPrice to set
	 */
	public void setItemTotalPrice(float itemTotalPrice) {
		this.orderItems.setItemTotalPrice(itemTotalPrice);
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
	 * @return the totalPrice
	 */
	public float getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
