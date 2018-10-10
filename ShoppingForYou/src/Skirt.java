import javax.faces.bean.ManagedBean;

@ManagedBean
public class Skirt extends Product{

	public Skirt(int catalogNumber, String description, String category, float price, float discount, float finalPrice,
			String image, float size, int amount) {
		super(catalogNumber, description, category, price, discount, finalPrice, image, size, amount);
	}	
}
