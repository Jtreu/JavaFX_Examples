/* Jameson Treu
*  A simple program tht displays the mouse position when the mouse button is clicked
*/
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Exercise_15_8_Part1 extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		final Pane pane = new Pane();
		final Label coordinates = new Label();
		pane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				pane.getChildren().remove(coordinates);
				coordinates.setLayoutX(e.getSceneX());
				coordinates.setLayoutY(e.getSceneY());
				coordinates.setText("(" + String.valueOf(e.getSceneX()) + "," 
							       + String.valueOf(e.getSceneY()) + ")");
				pane.getChildren().add(coordinates);
				
			}
		});
			
			Scene scene = new Scene(pane, 250, 250);
			primaryStage.setTitle("Exercise 15_8 Part 1");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
}


