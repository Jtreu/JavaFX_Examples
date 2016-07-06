/* Jameson Treu
 * jst86520
 */
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.shape.*;
import javafx.scene.input.MouseEvent;

public class Exercise_14_11 extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		
		/* Smile */
		Arc arc = new Arc();
		arc.setCenterX(250);
		arc.setCenterY(300);
		arc.setRadiusX(100);
		arc.setRadiusY(50);
		arc.setStartAngle(180);
		arc.setLength(180);
		arc.setStroke(Color.BLACK);
		arc.setFill(null);
		arc.setType(ArcType.OPEN);
		pane.getChildren().add(arc);
		
		/* Nose */
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(new Double[]{
		    245.0, 175.0,
		    200.0, 250.0,
		    300.0, 250.0 });
		polygon.setStroke(Color.BLACK);
		polygon.setFill(null);
		pane.getChildren().add(polygon);
		
		/* Eyes & Eyeballs*/
		Circle eye1 = new Circle();
		eye1.setCenterX(150);
		eye1.setCenterY(100);
		eye1.setRadius(50);
		eye1.setStroke(Color.BLACK);
		eye1.setFill(null);
		pane.getChildren().add(eye1);
		
		Circle eyeball1 = new Circle();
		eyeball1.setCenterX(150);
		eyeball1.setCenterY(100);
		eyeball1.setRadius(15);
		eyeball1.setStroke(Color.BLACK);
		eyeball1.setFill(Color.BLACK);
		pane.getChildren().add(eyeball1);
		
		Circle eye2 = new Circle();
		eye2.setCenterX(350);
		eye2.setCenterY(100);
		eye2.setRadius(50);
		eye2.setStroke(Color.BLACK);
		eye2.setFill(null);
		pane.getChildren().add(eye2);
		
		Circle eyeball2 = new Circle();
		eyeball2.setCenterX(350);
		eyeball2.setCenterY(100);
		eyeball2.setRadius(15);
		eyeball2.setStroke(Color.BLACK);
		eyeball2.setFill(Color.BLACK);
		pane.getChildren().add(eyeball2);
		
		/* Face */
		Circle face = new Circle();
		face.setCenterX(250);
		face.setCenterY(210);
		face.setRadius(225);
		face.setStroke(Color.BLACK);
		face.setFill(null);
		pane.getChildren().add(face);
		
		/* Create a scene and place it in the stage */
    	Scene scene = new Scene(pane, 500, 500);
    	primaryStage.setTitle("Exercise 14.11"); // Set the stage title
    	primaryStage.setScene(scene); // Place the scene in the stage
    	primaryStage.show(); // Display the stage
    	
    	pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        System.out.println(event.getSceneX());
		        System.out.println(event.getSceneY());
		    }
		});
	}
}
