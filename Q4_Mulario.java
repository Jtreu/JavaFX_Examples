import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;


public class Q4_Mulario extends Application {
	String color = "red"; // red is the default color if the user doesn't pick anything
	boolean showPlayerSize = false;
	Circle playerCircle;
	private static final int      KEYBOARD_MOVEMENT_DELTA = 5;
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);
	private static final int MAX_CIRCLES = 30;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override // Override the start method in the Application class
	public void start(final Stage primaryStage) {
		// VBoxes, gridPanes, Panes, etc...
		VBox menu = new VBox(10);
		final Pane gamePane = new Pane();
		
		// User chooses a color for  their circle
		menu.getChildren().add(new Text(0, 0, "Player Color (red, green, blue)"));
		final TextField tfPlayerColor = new TextField ();

		final CheckBox cbShowSize = new CheckBox("Show Player Size?");
		
		// Enemy Circles Variables
		final EnemyCircle[] enemyCircles;
		enemyCircles = generateEnemyCircles();
		
		Button submit = new Button("Play Game");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				color = tfPlayerColor.getText();
				color = color.toLowerCase();
				if(cbShowSize.isSelected() == true) {
					showPlayerSize = true;
				}
				// Player circle
				playerCircle = new Circle(25, Color.RED); // Default to red 
				final Text tPlayerSize = new Text("5");
				tPlayerSize.setFont(Font.font ("Verdana", 13));
				tPlayerSize.setFill(Color.WHITE);
				switch(color) {
				case "red": 
					playerCircle = new Circle(25, Color.RED);
					break;
				case "green":
					playerCircle = new Circle(25, Color.GREEN);
					break;
				case "blue":
					playerCircle = new Circle(25, Color.BLUE);
					break;
				}
		        
				playerCircle.setCenterX(500.0);
				playerCircle.setCenterY(500.0);
				tPlayerSize.setX(498.0);
				tPlayerSize.setY(505.0);
				
				final TranslateTransition transition = createTranslateTransition(playerCircle, tPlayerSize);
				
				// Add enemy circles
				for(int i=0;i<MAX_CIRCLES;i++) {
					enemyCircles[i].setCircle();
					gamePane.getChildren().add(enemyCircles[i].getCircle());
				}
				
				Timeline animation;
				animation = new Timeline(
		                new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								/* This is really bad O(n^2) type of stuff, if I were doing a serious game
								*  I'd change the collision detection to be coming from the player circle
								*  instead of the enemy circles.
								*/
							    for(int i=0;i<MAX_CIRCLES;i++) {
							    	if(enemyCircles[i].checkCollision(playerCircle) == true && (enemyCircles[i].getGotEaten() == false)) {
							    		enemyCircles[i].setGotEaten(true);
							    		double increasedSize = playerCircle.getRadius() + enemyCircles[i].getSize();
							    		playerCircle.setRadius(increasedSize);
							    		tPlayerSize.setText(Double.toString(Math.ceil(increasedSize)));
							    		gamePane.getChildren().remove(enemyCircles[i].getCircle());
							    	}
							    }
							}
						}));
				animation.setCycleCount(Timeline.INDEFINITE);
		        animation.play(); // Start animation
				
				gamePane.getChildren().add(playerCircle);
				if(showPlayerSize == true) 
					gamePane.getChildren().add(tPlayerSize);
				Scene gameScene = new Scene(gamePane, 1000, 1000);
				moveCircleOnKeyPress(gameScene, playerCircle, tPlayerSize);
				primaryStage.setTitle("Mulario");
				primaryStage.setScene(gameScene);
				primaryStage.show();
			}
		});
		
		menu.getChildren().addAll(tfPlayerColor, cbShowSize, submit);
		
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(menu, 250, 150);
		primaryStage.setTitle("Mulario Menu"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
	
	private EnemyCircle[] generateEnemyCircles() {
		EnemyCircle[] enemyCircles = new EnemyCircle[MAX_CIRCLES];
		for(int i=0;i<MAX_CIRCLES;i++) {
			enemyCircles[i] = new EnemyCircle(playerCircle);
		}
		return enemyCircles;
	}
	
	private TranslateTransition createTranslateTransition(final Circle circle, final Text text) {
	    final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
	    transition.setOnFinished(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent t) {
	        circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
	        circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
	        circle.setTranslateX(0);
	        circle.setTranslateY(0);
	        
	        text.setX(text.getTranslateX() + text.getX());
	        text.setY(text.getTranslateY() + text.getY());
	        text.setTranslateX(0);
	        text.setTranslateY(0);
	      }
	    });
	    return transition;
	  }
	
	private void moveCircleOnKeyPress(Scene scene, final Circle circle, final Text text) {
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	      @Override public void handle(KeyEvent event) {
	        switch (event.getCode()) {
	          case UP:    
	        	  circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA); 
	        	  text.setY(text.getY() - KEYBOARD_MOVEMENT_DELTA);
	        	  break;
	          case RIGHT: 
	        	  circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
	        	  text.setX(text.getX() + KEYBOARD_MOVEMENT_DELTA);
	        	  break;
	          case DOWN:  
	        	  circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA); 
	        	  text.setY(text.getY() + KEYBOARD_MOVEMENT_DELTA);
	        	  break;
	          case LEFT:  
	        	  circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA); 
	        	  text.setX(text.getX() - KEYBOARD_MOVEMENT_DELTA);
	        	  break;
	        }
	      }
	    });
	  }
	
	private class EnemyCircle {
		Circle enemyBlob; // Enemy Circle
		Circle otherCircle; // Player Circle
		double locationX;
		double locationY;
		double size;
		boolean gotEaten;
		
		EnemyCircle(Circle otherCircle) {
			this.otherCircle = otherCircle;
			this.locationX = (double)(Math.random() * 950 + 1);
			this.locationY = (double)(Math.random() * 950 + 1);
		}
		
		private void setCircle() {
			this.size = Math.random() * 17 + 8;
			enemyBlob = new Circle(size, getColor());
			enemyBlob.setCenterX(locationX);
			enemyBlob.setCenterY(locationY);
		}
		
		private Circle getCircle() {
			return enemyBlob;
		}
		
		private double getSize() {
			return size;
		}
		
		private void setGotEaten(boolean gotEaten) {
			this.gotEaten = true;
		}
		
		private boolean getGotEaten() {
			return gotEaten;
		}
		
		Color getColor() {
			Random rand = new Random();
			
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);
			
			Color randomColor = Color.rgb(r, g, b);
			
			return randomColor;
		}
		
		private boolean checkCollision(Circle otherCircle) {
			int number = 0;
			int number2 = 0;
			int number3 =0;
			if (Math.sqrt(Math.pow(enemyBlob.getCenterX() - otherCircle.getCenterX()+number, 2) + 
		            Math.pow(enemyBlob.getCenterY() - otherCircle.getCenterY(), 2)) <= 2 * enemyBlob.getRadius()) {
				number2 += 2;
				number3 += number2 + 2;
				number+=4*number2;
				number2+=2*number3;
				return true;
			} else {
				return false;
			}
		}
	}
}