import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "itemInOrder", eager = true)
public class ItemInOrder {

	private int itemOrderID;
	private int itemOrderNumber;
	private float totalPrice;
	private int amountInOrder;
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
	
	public ItemInOrder() {
	}

	public ItemInOrder(int itemOrderID, int itemOrderNumber, Product itemProduct, int amountInOrder) {
		super();
		this.itemOrderID = itemOrderID;
		this.itemOrderNumber = itemOrderNumber;
		this.itemProduct = itemProduct;
		this.amountInOrder = amountInOrder;
		this.setTotalPrice();
	}

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

	/**
	 * @return the catalogNumber
	 */
	public int getCatalogNumber() {
		if(this.itemProduct != null) {
			this.catalogNumber = this.itemProduct.getCatalogNumber();
			}
		return this.catalogNumber;
	}

	/**
	 * @param catalogNumber the catalogNumber to set
	 */
	public void setCatalogNumber(int catalogNumber) {
		this.itemProduct.setCatalogNumber(catalogNumber);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		if(this.itemProduct != null) {
			this.description = this.itemProduct.getDescription();
			}
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.itemProduct.setDescription(description);
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		if(this.itemProduct != null) {
			this.category = this.itemProduct.getCategory();
			}
		return this.category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.itemProduct.setCategory(category);
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		if(this.itemProduct != null) {
			this.price = this.itemProduct.getPrice();
			}
		return this.price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.itemProduct.setPrice(price);
	}

	/**
	 * @return the discount
	 */
	public float getDiscount() {
		if(this.itemProduct != null) {
			this.discount = this.itemProduct.getDiscount();
			}
		return this.discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(float discount) {
		this.itemProduct.setDiscount(discount);
	}

	/**
	 * @return the finalPrice
	 */
	public float getFinalPrice() {
		if(this.itemProduct != null) {
			this.finalPrice = this.itemProduct.getFinalPrice();
			}
		return this.finalPrice;
	}

	/**
	 * @param finalPrice the finalPrice to set
	 */
	public void setFinalPrice(float finalPrice) {
		this.itemProduct.setFinalPrice(finalPrice);
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		if(this.itemProduct != null) {
			this.image = this.itemProduct.getImage();
			}
		return this.image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.itemProduct.setImage(image);
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		if(this.itemProduct != null) {
			this.size = this.itemProduct.getSize();
			}
		return this.size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.itemProduct.setSize(size);
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		if(this.itemProduct != null) {
			this.amount = this.itemProduct.getAmount();
			}
		return this.amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.itemProduct.setAmount(amount);
	}

	/**
	 * @return the soldOut
	 */
	public boolean isSoldOut() {
		if(this.itemProduct != null) {
			this.soldOut = this.itemProduct.isSoldOut();
			}
		return this.soldOut;
	}

	/**
	 * @param soldOut the soldOut to set
	 */
	public void setSoldOut(boolean soldOut) {
		this.itemProduct.setSoldOut(soldOut);
	}

	/**
	 * @return the onSale
	 */
	public boolean isOnSale() {
		if(this.itemProduct != null) {
			this.onSale = this.itemProduct.isOnSale();
			}
		return this.onSale;
	}

	/**
	 * @param onSale the onSale to set
	 */
	public void setOnSale(boolean onSale) {
		this.itemProduct.setOnSale(onSale);
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
	public void setTotalPrice() {
		if(this.itemProduct != null) {
			this.totalPrice = this.itemProduct.getFinalPrice() * this.amountInOrder;
		}
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
}
