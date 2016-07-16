import javafx.scene.shape.Rectangle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Exercise_16_3 extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	  public void start(Stage stage) {
	    final Scene scene = new Scene(new Group());
	    stage.setTitle("Radio Button Sample");
	    stage.setWidth(250);
	    stage.setHeight(250);

	    final ToggleGroup group = new ToggleGroup();

	    RadioButton rb1 = new RadioButton("RED");
	    rb1.setToggleGroup(group);
	    rb1.setUserData("RED");

	    RadioButton rb2 = new RadioButton("YELLOW");
	    rb2.setToggleGroup(group);
	    rb2.setUserData("YELLOW");

	    RadioButton rb3 = new RadioButton("GREEN");
	    rb3.setToggleGroup(group);
	    rb3.setUserData("GREEN");
	    
	    final Circle redCircle = new Circle(10, Color.WHITE);
	    redCircle.setStroke(Color.BLACK);
	    final Circle yellowCircle = new Circle(10, Color.WHITE);
	    yellowCircle.setStroke(Color.BLACK);
	    final Circle greenCircle = new Circle(10, Color.WHITE);
	    greenCircle.setStroke(Color.BLACK);

	    final HBox hbox = new HBox();
	    final VBox vbox = new VBox();
	    
	    final HBox lightHBox = new HBox();
	    final VBox lightVbox = new VBox();

	    vbox.getChildren().add(rb1);
	    vbox.getChildren().add(rb2);
	    vbox.getChildren().add(rb3);
	    vbox.setSpacing(10);
	    
	    
	    lightVbox.getChildren().add(redCircle);
	    lightVbox.getChildren().add(yellowCircle);
	    lightVbox.getChildren().add(greenCircle);
	    lightVbox.setSpacing(10);

	    hbox.getChildren().add(vbox);
	    hbox.setSpacing(50);
	    hbox.setPadding(new Insets(20, 10, 10, 20));
	    
	    lightHBox.getChildren().add(lightVbox);
	    lightHBox.setSpacing(50);
	    lightHBox.setLayoutX(150);
	    lightHBox.setLayoutY(50);
	    lightHBox.setPadding(new Insets(0, 0, 1, 1));
	    
	    
	    
	    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		      public void changed(ObservableValue<? extends Toggle> ov,
		          Toggle old_toggle, Toggle new_toggle) {
		        if (group.getSelectedToggle() != null) {
		        	if(group.getSelectedToggle().getUserData().toString() == "RED") {
		        		redCircle.setFill(Color.RED);
		        		yellowCircle.setFill(null);
		        		greenCircle.setFill(null);
		        	} else if(group.getSelectedToggle().getUserData().toString() == "YELLOW") {
		        		yellowCircle.setFill(Color.YELLOW);
		        		redCircle.setFill(null);
		        		greenCircle.setFill(null);
		        	} else {
		        		greenCircle.setFill(Color.GREEN);
		        		yellowCircle.setFill(null);
		        		redCircle.setFill(null);
		        	}
		        }
		      }
		    });
	    lightHBox.setStyle("-fx-background-color: #000000");
	    ((Group) scene.getRoot()).getChildren().add(hbox);
	    ((Group) scene.getRoot()).getChildren().add(lightHBox);
	    stage.setTitle("Exercise 16.3: Traffic Light");
	    stage.setScene(scene);
	    stage.show();
	  }
}


