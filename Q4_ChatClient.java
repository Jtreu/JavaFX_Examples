import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Q4_ChatClient extends Application{
	String username;
	String password;
	String servername = "localhost"; 
    static PrintWriter writer;
    BufferedReader reader;
    Socket client;
    static Text lblServerResponse = new Text("");
    static Button btnRegister = new Button("Register");
    
    public Q4_ChatClient() {
    	
    }
    
	public Q4_ChatClient(String username, String password, String servername) throws Exception {
    	this.username = username;
    	this.password = password;
        client  = new Socket(servername,8000);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
        writer = new PrintWriter(client.getOutputStream(),true);
        
        /* Send username & password to writer */
        writer.println(username); 
        writer.println(password);
        
        /* Start Messages Thread */
        new MessagesThread().start(); 
    }
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane gridPane = new GridPane();
		Label lblUsername = new Label("Username: ");
		Label lblPassword = new Label("Password: ");
		final TextField txtUsername = new TextField();
		final PasswordField txtPassword = new PasswordField();
		
		lblUsername.setFont(Font.font ("Verdana", 20));
		lblPassword.setFont(Font.font("Verdana", 20));
		txtUsername.setPrefWidth(200);
		txtUsername.setPrefHeight(60);
		txtPassword.setPrefWidth(200);
		txtPassword.setPrefHeight(60);
		btnRegister.setPrefWidth(200);
		btnRegister.setPrefHeight(60);
		
		gridPane.getColumnConstraints().add(new ColumnConstraints(200)); // column 1 is 200 wide
		gridPane.getRowConstraints().add(new RowConstraints(200)); // Row 2 is 200 wide
		
		gridPane.add(lblUsername, 0, 0);
		gridPane.add(txtUsername, 1, 0);
		gridPane.add(lblPassword, 0, 1);
		gridPane.add(txtPassword, 1, 1);
		gridPane.add(btnRegister, 0, 2);
		gridPane.add(lblServerResponse, 1, 2);
		
		btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				try {
					new Q4_ChatClient(txtUsername.getText(), txtPassword.getText(), servername);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Scene scene = new Scene(gridPane, 400, 400);
		primaryStage.setTitle("Register Account");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	class MessagesThread extends Thread {
		String responseMessage;
        public void run() {
            try {
                while(true) {
					responseMessage = reader.readLine();
					if(responseMessage.equals("ok")) {
						lblServerResponse.setText("Account Created");
					} else if(responseMessage.equals("no")) {
						lblServerResponse.setText("Username already exists");
					} 
                }
            } catch(Exception ex) {}
        }
    }
}