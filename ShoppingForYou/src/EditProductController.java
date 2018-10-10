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
	private List<Shirt> shirts;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public EditProductController() throws Exception {
		products = new ArrayList<>();
		shirts = new ArrayList<>();
		
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public List<Shirt> getShirts() {
		return shirts;
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
	
	public void loadShirts() {

		logger.info("Loading shirts");
		
		shirts.clear();

		try {
			
			// get all shirts from database
			shirts = shoppingDbUtil.getShirts();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading shirts", exc);
			
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
		products.clear();
		
		try {
			// get product from database
			products = shoppingDbUtil.getProduct(catalogNumber);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("product", products);	
			
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
	
	public String decreaseProduct(Product theProduct) {

		logger.info("Decreasing amount of product number: " + theProduct.getCatalogNumber());
		
		try {

			// delete the product from the database
			theProduct.setAmount((theProduct.getAmount()) - 1);
			shoppingDbUtil.decreaseProduct(theProduct);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error decreasing amount of product number: " + theProduct.getCatalogNumber(), exc);
			
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
	
	public String loadProductDetails(int catalogNumber) {
		
		logger.info("loading details of product: " + catalogNumber);
		
		products.clear();
		
		try {
			// get product from database
			products = shoppingDbUtil.getProduct(catalogNumber);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("product", products);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading product number:" + catalogNumber, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "product-details.xhtml";
	}	
	
}