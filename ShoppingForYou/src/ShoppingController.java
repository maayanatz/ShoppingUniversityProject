import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ShoppingController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Customer> customers;
	private List<Product> products;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ShoppingController() throws Exception {
		customers = new ArrayList<>();
		products = new ArrayList<>();
		
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}

	public void loadCustomers() {

		logger.info("Loading customers");
		
		customers.clear();

		try {
			
			// get all customers from database
			customers = shoppingDbUtil.getCustomers();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading customers", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public void loadProducts() {

		logger.info("Loading products");
		
		products.clear();

		try {
			
			// get all customers from database
			products = shoppingDbUtil.getProducts();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading products", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
				
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}