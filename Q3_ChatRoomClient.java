/* **********UPDATE 1********** */

import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Q3_ChatRoomClient extends Application{
    String username;
    String servername = "localhost"; 
    static PrintWriter writer;
    BufferedReader reader;
    
    static Label usrLblEnter = new Label("Enter a username:");
    static TextField usrNameEnter = new TextField();
    static Button btnUserNameEnter = new Button("Join Chat");
    
    static Label usrLbl = new Label("Enter Your Text");
    static TextArea chatArea = new TextArea();
    static TextField usrChatBox  = new TextField();
    static Button usrSubmitChat = new Button("Send");
    Socket client;
    
    /* Needs a default constructor or else get an error */
    public Q3_ChatRoomClient() {
    	
    }
    
    public Q3_ChatRoomClient(String username, String servername) throws Exception {
    	this.username = username;
        client  = new Socket(servername,8001);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
        writer = new PrintWriter(client.getOutputStream(),true);
        
        /* Send username to writer */
        writer.println(username); 
        
        /* Start Messages Thread */
        new MessagesThread().start(); 
    }
    
    /* Had to write a method outside of start() or else a null child error came up */
    private static void setGUIProperties() {
        chatArea.setEditable(false);
		
		chatArea.setPrefWidth(400);
		chatArea.setPrefHeight(400);

        usrChatBox.setMaxWidth(325);
		usrChatBox.setMaxHeight(100);
		
		usrLbl.setLayoutX(0);
		usrLbl.setLayoutY(401);
		
		usrChatBox.setLayoutX(0);
		usrChatBox.setLayoutY(425);
		usrChatBox.setPrefWidth(425);
		usrChatBox.setPrefHeight(50);
		
		usrSubmitChat.setLayoutX(330);
		usrSubmitChat.setLayoutY(425);
		
		usrSubmitChat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				chatArea.appendText("You: " + usrChatBox.getText() + "\n");
				writer.println(usrChatBox.getText());
			}
		});
    }
    
    @Override
	public void start(final Stage primaryStage) throws Exception {
    	GridPane menuPane = new GridPane();
		final Pane chatPane = new Pane();
		
		btnUserNameEnter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				try {
					new Q3_ChatRoomClient(usrNameEnter.getText(), servername);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				chatPane.getChildren().addAll(chatArea, usrLbl, usrChatBox, usrSubmitChat);
				
				Scene chatScene = new Scene(chatPane, 500, 500);
				primaryStage.setTitle("Chat Room");
				primaryStage.setScene(chatScene);
				primaryStage.show();
			}
		});
		
		menuPane.getColumnConstraints().add(new ColumnConstraints(200)); // column 1 is 200 wide
		menuPane.getRowConstraints().add(new RowConstraints(200)); // Row 2 is 200 wide
		
		menuPane.add(usrLblEnter, 1, 1);
		menuPane.add(usrNameEnter, 1, 2);
		menuPane.add(btnUserNameEnter, 1, 3);
		
		Scene menuScene = new Scene(menuPane, 500, 500);
		primaryStage.setTitle("Chat Room Menu");
		primaryStage.setScene(menuScene);
		primaryStage.show();
	}
    
    public static void main(String[] args) { 
        setGUIProperties();
        launch(args);
    }
    
    class MessagesThread extends Thread {
        public void run() {
            String line;
            try {
                while(true) {
                    line = reader.readLine();
                    chatArea.appendText(line + "\n");
                }
            } catch(Exception ex) {}
        }
    }
} 
