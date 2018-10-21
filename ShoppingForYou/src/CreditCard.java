//import java.sql.Date;
import java.util.Date;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "creditCard", eager = true)
public class CreditCard {
	private String cardNumber;
	private int cardCustomer;
	private int cardOwner;
	private Date expiration;
	private int cvv;
	
	public CreditCard() {
	}
	
	public CreditCard(String cardNumber, int cardCustomer, int cardOwner, Date expiration, int cvv) {
		super();
		this.cardNumber = cardNumber;
		this.cardCustomer = cardCustomer;
		this.cardOwner = cardOwner;
		this.expiration = expiration;
		this.cvv = cvv;
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the cardCustomer
	 */
	public int getCardCustomer() {
		return cardCustomer;
	}

	/**
	 * @param cardCustomer the cardCustomer to set
	 */
	public void setCardCustomer(int cardCustomer) {
		this.cardCustomer = cardCustomer;
	}

	/**
	 * @return the cardOwner
	 */
	public int getCardOwner() {
		return cardOwner;
	}

	/**
	 * @param cardOwner the cardOwner to set
	 */
	public void setCardOwner(int cardOwner) {
		this.cardOwner = cardOwner;
	}

	/**
	 * @return the expiration
	 */
	public Date getExpiration() {
		return expiration;
	}

	/**
	 * @param expiration the expiration to set
	 */
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	/**
	 * @return the cvv
	 */
	public int getCvv() {
		return cvv;
	}

	/**
	 * @param cvv the cvv to set
	 */
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
}
