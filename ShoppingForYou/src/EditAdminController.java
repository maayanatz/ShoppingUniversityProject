import java.io.Serializable;
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
public class EditAdminController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Administrator> administrators;
	private boolean addNewAdminFailure;
	private boolean addNewAdminSuccess;
	private ShoppingDbUtil shoppingDbUtil;	
	private boolean duplicateID;
	private boolean duplicateEmail;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public EditAdminController() throws Exception {
		administrators = new ArrayList<>();
		addNewAdminFailure = false;
		addNewAdminSuccess = false;
		duplicateID = false;
		duplicateEmail = false;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the addNewAdminFailure
	 */
	public boolean isAddNewAdminFailure() {
		return addNewAdminFailure;
	}

	/**
	 * @param addNewAdminFailure the addNewAdminFailure to set
	 */
	public void setAddNewAdminFailure(boolean addNewAdminFailure) {
		this.addNewAdminFailure = addNewAdminFailure;
	}

	/**
	 * @return the addNewAdminSuccess
	 */
	public boolean isAddNewAdminSuccess() {
		return addNewAdminSuccess;
	}

	/**
	 * @param addNewAdminSuccess the addNewAdminSuccess to set
	 */
	public void setAddNewAdminSuccess(boolean addNewAdminSuccess) {
		this.addNewAdminSuccess = addNewAdminSuccess;
	}

	/**
	 * @return the duplicateID
	 */
	public boolean isDuplicateID() {
		return duplicateID;
	}

	/**
	 * @param duplicateID the duplicateID to set
	 */
	public void setDuplicateID(boolean duplicateID) {
		this.duplicateID = duplicateID;
	}

	/**
	 * @return the duplicateEmail
	 */
	public boolean isDuplicateEmail() {
		return duplicateEmail;
	}

	/**
	 * @param duplicateEmail the duplicateEmail to set
	 */
	public void setDuplicateEmail(boolean duplicateEmail) {
		this.duplicateEmail = duplicateEmail;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	private synchronized int validationChecks(Administrator theAdmin) {
		int tempID;
		String tempEmail;
		List<Administrator> currentAdmins = null;
		
		int id = theAdmin.getAdminID();
		String email = theAdmin.getEmail();
		
		duplicateID = false;
		duplicateEmail = false;
		
		try {
			
			logger.info("Loading current admins");
			currentAdmins = shoppingDbUtil.getAdministrators();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error getting admins", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return 0;
		}
		
		for (int i = 0; i < currentAdmins.size(); i++) {

			tempID = currentAdmins.get(i).getAdminID();
			tempEmail = currentAdmins.get(i).getEmail();
			
			if (id == tempID)
			{
				duplicateID = true;
			}
			if (email.equals(tempEmail)) {
				duplicateEmail = true;
			}
			
			if (duplicateID == true | duplicateEmail == true ) {
				return 0;
			}
		}	
		return 1;
	}
	
	public synchronized String addAdministrator(Administrator theAdministrator) {
		int validationChecksResult = validationChecks(theAdministrator);
		
		if (validationChecksResult == 0) {
			this.addNewAdminFailure = true;
			this.addNewAdminSuccess = false;
			return "add-admin-result?faces-redirect=true";
		}
		
		logger.info("Adding administrator: " + theAdministrator);

		try {
			
			// add administrator to the database
			shoppingDbUtil.addAdministrator(theAdministrator);
			
		} catch (Exception exc) {
			this.addNewAdminFailure = true;
			this.addNewAdminSuccess = false;
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding administrator", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		this.addNewAdminFailure = false;
		this.addNewAdminSuccess = true;
		
		return "add-admin-result?faces-redirect=true";
	}

	public String loadAdministrator(int adminID) {
		
		logger.info("loading administrator: " + adminID);
		
		try {
			// get administrator from database
			Administrator theAdministrator = shoppingDbUtil.getAdministrator(adminID);
			
			// put in the request attribute, so we can use it on the form page
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