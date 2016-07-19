import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

public class Q1_Prepared {
	static Connection connection;
	
	public static void main(String args[]) throws Exception {
		/* SQL Connection */
		try {
			// Load the JDBC driver
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded");

		    // Connect to a database
		    connection = DriverManager.getConnection
		      ("jdbc:mysql://localhost:3306/midterm" , "scott", "tiger");
		    System.out.println("Connection Established");
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
		long start = System.nanoTime();
		
		System.out.println("Creating Entries...");
		
		/* Create Entries in Database */
		for(int i=0;i<10000;i++) {
			createEntries(connection);
		}
		
		/* If entries added correctly and program didn't crash */
		System.out.println("Entries Added");
		
		long end = System.nanoTime();
		
		long timeTaken = (end-start) / 1000000;  
		
		System.out.println("Time Taken to Create Entries: " + timeTaken + " milliseconds");
	}
	
	public static void createEntries(Connection conn) throws Exception {
		Random rand = new Random();
		String firstName, lastName;
		int money;
		
		/* Generate First Name */
		firstName = generateNames();

		/* Generate Last Name */
		lastName = generateNames();
		
		/* Generate Money Amount */
		money = rand.nextInt(999999) + 1;
		
		/* SQL Stuff */
		try {
			PreparedStatement statement = conn.prepareCall
					("INSERT INTO watchlist ( first_name, last_name, money ) " +
						"VALUES ('"+firstName+"', '"+lastName+"', '"+money+"')");
			
			statement.executeUpdate(); 
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static String generateNames() {
		int size, randomCharVal;
		Random rand = new Random();
		char nameArray[];
		
		size = rand.nextInt(10) + 3;
		nameArray = new char[size];
		
		for(int i=0;i<size;i++) {
			randomCharVal = rand.nextInt(26) + 97;
			nameArray[i] = (char)(randomCharVal);
		}
	
		return new String(nameArray);
	}
}
