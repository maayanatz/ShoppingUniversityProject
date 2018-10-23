import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ShoppingDbUtil {

	private static ShoppingDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/shopping_tracker";
	
	public static ShoppingDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ShoppingDbUtil();
		}
		
		return instance;
	}
	
	private ShoppingDbUtil() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	public List<Administrator> getAdministrators() throws Exception {

		List<Administrator> administrators = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from administrators order by Last_Name";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int adminID = myRs.getInt("Admin_ID");
				String firstName = myRs.getString("First_Name");
				String lastName = myRs.getString("Last_Name");
				String email = myRs.getString("Email_Address");
				String password = myRs.getString("Password");
				int phoneNumber = myRs.getInt("Phone_Number");

				// create new Administrator object

				Administrator tempAdministrator = new Administrator(adminID, firstName, lastName, email, password, phoneNumber);

				// add it to the list of administrators
				administrators.add(tempAdministrator);
			}
			
			return administrators;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public Administrator getAdministrator(int adminID) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from administrators where Admin_ID = ?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, adminID);
			
			myRs = myStmt.executeQuery();

			Administrator theAdministrator = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				// retrieve data from result set row
				int id = myRs.getInt("Admin_ID");
				String firstName = myRs.getString("First_Name");
				String lastName = myRs.getString("Last_Name");
				String email = myRs.getString("Email_Address");
				String password = myRs.getString("Password");
				int phoneNumber = myRs.getInt("Phone_Number");

				// create new Administrator object

				theAdministrator = new Administrator(id, firstName, lastName, email, password, phoneNumber);
			}
			else {
				throw new Exception("Could not find administrator id: " + adminID);
			}

			return theAdministrator;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void addAdministrator(Administrator theAdministrator) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into administrators (Admin_ID, First_Name, Last_Name, Email_Address, Password, Phone_Number) values (?, ?, ?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, theAdministrator.getAdminID());
			myStmt.setString(2, theAdministrator.getFirstName());
			myStmt.setString(3, theAdministrator.getLastName());
			myStmt.setString(4, theAdministrator.getEmail());
			myStmt.setString(5, theAdministrator.getPassword());
			myStmt.setInt(6, theAdministrator.getPhoneNumber());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	
	public void updateAdministrator(Administrator theAdministrator) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update administrators set First_Name=?, Last_Name=?, Email_Address=?, Password=?, Phone_Number=? where Admin_ID=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theAdministrator.getFirstName());
			myStmt.setString(2, theAdministrator.getLastName());
			myStmt.setString(3, theAdministrator.getEmail());
			myStmt.setString(4, theAdministrator.getPassword());
			myStmt.setInt(5, theAdministrator.getPhoneNumber());
			myStmt.setInt(6, theAdministrator.getAdminID());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	
	public void deleteAdministrator(int adminID) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from administrators where Admin_ID = ?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, adminID);
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}		
	}	
	
	
	public Test1 getTest(int testing) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from test_table natural join test_table2 where testing = ?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, testing);
			
			myRs = myStmt.executeQuery();

			Test1 theTest = null;
			
			// retrieve data from result set row
			if (myRs.next()) {

				int testing1 = myRs.getInt("testing");
				int one = myRs.getInt("one");
				int two = myRs.getInt("two");
				int three = myRs.getInt("three");
				int testing2 = myRs.getInt("testing2");
				int uno = myRs.getInt("uno");
				int dos = myRs.getInt("dos");
				int tres = myRs.getInt("tres");
				// create new address, creditCard and customer objects
				
				Test2 test2 = new Test2(testing2, uno, dos, tres);
				theTest = new Test1(testing1, one, two, three, test2);
				
			}
			else {
				throw new Exception("Could not find test id: " + testing);
			}

			return theTest;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Test1> getTests() throws Exception
	{
		List<Test1> tests = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from test_table natural join test_table2";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int testing = myRs.getInt("testing");
				int one = myRs.getInt("one");
				int two = myRs.getInt("two");
				int three = myRs.getInt("three");
				int testing2 = myRs.getInt("testing2");
				int uno = myRs.getInt("uno");
				int dos = myRs.getInt("dos");
				int tres = myRs.getInt("tres");
				// create new address, creditCard and customer objects
				
				Test2 test2 = new Test2(testing2, uno, dos, tres);
				Test1 test1 = new Test1(testing, one, two, three, test2);
				
				// add it to the list of customers
				tests.add(test1);
			}
			
			return tests;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
		
	public List<Customer> getCustomers() throws Exception {

		List<Customer> customers = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from Customers natural join addresses natural join credit_cards order by Last_Name";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("Customer_ID");
				String firstName = myRs.getString("First_Name");
				String lastName = myRs.getString("Last_Name");
				String email = myRs.getString("Email_Address");
				String password = myRs.getString("Password");
				int phoneNumber = myRs.getInt("Phone_Number");
				
				int addressID = myRs.getInt("Address_ID");
				String streetName = myRs.getString("Street_Name");
				int houseNumber = myRs.getInt("House_Number");
				int apartmentNumber = myRs.getInt("Apartment_Number");
				String city = myRs.getString("City");
				String country = myRs.getString("Country");
				int postalCode = myRs.getInt("Postal_Code");
				
				String cardNumber = myRs.getString("Credit_Card_Number");
				int cardOwner = myRs.getInt("User_ID");
				
				// create new address, creditCard and customer objects
				Address customerAddress = new Address(addressID, id, streetName, houseNumber, 
						apartmentNumber, city, country, postalCode);
				
				CreditCard customerCard = new CreditCard(cardNumber, id, cardOwner);
				
				Customer tempCustomer = new Customer(id, firstName, lastName,
						email, password, phoneNumber, customerAddress, customerCard);

				// add it to the list of customers
				customers.add(tempCustomer);
			}
			
			return customers;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addCustomer(Customer theCustomer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmtCustomers = null;
		PreparedStatement myStmtAddresses = null;
		PreparedStatement myStmtCards = null;

		try {
			myConn = getConnection();

			String sqlCustomers = "insert into customers (Customer_ID, First_Name, Last_Name, Email_Address, Password, Phone_Number) values (?, ?, ?, ?, ?, ?)";
			String sqlAddresses = "insert into addresses (Address_ID, Customer_ID, Street_Name, House_Number, Apartment_Number, City, Country, Postal_Code) values (?, ?, ?, ?, ?, ?, ?, ?)";
			String sqlCards = "insert into Credit_Cards (Credit_Card_Number, Customer_ID, User_ID) values (?, ?, ?)";
			
			myStmtCustomers = myConn.prepareStatement(sqlCustomers);
			myStmtAddresses = myConn.prepareStatement(sqlAddresses);
			myStmtCards = myConn.prepareStatement(sqlCards);
			
			// set params
			myStmtCustomers.setInt(1, theCustomer.getId());
			myStmtCustomers.setString(2, theCustomer.getFirstName());
			myStmtCustomers.setString(3, theCustomer.getLastName());
			myStmtCustomers.setString(4, theCustomer.getEmail());
			myStmtCustomers.setString(5, theCustomer.getPassword());
			myStmtCustomers.setInt(6, theCustomer.getPhoneNumber());
			
			myStmtAddresses.setInt(1, theCustomer.getCustomerAddress().getAddressID());
			myStmtAddresses.setInt(2, theCustomer.getId());
			myStmtAddresses.setString(3, theCustomer.getCustomerAddress().getStreetName());
			myStmtAddresses.setInt(4, theCustomer.getCustomerAddress().getHouseNumber());
			myStmtAddresses.setInt(5, theCustomer.getCustomerAddress().getApartmentNumber());
			myStmtAddresses.setString(6, theCustomer.getCustomerAddress().getCity());
			myStmtAddresses.setString(7, theCustomer.getCustomerAddress().getCountry());
			myStmtAddresses.setInt(8, theCustomer.getCustomerAddress().getPostalCode());
			
			myStmtCards.setString(1, theCustomer.getCustomerCard().getCardNumber());
			myStmtCards.setInt(2, theCustomer.getId());
			myStmtCards.setInt(3, theCustomer.getCustomerCard().getCardOwner());
						
			myStmtCustomers.execute();
			myStmtAddresses.execute();
			myStmtCards.execute();
		}
		finally {
			close (myConn, myStmtCustomers);
			close (myConn, myStmtAddresses);
			close (myConn, myStmtCards);
		}
		
	}
	
	public Customer getCustomer(int customerId) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from Customers natural join addresses natural join credit_cards where Customer_ID = ?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, customerId);
			
			myRs = myStmt.executeQuery();

			Customer theCustomer = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("Customer_ID");
				String firstName = myRs.getString("First_Name");
				String lastName = myRs.getString("Last_Name");
				String email = myRs.getString("Email_Address");
				String password = myRs.getString("Password");
				int phoneNumber = myRs.getInt("Phone_Number");
				
				int addressID = myRs.getInt("Address_ID");
				String streetName = myRs.getString("Street_Name");
				int houseNumber = myRs.getInt("House_Number");
				int apartmentNumber = myRs.getInt("Apartment_Number");
				String city = myRs.getString("City");
				String country = myRs.getString("Country");
				int postalCode = myRs.getInt("Postal_Code");
				
				String cardNumber = myRs.getString("Credit_Card_Number");
				int cardOwner = myRs.getInt("User_ID");
				
				// create new address, creditCard and customer objects
				Address customerAddress = new Address(addressID, id, streetName, houseNumber, 
						apartmentNumber, city, country, postalCode);
				
				CreditCard customerCard = new CreditCard(cardNumber, id, cardOwner);
				
				theCustomer = new Customer(id, firstName, lastName,
						email, password, phoneNumber, customerAddress, customerCard);
			}
			else {
				throw new Exception("Could not find customer id: " + customerId);
			}

			return theCustomer;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateCustomer(Customer theCustomer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmtCustomers = null;
		PreparedStatement myStmtAddresses = null;
		PreparedStatement myStmtCards = null;

		try {
			myConn = getConnection();

			String sqlCustomers = "update customers set First_Name=?, Last_Name=?, Email_Address=?, Password=?, Phone_Number=? where Customer_ID=?";
			String sqlAddresses = "update addresses set Address_ID=?, Street_Name=?, House_Number=?, Apartment_Number=?, City=?, Country=?, Postal_Code=? where Customer_ID=?";
			String sqlCards = "update Credit_Cards set Credit_Card_Number=?, User_ID=? where Customer_ID=?";
			
			myStmtCustomers = myConn.prepareStatement(sqlCustomers);
			myStmtAddresses = myConn.prepareStatement(sqlAddresses);
			myStmtCards = myConn.prepareStatement(sqlCards);
			
			// set params
			myStmtCustomers.setString(1, theCustomer.getFirstName());
			myStmtCustomers.setString(2, theCustomer.getLastName());
			myStmtCustomers.setString(3, theCustomer.getEmail());
			myStmtCustomers.setString(4, theCustomer.getPassword());
			myStmtCustomers.setInt(5, theCustomer.getPhoneNumber());
			myStmtCustomers.setInt(6, theCustomer.getId());
			
			myStmtAddresses.setInt(1, theCustomer.getCustomerAddress().getAddressID());
			myStmtAddresses.setString(2, theCustomer.getCustomerAddress().getStreetName());
			myStmtAddresses.setInt(3, theCustomer.getCustomerAddress().getHouseNumber());
			myStmtAddresses.setInt(4, theCustomer.getCustomerAddress().getApartmentNumber());
			myStmtAddresses.setString(5, theCustomer.getCustomerAddress().getCity());
			myStmtAddresses.setString(6, theCustomer.getCustomerAddress().getCountry());
			myStmtAddresses.setInt(7, theCustomer.getCustomerAddress().getPostalCode());
			myStmtAddresses.setInt(8, theCustomer.getCustomerAddress().getCustomerID());
			
			myStmtCards.setString(1, theCustomer.getCustomerCard().getCardNumber());
			myStmtCards.setInt(2, theCustomer.getCustomerCard().getCardOwner());
			myStmtCards.setInt(3, theCustomer.getCustomerCard().getCardCustomer());
						
			myStmtCustomers.execute();
			myStmtAddresses.execute();
			myStmtCards.execute();
		}
		finally {
			close (myConn, myStmtCustomers);
			close (myConn, myStmtAddresses);
			close (myConn, myStmtCards);
		}
	}
	
	public void deleteCustomer(int customerId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmtCustomers = null;
		PreparedStatement myStmtAddresses = null;
		PreparedStatement myStmtCards = null;

		try {
			myConn = getConnection();

			String sqlCustomers = "delete from customers where Customer_ID = ?";
			String sqlAddresses = "delete from addresses where Customer_ID = ?";
			String sqlCards = "delete from credit_cards where Customer_ID = ?";

			myStmtCustomers = myConn.prepareStatement(sqlCustomers);
			myStmtAddresses = myConn.prepareStatement(sqlAddresses);
			myStmtCards = myConn.prepareStatement(sqlCards);

			// set params
			myStmtCustomers.setInt(1, customerId);
			myStmtAddresses.setInt(1, customerId);
			myStmtCards.setInt(1, customerId);
			
			myStmtAddresses.execute();
			myStmtCards.execute();			
			myStmtCustomers.execute();
		}
		finally {
			close (myConn, myStmtCustomers);
			close (myConn, myStmtAddresses);
			close (myConn, myStmtCards);
		}		
	}	
	
	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();
		
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public List<Product> getProducts() throws Exception {
		
		List<Product> products = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from products order by category, catalog_number";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int catalogNumber = myRs.getInt("Catalog_Number");
				String description = myRs.getString("Description");
				String category = myRs.getString("Category");
				float price = myRs.getFloat("Price");
				float discount = myRs.getFloat("Discount");
				float finalPrice = myRs.getFloat("Final_Price");
				String image = myRs.getString("Image");
				String size = myRs.getString("size");
				int amount = myRs.getInt("Amount_In_Stock");
				
				Product tempProduct=null;
				
				switch (category) {
		        case "Shirts":
		        	tempProduct = new Jacket(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		            break;
		        case "Skirts":
		        	tempProduct = new Skirt(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		        	break;
		        case "Dresses":
		        	tempProduct = new Dress(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		        	break;
		        case "Jackets":
		        	tempProduct = new Jacket(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		        	break;
		        default:
		        	tempProduct = new Product(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		        	break;
				}
				
				// add it to the list of products
				products.add(tempProduct);
			}
			return products;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addProduct(Product theProduct) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();
			
			String sql = "insert into products (Catalog_Number, Description, Category, Price, Discount, Image, Size, Amount_In_Stock) values (?, ?, ?, ?, ?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, theProduct.getCatalogNumber());
			myStmt.setString(2, theProduct.getDescription());
			myStmt.setString(3, theProduct.getCategory());
			myStmt.setFloat(4, theProduct.getPrice());
			myStmt.setFloat(5, theProduct.getDiscount());
			myStmt.setString(6, theProduct.getImage());
			myStmt.setString(7, theProduct.getSize());
			myStmt.setInt(8, theProduct.getAmount());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public void deleteProduct(Product theProduct) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();
				
			String sql = "delete from products where Catalog_Number = ?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, theProduct.getCatalogNumber());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}		
	}	

	public Product getProduct(int productNumber) throws Exception {
			
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from products where Catalog_Number = ?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, productNumber);
			
			myRs = myStmt.executeQuery();

			Product theProduct = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				
				// retrieve data from result set row
				int catalogNumber = myRs.getInt("Catalog_Number");
				String description = myRs.getString("Description");
				String category = myRs.getString("Category");
				float price = myRs.getFloat("Price");
				float discount = myRs.getFloat("Discount");
				float finalPrice = myRs.getFloat("Final_Price");
				String image = myRs.getString("Image");
				String size = myRs.getString("size");
				int amount = myRs.getInt("Amount_In_Stock");
				
				switch (category) {
		        case "Shirts":
		        	theProduct = new Jacket(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		            break;
		        case "Skirts":
		        	theProduct = new Skirt(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		        	break;
		        case "Dresses":
		        	theProduct = new Dress(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		        	break;
		        case "Jackets":
		        	theProduct = new Jacket(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		        	break;
		        default:
		        	theProduct = new Product(catalogNumber, description, category, price,
		        			discount, finalPrice, image, size, amount);
		        	break;
				}	
			}
			else {
				throw new Exception("Could not find catalog numebr: " + productNumber);
			}

			return theProduct;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateProduct(Product theProduct) throws Exception {

		Connection myConn = null;
		
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update products set Description=?, Category=?, Price=?, Discount=?, Image=?, Size=?, Amount_In_Stock=? where Catalog_Number=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theProduct.getDescription());
			myStmt.setString(2, theProduct.getCategory());
			myStmt.setFloat(3, theProduct.getPrice());
			myStmt.setFloat(4, theProduct.getDiscount());
			myStmt.setString(5, theProduct.getImage());
			myStmt.setString(6, theProduct.getSize());
			myStmt.setInt(7, theProduct.getAmount());
			myStmt.setInt(8, theProduct.getCatalogNumber());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}

	public List<Shirt> getShirts() throws Exception {
		
		List<Shirt> shirts = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from products where category='shirts' group by catalog_number";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int catalogNumber = myRs.getInt("Catalog_Number");
				String description = myRs.getString("Description");
				String category = myRs.getString("Category");
				float price = myRs.getFloat("Price");
				float discount = myRs.getFloat("Discount");
				float finalPrice = myRs.getFloat("Final_Price");
				String image = myRs.getString("Image");
				String size = myRs.getString("Size");
				int amount = myRs.getInt("Amount_In_Stock");
				
				// create new product object
				Shirt tempShirt = new Shirt(catalogNumber, description, category, price, 
						discount, finalPrice, image, size, amount);

				// add it to the list of shirts
				shirts.add(tempShirt);
			}
			
			return shirts;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void decreaseProduct(Product theProduct) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();
				
			String sql = "update products set Amount_In_Stock=? where Catalog_Number=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, (theProduct.getAmount()));
			myStmt.setInt(2, theProduct.getCatalogNumber());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
	
	public List<Skirt> getSkirts() throws Exception {
		
		List<Skirt> skirts = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from products where category='skirts' group by catalog_number";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int catalogNumber = myRs.getInt("Catalog_Number");
				String description = myRs.getString("Description");
				String category = myRs.getString("Category");
				float price = myRs.getFloat("Price");
				float discount = myRs.getFloat("Discount");
				float finalPrice = myRs.getFloat("Final_Price");
				String image = myRs.getString("Image");
				String size = myRs.getString("Size");
				int amount = myRs.getInt("Amount_In_Stock");
				
				// create new product object
				Skirt tempSkirt = new Skirt(catalogNumber, description, category, price, 
						discount, finalPrice, image, size, amount);

				// add it to the list of skirts
				skirts.add(tempSkirt);
			}
			
			return skirts;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Dress> getDresses() throws Exception {
		
		List<Dress> dresses = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from products where category='dresses' group by catalog_number";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int catalogNumber = myRs.getInt("Catalog_Number");
				String description = myRs.getString("Description");
				String category = myRs.getString("Category");
				float price = myRs.getFloat("Price");
				float discount = myRs.getFloat("Discount");
				float finalPrice = myRs.getFloat("Final_Price");
				String image = myRs.getString("Image");
				String size = myRs.getString("Size");
				int amount = myRs.getInt("Amount_In_Stock");
				
				// create new product object
				Dress tempDress = new Dress(catalogNumber, description, category, price, 
						discount, finalPrice, image, size, amount);

				// add it to the list of dresses
				dresses.add(tempDress);
			}
			
			return dresses;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public List<Jacket> getJackets() throws Exception {
		
		List<Jacket> jackets = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from products where category='jackets' group by catalog_number";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int catalogNumber = myRs.getInt("Catalog_Number");
				String description = myRs.getString("Description");
				String category = myRs.getString("Category");
				float price = myRs.getFloat("Price");
				float discount = myRs.getFloat("Discount");
				float finalPrice = myRs.getFloat("Final_Price");
				String image = myRs.getString("Image");
				String size = myRs.getString("Size");
				int amount = myRs.getInt("Amount_In_Stock");
				
				// create new product object
				Jacket tempJacket = new Jacket(catalogNumber, description, category, price, 
						discount, finalPrice, image, size, amount);

				// add it to the list of jackets
				jackets.add(tempJacket);
			}
			
			return jackets;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
}