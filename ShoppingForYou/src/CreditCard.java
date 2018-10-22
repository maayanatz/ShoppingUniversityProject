import javax.faces.bean.ManagedBean;

@ManagedBean(name = "creditCard", eager = true)
public class CreditCard {
	private String cardNumber;
	private int cardCustomer;
	private int cardOwner;
	
	public CreditCard() {
	}
	
	public CreditCard(String cardNumber, int cardCustomer, int cardOwner) {
		super();
		this.cardNumber = cardNumber;
		this.cardCustomer = cardCustomer;
		this.cardOwner = cardOwner;
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
}
