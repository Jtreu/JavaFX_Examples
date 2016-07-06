/* Jameson Treu
 * jst86520
 */
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class Exercise_14_18 extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage stage) {
		Pane pane = new Pane();
		
		Polyline polyline = new Polyline();
    	ObservableList<Double> list = polyline.getPoints();
    	double scaleFactor = -0.0125;
    	for(int x = -100; x <= 100; x++) {
    		list.add(x + 200.0);
    		list.add(250 + scaleFactor * x * x);
    	}
    	polyline.setStroke(Color.BLACK);
    	
    	pane.getChildren().add(polyline);
    	
    	Line line1 = new Line(0, 250, 400, 250);
    	line1.setStrokeWidth(1);
    	line1.setStroke(Color.BLACK);
    	pane.getChildren().add(line1);
    	
    	Line line2 = new Line(200, 0, 200, 400);
    	line2.setStrokeWidth(1);
    	line2.setStroke(Color.BLACK);
    	pane.getChildren().add(line2);
    	
    	Scene scene = new Scene(pane, 400, 400, Color.WHITE);
    	stage.setTitle("Exercise 14.18");
    	stage.setScene(scene);
    	stage.show();
	}
}