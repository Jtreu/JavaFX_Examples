/* Jameson Treu
*  A program that displays a rectangle.
   You can point the mouse inside the rectangle and drag (i.e., move with mouse
   pressed) the rectangle wherever the mouse goes. The mouse point becomes the
   center of the rectangle.
*/

import javafx.scene.shape.Rectangle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Exercise_15_18 extends Application{
	double originalX, originalY;
    double translateX, translateY;
	Rectangle r = new Rectangle(50, 50, 200, 100);
	Pane pane = new Pane();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		r.setOnMousePressed(rectangleMousePressedEventHandler);
		r.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
		pane.getChildren().add(r);
		
		Scene scene = new Scene(pane, 500, 500);
		
		primaryStage.setTitle("Center rectangle on mouse");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	EventHandler<MouseEvent> rectangleMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent m) {
        	originalX = m.getSceneX();
        	originalY = m.getSceneY();
        	translateX = r.getTranslateX();
        	translateY = r.getTranslateY();
        }
    };
     
    EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent m) {
            double offsetX = m.getSceneX() - originalX;
            double offsetY = m.getSceneY() - originalY;
            double newTranslateX = translateX + offsetX;
            double newTranslateY = translateY + offsetY;
            double lineLength;
             
            r.setX(m.getSceneX() - (r.getWidth() / 2));
            r.setY(m.getSceneY() - (r.getHeight() / 2));
        }
    };
}


