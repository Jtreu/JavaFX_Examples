import java.io.*;
import java.util.*;
import java.net.*;
import java.sql.*;
import static java.lang.System.out;

public class Q4_ChatServer {
	int clientNum = -1;
	ArrayList<HandleClient> clients = new ArrayList<HandleClient>();
	static Connection connection;
	
	public void process() throws Exception  {
		ServerSocket server = new ServerSocket(8000, 10);
		out.println("Networking setup. waiting for client");
		while(true) {
			Socket client = server.accept();
			/* Since the client is sending a new socket every time he clicks register,
			 * and the program doesn't work if you declare the new socket in main,
			 * I'm not sure how to keep clientNum consistent for each client, other
			 * than using getInetAdress as the client ID
			 */
			clientNum++;
			HandleClient handleClient = new HandleClient(client, clientNum);
			clients.add(handleClient);
			System.out.println("Server connected to Client " + clientNum + " (localhost at "+ client.getInetAddress() + ")");
		} 
	}
	public static void main(String[] args) throws Exception, SQLException, ClassNotFoundException  {
		// Load the JDBC driver
	    Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Driver loaded");

	    // Connect to a database
	    connection = DriverManager.getConnection
	      ("jdbc:mysql://localhost:3306/midterm" , "scott", "tiger");
	    System.out.println("Connection Established");

		new Q4_ChatServer().process();
	} 

	class HandleClient extends Thread {
		String name = "";
		String pass = "";
		BufferedReader input;
		PrintWriter output;
		int clientID;

		public HandleClient(Socket client, int clientNum) throws Exception {
			clientID = clientNum;
			input = new BufferedReader( new InputStreamReader( client.getInputStream())) ;
			output = new PrintWriter ( client.getOutputStream(),true);
	 
			start();
        }

    	public void sendToServer(String usrname,String passWrd) throws SQLException, ClassNotFoundException   {
    		/* SQL code here */
    		// Create a statement
    	    PreparedStatement statement = connection.prepareCall("INSERT INTO account ( username, password ) "
				       + "VALUES ('"+usrname+"', '"+passWrd+"')");
    	    
    	 // Execute a statement
    	    PreparedStatement statement2 = connection.prepareStatement("SELECT username, password FROM account");
    	    ResultSet resultSet = statement2.executeQuery();
    	    
    	 Boolean dontAdd = false;
    	 // Iterate through results and check if username already exists
    		while(resultSet.next()) {
    			if(resultSet.getString("username").equals(usrname)) {
    				/* username already exists, don't add user */
    				output.println("no");
    				dontAdd = true;
    				System.out.println("Client " + clientID + ": Account '" + name +"' with password '" +
    									passWrd + "' already exists");
    				break;
    			} 
    		}
    		
    		if(dontAdd == false) {
    			/* ok to add user */
				output.println("ok");
				statement.executeUpdate();
				System.out.println("Client " + clientID + ": Account '" + name +"' with password '" +
						passWrd + "' created");
    		}
    	}
    	
    	
	
    	public String getUsername() {  
    		return name; 
    	}
    	
    	public void run()  {
    		String pass;
    		
    		try {
    			name = input.readLine();
                pass = input.readLine();
                sendToServer(name, pass); 
            } catch(Exception e) {
    			e.printStackTrace();
    		}
    	} 
	} 
} 