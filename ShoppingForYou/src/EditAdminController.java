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
public class EditAdminController {

	private List<Administrator> administrators;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public EditAdminController() throws Exception {
		administrators = new ArrayList<>();
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the administrators
	 */
	public List<Administrator> getAdministrators() {
		return administrators;
	}

	/**
	 * @param administrators the administrators to set
	 */
	public void setAdministrators(List<Administrator> administrators) {
		this.administrators = administrators;
	}

	/**
	 * @return the shoppingDbUtil
	 */
	public ShoppingDbUtil getShoppingDbUtil() {
		return shoppingDbUtil;
	}

	/**
	 * @param shoppingDbUtil the shoppingDbUtil to set
	 */
	public void setShoppingDbUtil(ShoppingDbUtil shoppingDbUtil) {
		this.shoppingDbUtil = shoppingDbUtil;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void loadAdministrators() {

		logger.info("Loading administrators");
		
		administrators.clear();

		try {
			
			// get all administrators from database
			administrators = shoppingDbUtil.getAdministrators();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading administrators", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public String addAdministrator(Administrator theAdministrator) {

		logger.info("Adding administrator: " + theAdministrator);

		try {
			
			// add product to the database
			shoppingDbUtil.addAdministrator(theAdministrator);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding administrator", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "edit-administrators?faces-redirect=true";
	}

	public String loadAdministrator(int adminID) {
		
		logger.info("loading administrator: " + adminID);
		
		try {
			// get product from database
			Administrator theAdministrator = shoppingDbUtil.getAdministrator(adminID);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("administrator", theAdministrator);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading administrator id:" + adminID, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "update-admin-form.xhtml";
	}	
	
	public String updateAdministrator(Administrator theAdministrator) {

		logger.info("updating administrator: " + theAdministrator);
		
		try {
			
			// update administrator in the database
			shoppingDbUtil.updateAdministrator(theAdministrator);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating administrator: " + theAdministrator, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "edit-administrators?faces-redirect=true";		
	}
	
	public String deleteAdministrator(int adminID) {

		logger.info("Deleting administrator ID: " + adminID);
		
		try {

			// delete the administrator from the database
			shoppingDbUtil.deleteAdministrator(adminID);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting administrator ID: " + adminID, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "edit-administrators";	
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}