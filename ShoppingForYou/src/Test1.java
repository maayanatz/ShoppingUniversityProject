import javax.faces.bean.ManagedBean;

//@ManagedBean(name = "address", eager = true)
@ManagedBean
public class Test1 {
	
	private int testing;
	private int one;
	private int two;
	private int three;
	private Test2 test2;
	
	public Test1() {
	}

	public Test1(int testing, int one, int two, int three, Test2 test2) {
		super();
		this.testing = testing;
		this.one = one;
		this.two = two;
		this.three = three;
		this.test2 = test2;
	}

	/**
	 * @return the test2
	 */
	public Test2 getTest2() {
		return test2;
	}

	/**
	 * @param test2 the test2 to set
	 */
	public void setTest2(Test2 test2) {
		this.test2 = test2;
	}

	/**
	 * @return the testing
	 */
	public int getTesting() {
		return testing;
	}

	/**
	 * @param testing the testing to set
	 */
	public void setTesting(int testing) {
		this.testing = testing;
	}

	/**
	 * @return the one
	 */
	public int getOne() {
		return one;
	}

	/**
	 * @param one the one to set
	 */
	public void setOne(int one) {
		this.one = one;
	}

	/**
	 * @return the two
	 */
	public int getTwo() {
		return two;
	}

	/**
	 * @param two the two to set
	 */
	public void setTwo(int two) {
		this.two = two;
	}

	/**
	 * @return the three
	 */
	public int getThree() {
		return three;
	}

	/**
	 * @param three the three to set
	 */
	public void setThree(int three) {
		this.three = three;
	}
}