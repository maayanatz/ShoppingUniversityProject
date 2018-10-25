import javax.faces.bean.ManagedBean;

@ManagedBean(name = "product", eager = true)
public class Product {

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
	
	public Product() {
	}
	
	public Product(int catalogNumber, String description, String category, float price,
			float discount, float finalPrice, String image, String size, int amount) {
		this.catalogNumber = catalogNumber;
		this.description = description;
		this.category = category;
		this.price = price;
		this.discount = discount;
		this.finalPrice = finalPrice;
		this.image = image;
		this.size = size;
		this.amount = amount;
		if (this.amount > 0)
		{
			this.soldOut = false;
		}
		else
		{
			this.soldOut = true;
		}
		if (this.discount > 0)
		{
			this.onSale = true;
		}
		else
		{
			this.onSale = false;
		}
		amountInOrder = 0;
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
}