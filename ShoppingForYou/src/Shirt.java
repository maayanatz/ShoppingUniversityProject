import javax.faces.bean.ManagedBean;

@ManagedBean
public class Shirt extends Product{

	public Shirt(int catalogNumber, String description, String category, float price, float discount, float finalPrice,
			String image, String size, int amount, ItemInOrder productOrder) {
		super(catalogNumber, description, category, price, discount, finalPrice, image, size, amount, productOrder);
	}
	
}
