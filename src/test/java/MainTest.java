import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainTest extends Application{ 
    /*
     * For test compile only
     * 
     * (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
   @Override 
   public void start(Stage primaryStage) throws Exception{
	   URL location = getClass().getResource("main.fxml");
	   FXMLLoader loader = new FXMLLoader(location); 
       Parent root = (Parent) loader.load();
       
       primaryStage.setTitle("Something Normal - 2016"); 
       primaryStage.setScene(new Scene(root)); 
       primaryStage.show(); 
       primaryStage.setResizable(false);
       
       
       
   } 

   public static void main(String[] args) { 
       launch(args); 
   } 
} 