import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@SessionScoped
@ManagedBean(name = "adminLoginController", eager = true)
public class AdminLoginController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String currentEmail;
	private String currentPass;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public AdminLoginController() throws Exception {
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
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	//validate login
	public String adminLogin() {
		
		logger.info("validating admin credentials: " + currentEmail + currentPass);
		
		try {
			boolean valid = shoppingDbUtil.validateAdmin(currentEmail, currentPass);
			
			if (valid) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("currentEmail", currentEmail);
				return "admin.xhtml?faces-redirect=true";
			}
			else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "login-admin.xhtml?faces-redirect=true";
			}
			} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error in validating admin credentials.", exc);
			// add error message for JSF page
			addErrorMessage(exc);
			return "login-admin.xhtml?faces-redirect=true";
		}
	}

	//logout event, invalidate session
	public String adminLogout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		clearAdminReferences();
		return "home-page.xhtml?faces-redirect=true";
	}

	private void clearAdminReferences() {
		currentEmail = null;
		currentPass = null;
	}
}