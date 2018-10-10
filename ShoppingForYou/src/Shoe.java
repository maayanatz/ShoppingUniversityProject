import javax.faces.bean.ManagedBean;

@ManagedBean
public class Shoe extends Product{

	public Shoe(int catalogNumber, String description, String category, float price, float discount, float finalPrice,
			String image, float size, int amount) {
		this.catalogNumber = catalogNumber;
		this.description = description;
		this.category = category;
		this.price = price;
		this.discount = discount;
		this.finalPrice = finalPrice;
		this.image = image;
		this.size = size;
		this.amount = amount;
	}
	
}
