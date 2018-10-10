import javax.faces.bean.ManagedBean;

@ManagedBean
public class Product {

	protected int catalogNumber;
	protected String description;
	protected String category;
	protected float price;
	protected float discount;
	protected float finalPrice;
	protected String image;
	protected float size;
	protected int amount;

	public Product() {
	}
	
	public Product(int catalogNumber, String description, String category, float price,
			float discount, float finalPrice, String image, float size, int amount) {
		
		switch (category) {
        case "Shirts":
        	Product newShirt = new Shirt(catalogNumber, description, category, price,
        			discount, finalPrice, image, size, amount);
            break;
        case "Skirts":
        	Product newSkirt = new Dress(catalogNumber, description, category, price,
        			discount, finalPrice, image, size, amount);
        	break;
        case "Dresses":
        	Product newDress = new Dress(catalogNumber, description, category, price,
        			discount, finalPrice, image, size, amount);
        	break;
        case "Shoes":
        	Product newShoe = new Shoe(catalogNumber, description, category, price,
        			discount, finalPrice, image, size, amount);
        	break;
        default:
        {
    		this.catalogNumber = catalogNumber;
    		this.description = description;
    		this.category = category;
    		this.price = price;
    		this.discount = discount;
    		this.finalPrice = finalPrice;
    		this.image = image;
    		this.size = size;
    		this.amount = amount;
    		break;
        }
		}
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
	public float getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(float size) {
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