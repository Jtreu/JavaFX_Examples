import java.io.*;
import java.util.*;
import java.net.*;
import static java.lang.System.out;

public class Q3_ChatRoomServer {
	ArrayList<String> users = new ArrayList<String>();
	ArrayList<HandleClient> clients = new ArrayList<HandleClient>();

	public void process() throws Exception  {
		ServerSocket server = new ServerSocket(8000, 10);
		out.println("Server Started...");
		while(true) {
			Socket client = server.accept();
			HandleClient handleClient = new HandleClient(client);
			clients.add(handleClient);
		} 
	}
	public static void main(String[] args) throws Exception {
		new Q3_ChatRoomServer().process();
	} 

	public void sendMessages(String user, String message)  {
		/* send message to all users */
	    for (HandleClient handleClient : clients)
	       if(!handleClient.getUserName().equals(user))
	    	   handleClient.sendMessage(user, message);
	}

	class HandleClient extends Thread {
		String name = "";
		BufferedReader input;
		PrintWriter output;

		public HandleClient(Socket client) throws Exception {
			input = new BufferedReader( new InputStreamReader( client.getInputStream())) ;
			output = new PrintWriter ( client.getOutputStream(),true);
	 
			/* read username and add to users */
			name = input.readLine();
			users.add(name); 
			start();        
		}

    	public void sendMessage(String usrname,String message)  {
    		output.println(usrname + ": " + message);
    	}
	
    	public String getUserName() {  
    		return name; 
    	}
    	
    	public void run()  {
    		String line;
    		
    		try {
                while(true) {
                	line = input.readLine();
                	sendMessages(name,line); 
                } 
    		} 
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	} 
	} 
} 