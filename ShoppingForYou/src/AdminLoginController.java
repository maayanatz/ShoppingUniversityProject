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
	private static String currentAdminEmail;
	private String currentAdminPass;
	private boolean adminLoggedIn;
	private boolean adminLoginFailure;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public AdminLoginController() throws Exception {
		adminLoggedIn = false;
		adminLoginFailure = false;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the loginFailure
	 */
	public boolean isAdminLoginFailure() {
		return adminLoginFailure;
	}

	/**
	 * @param loginFailure the loginFailure to set
	 */
	public void setAdminLoginFailure(boolean loginFailure) {
		this.adminLoginFailure = loginFailure;
	}

	/**
	 * @return the isLoggedIn
	 */
	public boolean getAdminLoggedIn() {
		return adminLoggedIn;
	}

	/**
	 * @param isLoggedIn the isLoggedIn to set
	 */
	public void setAdminLoggedIn(boolean loggedIn) {
		this.adminLoggedIn = loggedIn;
	}

	/**
	 * @return the currentEmail
	 */
	public static String getAdminCurrentEmail() {
		return currentAdminEmail;
	}

	/**
	 * @param currentEmail the currentEmail to set
	 */
	public static void setAdminCurrentEmail(String currentEmail) {
		AdminLoginController.currentAdminEmail = currentEmail;
	}

	/**
	 * @return the currentPass
	 */
	public String getAdminCurrentPass() {
		return currentAdminPass;
	}

	/**
	 * @param currentPass the currentPass to set
	 */
	public void setAdminCurrentPass(String currentPass) {
		this.currentAdminPass = currentPass;
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
		
		logger.info("validating admin credentials: " + currentAdminEmail + currentAdminPass);
		
		try {
			boolean valid = shoppingDbUtil.validateAdmin(currentAdminEmail, currentAdminPass);
			
			if (valid) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("currentAdminEmail", currentAdminEmail);
				setAdminLoggedIn(true);
				setAdminLoginFailure(false);
				return "/adminRestricted/logout-admin.xhtml?faces-redirect=true";
			}
			else {
				clearAdminReferences();
				setAdminLoginFailure(true);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "login-admin.xhtml?faces-redirect=true";
			}
			} catch (Exception exc) {
				clearAdminReferences();
				setAdminLoginFailure(true);
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
		setAdminLoginFailure(false);
		return "/home-page.xhtml?faces-redirect=true";
	}

	private void clearAdminReferences() {
		setAdminCurrentEmail(null);
		setAdminCurrentPass(null);
		setAdminLoggedIn(false);
	}
}