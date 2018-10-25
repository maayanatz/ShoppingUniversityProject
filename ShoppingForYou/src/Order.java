import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "order", eager = true)
public class Order {
	
	private int orderNumber;
	private int orderCustomerID;
	private float totalPrice;
	private List<ItemInOrder> orderItems;
		
	public Order() {
	}

	public Order(int orderNumber, int orderCustomerID, float totalPrice, List<ItemInOrder> orderItems) {
		super();
		this.orderNumber = orderNumber;
		this.orderCustomerID = orderCustomerID;
		this.totalPrice = totalPrice;
		this.orderItems = orderItems;
	}

	/**
	 * @return the orderItems
	 */
	public List<ItemInOrder> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(List<ItemInOrder> orderItems) {
		this.orderItems = orderItems;
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
