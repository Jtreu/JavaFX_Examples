import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Exercise_15_3 extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override 
	public void start(Stage primaryStage) {
		Pane pane = new Pane();
		
		final Circle circle = new Circle(25, Color.GREEN); 
		circle.setCenterX(175);
		circle.setCenterY(225);
		
		/* Is there a way to add buttons to a group, and set the text size of all of them in 1 
		*  line of code?
		*/
		Button moveLeft = new Button("Left");
		Button moveRight = new Button("Right");
		Button moveUp = new Button("Up");
		Button moveDown = new Button("Down");
		
		moveLeft.setLayoutX(0);
		moveLeft.setLayoutY(475);
		
		moveRight.setLayoutX(100);
		moveRight.setLayoutY(475);
		
		moveUp.setLayoutX(200);
		moveUp.setLayoutY(475);
		
		moveDown.setLayoutX(300);
		moveDown.setLayoutY(475);
		
		moveLeft.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				circle.setTranslateX(circle.getTranslateX() -5);
			}
		});
		
		moveRight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				circle.setTranslateX(circle.getTranslateX() +5);
			}
		});
		
		moveUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				circle.setTranslateY(circle.getTranslateY() -5);
			}
		});
		
		moveDown.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				circle.setTranslateY(circle.getTranslateY() +5);
			}
		});
		
		pane.getChildren().addAll(circle, moveLeft, moveRight, moveUp, moveDown);
		Scene scene = new Scene(pane, 350, 500);
		primaryStage.setTitle("Exercise 15_03");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void moveLeft() {
		
	}
	
}
