import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Exercise_16_9 extends Application{
	double x1, y1,
           width1, height1 = 0;

	double x2, y2,
           width2, height2 = 0;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	  public void start(Stage stage) {
		VBox vBoxLabels1 = new VBox();
		VBox vBoxText1 = new VBox();
		VBox vBoxLabels2 = new VBox();
		VBox vBoxText2 = new VBox();
		
		HBox hBox1 = new HBox();
		HBox hBox2 = new HBox();
		
		stage.setWidth(462);
	    stage.setHeight(430);
	    
	    final Rectangle rec1 = new Rectangle();
		final Rectangle rec2 = new Rectangle();
		rec1.setFill(null);
        rec1.setStroke(Color.BLACK);
        rec2.setFill(null);
        rec2.setStroke(Color.BLACK);
		
	    final Label lblDisplay = new Label("Two Rectangles Intersect?: ");
	    lblDisplay.setLayoutX(160);
	    
	    Label lblEnterRec1 = new Label("Enter Rectangle 1 info:");
	    Label lblX1 = new Label("X:");
	    Label lblY1 = new Label("Y:");
	    Label lblWidth1 = new Label("Width:");
	    Label lblHeight1 = new Label("Height:");
		
	    Label lblEnterRec2 = new Label("Enter Rectangle 2 info:");
	    Label lblX2 = new Label("X:");
	    Label lblY2 = new Label("Y:");
	    Label lblWidth2 = new Label("Width:");
	    Label lblHeight2 = new Label("Height:");
	    
	    final TextField txtX1 = new TextField();
	    final TextField txtY1 = new TextField();
	    final TextField txtWidth1 = new TextField();
	    final TextField txtHeight1 = new TextField();
		
	    final TextField txtX2 = new TextField();
	    final TextField txtY2 = new TextField();
	    final TextField txtWidth2 = new TextField();
	    final TextField txtHeight2 = new TextField();
		
		Button btnRedrawRectangles = new Button("Redraw Rectangles");
		btnRedrawRectangles.setLayoutX(165);
		btnRedrawRectangles.setLayoutY(370);
		
		vBoxLabels1.setSpacing(8);
		vBoxLabels2.setSpacing(8);
	    
		lblEnterRec1.setLayoutX(40);
		lblEnterRec1.setLayoutY(220);
		hBox1.setLayoutY(240);
		hBox1.setSpacing(10);
		hBox1.setPadding(new Insets(20, 10, 10, 10));
		
		lblEnterRec2.setLayoutX(270);
		lblEnterRec2.setLayoutY(220);
		hBox2.setLayoutX(230);
		hBox2.setLayoutY(240);
		hBox2.setSpacing(10);
		hBox2.setPadding(new Insets(20, 10, 10, 10));
		
		hBox1.setStyle("-fx-border-color: #000000");
		hBox2.setStyle("-fx-border-color: #000000");
		
		vBoxLabels1.getChildren().addAll(lblX1, lblY1, lblWidth1, lblHeight1);
		vBoxText1.getChildren().addAll(txtX1, txtY1, txtWidth1, txtHeight1);
		hBox1.getChildren().addAll(vBoxLabels1, vBoxText1);
		
		vBoxLabels2.getChildren().addAll(lblEnterRec2, lblX2, lblY2, lblWidth2, lblHeight2);
		vBoxText2.getChildren().addAll(txtX2, txtY2, txtWidth2, txtHeight2);
		hBox2.getChildren().addAll(vBoxLabels2, vBoxText2);
	    
		Scene scene = new Scene(new Group());
		((Group) scene.getRoot()).getChildren().addAll(lblEnterRec1, hBox1, lblEnterRec2, 
				                                       hBox2, lblDisplay, btnRedrawRectangles,
				                                       rec1, rec2);
		/* Listeners */
		btnRedrawRectangles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                x1 = new Double(Double.valueOf(txtX1.getText()));
                y1 = new Double(Double.valueOf(txtY1.getText()));
                width1 = new Double(Double.valueOf(txtWidth1.getText()));
                height1 = new Double(Double.valueOf(txtHeight1.getText()));
                
                x2 = new Double(Double.valueOf(txtX2.getText()));
                y2 = new Double(Double.valueOf(txtY2.getText()));
                width2 = new Double(Double.valueOf(txtWidth2.getText()));
                height2 = new Double(Double.valueOf(txtHeight2.getText()));
                
                rec1.setX(x1);
                rec1.setY(y1);
                rec1.setWidth(width1);
                rec1.setHeight(height1);
                
                
                rec2.setX(x2);
                rec2.setY(y2);
                rec2.setWidth(width2);
                rec2.setHeight(height2);
                
                if(rec1.intersects(x2, y2, width2, height2)) {
                	lblDisplay.setText("Two Rectangles Intersect?: Yes");
                } else {
                	lblDisplay.setText("Two Rectangles Intersect?: No");
                }
            }
        });
		
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent e) {
		    	if(rec1.contains(e.getSceneX(), e.getSceneY())) {
		    		rec1.setX(e.getSceneX() - (width1 / 2));
		    		rec1.setY(e.getSceneY() - (height1 / 2));
		    		
		    		x1 = rec1.getX();
		    		y1 = rec1.getY();
		    		
		    		txtX1.setText(String.valueOf(x1));
		    		txtY1.setText(String.valueOf(y2));
		    	}
		    	if(rec2.contains(e.getSceneX(), e.getSceneY())) {
		    		rec2.setX(e.getSceneX() - (width2 / 2));
		    		rec2.setY(e.getSceneY() - (height2 / 2));
		    		
		    		x2 = rec2.getX();
		    		y2 = rec2.getY();
		    		
		    		txtX2.setText(String.valueOf(x2));
		    		txtY2.setText(String.valueOf(y2));
		    	}
		    	
		    	if(rec1.intersects(x2, y2, width2, height2)) {
                	lblDisplay.setText("Two Rectangles Intersect?: Yes");
                } else {
                	lblDisplay.setText("Two Rectangles Intersect?: No");
                }
		    	
		    	if(rec2.intersects(x1, y1, width1, height1)) {
                	lblDisplay.setText("Two Rectangles Intersect?: Yes");
                } else {
                	lblDisplay.setText("Two Rectangles Intersect?: No");
                }
		    }
		});
		
	    stage.setScene(scene);
	    stage.setTitle("Exercise 16_9");
	    stage.show();
	  }
}


