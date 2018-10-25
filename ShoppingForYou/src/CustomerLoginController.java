import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@SessionScoped
@ManagedBean(name = "customerLoginController", eager = true)
public class CustomerLoginController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static String currentEmail;
	private String currentPass;
	private boolean loggedIn;
	private boolean loginFailure;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public CustomerLoginController() throws Exception {
		loggedIn = false;
		loginFailure = false;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the loginFailure
	 */
	public boolean isLoginFailure() {
		return loginFailure;
	}

	/**
	 * @param loginFailure the loginFailure to set
	 */
	public void setLoginFailure(boolean loginFailure) {
		this.loginFailure = loginFailure;
	}

	/**
	 * @return the isLoggedIn
	 */
	public boolean getLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param isLoggedIn the isLoggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * @return the currentEmail
	 */
	public static String getCurrentEmail() {
		return currentEmail;
	}

	/**
	 * @param currentEmail the currentEmail to set
	 */
	public static void setCurrentEmail(String currentEmail) {
		CustomerLoginController.currentEmail = currentEmail;
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
	public String customerLogin() {
		
		logger.info("validating customer credentials: " + currentEmail + currentPass);
		
		try {
			boolean valid = shoppingDbUtil.validateCustomer(currentEmail, currentPass);
			
			if (valid) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("currentEmail", currentEmail);
				setLoggedIn(true);
				setLoginFailure(false);
				return "/customerRestricted/logout-customer.xhtml?faces-redirect=true";
			}
			else {
				clearCustomerReferences();
				setLoginFailure(true);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "login-customer.xhtml?faces-redirect=true";
			}
			} catch (Exception exc) {
				clearCustomerReferences();
				setLoginFailure(true);
				// send this to server logs
				logger.log(Level.SEVERE, "Error in validating customer credentials.", exc);
				// add error message for JSF page
				addErrorMessage(exc);
				return "login-customer.xhtml?faces-redirect=true";
		}
	}

	//logout event, invalidate session
	public String customerLogout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		clearCustomerReferences();
		setLoginFailure(false);
		return "/home-page.xhtml?faces-redirect=true";
	}

	private void clearCustomerReferences() {
		setCurrentEmail(null);
		setCurrentPass(null);
		setLoggedIn(false);
	}
}