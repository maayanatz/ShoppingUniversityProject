import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "itemInOrder", eager = true)
public class ItemInOrder {

	private int itemOrderID;
	private int itemOrderNumber;
	private int itemOrderAmount;
	private float totalPrice;
	@ManagedProperty(value="#{Product}")
	Product itemProduct;
	protected int catalogNumber;
	protected String description;
	protected String category;
	protected float price;
	protected float discount;
	protected float finalPrice;
	protected String image;
	protected String size;
	protected int amount;
	protected boolean soldOut;
	protected boolean onSale;
	protected int amountInOrder;
	
	public ItemInOrder() {
	}

	public ItemInOrder(int itemOrderID, int itemOrderNumber, int itemOrderAmount, 
			float totalPrice, Product itemProduct) {
		super();
		this.itemOrderID = itemOrderID;
		this.itemOrderNumber = itemOrderNumber;
		this.itemOrderAmount = itemOrderAmount;
		this.totalPrice = totalPrice;
		this.itemProduct = itemProduct;
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
	 * @return the itemProduct
	 */
	public Product getItemProduct() {
		return itemProduct;
	}

	/**
	 * @param itemProduct the itemProduct to set
	 */
	public void setItemProduct(Product itemProduct) {
		this.itemProduct = itemProduct;
	}

	
	//////////////////////////TODO///////////////////////////////
	/**
	 * @return the catalogNumber
	 */
	public int getCatalogNumber() {
		return catalogNumber;
	}

	/**
	 * @param catalogNumber the catalogNumber to set
	 */
	public void setCatalogNumber(int catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the discount
	 */
	public float getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(float discount) {
		this.discount = discount;
	}

	/**
	 * @return the finalPrice
	 */
	public float getFinalPrice() {
		return finalPrice;
	}

	/**
	 * @param finalPrice the finalPrice to set
	 */
	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the soldOut
	 */
	public boolean isSoldOut() {
		return soldOut;
	}

	/**
	 * @param soldOut the soldOut to set
	 */
	public void setSoldOut(boolean soldOut) {
		this.soldOut = soldOut;
	}

	/**
	 * @return the onSale
	 */
	public boolean isOnSale() {
		return onSale;
	}

	/**
	 * @param onSale the onSale to set
	 */
	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
	//////////////////////////TODO///////////////////////////////

	/**
	 * @return the amountInOrder
	 */
	public int getAmountInOrder() {
		return amountInOrder;
	}

	/**
	 * @param amountInOrder the amountInOrder to set
	 */
	public void setAmountInOrder(int amountInOrder) {
		this.amountInOrder = amountInOrder;
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
		return itemOrderAmount;
	}

	/**
	 * @param itemAmount the itemAmount to set
	 */
	public void setItemAmount(int itemAmount) {
		this.itemOrderAmount = itemAmount;
	}
}
