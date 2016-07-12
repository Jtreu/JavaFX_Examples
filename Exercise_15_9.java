/* Jameson Treu
*  Draw lines using the arrow keys
*/

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Exercise_15_9 extends Application{
	Pane pane = new Pane();
	double sceneWidth = 500;
	double sceneHeight = 500;
	double currX = sceneWidth / 2;
    double currY = sceneHeight / 2;
    
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(pane, sceneWidth, sceneHeight);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
            	/* Up Arrow */
                if (key.getCode().equals(KeyCode.UP)) {           
                	pane.getChildren().add(new Line(currX, currY, currX, currY - 10));
                    currY -= 10;
                /* Down Arrow */
                } else if(key.getCode().equals(KeyCode.DOWN)) {
                	pane.getChildren().add(new Line(currX, currY, currX, currY + 10));
                    currY += 10;
                /* Left Arrow */    
                } else if(key.getCode().equals(KeyCode.LEFT)) {
                	pane.getChildren().add(new Line(currX, currY, currX - 10, currY));
                    currX -= 10;
                /* Right Arrow */
                } else if(key.getCode().equals(KeyCode.RIGHT)) {
                	pane.getChildren().add(new Line(currX, currY, currX + 10, currY));
                	currX += 10;
                }
            }
        });
		
		primaryStage.setTitle("Draw Lines");
		primaryStage.setScene(scene);
		primaryStage.show();
	}	
}


