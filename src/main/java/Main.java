import gui.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Logic;

public class Main extends Application {

    Logic TDDTLogic;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader rootloader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = rootloader.load();
        primaryStage.setTitle("TDDT");
        primaryStage.setScene(new Scene(root, 1025, 750));
        primaryStage.show();
        TDDTLogic = new Logic(rootloader.getController());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
