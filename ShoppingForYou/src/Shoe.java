import javax.faces.bean.ManagedBean;

@ManagedBean
public class Shoe extends Product{

	public Shoe(int catalogNumber, String description, String category, float price, float discount, float finalPrice,
			String image, String size, int amount) {
		super(catalogNumber, description, category, price, discount, finalPrice, image, size, amount);
	}	
}
