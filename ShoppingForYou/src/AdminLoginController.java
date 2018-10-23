import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@SessionScoped
@ManagedBean(name = "AdminLoginController", eager = true)
public class AdminLoginController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Administrator> administrators;
	@ManagedProperty(value="#{Administrator}")
	private Administrator currentAdmin;
	private String currentEmail;
	private String currentPass;
	private int adminID;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int phoneNumber;
	private ShoppingDbUtil shoppingDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public AdminLoginController() throws Exception {
		administrators = new ArrayList<>();
		currentAdmin = null;
		shoppingDbUtil = ShoppingDbUtil.getInstance();
	}
	
	/**
	 * @return the adminID
	 */
	public int getAdminID() {
		if(this.currentAdmin != null) {
			this.adminID = this.currentAdmin.getAdminID();
			}
		return this.adminID;
	}

	/**
	 * @param adminID the adminID to set
	 */
	public void setAdminID(int adminID) {
		this.currentAdmin.setAdminID(adminID);
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		if(this.currentAdmin != null) {
			this.firstName = this.currentAdmin.getFirstName();
			}
		return this.firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.currentAdmin.setFirstName(firstName);
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		if(this.currentAdmin != null) {
			this.lastName = this.currentAdmin.getLastName();
			}
		return this.lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.currentAdmin.setLastName(lastName);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		if(this.currentAdmin != null) {
			this.email = this.currentAdmin.getEmail();
			}
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.currentAdmin.setEmail(email);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		if(this.currentAdmin != null) {
			this.password = this.currentAdmin.getPassword();
			}
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.currentAdmin.setPassword(password);
	}

	/**
	 * @return the phoneNumber
	 */
	public int getPhoneNumber() {
		if(this.currentAdmin != null) {
			this.phoneNumber = this.currentAdmin.getPhoneNumber();
			}
		return this.phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(int phoneNumber) {
		this.currentAdmin.setPhoneNumber(phoneNumber);
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
	

	//validate login
	public String adminLogin() {
		
		logger.info("validating admin credentials: " + currentEmail + currentPass);
		
		try {
			currentAdmin = shoppingDbUtil.validateAdmin(currentEmail, currentPass);
			
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("currentAdmin", currentAdmin);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Incorrect Username and Passowrd", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return "login-admin.xhtml";
		}
		return "login-admin.xhtml";
	}

	//logout event, invalidate session
	public String adminLogout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login-admin.xhtml";
	}
}