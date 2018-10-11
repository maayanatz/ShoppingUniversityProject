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
		
	public List<Customer> getCustomers() throws Exception {

		List<Customer> customers = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from customers order by Last_Name";

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
				
				// create new customer object
				Customer tempCustomer = new Customer(id, firstName, lastName,
						email, password, phoneNumber);

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
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into customers (Customer_ID, First_Name, Last_Name, Email_Address, Password, Phone_Number) values (?, ?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, theCustomer.getId());
			myStmt.setString(2, theCustomer.getFirstName());
			myStmt.setString(3, theCustomer.getLastName());
			myStmt.setString(4, theCustomer.getEmail());
			myStmt.setString(5, theCustomer.getPassword());
			myStmt.setInt(6, theCustomer.getPhoneNumber());
						
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Customer getCustomer(int customerId) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from customers where Customer_ID = ?";

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
				
				theCustomer = new Customer(id, firstName, lastName,
						email, password, phoneNumber);
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
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update customers set First_Name=?, Last_Name=?, Email_Address=?, Password=?, Phone_Number=? where Customer_ID=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theCustomer.getFirstName());
			myStmt.setString(2, theCustomer.getLastName());
			myStmt.setString(3, theCustomer.getEmail());
			myStmt.setString(4, theCustomer.getPassword());
			myStmt.setInt(5, theCustomer.getPhoneNumber());
			myStmt.setInt(6, theCustomer.getId());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteCustomer(int customerId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from customers where Customer_ID = ?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, customerId);
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
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