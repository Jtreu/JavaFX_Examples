/* Jameson Treu
*  Draws a polygon, and whenever the mouse is within the polygon,
*  display a message to the user.
*/

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Exercise_15_14 extends Application{
	Label lbl = new Label();
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		
		/* Polygon @ points (40, 20), (70, 40), (60, 80), (45, 45), and (20, 60) */
		final Polygon polygon = new Polygon();
		polygon.getPoints().addAll(new Double[]{
		    40.0, 20.0, // 1
		    70.0, 40.0, // 2 
		    60.0, 80.0, // 3
		    45.0, 45.0, // 4
		    20.0, 60.0 }); // 5
		
		pane.getChildren().add(polygon);
		
		pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouse) {
                if(mouse.getEventType() == MouseEvent.MOUSE_MOVED){
                    if(polygon.contains(mouse.getX(), mouse.getY())) {
                    	lbl.setText("Mouse in polygon");
                    } else {
                    	lbl.setText("");
                    }
                }

            }
        });
		pane.getChildren().add(lbl);
		
		Scene scene = new Scene(pane, 200, 200);
		primaryStage.setTitle("Chat Room Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}	
}


