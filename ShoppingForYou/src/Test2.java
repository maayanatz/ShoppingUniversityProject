import javax.faces.bean.ManagedBean;

//@ManagedBean(name = "address", eager = true)
@ManagedBean
public class Test2 {
	
	private int testing2;
	private int uno;
	private int dos;
	private int tres;
	
	public Test2() {
	}

	public Test2(int testing2, int uno, int dos, int tres) {
		super();
		this.testing2 = testing2;
		this.uno = uno;
		this.dos = dos;
		this.tres = tres;
	}

	/**
	 * @return the testing2
	 */
	public int getTesting2() {
		return testing2;
	}

	/**
	 * @param testing2 the testing2 to set
	 */
	public void setTesting2(int testing2) {
		this.testing2 = testing2;
	}

	/**
	 * @return the uno
	 */
	public int getUno() {
		return uno;
	}

	/**
	 * @param uno the uno to set
	 */
	public void setUno(int uno) {
		this.uno = uno;
	}

	/**
	 * @return the dos
	 */
	public int getDos() {
		return dos;
	}

	/**
	 * @param dos the dos to set
	 */
	public void setDos(int dos) {
		this.dos = dos;
	}

	/**
	 * @return the tres
	 */
	public int getTres() {
		return tres;
	}

	/**
	 * @param tres the tres to set
	 */
	public void setTres(int tres) {
		this.tres = tres;
	}
}