/* Jameson Treu
* a program that calculates the future value of an investment at a given interest rate for a specified number of
  years.
* NOTE: Possible arithmetic error, check values against other investment calculators before using in a real
        setting. If errors occur, modify program accordingly or use a better investment calculator, of which there
        are infinitely many
*/
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Exercise_15_5 extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	// futureValue = investmentAmount * (1 + monthlyInterestRate)^years*12
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane gridPane = new GridPane();
		
		Label lblinvestmentAmount = new Label("Investment Amount: ");
		Label lblnumYears = new Label("Number of years: ");
		Label lblannualInterestRate = new Label("Annual interest Rate: ");
		final TextField investmentAmount = new TextField();
		final TextField numYears = new TextField();
		final TextField annualInterestRate = new TextField();
		final TextField display = new TextField();
		display.setEditable(false);
		
		Button submit = new Button("Submit");
		
		gridPane.getColumnConstraints().add(new ColumnConstraints(200)); // column 1 is 200 wide
		gridPane.getRowConstraints().add(new RowConstraints(200)); // column 2 is 200 wide
		
		gridPane.add(lblinvestmentAmount, 0, 1);
		gridPane.add(lblnumYears, 0, 2);
		gridPane.add(lblannualInterestRate, 0, 3);
		
		gridPane.add(investmentAmount, 1, 1);
		gridPane.add(numYears, 1, 2);
		gridPane.add(annualInterestRate, 1, 3);
		gridPane.add(submit, 1, 4);
		gridPane.add(display, 0, 5);
		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				double futureValue;
				futureValue = Double.valueOf(investmentAmount.getText()) 
						      * Math.pow((1 + Double.valueOf(annualInterestRate.getText())), 
						      Double.valueOf(numYears.getText())*12);
				display.appendText(String.valueOf(futureValue));
			}
		});
		
		Scene scene = new Scene(gridPane, 500, 500);
		primaryStage.setTitle("Exercise 15_5");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
