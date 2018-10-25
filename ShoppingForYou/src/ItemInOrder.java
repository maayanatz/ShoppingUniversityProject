import javax.faces.bean.ManagedBean;

@ManagedBean(name = "itemInOrder", eager = true)
public class ItemInOrder {

	private int itemOrderID;
	private int itemCatalogNumber;
	private int itemOrderNumber;
	private int itemAmount;
	private float itemPrice;
	private float totalPrice;
	
	public ItemInOrder() {
	}

	public ItemInOrder(int itemOrderID, int itemCatalogNumber, int itemOrderNumber, int itemAmount,
			float itemPrice, float totalPrice) {
		super();
		this.itemOrderID = itemOrderID;
		this.itemCatalogNumber = itemCatalogNumber;
		this.itemOrderNumber = itemOrderNumber;
		this.itemAmount = itemAmount;
		this.itemPrice = itemPrice;
		this.totalPrice = totalPrice;
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

	/**
	 * @return the itemOrderID
	 */
	public int getItemOrderID() {
		return itemOrderID;
	}

	/**
	 * @param itemOrderID the itemOrderID to set
	 */
	public void setItemOrderID(int itemOrderID) {
		this.itemOrderID = itemOrderID;
	}

	/**
	 * @return the itemCatalogNumber
	 */
	public int getItemCatalogNumber() {
		return itemCatalogNumber;
	}

	/**
	 * @param itemCatalogNumber the itemCatalogNumber to set
	 */
	public void setItemCatalogNumber(int itemCatalogNumber) {
		this.itemCatalogNumber = itemCatalogNumber;
	}

	/**
	 * @return the itemOrderNumber
	 */
	public int getItemOrderNumber() {
		return itemOrderNumber;
	}

	/**
	 * @param itemOrderNumber the itemOrderNumber to set
	 */
	public void setItemOrderNumber(int itemOrderNumber) {
		this.itemOrderNumber = itemOrderNumber;
	}

	/**
	 * @return the itemAmount
	 */
	public int getItemAmount() {
		return itemAmount;
	}

	/**
	 * @param itemAmount the itemAmount to set
	 */
	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}

	/**
	 * @return the itemPrice
	 */
	public float getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param itemTotalPrice the itemPrice to set
	 */
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
}
