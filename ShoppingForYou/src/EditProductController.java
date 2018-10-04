import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class EditProductController {

	private List<Product> products;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public EditProductController() throws Exception {
		products = new ArrayList<>();
		
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void loadProducts() {

		logger.info("Loading products");
		
		products.clear();

		try {
			
			// get all products from database
			products = shoppingDbUtil.getProducts();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading products", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addProduct(Product theProduct) {

		logger.info("Adding product: " + theProduct);

		try {
			
			// add product to the database
			shoppingDbUtil.addProduct(theProduct);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding products", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "edit-products?faces-redirect=true";
	}

	public String loadProduct(int catalogNumber) {
		
		logger.info("loading product: " + catalogNumber);
		
		try {
			// get product from database
			Product theProduct = shoppingDbUtil.getProduct(catalogNumber);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("product", theProduct);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading product number:" + catalogNumber, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "update-product-form.xhtml";
	}	
	
	public String updateProduct(Product theProduct) {

		logger.info("updating product: " + theProduct);
		
		try {
			
			// update product in the database
			shoppingDbUtil.updateProduct(theProduct);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating product: " + theProduct, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "edit-products?faces-redirect=true";		
	}
	
	public String deleteProduct(Product theProduct) {

		logger.info("Deleting product number: " + theProduct.getCatalogNumber());
		
		try {

			// delete the product from the database
			shoppingDbUtil.deleteProduct(theProduct);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting product number: " + theProduct.getCatalogNumber(), exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "edit-products";	
	}
		
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}