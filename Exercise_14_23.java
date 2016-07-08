/* Jameson Treu
 * jst86520
 */
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

public class Exercise_14_23 extends Application{	
   /* Global Variables */
	static int x1, x2,
   			   y1, y2,
   			   width1, width2,
   			   height1, height2;
	static String message;

    public static void main(String[] args) {
	    launch(args);
	}
    
    @Override
	public void start(Stage primaryStage) {
    	Scanner in = new Scanner(System.in);
	    
	    System.out.println("*****Enter Details for Rectangle 1*****");
	    System.out.print("Center Co-ordinate 'X': ");
	    x1 = in.nextInt();
	    System.out.print("Center Co-ordinate 'Y': ");
	    y1 = in.nextInt();
	    
	    System.out.print("Width: ");
	    width1 = in.nextInt();
	    System.out.print("Height: ");
	    height1 = in.nextInt();
	
	    System.out.println("*****Enter Details for Rectangle 2***** ");
	    System.out.print("Center Co-ordinate 'X': ");
	    x2 = in.nextInt();
	    System.out.print("Center Co-ordinate 'Y': ");
	    y2 = in.nextInt();
	    
	    System.out.print("Width: ");
	    width2 = in.nextInt();
	    System.out.print("Height: ");
	    height2 = in.nextInt();
	
	    in.close();
	
	    if(x2 > x1+width1 && y2 > y1+height1){
	    	message = "The rectangles do not overlap";
	    }
	    else if(x1 > x2+width2 && y1 > y2+height2){
	        message = "The rectangles do not overlap";
	    }
	    else if(x2 > x1 && x2+width2 < x1+width1){
	        message = "One rectangle is contained in another";
	    }
	    else if(x1 > x2 && x1+width1 < x2+width2){
	        message = "One rectangle is contained in another";
	    }
	    else {
	        message = "The rectangles overlap";
	    }
    	
		Pane pane = new Pane();
		
		Rectangle rec1 = new Rectangle(x1, y1, width1, height1);
        Rectangle rec2 = new Rectangle(x2, y2, width2, height2);
        rec1.setStroke(Color.BLACK);
        rec2.setStroke(Color.BLACK);
        rec1.setFill(null);
        rec2.setFill(null);
        pane.getChildren().add(rec1);
        pane.getChildren().add(rec2);
        pane.getChildren().add(new Text(10, 475, message));
        
        /* Create a scene and place it in the stage */
    	Scene scene = new Scene(pane, 1000, 500);
    	primaryStage.setTitle("Exercise 14.23"); // Set the stage title
    	primaryStage.setScene(scene); // Place the scene in the stage
    	primaryStage.show(); // Display the stage
	}
}
