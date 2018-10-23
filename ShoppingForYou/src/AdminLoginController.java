import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.w3c.dom.UserDataHandler;

@SessionScoped
@ManagedBean
public class AdminLoginController {
	
	private List<Administrator> administrators;
	private Administrator currentAdmin;
	private String currentEmail;
	private String currentPass;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public AdminLoginController() throws Exception {
		administrators = new ArrayList<>();
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the currentEmail
	 */
	public String getCurrentEmail() {
		return currentEmail;
	}

	/**
	 * @param currentEmail the currentEmail to set
	 */
	public void setCurrentEmail(String currentEmail) {
		this.currentEmail = currentEmail;
	}

	/**
	 * @return the currentPass
	 */
	public String getCurrentPass() {
		return currentPass;
	}

	/**
	 * @param currentPass the currentPass to set
	 */
	public void setCurrentPass(String currentPass) {
		this.currentPass = currentPass;
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
	
	/**
	 * @return the currentAdmin
	 */
	public Administrator getCurrentAdmin() {
		return currentAdmin;
	}

	/**
	 * @param currentAdmin the currentAdmin to set
	 */
	public void setCurrentAdmin(Administrator currentAdmin) {
		this.currentAdmin = currentAdmin;
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
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

	public String adminLogin() {
		this.loadAdministrators();
		for (Administrator administrator : this.getAdministrators()) {
			   if (administrator.getEmail().equals(this.getCurrentEmail()))
			   {
				   if (administrator.getPassword().equals(this.getCurrentPass()))
				   {
					   this.setCurrentAdmin(administrator);
					   FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentAdmin", currentAdmin);
			           return "inner/index.xhtml?faces-redirect=true";
				   }
				   else
				   {
					   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password is invalid"));
					   return "index.xhtml";
				   }
			   }
			}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("This administrator does not exist!"));
		return "index.xhtml";
	}
	
    public String adminLogout(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("currentAdmin");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.setCurrentAdmin(null);
        return "index.xhtml?faces-redirect=true";
    }
}