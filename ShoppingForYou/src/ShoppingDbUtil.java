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

			String sql = "select * from products natural join sizes order by category, catalog_number";

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
				float size = myRs.getFloat("size");
				int amount = myRs.getInt("Amount_In_Stock");
				
				// create new product object
				Product tempProduct = new Product(catalogNumber, description, category, price, 
						discount, finalPrice, image, size, amount);

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
		PreparedStatement myStmt_products = null;
		PreparedStatement myStmt_sizes = null;

		try {
			myConn = getConnection();

			String sql_products = "insert into products (Catalog_Number, Description, Category, Price, Discount, Image) values (?, ?, ?, ?, ?, ?)";
			String sql_sizes = "insert into sizes (Size, Catalog_Number, Amount_In_Stock) values (?, ?, ?)";
			
			myStmt_products = myConn.prepareStatement(sql_products);
			myStmt_sizes = myConn.prepareStatement(sql_sizes);
			
			// set params
			myStmt_products.setInt(1, theProduct.getCatalogNumber());
			myStmt_products.setString(2, theProduct.getDescription());
			myStmt_products.setString(3, theProduct.getCategory());
			myStmt_products.setFloat(4, theProduct.getPrice());
			myStmt_products.setFloat(5, theProduct.getDiscount());
			myStmt_products.setString(6, theProduct.getImage());
			
			myStmt_sizes.setFloat(1, theProduct.getSize());
			myStmt_sizes.setInt(2, theProduct.getCatalogNumber());
			myStmt_sizes.setInt(3, theProduct.getAmount());
			
			myStmt_products.execute();
			myStmt_sizes.execute();
		}
		finally {
			close (myConn, myStmt_products);
			close (myConn, myStmt_sizes);
		}
		
	}

	public void deleteProduct(Product theProduct) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt_sizes = null;
		PreparedStatement myStmt_products = null;

		try {
			myConn = getConnection();
				
			String sql_sizes = "delete from sizes where Catalog_Number = ?";
			String sql_products = "delete from products where Catalog_Number = ?";

			myStmt_sizes = myConn.prepareStatement(sql_sizes);
			myStmt_products = myConn.prepareStatement(sql_products);
			
			// set params
			myStmt_sizes.setInt(1, theProduct.getCatalogNumber());
			myStmt_products.setInt(1, theProduct.getCatalogNumber());
			
			myStmt_sizes.execute();
			myStmt_products.execute();
		}
		finally {
			close (myConn, myStmt_sizes);
			close (myConn, myStmt_products);
		}		
	}	

	public Product getProduct(int productNumber) throws Exception {
			
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from products natural join sizes where Catalog_Number = ?";

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
				float size = myRs.getFloat("size");
				int amount = myRs.getInt("Amount_In_Stock");
				
				theProduct = new Product(catalogNumber, description, category, price, 
						discount, finalPrice, image, size, amount);	
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
		
		PreparedStatement myStmt_products = null;
		PreparedStatement myStmt_sizes = null;

		try {
			myConn = getConnection();

			String sql_products = "update products set Description=?, Category=?, Price=?, Discount=?, Image=? where Catalog_Number=?";
			String sql_sizes = "update sizes set Amount_In_Stock=? where Catalog_Number=? and Size=?";
			
			myStmt_products = myConn.prepareStatement(sql_products);
			myStmt_sizes = myConn.prepareStatement(sql_sizes);
			
			// set params
			myStmt_products.setString(1, theProduct.getDescription());
			myStmt_products.setString(2, theProduct.getCategory());
			myStmt_products.setFloat(3, theProduct.getPrice());
			myStmt_products.setFloat(4, theProduct.getDiscount());
			myStmt_products.setString(5, theProduct.getImage());
			myStmt_products.setInt(6, theProduct.getCatalogNumber());
			
			myStmt_sizes.setInt(1, theProduct.getAmount());
			myStmt_sizes.setInt(2, theProduct.getCatalogNumber());
			myStmt_sizes.setFloat(3, theProduct.getSize());
			
			myStmt_products.execute();
			myStmt_sizes.execute();
		}
		finally {
			close (myConn, myStmt_products);
			close (myConn, myStmt_sizes);
		}
	}

	public List<Shirt> getShirts() throws Exception {
		
		List<Shirt> shirts = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from products natural join sizes where category='shirts' group by catalog_number";

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
				float size = myRs.getFloat("size");
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
				
			String sql = "update sizes set Amount_In_Stock=? where Catalog_Number=? and size=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, (theProduct.getAmount()));
			myStmt.setInt(2, theProduct.getCatalogNumber());
			myStmt.setFloat(3, theProduct.getSize());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
	}
}