/* Jameson Treu 
*  Two circles connected by a line.
   The distance between the 2 circles is shown in the middle using the midpoint
   formula (this was the first time I've used it outside of school!).
   The user can also drag the circles around and the distance will change accordingly.
*/

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Exercise_15_16 extends Application{
	double originalX, originalY;
    double translateX, translateY;
	Label lbl = new Label();
	Circle circle1 = new Circle(18);
	Circle circle2 = new Circle(18);
	Line line;
	Pane pane = new Pane();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		circle1.setCenterX(40);
		circle1.setCenterY(40);
		circle1.setStroke(Color.BLACK);
		circle1.setFill(Color.WHITE);
		circle1.setCursor(Cursor.HAND);
		circle1.setOnMousePressed(circle1OnMousePressedEventHandler);
		circle1.setOnMouseDragged(circle1OnMouseDraggedEventHandler);
        
		circle2.setCenterX(120);
		circle2.setCenterY(150);
		circle2.setStroke(Color.BLACK);
		circle2.setFill(Color.WHITE);
		circle2.setCursor(Cursor.HAND);
		circle2.setOnMousePressed(circle2OnMousePressedEventHandler);
		circle2.setOnMouseDragged(circle2OnMouseDraggedEventHandler);
		
		line = new Line(40, 40, 120, 150);
		
		pane.getChildren().addAll(circle1, circle2, line, lbl);
		
		Scene scene = new Scene(pane, 500, 500);
		
		primaryStage.setTitle("Chat Room Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	EventHandler<MouseEvent> circle1OnMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent m) {
        	originalX = m.getSceneX();
        	originalY = m.getSceneY();
        	translateX = circle1.getTranslateX();
        	translateY = circle1.getTranslateY();
        }
    };
     
    EventHandler<MouseEvent> circle1OnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent m) {
            double offsetX = m.getSceneX() - originalX;
            double offsetY = m.getSceneY() - originalY;
            double newTranslateX = translateX + offsetX;
            double newTranslateY = translateY + offsetY;
            double lineLength;
             
            circle1.setTranslateX(newTranslateX);
            circle1.setTranslateY(newTranslateY);
            
            line.setStartX(circle1.getCenterX() + newTranslateX);
            line.setStartY(circle1.getCenterY() + newTranslateY);
            
            lbl.setLayoutX((line.getStartX() + line.getEndX()) / 2);
            lbl.setLayoutY((line.getStartY() + line.getEndY()) / 2);
            
            lineLength = Math.sqrt((Math.pow(line.getEndX() - line.getStartX(), 2)) + 
            		     (Math.pow(line.getEndY() - line.getStartY(), 2)));
            
            lbl.setText("length: " + String.valueOf(Math.ceil(lineLength)));
        }
    };
    
    EventHandler<MouseEvent> circle2OnMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent m) {
        	originalX = m.getSceneX();
        	originalY = m.getSceneY();
        	translateX = circle2.getTranslateX();
        	translateY = circle2.getTranslateY();
        }
    };
     
    EventHandler<MouseEvent> circle2OnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent m) {
            double offsetX = m.getSceneX() - originalX;
            double offsetY = m.getSceneY() - originalY;
            double newTranslateX = translateX + offsetX;
            double newTranslateY = translateY + offsetY;
            double lineLength;
             
            circle2.setTranslateX(newTranslateX);
            circle2.setTranslateY(newTranslateY);
            
            line.setEndX(circle2.getCenterX() + newTranslateX);
            line.setEndY(circle2.getCenterY() + newTranslateY);

            lbl.setLayoutX((line.getStartX() + line.getEndX()) / 2);
            lbl.setLayoutY((line.getStartY() + line.getEndY()) / 2);
            
            lineLength = Math.sqrt((Math.pow(line.getEndX() - line.getStartX(), 2)) + 
       		     (Math.pow(line.getEndY() - line.getStartY(), 2)));
       
       lbl.setText("length: " + String.valueOf(Math.ceil(lineLength)));
        }
    };
}


