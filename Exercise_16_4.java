import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Exercise_16_4 extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	  public void start(Stage stage) {
	    Label lblMile = new Label("Mile");
	    Label lblKilometer = new Label("Kilometer");
	    stage.setWidth(400);
	    stage.setHeight(400);
	    
	    final TextField txtMile = new TextField();
	    final TextField txtKilo = new TextField();
	    
	    VBox vBox = new VBox();
	    VBox txtBox = new VBox();
	    HBox hBox = new HBox();
	    HBox hTxtBox = new HBox();
	    hTxtBox.setLayoutX(100);
	    hTxtBox.setLayoutY(20);
	    
	    vBox.getChildren().add(lblMile);
	    vBox.getChildren().add(lblKilometer);
	    vBox.setSpacing(10);
	    
	    txtBox.getChildren().add(txtMile);
	    txtBox.getChildren().add(txtKilo);
	    vBox.setSpacing(10);
	    
	    hBox.getChildren().add(vBox);
	    hTxtBox.getChildren().add(txtBox);
	    hBox.setSpacing(50);
	    hBox.setPadding(new Insets(20, 10, 10, 20));
	    
	    txtMile.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	Double value = Double.valueOf(txtMile.getText()) * 1.60934;
	                txtKilo.setText(String.valueOf(value));
	            }
	        }
	    });
	    
	    txtKilo.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent key)
	        {
	            if (key.getCode().equals(KeyCode.ENTER))
	            {
	            	Double value = Double.valueOf(txtKilo.getText()) * 0.621371;
	                txtMile.setText(String.valueOf(value));
	            }
	        }
	    });
	    
		Scene scene = new Scene(new Group());
		((Group) scene.getRoot()).getChildren().addAll(hBox, hTxtBox);
		stage.setTitle("Exercise 16_4");
	    stage.setScene(scene);
	    stage.show();
	  }
}


